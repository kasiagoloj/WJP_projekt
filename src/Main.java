public class Main {
    public static void main(String[] args) throws InterruptedException {
        Gui gui = new Gui();
        Menu menu = new Menu();
        menu.wyswietl_v1();

        while (!Menu.read){
            Thread.sleep(100);
            if(Menu.read){
                gui.start();
            }
        }
    }
}
