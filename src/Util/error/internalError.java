package Util.error;
import Util.position;

public class internalError extends error {

    private static final long serialVersionUID = 1L;

    public internalError(String msg, position pos) {
        super("Internal Error:" + msg, pos);
    }

}
