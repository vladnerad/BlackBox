package analyze;

import rawdata.ParameterNumber;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PressureAnalyze {

    private Integer[][] rawValues;
    private int interval = 10;
    private int startsWith = 30;
    private Map<Integer, Integer> gapsL;
    private Map<Integer, Integer> gapsR;

    //Нужно передавть только один столбец наверное
    public PressureAnalyze(Integer[][] rawValues) {
        this.rawValues = rawValues;
        gapsL = getGap(ParameterNumber.PUMP_L_PRESS);
        gapsR = getGap(ParameterNumber.PUMP_R_PRESS);
    }

    private Map<Integer, Integer> getGap(ParameterNumber parameterNumber) {
        Map<Integer, Integer> map = new TreeMap<>();
        int buff = startsWith;
        while(buff<460){
            map.put(buff, 0);
            buff+=interval;
        }
        for(Integer[] rawValue: rawValues){
            int pressL = rawValue[parameterNumber.ordinal()];
            for (Integer border: map.keySet()){
                if(border-pressL>=0 && border-pressL<interval){
                    map.replace(border, (map.get(border)+1));
                    break;
                }
                else if(pressL>=0 && pressL<startsWith){
                    map.replace(startsWith, (map.get(startsWith)+1));
                    break;
                }
            }
        }
        return map;
    }

    public Map<Integer, Integer> getGapsL() {
        return gapsL;
    }

    public Map<Integer, Integer> getGapsR() {
        return gapsR;
    }
}
