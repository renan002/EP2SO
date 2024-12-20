import java.util.List;
import java.util.concurrent.Semaphore;

public class MutexImpl {

    private final Semaphore writeLock = new Semaphore(1); //If a reader is reading, a writer can't write (não me diga)
    private final Semaphore mutex = new Semaphore(1);
    private int readers = 0;

    public final List<String> texts;
    public MutexImpl(List<String> texts) {
        this.texts = texts;
    }

    public void startWrite() {
        try {
            writeLock.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void endWrite() {
        writeLock.release();
    }

    public void startRead() {
        try {
            mutex.acquire();
            readers++;
            if (readers == 1) {
                writeLock.acquire();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void endRead() {
        readers--;
        if (readers == 0) {
            writeLock.release();
        }
        mutex.release();
    }
}
