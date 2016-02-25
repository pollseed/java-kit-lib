
public class MemCachedException extends RuntimeException {
    public MemCachedException() {
    }

    public MemCachedException(String message) {
        super(message);
    }

    public MemCachedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemCachedException(Throwable cause) {
        super(cause);
    }
}
