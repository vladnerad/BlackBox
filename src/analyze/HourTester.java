package analyze;

import rawdata.ParameterNumber;

import java.util.Map;
import java.util.TreeMap;

public class HourTester {

    private Integer[][] rawValues;

    public HourTester(Integer[][] rawValues) {
        this.rawValues = rawValues;
    }

    public TreeMap<Integer, Integer> getHoursMap(){
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (Integer[] rawValue : rawValues) {
            int hour = rawValue[ParameterNumber.WORK_HOURS.ordinal()];
            if (!map.containsKey(hour)){
                map.put(hour, 1);
            } else {
                map.replace(hour, map.get(hour)+1);
            }
        }
        return map;
    }

    public boolean checkSum(){
        int summ = 0;
        for (Map.Entry<Integer, Integer> entry: getHoursMap().entrySet()){
            summ+=entry.getValue();
        }
        return summ == rawValues.length;
    }
}
