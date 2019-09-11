package Helpers;

import command.Operation;
import exception.InterruptOperationException;
import rawdata.RawValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

public class ConsoleHelper {


    private static ResourceBundle res = ResourceBundle.getBundle("resources.common_en");

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        try {
            String enter = bis.readLine();
            if(enter.toLowerCase().equals("exit")) {
                writeMessage(res.getString("the.end"));
                throw new InterruptOperationException();
            }
            return enter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("choose.currency.code"));
        String code = readString();
        if(code != null && code.length() == 3) return code.toUpperCase();
        else {
            writeMessage(res.getString("invalid.data"));
            return askCurrencyCode();
        }
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        String[] result = new String[2];
        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), "money"));
        String nominal = readString();
        if (nominal != null){
            try {
                int zal = Integer.parseInt(nominal.split(" ")[0]);
                result[0] = String.valueOf(zal);
                int upa = Integer.parseInt(nominal.split(" ")[1]);
                if(upa>0){
                    result[1] = String.valueOf(upa);
                    return result;
                }
                else throw new InvalidParameterException();
            } catch (Exception e){
                writeMessage(res.getString("invalid.data"));
                return getValidTwoDigits(currencyCode);
            }
        }
        return getValidTwoDigits(currencyCode);
    }

    public static Operation askOperation() throws InterruptOperationException {
        try{
            writeMessage(res.getString("choose.operation"));
            writeMessage("1 " + res.getString("operation.GENERAL"));
            writeMessage("2 " + res.getString("operation.RPM.DROP"));
            writeMessage("3 " + res.getString("operation.FANDRIVE"));
            writeMessage("4 " + res.getString("operation.PRESSURE"));
            writeMessage("5 " + res.getString("operation.EXIT"));
            String oper = readString();
            return Operation.getAllowableOperationByOrdinal(Integer.parseInt(oper));
        } catch (IllegalArgumentException e){
            writeMessage(res.getString("invalid.data"));
            return askOperation();
        }
    }

    public static Integer[][] askLogFile(){
        writeMessage("Enter .csv file path:");
        try {
            return new RawValues(readString().replace("\"", "")).getRawValues();
        } catch (InterruptOperationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
