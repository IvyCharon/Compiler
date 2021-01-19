package Util.error;
import Util.position;

public class semanticError extends error {

    private static final long serialVersionUID = 1L;

    public semanticError(String msg, position pos) {
        super("Semantic Error: " + msg, pos);
    }

}
