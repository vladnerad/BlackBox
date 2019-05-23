import aggregates.BoschFanPump;
import analyze.*;
import rawdata.RawValues;

public class MainClass {
    public static void main(String[] args) {
//        RawValues rawValues = new RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\examples\\23.04 ГСТ20.CSV");
//        rawdata.RawValues rawValues = new rawdata.RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\examples\\mock.CSV");
        RawValues rawValues = new RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\examples\\ГСТ20 выставка.CSV");

        DataVerificator dataVerificator = new DataVerificator(rawValues.getRawValues());

        if (dataVerificator.checkData()) {
            GeneralInfo generalInfo = new GeneralInfo(rawValues.getRawValues());
            generalInfo.printInfo();

            DataAnalyzer dataAnalyzer = new DataAnalyzer(rawValues.getRawValues());
            dataAnalyzer.analyze();

            FanDriveAnalyzer fanDriveAnalyzer = new FanDriveAnalyzer(rawValues.getRawValues(), new BoschFanPump());
            fanDriveAnalyzer.checkFanCurr();

//            DPCSystem dpc = new DPCSystem(rawValues.getRawValues());
//            System.out.println(dpc.leftDivRight());
//            System.out.println(dpc.getBiggestDifference());
//            System.out.println(dpc.getAverage());
        } else System.out.println("Check the device");
    }
    }

