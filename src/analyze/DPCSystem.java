package analyze;

import rawdata.ParameterNumber;

import java.util.ArrayList;

public class DPCSystem {

    private Integer [][] filteredValues;
    private int leftGreater = 0,
            rightGreater = 0,
            lEqualsR = 0;

    public DPCSystem(Integer[][] rawValues) {
        ArrayList<Integer[]> arrayList = new ArrayList<>();
        for (Integer[] rawValue : rawValues) {
            if (rawValue[ParameterNumber.POTI_STEER.ordinal()] == 0
                    && rawValue[ParameterNumber.POTI_DIR.ordinal()] == 0
                    && rawValue[ParameterNumber.POTI_SIDE.ordinal()] == 0
                    && rawValue[ParameterNumber.POTI_DRIVE.ordinal()] != 0
                    && rawValue[ParameterNumber.MOTOR_L_RPM.ordinal()] != 0
                    && rawValue[ParameterNumber.MOTOR_R_RPM.ordinal()] != 0) {
                arrayList.add(rawValue);
            }
        }

        filteredValues = arrayList.toArray(new Integer[arrayList.size()][]);
    }

    public double leftDivRight(){
        int leftGreater = 0;
        int rightGreater = 0;
        int eq = 0;

        for(Integer[] row: filteredValues){
            if(row[ParameterNumber.MOTOR_L_RPM.ordinal()] > row[ParameterNumber.MOTOR_R_RPM.ordinal()]) leftGreater++;
            else if (row[ParameterNumber.MOTOR_L_RPM.ordinal()] < row[ParameterNumber.MOTOR_R_RPM.ordinal()]) rightGreater++;
            else eq++;
        }
        this.leftGreater = leftGreater;
        this.rightGreater = rightGreater;
        this.lEqualsR = eq;
        return ((double) leftGreater)/((double) rightGreater);
    }

    public int getBiggestDifference(){
        int biggestDifferense = 0;
        for(Integer[] row: filteredValues){
            int dif = Math.abs(row[ParameterNumber.MOTOR_L_RPM.ordinal()] - row[ParameterNumber.MOTOR_R_RPM.ordinal()]);
            if(dif > biggestDifferense) biggestDifferense = dif;
        }
        return biggestDifferense;
    }

    public int getAverage(){
        int sum = 0;
        for(Integer[] row: filteredValues){
            sum+= Math.abs(row[ParameterNumber.MOTOR_L_RPM.ordinal()] - row[ParameterNumber.MOTOR_R_RPM.ordinal()]);
        }
        return sum/filteredValues.length;
    }
}
