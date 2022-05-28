package com.cyryl.kyu3;

import java.util.*;

public class Inspector {

    Set<String> allowedNations;
    Map<String, Set<String>> requiredDocuments;
    Map<String, Set<String>> requiredVaccination;
    String wantedCriminal;
    String homeNation = "Arstotzka";
    String[] foreignNations = { "Antegria", "Impor", "Kolechia", "Obristan", "Republia", "United Federation"};
    String[] allNations = {"Arstotzka", "Antegria", "Impor", "Kolechia", "Obristan", "Republia", "United Federation"};
    final String VERIFICATION_MSG = "VERIFIED";
    final String CRIMINAL = "Entrant is a wanted criminal.";
    final String WELCOME_CITIZEN = "Glory to Arstotzka.";
    final String WELCOME_FOREIGNER = "Cause no trouble.";
    final String BANNED_NATION = "citizen of banned nation.";
    final String DETAINED = "Detainment: ";
    final String DENIED = "Entry denied: ";

    private final int NUMBER_OF_COUNTRIES = 7;

    public Inspector(){
        allowedNations = new HashSet<>(NUMBER_OF_COUNTRIES);

        requiredDocuments = new HashMap<>();
        requiredVaccination = new HashMap<>();
        for(String nation : allNations) {
            requiredDocuments.put(nation, new HashSet<>());
            requiredVaccination.put(nation, new HashSet<>());
        }
        requiredDocuments.put("Workers", new HashSet<>());
    }

    public String inspect(Map<String, String> person) {

        for(String info : person.keySet()){
            System.out.println(info);
            System.out.println(person.get(info));
            System.out.println("------");
        }

        Person personalData = new Person();

        String verification = verifyRequiredDocuments(personalData, person);

        if(verification.equals(VERIFICATION_MSG))
            verification = checkPersonRequirements(personalData, person);

        if(verification.equals(VERIFICATION_MSG))
            verification = checkVaccination(personalData, person);

        if(verification.equals(VERIFICATION_MSG))
            verification = personalData.peronData.get("NATION").equals(homeNation) ? WELCOME_CITIZEN : WELCOME_FOREIGNER;

        return verification.replaceAll("_", " ");
    }

    private String checkVaccination(Person personalData, Map<String, String> person) {
        String nation = personalData.peronData.get("NATION");
        List<String> vaccineList = null;
        if(person.containsKey("certificate_of_vaccination"))
            vaccineList = Arrays.asList(getTargetedGroups(readDocument(person.get("certificate_of_vaccination")).get("VACCINES")));

        System.out.println(vaccineList);
        for(String vaccine : requiredVaccination.get(nation)){
            System.out.println(vaccine);

            if(vaccineList == null)
                return DENIED + "missing required certificate of vaccination.";

            if(!vaccineList.contains(vaccine))
                return DENIED + "missing required vaccination.";
        }
        return VERIFICATION_MSG;
    }

    public String verifyRequiredDocuments(Person personalData, Map<String, String> person){
        Map<String, String> documentData;
        String inspectionResult;

        // criminal check
        for(String document: person.keySet()){
            documentData = readDocument(person.get(document));
            if(isWantedCriminal(documentData.get("NAME")))
                return DETAINED + CRIMINAL;
        }
        // detention check
        for(String document: person.keySet()){
            documentData = readDocument(person.get(document));

            inspectionResult = personalData.verifyAndUpdatePersonData(documentData);
            if(!inspectionResult.equals(VERIFICATION_MSG)){
                return DETAINED + processResult(inspectionResult) + " mismatch.";
            }
        }
        // denial check - maybe in other function to check only required documents
        for(String document: person.keySet()) {
            documentData = readDocument(person.get(document));

            if (documentData.containsKey("EXP") && !isExpireDateValid(documentData.get("EXP")))
                return DENIED + document + " expired.";
        }

        return VERIFICATION_MSG;

    }

    private String processResult(String result){
        switch (result){
            case ("NATION"): return "nationality";
            case ("NAME"): return "name";
            default: return result;
        }
    }

    public String checkPersonRequirements(Person personalData, Map<String, String> person){
        String nation = personalData.peronData.get("NATION");
        if(nation == null)
            return DENIED + "missing required passport.";

        if(!isValidNation(nation))
            return DENIED + BANNED_NATION;

        String documentsCheck = hasAllRequiredDocuments(person, nation);
        if(!documentsCheck.equals(VERIFICATION_MSG))
            return documentsCheck;

        if(personalData.peronData.containsKey("PURPOSE")){
            if(personalData.peronData.get("PURPOSE").equals("WORK")){
                documentsCheck = hasAllRequiredDocuments(person, "Workers");
                if(!documentsCheck.equals(VERIFICATION_MSG))
                    return documentsCheck;
            }
        }

        return VERIFICATION_MSG;

    }

    public boolean isValidNation(String nation){
//        if(nation.equals(homeNation))
//           return true;
        return allowedNations.contains(nation);
    }


    public String hasAllRequiredDocuments(Map<String, String> person, String nation){

        if(requiredDocuments.get(nation).isEmpty())
            return VERIFICATION_MSG;

        for(String required : requiredDocuments.get(nation)){
            if(!person.containsKey(required)) {
                if(required.equals("access_permit")){
                    if (person.containsKey("grant_of_asylum"))
                        continue;
                    if (person.containsKey("diplomatic_authorization")) {
                        if (isDiplomaticAuthorizationValid(person))
                            continue;
                        else
                            return DENIED + "invalid diplomatic authorization.";
                    }
                }
                return DENIED + "missing required " + required + ".";
            }
        }
        return VERIFICATION_MSG;
    }

    private boolean isDiplomaticAuthorizationValid(Map<String, String> person){
        Map<String, String> auth = readDocument(person.get("diplomatic_authorization"));
        String[] authCountries = getTargetedGroups(auth.get("ACCESS"));
        return Arrays.asList(authCountries).contains(homeNation);
    }

    public Map<String, String> readDocument(String data){
        String[] elements = data.split("\\n");
        Map<String, String> documentData = new HashMap<>(elements.length);
        String[] descValue;

        for(String element : elements){
            descValue = element.split(": ");
            documentData.put(descValue[0], descValue[1]);
        }

        return documentData;
    }

    public boolean isExpireDateValid(String expDate){
        final int YEAR = 1982;
        final int MONTH = 11;
        final int DAY = 22;
        String[] date = expDate.split("\\.");

        int compare;

        if((compare = Integer.compare(Integer.parseInt(date[0]), YEAR)) == 0)
            if((compare = Integer.compare(Integer.parseInt(date[1]), MONTH)) == 0)
                compare = Integer.compare(Integer.parseInt(date[2]), DAY);

        return compare > 0;
    }

    public boolean isWantedCriminal(String name){
        String[] nameSurname = name.split(", ");
        return wantedCriminal.equals(nameSurname[1] + " " + nameSurname[0]);
    }

    public void receiveBulletin(String bulletin) {

        clearCriminal();
        String[] bulletinLines = bulletin.split("\\n");

        for(String bulletinLine : bulletinLines){
            System.out.println(bulletin);
            boolean success = checkAndUpdateRequirements(bulletinLine) ||
                    checkAndUpdateNation(bulletinLine) ||
                    checkAndUpdateCriminal(bulletinLine);
            if(!success) throw new IllegalArgumentException();
        }
    }

    private void clearCriminal() {
        wantedCriminal = "";
    }

    public void updateRequirements(String bulletin, Map<String, Set<String>> requiredData){

        String[] data = bulletin.replaceAll("no longer ", "").split(" require ");
        String[] who = getTargetedGroups(data[0]);
        String requirement = data[data.length-1].replaceAll(" ", "_");

        if(who[0].equals("Entrants"))
            who = allNations;
        else if(who[0].equals("Foreigners"))
            who = foreignNations;

        // workers can they be after coma?

        if(bulletin.contains("no longer"))
            removeRequirement(who, requirement, requiredData);
        else
            addRequirement(who, requirement, requiredData);
    }

    public boolean checkAndUpdateCriminal(String bulletin){
        String wantedTemplate = "Wanted by the State: ";
        if(bulletin.startsWith(wantedTemplate))
            wantedCriminal = bulletin.substring(wantedTemplate.length());
        else
            return false;
        return true;
    }

    public boolean checkAndUpdateRequirements(String bulletin){

        if(bulletin.contains("require") && !bulletin.endsWith("vaccination"))
            updateRequirements(bulletin, requiredDocuments);
        else if(bulletin.contains("require") && bulletin.endsWith("vaccination"))
            updateRequirements(bulletin.replaceAll(" vaccination", ""), requiredVaccination);
        else
            return false;
        return true;

    }

    private String[] getTargetedGroups(String groups){
        return groups.replaceAll("(?i)citizens of ", "").split(", ");
    }

    public void removeRequirement(String[] who, String requirement, Map<String, Set<String>> requiredData){
        for(String group : who){
            if(requiredData.containsKey(group))
                requiredData.get(group).remove(requirement);
        }
    }

    public void addRequirement(String[] who, String requirement, Map<String, Set<String>> requiredData){
        for(String group: who){
            requiredData.putIfAbsent(group, new HashSet<>());
            requiredData.get(group).add(requirement);
        }
    }

    public boolean checkAndUpdateNation(String bulletin){
        if (bulletin.startsWith("Allow "))
            addAllowedNations(bulletin.substring(6), allowedNations);
        else if(bulletin.startsWith("Deny "))
            removeAllowedNations(bulletin.substring(5));
        else
            return false;
        return true;
    }

    public void removeAllowedNations(String bulletin){
        String[] nations = getTargetedGroups(bulletin);
        Arrays.asList(nations).forEach(allowedNations::remove);
    }

    public void addAllowedNations(String bulletin, Set<String> nationSet){
        String[] nations = getTargetedGroups(bulletin);
        nationSet.addAll(Arrays.asList(nations));
    }

    private class Person{
        Map <String, String> peronData;

        Person(){
            peronData = new HashMap<>();
        }

        public String verifyAndUpdatePersonData(Map<String, String> documentData) {
            for (String data : documentData.keySet()) {
                if(repeatableData(data))
                    continue;

                if (peronData.containsKey(data) && !peronData.get(data).equals(documentData.get(data)))
                    return processData(data);
                else
                    peronData.put(data, documentData.get(data));
            }
            return VERIFICATION_MSG;
        }

        private boolean repeatableData(String data){
            switch (data){
                case "EXP":
                    return true;
                default:
                    return false;
            }
        }

        private String processData(String data){
            if(data.equals("ID#"))
                return "ID number";
            return data;
        }
    }
}
