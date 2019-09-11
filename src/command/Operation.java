package command;

public enum Operation {
    GENERAL_INFO,
    DIESEL_RPM_DROP_TEST,
    DPC_TEST,
    FAN_DRIVE_TEST,
    PRESSURE_ANALYZE,
    MOTO_HOUR_TEST,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        if (i == null || i < 1 || i > 6) throw new IllegalArgumentException();
        return Operation.values()[i];
    }
}
