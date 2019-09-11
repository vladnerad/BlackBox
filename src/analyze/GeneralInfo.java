package analyze;

import Helpers.DataHelper;
import Helpers.FileWriteHelper;
import command.Command;
import rawdata.ParameterNumber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GeneralInfo implements Command {

    private Integer[][] rawValues = DataHelper.getRawValues();

    //Добавить количество включений массы
    private int getDiselStartQuant() {
        int counter = 0;
        for (int i = 0; i < rawValues.length; i++) {
            if (rawValues[i][ParameterNumber.ENGINE_RPM.ordinal()] > 0 && rawValues[i - 1][ParameterNumber.ENGINE_RPM.ordinal()] == 0) {
                counter++;
            }
        }
        return counter;
    }

    private int getMaxFromColumn(ParameterNumber column) {
        return max(column.ordinal());
    }

    private int getMaxFromColumn(ParameterNumber column, int gap) {
        return max(column.ordinal(), gap);
    }

    private int max(int column) {
        int max = 0;
        for (Integer[] rawValue : rawValues) {
            int currentValue = rawValue[column];
            if (currentValue > max) {
                max = currentValue;
            }
        }
        return max;
    }

    private int maxAirTurbo() {
        int max = 0;
//        for (Integer[] rawValue : rawValues) {
        for (int i = 0; i < rawValues.length; i++) {
            int currentValue = rawValues[i][ParameterNumber.TURBO_TEMP.ordinal()];
            if (currentValue > max &&
                    /*!(Math.abs(currentValue - rawValues[i-1][ParameterNumber.TURBO_TEMP.ordinal()]) > 100
                            && Math.abs(rawValues[i-1][ParameterNumber.TURBO_TEMP.ordinal()] - rawValues[i+1][ParameterNumber.TURBO_TEMP.ordinal()])<4)*/
                    currentValue != 196) {
                max = currentValue;
            }
        }
        return max;
    }

    private int max(int column, int gap) {
        int max = 0;
        for (Integer[] rawValue : rawValues) {
            int currentValue = rawValue[column];
            if (currentValue > max && rawValue[ParameterNumber.ROW_NUMBER.ordinal()] > gap) {
                max = currentValue;
            }
        }
        return max;
    }

    private String getWholeRecTime() {
        ArrayList<Integer> records = new ArrayList<>();
        for (int i = 0; i < rawValues.length - 1; i++) {
            int currentValue = rawValues[i][ParameterNumber.TIME_AFTER_START.ordinal()];
            if (rawValues[i + 1][ParameterNumber.ROW_NUMBER.ordinal()] == 1) {
                records.add(currentValue);
            } else if (i == rawValues.length - 2) {
                records.add(rawValues[i + 1][ParameterNumber.TIME_AFTER_START.ordinal()]);
            }
        }
        int summ = 0;
        for (Integer i : records) {
            summ += i;
        }
//        System.out.println("proverka");
//        System.out.println(records);
        return getRecordingTime(summ);
    }

    private int getBatteryOnQuant() {
        int counter = 0;
        for (Integer[] rawValue : rawValues) {
            int currentValue = rawValue[ParameterNumber.ROW_NUMBER.ordinal()];
            if (currentValue == 1) {
                counter++;
            }
        }
        return counter;
    }

    private String getRecordingTime(int millisecX100) {
        return millisecX100 / 36000 +
                " ч " +
                millisecX100 % 36000 / 600 +
                " мин " +
                millisecX100 % 600 / 10 +
                " сек";
    }

    private double getMaxSpeed(int rpm, double ratio) {
//        input.sideL_speed	:= REAL_TO_INT((input.nMotorL_rpm_s16 * 2 * 3.14 * para_s.drive_gear_radius_s16 * 3.6)/para_s.gear_ratio_s16/60/10);
        double x100 = Math.round((rpm * 2 * Math.PI * 440 * 3.6) / ratio / 600);
        return x100 / 100;
    }

    private Set<Integer> getSerialNumbers() {
        HashSet<Integer> result = new HashSet<>();
        for (Integer[] rawValue : rawValues) {
            int currentValue = rawValue[ParameterNumber.SERIAL_NUMBER.ordinal()];
            result.add(currentValue);
        }
        return result;
    }

    @Override
    public void execute() {
        try {
            FileWriteHelper.writeln("Номер машины: " + getSerialNumbers().toString());
            FileWriteHelper.writeln("Продолжительность записи: " + getWholeRecTime());
            FileWriteHelper.writeln("Количество включений массы: " + getBatteryOnQuant());
            FileWriteHelper.writeln("Количество запусков дизеля: " + getDiselStartQuant());
            FileWriteHelper.writeln("Самая долгая сессия: " + getRecordingTime(getMaxFromColumn(ParameterNumber.TIME_AFTER_START)));
            FileWriteHelper.writeln("Максимальная температура гидравлического масла: " + getMaxFromColumn(ParameterNumber.HYD_OIL_TEMP));
            FileWriteHelper.writeln("Максимальная температура ОЖ ДВС: " + getMaxFromColumn(ParameterNumber.ENGINE_TEMP));
            FileWriteHelper.writeln("Максимальная температура наддувочного воздуха: " + maxAirTurbo());
            FileWriteHelper.writeln("Максимальные обороты дизеля: " + getMaxFromColumn(ParameterNumber.ENGINE_RPM));
            int leftMotRpm = getMaxFromColumn(ParameterNumber.MOTOR_L_RPM);
            FileWriteHelper.writeln("Максимальные обороты левого борта: " + leftMotRpm + " об/мин | Скорость: " + getMaxSpeed(leftMotRpm, 56));
            int rightMotRpm = getMaxFromColumn(ParameterNumber.MOTOR_R_RPM);
            FileWriteHelper.writeln("Максимальные обороты правого борта: " + rightMotRpm + " об/мин | Скорость: " + getMaxSpeed(rightMotRpm, 56));
            FileWriteHelper.writeln("Максимальное давление левого борта: " + getMaxFromColumn(ParameterNumber.PUMP_L_PRESS));
            FileWriteHelper.writeln("Максимальное давление правого борта: " + getMaxFromColumn(ParameterNumber.PUMP_R_PRESS));
            FileWriteHelper.writeln("Максимальное давление в тормозном контуре: " + getMaxFromColumn(ParameterNumber.BRAKE_PRESS));
            FileWriteHelper.closeWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        System.out.println("Продолжительность записи: " + getWholeRecTime());
//        System.out.println("Количество запусков дизеля: " + getDiselStartQuant());
//        System.out.println("Самая долгая сессия: " + getRecordingTime(getMaxFromColumn(ParameterNumber.ROW_NUMBER)));
//        System.out.println("Максимальная температура гидравлического масла: " + getMaxFromColumn(ParameterNumber.HYD_OIL_TEMP));
//        System.out.println("Максимальная температура ОЖ ДВС: " + getMaxFromColumn(ParameterNumber.ENGINE_TEMP));
//        System.out.println("Максимальная температура наддувочного воздуха: " + maxAirTurbo());
//        System.out.println("Максимальные обороты дизеля: " + getMaxFromColumn(ParameterNumber.ENGINE_RPM));
//        int leftMotRpm = getMaxFromColumn(ParameterNumber.MOTOR_L_RPM);
//        System.out.println("Максимальные обороты левого борта: " + leftMotRpm + " об/мин | Скорость: " + getMaxSpeed(leftMotRpm, 56));
//        int rightMotRpm = getMaxFromColumn(ParameterNumber.MOTOR_R_RPM);
//        System.out.println("Максимальные обороты правого борта: " + rightMotRpm + " об/мин | Скорость: " + getMaxSpeed(rightMotRpm, 56));
//        System.out.println("Максимальное давление левого борта: " + getMaxFromColumn(ParameterNumber.PUMP_L_PRESS));
//        System.out.println("Максимальное давление правого борта: " + getMaxFromColumn(ParameterNumber.PUMP_R_PRESS));
//        System.out.println("Максимальное давление в тормозном контуре: " + getMaxFromColumn(ParameterNumber.BRAKE_PRESS));
}


