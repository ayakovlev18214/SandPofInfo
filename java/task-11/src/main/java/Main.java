import java.util.concurrent.Semaphore;

public class Main {


  public static void main(String[] args) throws InterruptedException {

    Semaphore mutex1 = new Semaphore(0);
    Semaphore mutex2 = new Semaphore(1);

    Thread thread = new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        try {
          mutex1.acquire();
          System.out.println("Child " + i);
        } catch (InterruptedException e) {
          assert false;
        } finally {
          mutex2.release();
        }
      }
    });


    thread.start();

    for (int i = 0; i < 10; ) {
      mutex2.acquire();
      System.out.println("Parent " + i++);
      mutex1.release();
    }
  }

}


