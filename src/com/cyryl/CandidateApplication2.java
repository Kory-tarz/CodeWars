package com.cyryl;

import java.util.Arrays;
import java.util.List;

public class CandidateApplication2 {
    public CandidateApplication2() {
    }

    public enum OfficeLocation {
        WARSAW, LUBLIN, POZNAN, KRAKOW, WROCLAW, BIALYSTOK, GDANSK, LODZ, SZCZECIN,
        BARCELONA, VALENCIA, PARIS,
        TOKYO, KUALA_LUMPUR, NETHERLANDS
    }

    public static final int ASSESSMENT_PASSING_SCORE = 75;
    public static final String REQUIRED_LANGUAGE = "english";
    public static final int REQUIRED_EXPERIENCE_IN_POLAND = 3;
    public static final int REQUIRED_EXPERIENCE_SPAIN_AND_FRANCE = 5;
    public static final int REQUIRED_EXPERIENCE_OTHER_LOCATIONS = 7;
    private int experience;
    private OfficeLocation officeLocation;
    private String name;
    private String surname;
    private String email;
    private List<String> skills;
    private List<String> languages;
    private int assessmentScore;


    public OfficeLocation getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(OfficeLocation officeLocation) {
        this.officeLocation = officeLocation;
    }

    public boolean process() {
        validateData();
        boolean approved = meetsGeneralHiringRequirements() && hasRequiredExperienceForAppliedOfficeLocation();
        if (approved) {
            sendApprovedEmail(email);
        } else {
            sendRejectedEmail(email);
        }
        return approved;
    }

    private void validateData() {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Missing candidate information.");
        if (surname == null || surname.isEmpty())
            throw new IllegalArgumentException("Missing candidate information.");
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Missing email address.");
    }

    private boolean meetsGeneralHiringRequirements() {
        return hasOneOfRequiredSkills() && knowsRequiredLanguage() && passedAssessment();
    }

    private boolean hasOneOfRequiredSkills() {
        List<String> requiredSkills = Arrays.asList("java", "C#", "Gosu");

        return skills != null &&
                skills.stream().anyMatch(requiredSkills::contains);
    }

    private boolean knowsRequiredLanguage() {
        return languages != null && languages.contains(REQUIRED_LANGUAGE);
    }

    private boolean passedAssessment() {
        return assessmentScore > ASSESSMENT_PASSING_SCORE;
    }

    private boolean hasRequiredExperienceForAppliedOfficeLocation() {
        int requiredExperience = 0;
        switch (officeLocation) {
            case WARSAW:
            case LUBLIN:
            case POZNAN:
            case KRAKOW:
            case WROCLAW:
            case BIALYSTOK:
            case GDANSK:
            case LODZ:
            case SZCZECIN:
                requiredExperience = REQUIRED_EXPERIENCE_IN_POLAND;
                break;

            case BARCELONA:
            case VALENCIA:
            case PARIS:
                requiredExperience = REQUIRED_EXPERIENCE_SPAIN_AND_FRANCE;
                break;
            case TOKYO:
            case KUALA_LUMPUR:
            case NETHERLANDS:
                requiredExperience = REQUIRED_EXPERIENCE_OTHER_LOCATIONS;
                break;
        }
        return experience >= requiredExperience;
    }

    private void sendApprovedEmail(String email) {
        String template = "Candidate approved email template";
        //placeholder code for building an approved application email template
        sendEmail(email, template);
    }

    private void sendRejectedEmail(String email) {
        String template = "Candidate rejected email template";
        //placeholder code for building a rejected application email template
        sendEmail(email, template);
    }

    private void sendEmail(String email, String template) {
        //placeholder for sending email logic
    }


}
