package Helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriteHelper {

    private static File csvSourceFile;
    private File outputFile;

    public FileWriteHelper(File csvSourceFile) {
        this.csvSourceFile = csvSourceFile;
        this.outputFile = new File(csvSourceFile.toString().replace(".CSV", "").concat(" - result.txt"));
    }

//    public void setCsvSourceFile(File file){
//        this.csvSourceFile = file;
//    }

    public void writeln(String string){
        try {
            FileWriter fw = new FileWriter(outputFile);
            fw.write(string);
            fw.write("\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
