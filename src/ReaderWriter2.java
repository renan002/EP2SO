import java.util.concurrent.ThreadLocalRandom;

public class ReaderWriter extends Thread {
    private boolean isWriter;
    private final MutexImpl mutex;
    private final int id;

    private Logger log = new Logger(this);

    public ReaderWriter(int id, boolean isWriter, MutexImpl mutex) {
        super();
        this.isWriter = isWriter;
        this.mutex = mutex;
        this.id = id;
    }

    @Override
    public void run() {
        log.log("Start");
        if (isWriter) {
            writer();
        } else {
            reader();
        }
        log.log("========================================================");
    }

    private void writer() {
        try {
            log.log("Writing");
            mutex.startAccess(); // Acesso único à região crítica, bloqueia para leitura e escrita
            log.log("I was able to get mutex");
            for (int i = 0; i < 100; i++) {
                int randomPos = (int) zeroToUpperBound(mutex.texts.size());
                mutex.texts.set(randomPos, "MODIFICADO");
            }
            log.log("Sleeping...");
            Thread.sleep(1);
            log.log("Woke up");
            mutex.endAccess(); // Libera o acesso
            log.log("End writing");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void reader() {
        try {
            log.log("Reading");
            mutex.startAccess(); // Acesso único à região crítica, bloqueia para leitura e escrita
            log.log("I was able to get mutex");
            for (int i = 0; i < 100; i++) {
                int randomPos = (int) zeroToUpperBound(mutex.texts.size());
                String a = mutex.texts.get(randomPos);
            }
            log.log("Sleeping...");
            Thread.sleep(1);
            log.log("Woke up");
            mutex.endAccess(); // Libera o acesso
            log.log("End reading");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    long zeroToUpperBound(long upper){
        return range(0, upper);
    }

    long range(long lower, long upper){
        ThreadLocalRandom generator = ThreadLocalRandom.current();
        return generator.nextLong(lower, upper);
    }

    @Override
    public String toString() {
        String name = isWriter ? "Writer" : "Reader";
        return name + " " + id;
    }
}
