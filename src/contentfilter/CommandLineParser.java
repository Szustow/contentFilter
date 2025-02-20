package contentfilter;

import java.util.*;

public class CommandLineParser {
    List<String> args = new ArrayList<>();
    List<String> txtFiles = new ArrayList<>();
    HashMap<String, String> map = new HashMap<>();
    Set<String> flags = new HashSet<>();

    CommandLineParser(String arguments[])
    {
        this.args = Arrays.asList(arguments);
        map();
    }

    public String[] getTxtNames()
    {
        String[] txtNamesArray = new String[txtFiles.size()];
        txtFiles.toArray(txtNamesArray);
        if (txtFiles.isEmpty()) return null;
        return txtNamesArray;
    }

    public String[] getFlags()
    {
        String[] flagsArray = new String[flags.size()];
        flags.toArray(flagsArray);
        return flagsArray;
    }

    public String getArgumentValue(String argumentName)
    {
        return map.getOrDefault(argumentName, null);
    }

    public boolean isTxtFile(String str) {
        if (str.length() > 3) {
            int length = str.length();
            return str.substring(length - 4).equals(".txt");
        }
        return false;
    }

    public void map()
    {
        for(String arg: args)
        {
            if (isTxtFile(args.get(args.indexOf(arg)))) {
                txtFiles.add(arg);
            }
            else if(arg.startsWith("-"))
            {
                if (args.indexOf(arg) == (args.size() - 1))
                {
                    flags.add(arg.replace("-", ""));
                }
                else if (args.get(args.indexOf(arg)+1).startsWith("-"))
                {
                    flags.add(arg.replace("-", ""));
                }

                else if (isTxtFile(args.get(args.indexOf(arg)+1))) {
                    continue;
                }
                else
                {
                    String argumentValue = "";
                    if (args.indexOf(arg)+1 != args.size()
                            && !args.get(args.indexOf(arg)+1).startsWith("-")
                            && !isTxtFile(args.get(args.indexOf(arg)+1)))
                    {
                        argumentValue = args.get(args.indexOf(arg)+1);
                    }
                    flags.add(arg.replace("-", ""));
                    map.put(arg.replace("-", ""), argumentValue);
                }
            }
        }
    }
}
