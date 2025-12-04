package com.cyryl;

import java.util.Arrays;
import java.util.List;

public class CandidateApplication {

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

    private boolean isValidApplication() {
        return name != null && !name.isEmpty() &&
                surname != null && !surname.isEmpty() &&
                email != null && !email.isEmpty();
    }

    private boolean hasRequiredSkills() {
        List<String> requiredSkills = Arrays.asList("java", "C#", "Gosu");
        return skills.stream().anyMatch(requiredSkills::contains) &&
                languages != null && languages.contains("english")
                && assessmentScore > 75;

    }

    private boolean hasExperience() {
        int requriedExperience = 0;

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
                requriedExperience = 3;
                break;

            case "Barcelona":
            case "Valencia":
            case "Paris":
                requriedExperience = 5;
                break;

            case "Tokyo":
            case "Kuala Lumpur":
            case "Netherlands":
                requriedExperience = 7;
                break;
        }
        return experience >= requriedExperience;
    }

    public boolean verify() {
        if (!isValidApplication()) {
            throw new IllegalArgumentException("Missing candidate information.");
        }
        boolean isApproved = hasRequiredSkills() && hasExperience();
        if (isApproved) {
            sendApprovalEmailToCandidate(email);
        } else {
            sendRejectionEmailToCandidate(email);
        }
        return isApproved;
    }

    private void sendApprovalEmailToCandidate(String email) {
        String template = "Candidate approved email template";
        sendEmail(email, template);
    }

    private void sendRejectionEmailToCandidate(String email) {
        String template = "Candidate approved email template";
        sendEmail(email, template);
    }

    private void sendEmail(String email, String template) {
        //placeholder for sending email logic
    }
}