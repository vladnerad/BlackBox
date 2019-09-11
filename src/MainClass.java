import Helpers.ConsoleHelper;
import Helpers.DataHelper;
import analyze.DataVerificator;
import command.CommandExecutor;
import command.Operation;
import exception.InterruptOperationException;

public class MainClass {
    public static void main(String[] args) {

        try {

            DataVerificator dataVerificator = new DataVerificator(DataHelper.getRawValues());

            if (dataVerificator.checkData()) {
                CommandExecutor.execute(Operation.GENERAL_INFO);

//                DataAnalyzer dataAnalyzer = new DataAnalyzer(rawValues.getRawValues());
//                dataAnalyzer.analyze();
//
//                FanDriveAnalyzer fanDriveAnalyzer = new FanDriveAnalyzer(rawValues.getRawValues(), new HydrosylaFanPump());
//                fanDriveAnalyzer.checkFanCurr();

//                PressureAnalyze pressureAnalyze = new PressureAnalyze(rawValues.getRawValues());
//                pressureAnalyze.printGap(pressureAnalyze.getGapsL());
//                pressureAnalyze.printGap(pressureAnalyze.getGapsR());

//                HourTester hourTester = new HourTester(rawValues.getRawValues());
//                System.out.println(hourTester.getHoursMap());
//                System.out.println(hourTester.checkSum());

//                DiselRPMDrop diselRPMDrop = new DiselRPMDrop(rawValues.getRawValues());
//                System.out.println(String.format("Max diesel rpm drop is: %d rpm. (%d rpm needed, real rpm was %d)",
//                        diselRPMDrop.getMaxDrop(), diselRPMDrop.getNeedWhenDropped(), diselRPMDrop.getMaxDropTo()));


//            DPCSystem dpc = new DPCSystem(rawValues.getRawValues());
//            System.out.println(dpc.leftDivRight());
//            System.out.println(dpc.getBiggestDifference());
//            System.out.println(dpc.getAverage());

                Operation operation;
                do {
                    operation = ConsoleHelper.askOperation();
                    CommandExecutor.execute(operation);
                } while (operation != Operation.EXIT);
            } else System.out.println("Check the device");
        } catch (InterruptOperationException e) {
            e.printStackTrace();
        }
    }
}

