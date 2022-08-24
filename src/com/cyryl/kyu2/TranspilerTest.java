package com.cyryl.kyu2;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.rules.ErrorCollector;



public class TranspilerTest{

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    public void expect(String actual, String expected) {
        assertEquals(expected, actual);
        collector.checkThat (expected, CoreMatchers.equalTo (actual));
    }

    public void fromTo(String input, String expected) {
        expect(Transpiler.transpile(input), expected);
    }

    public void shouldFail(String input) {
        fromTo(input, "");
    }

    @Test
    public void testSomething() {
        fromTo("", "");
        fromTo("1()", "1()");
        fromTo("123()", "123()");
        fromTo("a()", "a()");
        fromTo("abc()", "abc()");
    }

    @Test
    public void testWhen_there_is_no_lambda() {
        fromTo("call()", "call()");
        shouldFail("%^&*(");
        shouldFail("x9x92xb29xub29bx120()!(");
        fromTo("invoke  (       a    ,   b   )", "invoke(a,b)");
        fromTo("invoke(a, b)", "invoke(a,b)");
    }

    @Test
    public void testWhen_there_are_lambda_expressions() {
        fromTo("call({})", "call((){})");
        fromTo("f({a->})", "f((a){})");
        fromTo("f({a->a})", "f((a){a;})");
    }

    @Test
    public void testWhen_lambda_expressions_aren_t_inside_brackets() {
        fromTo("call(\n){}", "call((){})");
        fromTo("invoke  (       a    ,   b   ) { } ", "invoke(a,b,(){})");
        fromTo("f(x){a->}", "f(x,(a){})");
        fromTo("f(a,b){a->a}", "f(a,b,(a){a;})");
        fromTo("run{a}", "run((){a;})");
    }

    @Test
    public void testWhen_invoking_a_lambda_directly() {
        fromTo("{}()", "(){}()");
        fromTo("{a->a}(233)", "(a){a;}(233)");
    }

    @Test
    public void testSplits() {
        Transpiler.transpile("f(a,b){a->a}");
        Transpiler.transpile("fun(a,b){xyz}");
        Transpiler.transpile("{a->a}(233)");
        // ignoring
    }

    @Test
    public void simpleNameChecking() {
        Assert.assertEquals(Transpiler.NameCheck.NAME, Transpiler.checkName("abcd"));
    }

    @Test
    public void whitespacesNameChecking() {
        Assert.assertEquals(Transpiler.NameCheck.ERROR, Transpiler.checkName("   abcd    "));
    }


    @Test
    public void correctNameWithNumbers() {
        Assert.assertEquals(Transpiler.NameCheck.NAME, Transpiler.checkName("a123"));
        Assert.assertEquals(Transpiler.NameCheck.NAME, Transpiler.checkName("a123d"));
        Assert.assertEquals(Transpiler.NameCheck.NAME, Transpiler.checkName("AAAAA1AAAA1AAA1"));
    }

    @Test
    public void simpleNumber() {
        Assert.assertEquals(Transpiler.NameCheck.NUMBER, Transpiler.checkName("123"));
        Assert.assertEquals(Transpiler.NameCheck.NUMBER, Transpiler.checkName("1"));
    }

    @Test
    public void errorNumberInFront() {
        Assert.assertEquals(Transpiler.NameCheck.ERROR, Transpiler.checkName("123asd"));
        Assert.assertEquals(Transpiler.NameCheck.ERROR, Transpiler.checkName("1a"));
        Assert.assertEquals(Transpiler.NameCheck.ERROR, Transpiler.checkName("00O0"));
    }

    @Test
    public void nameCheckingInvalidCharacters() {
        Assert.assertEquals(Transpiler.NameCheck.ERROR, Transpiler.checkName("a+s"));
        Assert.assertEquals(Transpiler.NameCheck.ERROR, Transpiler.checkName("!sda"));
        //Assert.assertEquals(Transpiler.NameCheck.ERROR, Transpiler.checkName("_123"));
        Assert.assertEquals(Transpiler.NameCheck.ERROR, Transpiler.checkName("ABBB&"));
    }

    @Test
    public void emptyNameCheck() {
        Assert.assertEquals(Transpiler.NameCheck.ERROR, Transpiler.checkName(""));
        Assert.assertEquals(Transpiler.NameCheck.ERROR, Transpiler.checkName("  "));
    }

    @Test
    public void simpleGetParameters(){
        Assert.assertArrayEquals(new String[]{"a", "b"}, Transpiler.getParameters("a,b"));
    }

    @Test
    public void emptyGetParameters(){
        Assert.assertArrayEquals(new String[]{""}, Transpiler.getParameters(""));
        Assert.assertArrayEquals(new String[]{""}, Transpiler.getParameters("   "));
    }

    @Test
    public void wrongGetParameters(){
        Assert.assertArrayEquals(null, Transpiler.getParameters("a,b c,d"));
        Assert.assertArrayEquals(null, Transpiler.getParameters(",asdf"));
    }

    @Test
    public void comaEndingGetParameters(){
        Assert.assertArrayEquals(new String[]{"abce", ""}, Transpiler.getParameters("abce,"));
    }

    @Test
    public void moreParamgGetParameters(){
        Assert.assertArrayEquals(new String[]{"abce", "fg", "ijk", "r", "h", "asdf"}, Transpiler.getParameters("abce, fg, ijk  , r,  h,  asdf"));
    }

    @Test
    public void emptySpaceBetweenComasGetParametersTest(){
        Assert.assertArrayEquals(null, Transpiler.getParameters("abce, ,ggg"));
    }

    @Test
    public void oneGetParameters(){
        Assert.assertArrayEquals(new String[]{"crazy"}, Transpiler.getParameters("crazy"));
    }

    @Test
    public void simpleSplitExpressionTest(){
        Assert.assertArrayEquals(new String[]{"(a,b", ")" }, Transpiler.splitExpression("(a,b)"));
    }

    @Test
    public void trimmingSplitExpressionTest(){
        Assert.assertArrayEquals(new String[]{"fun", "{  x  ,   y", "(", ")", "}" }, Transpiler.splitExpression("   fun  {  x  ,   y   (    )  }"));
    }

    @Test
    public void emptyGetLambdaTests(){
        Assert.assertArrayEquals(new String[]{""}, Transpiler.getLambdaStatements(""));
        Assert.assertArrayEquals(new String[]{""}, Transpiler.getLambdaStatements("   "));
    }

    @Test
    public void simpleGetLambdaTests() {
        Assert.assertArrayEquals(new String[]{"a", "b"}, Transpiler.getLambdaStatements("a b"));
    }

    @Test
    public void moreGetLambdaTests() {
        Assert.assertArrayEquals(new String[]{"argh", "b", "c","dodo","e","f"}, Transpiler.getLambdaStatements("   argh b c   dodo      e  f   "));
    }

    @Test
    public void errorsGetLambdaTest(){
        Assert.assertArrayEquals(null, Transpiler.getLambdaStatements("   argh b c ,  dodo      e  f   "));
        Assert.assertArrayEquals(null, Transpiler.getLambdaStatements("a,b,c"));
        Assert.assertArrayEquals(null, Transpiler.getLambdaStatements("ad,r"));
    }

    @Test
    public void simpleGetBracketParams(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("a->b");
        Assert.assertArrayEquals(new String[]{"a"}, lambdaElements.params);
        Assert.assertArrayEquals(new String[]{"b"}, lambdaElements.statements);
    }

    @Test
    public void noStatementsGetBracketParams(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("a->");
        Assert.assertArrayEquals(new String[]{"a"}, lambdaElements.params);
        Assert.assertArrayEquals(new String[]{""}, lambdaElements.statements);
    }

    @Test
    public void onlyParamsGetBracketsParams(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("a b c  ");
        Assert.assertArrayEquals(new String[]{""}, lambdaElements.params);
        Assert.assertArrayEquals(new String[]{"a", "b", "c"}, lambdaElements.statements);
    }

    @Test
    public void emptyGetBracketsParams(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("{".substring(1));
        Assert.assertArrayEquals(new String[]{""}, lambdaElements.params);
        Assert.assertArrayEquals(new String[]{""}, lambdaElements.statements);
    }

    @Test
    public void comaInParamsGetBracketsParams(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("a,b,");
        Assert.assertEquals(null, lambdaElements);
    }

    @Test
    public void wrongTestsGetBracketsParams(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("a,b,->detr");
        Assert.assertEquals(null, lambdaElements);
        lambdaElements = Transpiler.getBracketParameters("go,go->hey,hey");
        Assert.assertEquals(null, lambdaElements);
        lambdaElements = Transpiler.getBracketParameters("->hola, hajlo");
        Assert.assertEquals(null, lambdaElements);
    }

    @Test
    public void moreGetBracketsParams(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("alfa,   beta, omega   -> centauri proxima    hades");
        Assert.assertArrayEquals(new String[]{"alfa", "beta", "omega"}, lambdaElements.params);
        Assert.assertArrayEquals(new String[]{"centauri", "proxima", "hades"}, lambdaElements.statements);
    }

    @Test
    public void onlyStmtBuildLambda(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("   halo   hura hey");
        assert lambdaElements != null;
        Assert.assertEquals("(){halo;hura;hey;}", Transpiler.buildLambda(lambdaElements));
    }

    @Test
    public void simpleBuildLambda(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("a->b");
        assert lambdaElements != null;
        Assert.assertEquals("(a){b;}", Transpiler.buildLambda(lambdaElements));
    }

    @Test
    public void emptyStmtBuildLambda(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("a->");
        assert lambdaElements != null;
        Assert.assertEquals("(a){}", Transpiler.buildLambda(lambdaElements));
    }

    @Test
    public void emptyLambdaBuildLambda(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("");
        assert lambdaElements != null;
        Assert.assertEquals("(){}", Transpiler.buildLambda(lambdaElements));
    }

    @Test
    public void moreBuildLambda(){
        Transpiler.LambdaElements lambdaElements = Transpiler.getBracketParameters("a  , b -> ergo  sum");
        assert lambdaElements != null;
        Assert.assertEquals("(a,b){ergo;sum;}", Transpiler.buildLambda(lambdaElements));
    }

    @Test
    public void simpleBuildParams(){
        Assert.assertEquals("a,b,cedr", Transpiler.buildParams(new String[]{"a", "b", "cedr"}));
    }

    @Test
    public void comaBuildParams(){
        Assert.assertEquals("a,b,", Transpiler.buildParams(new String[]{"a", "b", ""}));
    }

    @Test
    public void simpleVerifyParenthesis(){
        Assert.assertEquals(true, Transpiler.verifyParenthesis("fun(a) {}"));
    }

    @Test
    public void manyVerifyParenthesis(){
        Assert.assertEquals(true, Transpiler.verifyParenthesis("{}(a   ){}{   }{a   ->b}"));
    }

    @Test
    public void wrongVerifyParenthesis(){
        Assert.assertEquals(false, Transpiler.verifyParenthesis("fun({}a){}{}{a->b})"));
        Assert.assertEquals(false, Transpiler.verifyParenthesis("{}(a,b,){}"));
        Assert.assertEquals(false, Transpiler.verifyParenthesis("grzyby(a, {a->bc)}"));
        Assert.assertEquals(false, Transpiler.verifyParenthesis("summer(a,b,{x)"));
        Assert.assertEquals(false, Transpiler.verifyParenthesis("ajaja (x,yy})"));
        Assert.assertEquals(false, Transpiler.verifyParenthesis("fun{}a){}{}{a->b}"));
    }

    @Test
    public void nextLineTranspile(){
        String input = "f(x,y){a,b->a \nb}";
        Assert.assertEquals("f(x,y,(a,b){a;b;})", Transpiler.transpile(input));
    }

    @Test
    public void paramOutsideTranspileTest(){
        String input = "f(12,ab)c";
        Assert.assertEquals("", Transpiler.transpile(input));
    }

    @Test
    public void twoLambdaInsideTranspileTest(){
        String input = "invoke({},{})";
        Assert.assertEquals("invoke((){},(){})", Transpiler.transpile(input));
    }
    @Test
    public void underLULscoreTranspileTest(){
        String input = "f({_->})";
        Assert.assertEquals("f((_){})", Transpiler.transpile(input));
    }

    @Test
    public void underLULscoreCheckName(){
        Assert.assertEquals(Transpiler.NameCheck.NAME, Transpiler.checkName("_"));
    }


    @Test
    public void whyFailTranspileTest(){
        String input = "    { a   -> a }(cde,y,z){x,y,d -> stuff}  ";
        Assert.assertEquals("(a){a;}(cde,y,z,(x,y,d){stuff;})", Transpiler.transpile(input));
    }

    @Test
    public void randomTranspileTest(){
        String input = "zGt (OYe1pnL,  {37 ,  8037  , 2,  Dwk , 816394->469654     Wxic  5689509    Q    29}  ,{K_TYr  ,719371 ,  hSfwZG  , i  ,  10016381 ,  Np  , FTSUO_G-> oL0xxJp     87   tKJR    8    80 }, TjFroVI  , vhIra)";
        Assert.assertEquals("zGt(OYe1pnL,(37,8037,2,Dwk,816394){469654;Wxic;5689509;Q;29;},(K_TYr,719371,hSfwZG,i,10016381,Np,FTSUO_G){oL0xxJp;87;tKJR;8;80;},TjFroVI,vhIra)", Transpiler.transpile(input));
    }

    @Test
    public void lambdasInRowTranspileTest(){
        String input = "{}{}{}";
        Assert.assertEquals("", Transpiler.transpile(input));
    }

}