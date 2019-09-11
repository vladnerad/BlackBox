package analyze;

import Helpers.ConsoleHelper;
import Helpers.DataHelper;
import command.Command;
import exception.InterruptOperationException;
import rawdata.ParameterNumber;

public class DiselRPMDrop implements Command {

    private Integer[][] rawValues = DataHelper.getRawValues();
    private int maxDiselRpm = 2150;
    private int minDiselRpm = 700;
    private int needWhenDropped = 0;
    private int maxDropTo = 0;

    public int getMaxDrop() {
        int result = 0;
        for (int i = 0; i < rawValues.length; i++) {
            int potiDisel = rawValues[i][ParameterNumber.POTI_DIESEL.ordinal()];
            int realRpm = rawValues[i][ParameterNumber.ENGINE_RPM.ordinal()];
            if (realRpm > 500 && rawValues[i][ParameterNumber.SAFETY_BUTTON.ordinal()] != 0) {
//                int counter = i;
//                while (rawValues[counter][ParameterNumber.POTI_DIESEL.ordinal()] > realRpm-2
//                    && rawValues[counter][ParameterNumber.POTI_DIESEL.ordinal()] < realRpm+2){
//                    int drop =
//                    counter++;
//                }
                int rpmFromPoti = (maxDiselRpm - minDiselRpm) * potiDisel / 100 + minDiselRpm;
                if (rpmFromPoti - realRpm > result) {
                    result = rpmFromPoti - realRpm;
                    needWhenDropped = rpmFromPoti;
                    maxDropTo = realRpm;
//                    System.out.println(i);
                }
            }
        }
        return result;
    }

    public int getNeedWhenDropped() {
        return needWhenDropped;
    }

    public int getMaxDropTo() {
        return maxDropTo;
    }

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage("*****************************************************************");
        ConsoleHelper.writeMessage(String.format("Max diesel rpm drop is: %d rpm. (%d rpm needed, real rpm was %d)",
                getMaxDrop(), getNeedWhenDropped(), getMaxDropTo()));
        ConsoleHelper.writeMessage("*****************************************************************");
    }
}

