package rs.raf.userservice.error;

public class BannedUserException extends RuntimeException {
    public BannedUserException(String message) {
        super(message);
    }
}
