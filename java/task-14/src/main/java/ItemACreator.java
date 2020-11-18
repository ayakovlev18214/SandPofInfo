import java.util.concurrent.Semaphore;

public class ItemACreator implements Runnable {

  private Semaphore itemACount = new Semaphore(0);

  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        Thread.sleep(1000);
        System.out.println("Item A created.");
        itemACount.release(1);
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  public void getItemA() {
    try {
      itemACount.acquire(1);
    } catch (InterruptedException e) {
      assert false;
    }
  }
}
