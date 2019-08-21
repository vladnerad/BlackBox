package analyze;

import rawdata.ParameterNumber;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class GeneralInfo {

    private Integer[][] rawValues;
    private File outputFile;

    public GeneralInfo(Integer[][] rawValues, String fileName) {
        this.rawValues = rawValues;
        this.outputFile = new File(new File(fileName).toString().replace(".CSV", "").concat(" - result.txt"));
    }

//    private int getDiselStartQuant(){
//        int counter = 0;
//        for (Integer[] rawValue : rawValues) {
//            if (rawValue[0] == 1) {
//                counter++;
//            }
//        }
//        return counter;
//    }

    //Добавить количество включений массы
    private int getDiselStartQuant(){
        int counter = 0;
        for (int i = 0; i<rawValues.length; i++) {
            if (rawValues[i][ParameterNumber.ENGINE_RPM.ordinal()]>0 && rawValues[i-1][ParameterNumber.ENGINE_RPM.ordinal()]==0) {
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

    private int maxAirTurbo() {
        int max = 0;
//        for (Integer[] rawValue : rawValues) {
        for (int i = 0;  i< rawValues.length; i++) {
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

    private int max(int column, int gap){
        int max = 0;
        for (Integer[] rawValue : rawValues) {
            int currentValue = rawValue[column];
            if (currentValue > max && rawValue[ParameterNumber.ROW_NUMBER.ordinal()]>gap) {
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
        try {
            Files.createFile(outputFile.toPath());
            FileWriter fw = new FileWriter(outputFile);
            fw.write("Продолжительность записи: " + getWholeRecTime());
            fw.write("\n");
            fw.write("Количество запусков дизеля: " + getDiselStartQuant());
            fw.write("\n");
            fw.write("Самая долгая сессия: " + getRecordingTime(getMaxFromColumn(ParameterNumber.ROW_NUMBER)));
            fw.write("\n");
            fw.write("Максимальная температура гидравлического масла: " + getMaxFromColumn(ParameterNumber.HYD_OIL_TEMP));
            fw.write("\n");
            fw.write("Максимальная температура ОЖ ДВС: " + getMaxFromColumn(ParameterNumber.ENGINE_TEMP));
            fw.write("\n");
            fw.write("Максимальная температура наддувочного воздуха: " + maxAirTurbo());
            fw.write("\n");
            fw.write("Максимальные обороты дизеля: " + getMaxFromColumn(ParameterNumber.ENGINE_RPM));
            fw.write("\n");
            int leftMotRpm = getMaxFromColumn(ParameterNumber.MOTOR_L_RPM);
            fw.write("Максимальные обороты левого борта: " + leftMotRpm + " об/мин | Скорость: " + getMaxSpeed(leftMotRpm, 56));
            fw.write("\n");
            int rightMotRpm = getMaxFromColumn(ParameterNumber.MOTOR_R_RPM);
            fw.write("Максимальные обороты правого борта: " + rightMotRpm + " об/мин | Скорость: " + getMaxSpeed(rightMotRpm, 56));
            fw.write("\n");
            fw.write("Максимальное давление левого борта: " + getMaxFromColumn(ParameterNumber.PUMP_L_PRESS));
            fw.write("\n");
            fw.write("Максимальное давление правого борта: " + getMaxFromColumn(ParameterNumber.PUMP_R_PRESS));
            fw.write("\n");
            fw.write("Максимальное давление в тормозном контуре: " + getMaxFromColumn(ParameterNumber.BRAKE_PRESS));
            fw.write("\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
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
}
