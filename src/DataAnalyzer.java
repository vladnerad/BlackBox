public class DataAnalyzer {

    private Integer[][] rawValues;

    public DataAnalyzer(Integer[][] rawValues) {
        this.rawValues = rawValues;
    }

    private void checkAllJoys(){
        checkJoy(ParameterNumber.POTI_DRIVE);
        checkJoy(ParameterNumber.POTI_STEER);
        checkJoy(ParameterNumber.POTI_DIR);
        checkJoy(ParameterNumber.POTI_SIDE);
    }

    private void checkJoy(ParameterNumber joyAxis){
        if (joyAxis != ParameterNumber.POTI_DIR &&
                joyAxis !=  ParameterNumber.POTI_DRIVE &&
                joyAxis != ParameterNumber.POTI_SIDE &&
                joyAxis != ParameterNumber.POTI_STEER) {
            System.out.println("Ошибка. Передан неверный параметр");
        }
        else {
            boolean isTouched = false;
            for (Integer[] rawValue : rawValues) {
                if (rawValue[joyAxis.ordinal()] != 0) {
                    isTouched = true;
                    break;
                }
            }
            if (!isTouched) System.out.println("Джостик " + joyAxis.toString() +" не был тронут");
        }
    }
}
