package rawdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class RawValues {

    private Integer[][] rawValues;

    public RawValues(String fileName) {
        Stream<String> csv;
        try {
            csv = Files.lines(Paths.get(fileName));
            rawValues = csv.map(s -> Arrays.stream(s.split(",")).map(Integer::parseInt).toArray(Integer[]::new)).toArray(Integer[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer[][] getRawValues() {
        return rawValues;
    }
}
