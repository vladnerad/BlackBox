package Helpers;

import exception.InterruptOperationException;
import rawdata.RawValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataHelper {

    private static Integer[][] rawValues;
    private static String filePath;

    static {
        ConsoleHelper.writeMessage("Enter .csv file path:");
        try {
            filePath = ConsoleHelper.readString().replace("\"", "");
        } catch (InterruptOperationException e) {
            e.printStackTrace();
        }
        rawValues = new RawValues(filePath).getRawValues();
    }

//    static {
//        rawValues = ConsoleHelper.askLogFile();
//    }

    public static Integer[][] getRawValues() {
        return rawValues;
    }

    public static String getFilePath() {
        return filePath;
    }
}
