import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
  public static void main(String[] args) {
    List<String> list = new LinkedList<>();
    ReentrantLock lock = new ReentrantLock(true);

    Thread sorter = new Thread(new Sorter(list, lock));
    sorter.start();

    String s = "";
    Scanner scanner = new Scanner(System.in);
    while (true) {
      s = scanner.nextLine();
      if (s.equals("")) {
        list.forEach(System.out::println);
        sorter.interrupt();
        break;
      } else {
        lock.lock();
        list.add(0, s);
        lock.unlock();
      }

    }
  }
}
