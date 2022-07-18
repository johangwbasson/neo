package net.johanbasson.neo.core.bus;

public class QueryException extends RuntimeException {

    public QueryException(Throwable cause) {
        super(cause);
    }

    public QueryException(String message) {
        super(message);
    }
}
