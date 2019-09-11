package Helpers;

import rawdata.RawValues;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataHelper {

    private static Integer[][] rawValues;
    private static String filePath;

    static {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter .csv file path:");
            filePath = br.readLine().replace("\"", "");
            rawValues = new RawValues(filePath).getRawValues();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer[][] getRawValues() {
        return rawValues;
    }

    public static String getFilePath() {
        return filePath;
    }
}
