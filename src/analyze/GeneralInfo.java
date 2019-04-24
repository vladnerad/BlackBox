package analyze;

import rawdata.ParameterNumber;

public class GeneralInfo {

    private Integer[][] rawValues;

    public GeneralInfo(Integer[][] rawValues) {
        this.rawValues = rawValues;
    }

    private int getDiselStartQuant(){
        int counter = 0;
        for (Integer[] rawValue : rawValues) {
            if (rawValue[0] == 1) {
                counter++;
            }
        }
        return counter;
    }

    private int getMaxFromColumn(ParameterNumber column) {
        return max(column.ordinal());
    }

    private int max(int column){
        int max = 0;
        for (Integer[] rawValue : rawValues) {
            int currentValue = rawValue[column];
            if (currentValue > max) {
                max = currentValue;
            }
        }
        return max;
    }

    private String getWholeRecTime(){
        return getRecordingTime(rawValues.length);
    }

    private String getRecordingTime(int millisecX100){
        return millisecX100 / 36000 +
                " ч " +
                millisecX100 % 36000 / 600 +
                " мин " +
                millisecX100 % 600 / 10 +
                " сек";
    }

    private double getMaxSpeed(int rpm, double ratio){
//        input.sideL_speed	:= REAL_TO_INT((input.nMotorL_rpm_s16 * 2 * 3.14 * para_s.drive_gear_radius_s16 * 3.6)/para_s.gear_ratio_s16/60/10);
        double x100 = Math.round((rpm*2*Math.PI*440*3.6)/ratio/600);
        return x100/100;
    }

    public void printInfo(){
        System.out.println("Продолжительность записи: " + getWholeRecTime());
        System.out.println("Количество запусков дизеля: " + getDiselStartQuant());
        System.out.println("Самая долгая сессия: " + getRecordingTime(getMaxFromColumn(ParameterNumber.ROW_NUMBER)));
        System.out.println("Максимальная температура гидравлического масла: " + getMaxFromColumn(ParameterNumber.HYD_OIL_TEMP));
        System.out.println("Максимальная температура масла ДВС: " + getMaxFromColumn(ParameterNumber.ENGINE_TEMP));
        System.out.println("Максимальная температура наддувочного воздуха: " + getMaxFromColumn(ParameterNumber.TURBO_TEMP));
        System.out.println("Максимальные обороты дизеля: " + getMaxFromColumn(ParameterNumber.ENGINE_RPM));
        int leftMotRpm = getMaxFromColumn(ParameterNumber.MOTOR_L_RPM);
        System.out.println("Максимальные обороты левого борта: " + leftMotRpm + " об/мин | Скорость: " + getMaxSpeed(leftMotRpm, 78));
        int rightMotRpm = getMaxFromColumn(ParameterNumber.MOTOR_R_RPM);
        System.out.println("Максимальные обороты правого борта: " + rightMotRpm + " об/мин | Скорость: " + getMaxSpeed(rightMotRpm, 78));
        System.out.println("Максимальное давление левого борта: " + getMaxFromColumn(ParameterNumber.PUMP_L_PRESS));
        System.out.println("Максимальное давление правого борта: " + getMaxFromColumn(ParameterNumber.PUMP_R_PRESS));
        System.out.println("Максимальное давление в тормозном контуре: " + getMaxFromColumn(ParameterNumber.BRAKE_PRESS));
    }
}
