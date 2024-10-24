public class Logger {
    private final Object obj;

    public Logger(Object obj) {
        this.obj = obj;
    }

    public void log(String message) {
        System.out.println(obj + ": " + message);
    }

    public void log(String message, Object... args) {
        log(String.format(message, args));
    }
}
