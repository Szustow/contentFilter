package contentfilter;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LineReader {
    public static List<String> getListLines(String path) {
        Path file = Path.of(path).toAbsolutePath();
        String filePath = String.valueOf(file);

        try {
            LineNumberReader lineReader = new LineNumberReader(new FileReader(filePath));
            List<String> listLines = new ArrayList<>();
            String lineText;

            while ((lineText = lineReader.readLine()) != null) {
                listLines.add(lineText);
            }
            lineReader.close();

            return listLines;

        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
            System.exit(0);
        }
        return null;
    }
}
