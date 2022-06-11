package com.sebasu.journey.util;

public class MultiLanguageString {

    enum langCodes{
        EN,
        ES
    }

    String enDescription;
    String esDescription;

    public MultiLanguageString(String enDescription, String esDescription) {
        this.enDescription = enDescription;
        this.esDescription = esDescription;
    }

    public String getValueForLang(langCodes langCode) {
        switch (langCode) {
            case ES:
                return esDescription;
            default:
                return enDescription;
        }
    }

    public String exportToDBString() {
        return "<EN>" + enDescription + "</EN><ES>" + esDescription+ "</ES>";
    }

    public void importFromDBString(String dbString) {
        String[] split = dbString.split("<EN>");
        String en = split[1].split("</EN>")[0];
        split = split[1].split("<ES>");
        String es = split[1].split("</ES>")[0];
        this.enDescription = en;
        this.esDescription = es;
    }
}
