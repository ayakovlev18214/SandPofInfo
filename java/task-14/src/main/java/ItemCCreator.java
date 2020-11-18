import java.util.concurrent.Semaphore;

public class ItemCCreator implements Runnable {
  private Semaphore itemCCount = new Semaphore(0);

  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        Thread.sleep(3000);
        System.out.println("Item C created.");
        itemCCount.release(1);
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  public void getItemC() {
    try {
      itemCCount.acquire(1);
    } catch (InterruptedException e) {
      assert false;
    }
  }
}
