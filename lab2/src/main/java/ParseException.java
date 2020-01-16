public class ParseException extends Exception {
    public ParseException(String message, int curPos) {
        super(message + " at position " + curPos);
    }
}
