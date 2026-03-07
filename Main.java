public class Main {

  public static void main(String[] args) {
    System.out.println(System.nanoTime());
    System.out.println(System.currentTimeMillis());

    TimeProvider timer = TimeProvider.getTimeProviderInstance();

    System.out.println(timer.now());

    // the code goes here
  }
}

class helper {
  //all methods and variables can be listed here
}
