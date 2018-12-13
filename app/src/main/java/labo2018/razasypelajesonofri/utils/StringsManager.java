package labo2018.razasypelajesonofri.utils;

public class StringsManager {

    public static String[] splitString(String string, String delimiter){
        return string.split(new StringBuilder().append("\\").append(delimiter).toString());
    }

    public static String generateWordToShow(String string, String delimiter) {
        String[] array = splitString(string, delimiter);
        String res = "";
        for (int i = 0; i < array.length; i++) {
            String[] arrayTmp = splitString(array[i], "d");
            for (int j = 0; j < arrayTmp.length; j++) {
                res += arrayTmp[j] + " ";
            }
        }
        return res.toUpperCase();
    }

}
