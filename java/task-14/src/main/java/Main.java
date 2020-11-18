public class Main {
  public static void main(String[] args) {
    ItemACreator itemACreator = new ItemACreator();
    ItemBCreator itemBCreator = new ItemBCreator();
    ItemCCreator itemCCreator = new ItemCCreator();
    WidgetCreator widgetCreator = new WidgetCreator(itemACreator, itemBCreator, itemCCreator);

    Thread threadA = new Thread(itemACreator);
    Thread threadB = new Thread(itemBCreator);
    Thread threadC = new Thread(itemCCreator);
    Thread threadW = new Thread(widgetCreator);

    threadA.start();
    threadB.start();
    threadC.start();
    threadW.start();
  }
}
