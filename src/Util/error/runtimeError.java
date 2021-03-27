package Util.error;

import Util.position;

public class runtimeError extends error {
    private static final long serialVersionUID = 1L;

    public runtimeError(String msg, position pos) {
        super("Runtime Error: " + msg, pos);
    }

    public runtimeError(String msg) {
        super("Runtime Error: " + msg, new position(0, 0));
    }
}
