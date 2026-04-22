package rs.raf.userservice.error;

public class BanNotFoundException extends RuntimeException {
    public BanNotFoundException(String message) {
        super(message);
    }
}
