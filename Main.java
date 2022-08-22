
public class Main {
    public static boolean firstRun = true;
    public static void main(String[] args) {
        if(firstRun){
            new GameFrame().startScreen();
        }else {
            new GameFrame();
        }
    }
}
