import java.util.List;
import java.util.concurrent.Semaphore;

public class MutexImpl2 {
    private final Semaphore lock = new Semaphore(1); // Controle único de acesso à base

    public final List<String> texts;

    public MutexImpl2(List<String> texts) {
        this.texts = texts;
    }

    public void startAccess() {
        try {
            lock.acquire(); // Aguarda até que a região crítica esteja disponível
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void endAccess() {
        lock.release(); // Libera o acesso à região crítica para outra thread
    }
}
