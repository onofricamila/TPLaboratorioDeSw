package labo2018.razasypelajesonofri.utils;

public class StringsManager {

    public static String[] splitString(String string, String delimiter){
        return string.split(new StringBuilder().append("\\").append(delimiter).toString());
    }

    public static String generateWordToShow(String string, String delimiter) {
        String[] array = splitString(string, delimiter);
        return capitalizeString(array[0]) + " " + capitalizeString(array[1]);
    }

    public static String getFirstStringChar(String word){
        return word.substring(0, 1);
    }

    private static String capitalizeString(String word){
        return getFirstStringChar(word).toUpperCase() + word.substring(1);
    }
}
