package com.example.demo.Model;

public class Language {
    private String languageName;
    private int scoreOutof100;

    // Getters and setters

    public Language() {
        // Default constructor for Jackson deserialization
    }

    public Language(String languageName, int scoreOutof100) {
        this.languageName = languageName;
        this.scoreOutof100 = scoreOutof100;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getScoreOutof100() {
        return scoreOutof100;
    }

    public void setScoreOutof100(int scoreOutof100) {
        this.scoreOutof100 = scoreOutof100;
    }
}
