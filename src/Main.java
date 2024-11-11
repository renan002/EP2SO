import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        long Medtemp = 0;
        long Tempo = 0;
        for (int j = 1; j <= 50; j++) {
            System.out.println("Rodando execução: " + j);
            List<ReaderWriter2> readersWriters = new LinkedList<>();
            int readerQuantity = 50;
            int writerQuantity = 50;
            if (args.length > 0) {
                readerQuantity = Integer.parseInt(args[0]);
                writerQuantity = Integer.parseInt(args[1]);
            }

            LinkedList<String> list = new LinkedList<>();
            File bd = new File("bd.txt");

            Scanner scanner = new Scanner(bd);

            MutexImpl2 mutex = new MutexImpl2(list);

            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }

            for (int i = 0; i < readerQuantity; i++) {
                readersWriters.add(new ReaderWriter2(i,false, mutex));
            }
            for (int i = 0; i < writerQuantity; i++) {
                readersWriters.add(new ReaderWriter2(i,true, mutex));
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
            Tempo = System.currentTimeMillis() - currentTime;
            System.out.println("Time: " + Tempo + "ms");
            Medtemp =+ Tempo;
        }
        System.out.println("Tempo médio:" + Medtemp/5 + "ms");
    }
    }