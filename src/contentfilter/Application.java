package contentfilter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Application {
    static String outputPath = "";
    static String inputPath = "";
    static String prefix = "";
    static boolean append = false;
    static boolean shortStatistics = false;
    static boolean fullStatistic = false;

    public static void main(String[] args) {
        Path path = Path.of("");
        outputPath = path.toAbsolutePath().toString();
        inputPath = path.toAbsolutePath().toString();

        CommandLineParser clp = new CommandLineParser(args);
        parseArguments(clp);

        if (clp.getTxtNames() == null) {
            System.out.println("Не указан файл для ввода данных");
            System.exit(0);
        }
        else {
            for (String str : clp.getTxtNames()) {
                SplitArray.fillArrays(LineReader.getListLines(inputPath + File.separator + str));
            }
            writeOutputFiles(SplitArray.getIntegersList(), SplitArray.getFloatsList(), SplitArray.getStringsList());
        }

        if (shortStatistics) printShortStatistic(SplitArray.getIntegersList(),
                SplitArray.getFloatsList(), SplitArray.getStringsList());
        if (fullStatistic) printFullStatistic(SplitArray.getIntegersList(),
                SplitArray.getFloatsList(), SplitArray.getStringsList());
    }
    public static void parseArguments(CommandLineParser clp) {
        for(String flag : clp.getFlags()) {
            switch (flag) {
                case "o":
                    outputPath = clp.getArgumentValue("o");
                    if (outputPath == null) {
                        System.out.println("Укажите путь после опции -o");
                        System.exit(0);
                    }
                    break;
                case "p":
                    prefix = clp.getArgumentValue("p");
                    if (prefix == null) {
                        System.out.println("Укажите префикс после опции -p");
                        System.exit(0);
                    }
                    break;
                case "a":
                    append = true;
                    break;
                case "s":
                    shortStatistics = true;
                    break;
                case "f":
                    fullStatistic = true;
                    break;
            }
        }
    }

    public static void writeToFile(List<String> array, String defaultFilename) {
        String path = "";

        if (outputPath.endsWith(File.separator)) path = outputPath + prefix + defaultFilename;
        else path = outputPath + File.separator + prefix + defaultFilename;
        File file = new File(path);
        try (FileWriter fileWriter = new FileWriter(file, append)) {
            for (String str : array) {
                fileWriter.append(str)
                        .append("\n");
                fileWriter.flush();
            }
        }
        catch (IOException e) {

            System.out.println("Ошибка при записи в файл " + path + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void writeOutputFiles(List<String> integers, List<String> floats, List<String> strings) {
        if (!integers.isEmpty()) {
            writeToFile(integers, "integers.txt");
        }
        if (!floats.isEmpty()) {
            writeToFile(floats, "floats.txt");
        }
        if (!strings.isEmpty()) {
            writeToFile(strings, "strings.txt");
        }
    }

    public static double min(List<String> num) {
        double min = Double.parseDouble(num.get(0));
        for (String number: num) {
            if (Double.parseDouble(number) < min) min = Double.parseDouble(number);
        }
        return min;
    }
    public static double max(List<String> num) {
        double max = Double.parseDouble(num.get(0));
        for (String number: num) {
            if (Double.parseDouble(number) > max) max = Double.parseDouble(number);
        }
        return max;
    }
    public static double sum(List<String> num) {
        double sum = 0;
        for (String number: num) {
            sum += Double.parseDouble(number);
        }
        return sum;
    }
    public static double average(List<String> num) {
        double sumNum = sum(num);
        return sumNum / num.size();
    }

    public static int minStr(List<String> str) {
        int min = str.get(0).length();

        for (String line: str) {
            if (line.length() < min) min = line.length();
        }
        return min;
    }
    public static int maxStr(List<String> str) {
        int max = str.get(0).length();

        for (String line: str) {
            if (line.length() > max) max = line.length();
        }
        return max;
    }

    public static void printShortStatistic(List<String> integers, List<String> floats, List<String> strings) {
        System.out.println("Краткая статистика:");
        System.out.printf("Целые числа: %s эл.\n", integers.size());
        System.out.printf("Вещественные числа: %s эл.\n", floats.size());
        System.out.printf("Строки: %s эл.\n", strings.size());
    }

    public static void printFullStatistic(List<String> integers, List<String> floats, List<String> strings) {
        System.out.println("Полная статистика:");

        System.out.println();
        System.out.printf("Целые числа: %s эл.\n", integers.size());
        if (!integers.isEmpty()) {
            System.out.printf("Минимальное значение: %s\n", min(integers));
            System.out.printf("Максимальное значение: %s\n", max(integers));
            System.out.printf("Сумма всех чисел: %s\n", sum(integers));
            System.out.printf("Среднее значение: %s\n", average(integers));
        }

        System.out.println();
        System.out.printf("Вещественные числа: %s эл.\n", floats.size());
        if (!floats.isEmpty()) {
            System.out.printf("Минимальное значение: %s\n", min(floats));
            System.out.printf("Максимальное значение: %s\n", max(floats));
            System.out.printf("Сумма всех чисел: %s\n", sum(floats));
            System.out.printf("Среднее значение: %s\n", average(floats));
        }


        System.out.println();
        System.out.printf("Строки: %s эл.\n", strings.size());
        if (!strings.isEmpty()) {
            System.out.printf("Размер короткой строки: %s\n", minStr(strings));
            System.out.printf("Размер длинной строки: %s\n", maxStr(strings));
        }
    }
}

