package com.cyryl;

import java.util.Arrays;
import java.util.List;

public class CandidateApplicationOriginal {

    private int experience;
    private String officeLocation;
    private String name;
    private String surname;
    private String education;
    private String email;
    private List<String> skills;
    private List<String> languages;
    private int assessmentScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getAssessmentScore() {
        return assessmentScore;
    }

    public void setAssessmentScore(int assessmentScore) {
        this.assessmentScore = assessmentScore;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public boolean process() {
        //init variables
        boolean aprv = false; // Final approval status
        boolean ok = false; // flag to track candidate requirements status
        List<String> reqs = Arrays.asList("java", "C#", "Gosu");//list of required skills

        // Step 1: Validation of required data
        if (name != null && !name.isEmpty()) {
            if (surname != null && !surname.isEmpty()) {
                // Candidate information is not null or empty, so consider it valid
                //JIRA-1234 We no longer require higher education
                //boolean higherEducation = cd.getEducation().equals("higher");
                // Step 2: Verify skills and languages
                boolean hasSkill = false;
                if (skills != null) {
                    for (String s : reqs) {
                        if (skills.contains(s)) {
                            hasSkill = true;
                        }
                        ok = hasSkill && languages != null && languages.contains("english") && assessmentScore > 75;
                        if (ok) {
                            int exp = 0;

                            switch (officeLocation) {
                                case "Warsaw":
                                case "Lublin":
                                case "Poznan":
                                case "Krakow":
                                case "Wroclaw":
                                case "Bialystok":
                                case "Gdansk":
                                case "Łódź":
                                case "Szczecin":
                                    exp = 3;
                                    break;

                                case "Barcelona":
                                case "Valencia":
                                case "Paris":
                                    exp = 5;
                                    break;

                                case "Tokyo":
                                case "Kuala Lumpur":
                                case "Netherlands":
                                    exp = 7;
                                    break;
                            }
                            aprv = experience >= exp;
                            //sending email to candidate about application status
                            if (email != null && !email.isEmpty()) {
                                sendEmailToCandidate(email, aprv);
                            } else {
                                throw new IllegalArgumentException("Missing email address.");
                            }
                        }
                    }}
            } else {
                // Candidate information is missing, invalid!
                throw new IllegalArgumentException("Missing candidate information.");
            }
        } else {
            throw new IllegalArgumentException("Missing candidate information.");
        }

        return aprv;
    }

    private void sendEmailToCandidate(String email, boolean approved) {
        String template;
        if (approved == true) {
            //placeholder code for building an approved application email template
            template = "Candidate approved email template";
        } else {
            //placeholder code for building a rejected application email template
            template = "Candidate rejected email template";
        }
        sendEmail(email, template);
    }

    private void sendEmail(String email, String template) {
        //placeholder for sending email logic
    }
}
