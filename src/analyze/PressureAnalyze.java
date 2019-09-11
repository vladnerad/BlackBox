package analyze;

import Helpers.ConsoleHelper;
import Helpers.DataHelper;
import command.Command;
import exception.InterruptOperationException;
import rawdata.ParameterNumber;

import java.util.Map;
import java.util.TreeMap;

public class PressureAnalyze implements Command {

    private Integer[][] rawValues = DataHelper.getRawValues();
    private int interval = 10;
    private int startsWith = 30;
    private Map<Integer, Integer> gapsL = getGap(ParameterNumber.PUMP_L_PRESS);
    private Map<Integer, Integer> gapsR = getGap(ParameterNumber.PUMP_R_PRESS);

    //Нужно передавть только один столбец наверное

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

//    public Map<Integer, Integer> getGapsL() {
//        return gapsL;
//    }
//
//    public Map<Integer, Integer> getGapsR() {
//        return gapsR;
//    }

    public void printGap(Map<Integer, Integer> gap) {
        for (Map.Entry<Integer, Integer> entry : gap.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (key == startsWith) {
                String message = "0 - " + startsWith + " Bar: " + value + " x 0.1s.";
                ConsoleHelper.writeMessage(message);
            } else{
                String message = key - 9 + " - " + key + " Bar: " + value + " x 0.1s.";
                ConsoleHelper.writeMessage(message);
            }
        }
    }

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage("Left Pump:");
        printGap(gapsL);
        ConsoleHelper.writeMessage("Right Pump:");
        printGap(gapsR);
    }
}
