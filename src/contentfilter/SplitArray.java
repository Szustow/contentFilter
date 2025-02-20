package contentfilter;

import java.util.ArrayList;
import java.util.List;

public class SplitArray {
    static List<String> integers = new ArrayList<>();
    static List<String> floats = new ArrayList<>();
    static List<String> strings = new ArrayList<>();

    public static List<String> getIntegersList() { return integers; }
    public static List<String> getFloatsList() { return floats; }
    public static List<String> getStringsList() { return strings; }

    public static void fillArrays(List<String> array) {
        for(String str : array){
            if(str.matches("[+-]?[0-9]+")) {
                integers.add(str);
            }
            else if(str.matches("[+-]?([0-9]+([.][0-9]*)?([eE][+-]?[0-9]+)?|[.][0-9]+([eE][+-]?[0-9]+)?)")) {
                floats.add(str);
            }
            else {
                strings.add(str);
            }
        }
    }

}
