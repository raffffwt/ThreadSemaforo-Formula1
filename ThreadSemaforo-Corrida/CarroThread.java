package SO.pag38;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CarroThread {
    int id;

    Random random = new Random();
    public CarroThread(int id) {
        this.id = id;
    }

    public void volta() {
        try {
            Thread.sleep(random.nextInt(4001)+1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
