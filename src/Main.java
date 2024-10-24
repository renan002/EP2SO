import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        List<ReaderWriter> readersWriters = new LinkedList<>();
        int readerQuantity = 50;
        int writerQuantity = 50;
        if (args.length > 0) {
            readerQuantity = Integer.parseInt(args[0]);
            writerQuantity = Integer.parseInt(args[1]);
        }

        LinkedList<String> list = new LinkedList<>();
        File bd = new File("bd.txt");

        Scanner scanner = new Scanner(bd);

        MutexImpl mutex = new MutexImpl(list);

        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }

        for (int i = 0; i < readerQuantity; i++) {
            readersWriters.add(new ReaderWriter(i,false, mutex));
        }
        for (int i = 0; i < writerQuantity; i++) {
            readersWriters.add(new ReaderWriter(i,true, mutex));
        }

        Collections.shuffle(readersWriters);

        long currentTime = System.currentTimeMillis();
        readersWriters.forEach((r) -> {
            try {
                r.start();
                r.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Time: " + (System.currentTimeMillis() - currentTime) + "ms" + " " + System.currentTimeMillis());
    }
}