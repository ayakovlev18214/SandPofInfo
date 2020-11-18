package yakovlev;

public class Main {


  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Enter a number of threads");
      return;
    }

    Logic logic = new Logic(Integer.valueOf(args[0]));
    System.out.println(logic.run());
  }
}
