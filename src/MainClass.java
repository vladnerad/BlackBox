import analyze.DataVerificator;
import analyze.GeneralInfo;
import rawdata.RawValues;

public class MainClass {
    public static void main(String[] args) {
        RawValues rawValues = new RawValues("C:\\Users\\vpriselkov\\IdeaProjects\\BlackBox\\src\\examples\\23.04 ГСТ20.CSV");
//        rawdata.RawValues rawValues = new rawdata.RawValues("C:\Users\vpriselkov\IdeaProjects\BlackBox\src\examples\mock.CSV");

        DataVerificator dataVerificator = new DataVerificator(rawValues.getRawValues());

        if (dataVerificator.checkData()) {
            GeneralInfo generalInfo = new GeneralInfo(rawValues.getRawValues());
            generalInfo.printInfo();
        } else System.out.println("Check the device");
    }
    }

