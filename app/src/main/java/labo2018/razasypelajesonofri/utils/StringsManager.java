package labo2018.razasypelajesonofri.utils;

public class StringsManager {

    public static String[] splitString(String string, String delimiter){
        return string.split(new StringBuilder().append("\\").append(delimiter).toString());
    }

    public static String generateWordToShow(String string, String delimiter) {
        String[] array = splitString(string, delimiter);
        String res = "";
        for (int i = 0; i < array.length; i++) {
            res += array[i] + " ";
        }
        return res.toUpperCase();
    }

    public static String getFirstStringChar(String word){
        return word.substring(0, 1);
    }

}
