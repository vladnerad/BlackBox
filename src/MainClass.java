import Helpers.FileWriteHelper;
import aggregates.BoschFanPump;
import aggregates.HydrosylaFanPump;
import analyze.*;
import rawdata.RawValues;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainClass {
    public static void main(String[] args) {
//        RawValues rawValues = new RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\examples\\23.04 ГСТ20.CSV");
//        RawValues rawValues = new RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\examples\\05-05.07 черный ящик.CSV");
//        rawdata.RawValues rawValues = new rawdata.RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\examples\\mock.CSV");
//        RawValues rawValues = new RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\examples\\ГСТ20 выставка.CSV");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
//            RawValues rawValues = new RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\examples\\08.07.19 BS.CSV");
            System.out.println("Enter .csv file path:");
            String filePath = br.readLine();
            File sourceFile = new File(filePath);
            FileWriteHelper fileWriteHelper = new FileWriteHelper(sourceFile);
            RawValues rawValues = new RawValues(filePath);

            DataVerificator dataVerificator = new DataVerificator(rawValues.getRawValues());

            if (dataVerificator.checkData()) {
                GeneralInfo generalInfo = new GeneralInfo(rawValues.getRawValues(), filePath);
                generalInfo.printInfo();

                DataAnalyzer dataAnalyzer = new DataAnalyzer(rawValues.getRawValues());
                dataAnalyzer.analyze();

                FanDriveAnalyzer fanDriveAnalyzer = new FanDriveAnalyzer(rawValues.getRawValues(), new HydrosylaFanPump());
                fanDriveAnalyzer.checkFanCurr();

//                PressureAnalyze pressureAnalyze = new PressureAnalyze(rawValues.getRawValues());
//                pressureAnalyze.printGap(pressureAnalyze.getGapsL());
//                pressureAnalyze.printGap(pressureAnalyze.getGapsR());

                HourTester hourTester = new HourTester(rawValues.getRawValues());
                System.out.println(hourTester.getHoursMap());
                System.out.println(hourTester.checkSum());

                DiselRPMDrop diselRPMDrop = new DiselRPMDrop(rawValues.getRawValues());
                System.out.println(String.format("Max diesel rpm drop is: %d rpm. (%d rpm needed, real rpm was %d)",
                        diselRPMDrop.getMaxDrop(), diselRPMDrop.getNeedWhenDropped(), diselRPMDrop.getMaxDropTo()));


//            DPCSystem dpc = new DPCSystem(rawValues.getRawValues());
//            System.out.println(dpc.leftDivRight());
//            System.out.println(dpc.getBiggestDifference());
//            System.out.println(dpc.getAverage());
            } else System.out.println("Check the device");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

