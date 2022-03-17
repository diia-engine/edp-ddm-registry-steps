package platform.qa.enums;


import lombok.Getter;

@Getter
public enum FieldType {
    RADIOBUTTON ("radiobutton"),
    CHECKBOX("checkbox"),
    INPUT ("input"),
    SELECT ("select"),
    DATETIME("datetime");

    private final String fieldType;

    FieldType(String fieldType) {
        this.fieldType = fieldType;
    }
    public String getType() {
        return this.fieldType;
    }
}
