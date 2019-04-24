package analyze;

import rawdata.ParameterNumber;

//Класс проверяет только корректность снятых значений, логику работы и состояние машины НЕ ПРОВЕРЯЕТ
public class DataVerificator {

    private Integer[][] rawValues;

    public DataVerificator(Integer[][] rawValues) {
        this.rawValues = rawValues;
    }

    private boolean isMaxBorderExceeded(ParameterNumber column, int border){

        for (Integer[] rawValue : rawValues) {
            if (rawValue[column.ordinal()] > border) {
                return true;
            }
        }
        return false;
        //if (isMaxBorderExceeded) System.out.println("Параметр " + column.toString() +" превысил границу " + border);
    }

    private boolean isMinBorderExceeded(ParameterNumber column, int border){

        for (Integer[] rawValue : rawValues) {
            if (rawValue[column.ordinal()] < border && rawValue[column.ordinal()]!=0) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBorders(ParameterNumber column, int min, int max){
        return !(isMaxBorderExceeded(column, max) || isMinBorderExceeded(column, min));
    }

    private boolean isJoysDataCorrect(){
        int min = 0;
        int max = 225;
        boolean drive = isInBorders(ParameterNumber.POTI_DRIVE, min, max);
        boolean steer = isInBorders(ParameterNumber.POTI_STEER, min, max);
        boolean dir = isInBorders(ParameterNumber.POTI_DIR, min, max);
        boolean side = isInBorders(ParameterNumber.POTI_SIDE, min, max);
        return drive && steer && dir && side;
    }

    private boolean isCurrentDataCorrect(){
//        if(!isInBorders(rawdata.ParameterNumber.CURR_P_FWD_L, 0, 1000))  System.out.println("Left pump forward current data error");
//        if(!isInBorders(rawdata.ParameterNumber.CURR_P_FWD_R, 0, 1000))  System.out.println("Right pump forward current data error");
//        if(!isInBorders(rawdata.ParameterNumber.CURR_P_REV_L, 0, 1000))  System.out.println("Left pump revers current data error");
//        if(!isInBorders(rawdata.ParameterNumber.CURR_P_REV_R, 0, 1000))  System.out.println("Right pump revers current data error");
        int min = 0;
        int max = 1000;
        boolean leftPfwd = isInBorders(ParameterNumber.CURR_P_FWD_L, min, max);
        boolean rightPfwd = isInBorders(ParameterNumber.CURR_P_FWD_R, min, max);
        boolean leftPrev = isInBorders(ParameterNumber.CURR_P_REV_L, min, max);
        boolean rightPrev = isInBorders(ParameterNumber.CURR_P_REV_R, min, max);
        boolean motorL =  isInBorders(ParameterNumber.CURR_M_L, min, max);
        boolean motorR =  isInBorders(ParameterNumber.CURR_M_R, min, max);
        boolean fanPump =  isInBorders(ParameterNumber.CURR_FAN_PUMP, min, max);
        return leftPfwd && rightPfwd && leftPrev && rightPrev && motorL && motorR && fanPump;
    }

    private boolean isPumpPressDataCorrect(){
//        if(!isInBorders(rawdata.ParameterNumber.PUMP_L_PRESS, 0, 600))  System.out.println("Left pump pressure data error");
//        if(!isInBorders(rawdata.ParameterNumber.PUMP_R_PRESS, 0, 600))  System.out.println("Right pump pressure data error");
//        if(!isInBorders(rawdata.ParameterNumber.ATT_PUMP_PRESS, 0, 600))  System.out.println("Attachment pump pressure data error");
//        if(!isInBorders(rawdata.ParameterNumber.FAN_PUMP_PRESS, 0, 600))  System.out.println("Fan pump pressure data error");
        int min = 0;
        int max = 600;
        boolean leftPpress = isInBorders(ParameterNumber.PUMP_L_PRESS, min, max);
        boolean rightPpress = isInBorders(ParameterNumber.PUMP_R_PRESS, min, max);
        boolean attPpress = isInBorders(ParameterNumber.ATT_PUMP_PRESS, min, max);
        boolean fanPpress = isInBorders(ParameterNumber.FAN_PUMP_PRESS, min, max);
        return leftPpress && rightPpress && attPpress && fanPpress;
    }

    private boolean isTemperatureDataCorrect(){
//        if(!isInBorders(rawdata.ParameterNumber.ENGINE_TEMP, -40, 255))  System.out.println("Coolant temperature data error");
//        if(!isInBorders(rawdata.ParameterNumber.HYD_OIL_TEMP, -40, 255))  System.out.println("Hydro oil temperature data error");
//        if(!isInBorders(rawdata.ParameterNumber.TURBO_TEMP, -40, 255))  System.out.println("Air turbo temperature data error");
//        if(!isInBorders(rawdata.ParameterNumber.ENVIR_TEMP, -40, 255))  System.out.println("Environment temperature data error");
        int min = -40;
        int max = 255;
        boolean coolant = isInBorders(ParameterNumber.ENGINE_TEMP, min, max);
        boolean hydOil = isInBorders(ParameterNumber.HYD_OIL_TEMP, min, max);
        boolean turbo = isInBorders(ParameterNumber.TURBO_TEMP, min, max);
        boolean envir = isInBorders(ParameterNumber.ENVIR_TEMP, min, max);
        return  coolant && hydOil && turbo && envir;
    }

    public boolean checkData(){
        boolean isDataCorrect = true;
        if(!isJoysDataCorrect()) System.out.println("Joy data error");
        if(!isInBorders(ParameterNumber.BRAKE_PRESS, 0, 50)) {
            System.out.println("Brake pressure data error");
            isDataCorrect = false;
        }
        if(!isPumpPressDataCorrect()){
            System.out.println("Pump pressure data error");
            isDataCorrect = false;
        }
        if(!isInBorders(ParameterNumber.ENGINE_OIL_PRESS, 0, 100)) {
            System.out.println("Diesel oil pressure data error");  //check this
            isDataCorrect = false;
        }
        if(!isInBorders(ParameterNumber.GEAR_NUMBER, 0, 6)) {
            System.out.println("Gear number data error");
            isDataCorrect = false;
        }
        if(!isInBorders(ParameterNumber.ENGINE_RPM, 0, 3000)) {
            System.out.println("Diesel rpm data error");
            isDataCorrect = false;
        }
        if(!isInBorders(ParameterNumber.MOTOR_L_RPM, 0, 5000)) {
            System.out.println("Left motor rpm data error");
            isDataCorrect = false;
        }
        if(!isInBorders(ParameterNumber.MOTOR_R_RPM, 0, 5000)) {
            System.out.println("Right motor rpm data error");
            isDataCorrect = false;
        }
        if(!isTemperatureDataCorrect()){
            System.out.println("Temperature data error");
            isDataCorrect = false;
        }
        if(!isCurrentDataCorrect()) {
            System.out.println("Current data error");
            isDataCorrect = false;
        }
        if(!isInBorders(ParameterNumber.POTI_DIESEL, 0, 1000)) {
            System.out.println("Diesel poti data error");
            isDataCorrect = false;
        }
        if(!isInBorders(ParameterNumber.PEDAL_BRAKE, 0, 1000)){
            System.out.println("Brake pedal data error");
            isDataCorrect = false;
        }
        if(!isInBorders(ParameterNumber.SAFETY_BUTTON, 0, 1)){
            System.out.println("Safety switch data error");
            isDataCorrect = false;
        }
        if(!isInBorders(ParameterNumber.WORK_HOURS, 0, 10000)){
            System.out.println("Working hours data error");
            isDataCorrect = false;
        }

        return isDataCorrect;
    }
}
