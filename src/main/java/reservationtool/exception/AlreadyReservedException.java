package reservationtool.exception;

public class AlreadyReservedException extends RuntimeException {
    public AlreadyReservedException(String message) {
        super(message);
    }
}
