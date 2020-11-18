public class WidgetCreator implements Runnable {

  private ItemACreator itemACreator;
  private ItemBCreator itemBCreator;
  private ItemCCreator itemCCreator;

  public WidgetCreator(ItemACreator itemACreator, ItemBCreator itemBCreator, ItemCCreator itemCCreator) {
    this.itemACreator = itemACreator;
    this.itemBCreator = itemBCreator;
    this.itemCCreator = itemCCreator;
  }


  @Override
  public void run() {
    while (!Thread.interrupted()) {
      itemACreator.getItemA();
      itemBCreator.getItemB();
      itemCCreator.getItemC();

      System.out.println("Widget created.");
    }
  }
}
