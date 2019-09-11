package Helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileWriteHelper {

    private static FileWriter fw;

    static {
        File outputFile = new File(DataHelper.getFilePath().replace(".CSV", "").concat(" - result.txt"));
        try {
            Files.deleteIfExists(outputFile.toPath());
            fw = new FileWriter(outputFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeln(String string) throws IOException {
        fw.write(string);
        fw.write("\n");
        fw.flush();
    }

    public static void closeWriter() throws IOException {
        fw.close();
    }


}
