public class Controller {

    private Integer[][] rawValues;
    private int diselStartQuant;
    private int maxHydOilTemp;

    public Controller(Integer[][] rawValues) {
        this.rawValues = rawValues;
    }

    public int getDiselStartQuant(){
        int counter = 0;
        for(int i = 0; i<rawValues.length; i++){
            if( rawValues[i][0]==1){
                counter++;
            }
        }
        return counter;
    }

    public int getMaxFromColumn(int columnNumber) {
        return max(columnNumber);
    }

    private int max(int column){
        int max = 0;
        for(int i = 0; i<rawValues.length; i++){
            int currentValue = rawValues[i][column];
            if(currentValue>max){
                max = currentValue;
            }
        }
        return max;
    }

    public String getRecordingTime(){
        StringBuilder sb = new StringBuilder();
        sb.append(rawValues.length / 36000)
                .append(" ч ")
                .append(rawValues.length%36000/60)
                .append(" мин ")
                .append(rawValues.length%600/10)
                .append(" сек");
        return sb.toString();
    }

    public double getMaxSpeed(int rpm, double ratio){
//        input.sideL_speed	:= REAL_TO_INT((input.nMotorL_rpm_s16 * 2 * 3.14 * para_s.drive_gear_radius_s16 * 3.6)/para_s.gear_ratio_s16/60/10);
        double x100 = Math.round((rpm*2*Math.PI*440*3.6)/ratio/600);
        return x100/100;
    }
}
