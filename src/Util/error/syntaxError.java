package Util.error;
import Util.position;

public class syntaxError extends error {

    private static final long serialVersionUID = 1L;

    public syntaxError(String msg, position pos) {
        super("SyntaxError: " + msg, pos);
    }

}
