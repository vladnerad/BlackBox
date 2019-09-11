package command;

public enum Operation {
    GENERAL_INFO,
    SHOW_GENERAL_INFO,
    DIESEL_RPM_DROP_TEST,
    FAN_DRIVE_TEST,
    PRESSURE_ANALYZE,
    DPC_TEST,
    MOTO_HOUR_TEST,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        if (i == null || i < 1 || i > 4) throw new IllegalArgumentException();
        return Operation.values()[i];
    }
}
