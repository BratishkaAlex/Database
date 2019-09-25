package framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberReader {

    public static int getIntNumber(String line) {
        return Integer.parseInt(getLineWithNumber("Integer", line));
    }

    public static Double getDoubleNumber(String line) {
        return Double.parseDouble(getLineWithNumber("Double", line));
    }

    private static String getLineWithNumber(String type, String line) {
        Pattern pattern;
        switch (type) {
            case "Integer":
                pattern = Pattern.compile("\\d+");
                break;
            case "Double":
                pattern = Pattern.compile("\\d+.\\d+");
                break;
            default:
                throw new IllegalArgumentException("Unknown wanted number type");
        }
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return line.substring(matcher.start(), matcher.end());
        } else {
            throw new IllegalStateException("Line with number wasn't found");
        }
    }
}
