package analyze;

import rawdata.ParameterNumber;

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
        while (buff < 460) {
            map.put(buff, 0);
            buff += interval;
        }
        for (Integer[] rawValue : rawValues) {
            int pressL = rawValue[parameterNumber.ordinal()];
            for (Integer border : map.keySet()) {
                //Промежутки 0-30, 31-40, 41-50 и т.д.
                if (border - pressL >= 0 && border - pressL < interval) {
                    map.replace(border, (map.get(border) + 1));
                    break;
                } else if (pressL >= 0 && pressL < startsWith) {
                    map.replace(startsWith, (map.get(startsWith) + 1));
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

    public void printGap(Map<Integer, Integer> gap){
        for (Map.Entry<Integer, Integer> entry: gap.entrySet()){
            int key = entry.getKey();
            int value = entry.getValue();
            if (key == startsWith) {
                System.out.print("0 - " + startsWith + " Bar: " + value + " x 0.1s.\n");
            }
            else System.out.print(key-9 + " - " + key + " Bar: " + value + " x 0.1s.\n");
        }
    }
}
