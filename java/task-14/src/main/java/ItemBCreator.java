import java.util.concurrent.Semaphore;

public class ItemBCreator implements Runnable {

  private Semaphore itemBCount = new Semaphore(0);

  @Override
  public void run() {
    while(!Thread.interrupted()) {
      try {
        Thread.sleep(2000);
        System.out.println("Item B created.");
        itemBCount.release(1);
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  public void getItemB() {
    try {
      itemBCount.acquire(1);
    } catch (InterruptedException e) {
      assert false;
    }
  }
}
