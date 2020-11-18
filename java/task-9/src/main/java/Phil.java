import java.util.concurrent.locks.ReentrantLock;

public class Phil implements Runnable {

  private final ReentrantLock lock;
  private int id;
  private Fork left;
  private Fork right;

  public Phil(ReentrantLock lock, int id, Fork left, Fork right) {
    this.lock = lock;
    this.id = id;
    this.left = left;
    this.right = right;
  }

  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        lock.lock();
        System.out.println("SEL " + id);
        left.getFork();
        right.getFork();
        Thread.sleep(4000);
        System.out.println("POEL " + id);
        left.dropFork();
        right.dropFork();
        lock.unlock();
      } catch (InterruptedException e) {
        assert false;
      }
    }
  }
}
