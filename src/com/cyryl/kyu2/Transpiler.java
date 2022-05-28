package com.cyryl.kyu2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transpiler {

    public enum NameCheck{
        ERROR, NAME, NUMBER
    }

    public static String transpile (String expression) {
        String result;
        System.out.println(expression);

        if(!verifyParenthesis(expression))
            return "";

        String[] elements = splitExpression(expression.replaceAll("\n", " "));
        result = startTranspiling(elements);

        return result;
    }

    public static boolean verifyParenthesis(String expression){
        String trimmedExpression = expression.replaceAll("\\s", "");
        boolean openParenthesis = false;
        boolean openBracket = false;
        int countPar = 0;
        int countTotalBrackets = 0;

        for(int i=0; i<trimmedExpression.length(); i++){
            switch (trimmedExpression.charAt(i)){
                case '(':
                    if(openBracket || countPar > 0 || countTotalBrackets > 2)
                        return false;
                    openParenthesis = true;
                    countPar++;
                    break;
                case '{':
                    if(openBracket)
                        return false;
                    openBracket = true;
                    countTotalBrackets++;
                    break;
                case ')':
                    if(!openParenthesis || countPar > 1 || trimmedExpression.charAt(i-1) == ',' || openBracket)
                        return false;
                    openParenthesis = false;
                    countPar++;
                    break;
                case '}':
                    if(!openBracket)
                        return false;
                    openBracket = false;
                    countTotalBrackets++;
                    break;
                default:
                    break;
            }
        }
        return countTotalBrackets % 2 == 0 && !openParenthesis;
    }

    private static String startTranspiling(String[] elements){

        StringBuilder stringBuilder = new StringBuilder("");
        boolean functionName = false;
        boolean insideParenthesis = false;
        boolean outsideLambda = false;
        StringBuilder paramsBuilder = new StringBuilder("");
        String[] parameters;
        LambdaElements lambdaElements;
        int lambdasInARow = 0;

        for(String element : elements){

            if(element.startsWith("(")){

                insideParenthesis = true;
                stringBuilder.append("(");
                parameters = getParameters(element.substring(1));
                if(parameters == null)
                    return "";
                paramsBuilder.append(buildParams(parameters));

            }else if(element.startsWith("{")){

                if(functionName && !insideParenthesis) {
                    stringBuilder.append("(");
                    insideParenthesis = true;
                    outsideLambda = true;
                }
                if (outsideLambda)
                    lambdasInARow++;
                if(lambdasInARow>1)
                    return "";
                lambdaElements = getBracketParameters(element.substring(1));
                if(lambdaElements== null)
                    return "";
                if(insideParenthesis){
                    if(outsideLambda && !paramsBuilder.toString().isEmpty())
                        paramsBuilder.append(",");
                    paramsBuilder.append(buildLambda(lambdaElements));
                }else{
                    stringBuilder.append(buildLambda(lambdaElements));
                }

            }else if(element.startsWith(")")){
                if(element.length()>1) { // only lambda can be outside
                    return "";
                }
                outsideLambda = true;
            }else if(element.startsWith("}")){
                if(element.length()>1) {
                    String temp = element.replaceAll(" ", "");
                    if(!outsideLambda && insideParenthesis && temp.charAt(1) == ','){
                        paramsBuilder.append(","); // we except another '{' after this and it can't be ')' because it's checked at the beginning
                        if(temp.length() > 2) { // more than {,
                            parameters = getParameters(element.substring(element.indexOf(",")+1));
                            if (parameters == null)
                                return "";
                            paramsBuilder.append(buildParams(parameters));
                        }
                    }else
                        return "";
                }

                if(!insideParenthesis)
                    functionName = true;

            }else{

                if(isFunctionName(element.trim()) && stringBuilder.toString().isEmpty())
                    stringBuilder.append(element.trim());
                else
                    return "";
                functionName = true;
            }
        }
        if (!functionName)
            return "";

        if(!paramsBuilder.toString().isEmpty())
            stringBuilder.append(paramsBuilder.toString());
        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    public static String buildParams(String[] params){
        return String.join(",", params);
    }

    public static String buildLambda(LambdaElements lambdaElements){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("(");
        stringBuilder.append(String.join(",", lambdaElements.params));
        stringBuilder.append(")");
        stringBuilder.append("{");
        if(!lambdaElements.statements[0].equals(""))
            stringBuilder.append(String.join(";", lambdaElements.statements)).append(";");
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public static LambdaElements getBracketParameters(String expression){
        String lambdaRegex = "^.+\\-\\>.*$";
        Pattern pattern = Pattern.compile(lambdaRegex);
        Matcher matcher = pattern.matcher(expression);
        String[] lambdaParams = {""};
        String[] lambdaStatements = {""};
        String[] splitLambda;
        boolean error = false;


        // Lambda statement with ->
        if (matcher.matches()){
            splitLambda = expression.split("\\-\\>", -1);
            lambdaParams = getParameters(splitLambda[0]);
            if(lambdaParams == null || endsWithComa(lambdaParams))
                error = true;
            lambdaStatements = getLambdaStatements(splitLambda[1]);
            if(lambdaStatements == null)
                error = true;
        }else{ // empty lambda, or just parameters
            lambdaStatements = getLambdaStatements(expression);
            if(lambdaStatements == null)
                error = true;
        }
        if(error)
            return null;
        else{
            return new LambdaElements(lambdaParams, lambdaStatements);
        }
    }

    public static boolean endsWithComa(String[] elements){
        return elements.length > 1 && elements[elements.length-1].equals("");
    }

    public static String[] getLambdaStatements(String statement){
        if(statement.trim().isEmpty())
            return new String[]{""};
        boolean correct = true;
        String[] splitStatements = statement.trim().split("\\s+");
        for(int i=0; i<splitStatements.length; i++){
            splitStatements[i] = splitStatements[i].trim();
            if (checkName(splitStatements[i]) == NameCheck.ERROR)
                correct = false;
        }
        return correct ? splitStatements : null;
    }

    public static boolean isFunctionName(String expression){
        return checkName(expression) != NameCheck.ERROR;
    }

    public static String[] getParameters(String parameters){

        if(parameters.trim().isEmpty())
            return new String[]{""};

        String[] splitParameters = parameters.split(",", -1); // -1 to leave "" after coma
        boolean correct = true;

        for(int i=0; i<splitParameters.length; i++) {
            splitParameters[i] = splitParameters[i].trim();
            if(checkName(splitParameters[i]) == NameCheck.ERROR) {
                 if(!(i==splitParameters.length-1 && splitParameters[i].equals(""))) // coma at the end is ok
                     correct = false;
            }
        }
        return correct ? splitParameters : null;
    }

    public static NameCheck checkName(String name){
        String nameRegex = "(^[a-zA-Z_]+\\w*s*$)|(^_?\\d+$)";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);

        if (matcher.matches()){
            if (matcher.group(1) != null)
                return NameCheck.NAME;
            if (matcher.group(2) != null)
                return NameCheck.NUMBER;
        }
        return NameCheck.ERROR;
    }

    public static String[] splitExpression(String expression){
        String splitPattern = "(?=[(){}])";
        String[] splitExpression = expression.trim().split(splitPattern);
        for(int i=0; i<splitExpression.length; i++)
            splitExpression[i] = splitExpression[i].trim();

        return splitExpression;
    }

    public static class LambdaElements{
        public String[] params;
        public String[] statements;

        LambdaElements(String[] params, String[] statements){
            this.params = params;
            this.statements = statements;
        }
    }
}
