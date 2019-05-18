import view.Window;

public class MainEgg {
    public static void main(String[] args) throws InterruptedException {
        Window window = new Window();
        while (true) {
            window.setState(1);
            Thread.sleep(1000);
            window.setState(2);
            Thread.sleep(1000);
            window.setState(3);
            Thread.sleep(1000);
            window.setState(4);
            Thread.sleep(1000);
        }
    }
}
