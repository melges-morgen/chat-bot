package ru.frtk.das.dataasset;

public enum Params {

    NAME("имя", ""), SURNAME("фамилия", ""), MIDDLENAME("отчество", ""),
    GENTIVE_NAME("имя_рп", ""), GENTIVE_SURNAME("фамилия_рп", ""), GENTIVE_MIDDLENAME("отчество_рп", ""),
    PHONE_NUMBER("тлф", ""), GROUP_NUMBER("гр", "");

    private String commandValue;
    private String valueInDB;

    Params(String commandValue, String valueInDB) {
        this.commandValue = commandValue;
        this.valueInDB = valueInDB;
    }

    public static Params getParam(String param) {
        Params[] array = Params.values();
        for (int i = 0; i < array.length; i++) {
            if(array[i].commandValue.equalsIgnoreCase(param)){
                return array[i];
            }
        }
        return null;
    }

    public String getCommandValue() {
        return commandValue;
    }

    public String getValueInDB() {
        return valueInDB;
    }
}
