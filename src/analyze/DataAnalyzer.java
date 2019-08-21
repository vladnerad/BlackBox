package analyze;

import rawdata.ParameterNumber;

import java.util.HashMap;
import java.util.Map;

public class DataAnalyzer {

    private Integer[][] rawValues;

    public DataAnalyzer(Integer[][] rawValues) {
        this.rawValues = rawValues;
    }

    private void checkAllPoti(){
        checkPoti(ParameterNumber.POTI_DRIVE);
        checkPoti(ParameterNumber.POTI_STEER);
        checkPoti(ParameterNumber.POTI_DIR);
        checkPoti(ParameterNumber.POTI_SIDE);
        checkPoti(ParameterNumber.PEDAL_BRAKE);
    }

    private void checkPoti(ParameterNumber poti){
        if (poti != ParameterNumber.POTI_DIR &&
                poti !=  ParameterNumber.POTI_DRIVE &&
                poti != ParameterNumber.POTI_SIDE &&
                poti != ParameterNumber.POTI_STEER &&
                poti != ParameterNumber.PEDAL_BRAKE) {
            System.out.println("Ошибка. Передан неверный параметр");
        }
        else {
            boolean isTouched = false;
            for (Integer[] rawValue : rawValues) {
                if (rawValue[poti.ordinal()] != 0) {
                    isTouched = true;
                }
            }
            if (!isTouched) System.out.println("Потенциометр " + poti.toString() +" не был протестирован");
        }
    }

    private void isPedalTested(){
        boolean isTouched = false;
        for (Integer[] rawValue : rawValues) {
            if (rawValue[ParameterNumber.PEDAL_BRAKE.ordinal()] > 95) {
                isTouched = true;
            }
        }
        if (!isTouched) System.out.println("Педаль не выжималась до упора");
    }

    private void checkWorkHours(int initTime){
        int currHoursQuant;
        if(rawValues.length > 10) {
            currHoursQuant = rawValues[initTime][ParameterNumber.WORK_HOURS.ordinal()];
        } else currHoursQuant = rawValues[0][ParameterNumber.WORK_HOURS.ordinal()];
        int counter = 0;
        HashMap<Integer, Integer> timeBrakes = new HashMap<>();
        for (Integer[] rawValue : rawValues) {
            int i = rawValue[ParameterNumber.WORK_HOURS.ordinal()];
            if (Math.abs(i - currHoursQuant) > 1 && i!=0) {
                counter++;
                timeBrakes.put(counter, Math.abs(i - currHoursQuant));
                currHoursQuant = i;
                //System.out.println(rawValue[0] + " " + i);
            }
            else if(i - currHoursQuant == 1){
                currHoursQuant = i;
            }
        }
        if (!timeBrakes.isEmpty()) {
            System.out.println("Черный ящик отключали " + counter + " раз:");
            for (Map.Entry entry: timeBrakes.entrySet()){
                System.out.println(entry.getKey() + ": на " + entry.getValue() + " ч");
            }
        }
    }

    private void checkTempSens(){
        boolean isWork = true;
        if(rawValues.length > 100) {
            for (Integer[] rawValue : rawValues) {
                int i = rawValue[ParameterNumber.ENVIR_TEMP.ordinal()];
                if ((i > 100 || i < -39) && rawValue[ParameterNumber.ROW_NUMBER.ordinal()] > 100) {
                    isWork = false;
                }
            }
            if (!isWork)
                System.out.println("Обнаружена неисправность датчика " + ParameterNumber.ENVIR_TEMP.toString());
        }
    }

    public void analyze(){
        checkAllPoti();
        isPedalTested();
        checkWorkHours(86);
        checkTempSens();
    }
}
