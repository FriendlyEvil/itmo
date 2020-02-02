package utils;

public class ParseException extends Exception {
    public ParseException(char ch, int pos) {
        super("Error on char: " + ch + " on position " + pos);
    }
}
