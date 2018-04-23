package chapter3.lesson6;

import java.util.List;
import java.util.concurrent.Exchanger;

public class Consumer implements Runnable {
    private final Exchanger<List<String>> exchanger;
    private List<String> buffer;

    public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        for (int cycle = 1; cycle <= 10; cycle++) {
            System.out.printf("Consumer: Cycle %d\n", cycle);

            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Consumer buffer.size(): " + buffer.size());

            for (int j = 0; j < 10; j++) {
                String message = buffer.get(0);
                System.out.println("Consumer: " + message);
                buffer.remove(0);
            }
        }
    }
}
