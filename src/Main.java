public class Main {
    public static void main(String[] args) throws InterruptedException {

        /** wywoływanie Gui i menu **/
        Gui gui = new Gui();
        Menu menu = new Menu();
        menu.wyswietlV1();

        /** Główna metoda Gui uruchamia się po instrukcji **/
        while (!Menu.read) {
            Thread.sleep(100);
            if (Menu.read) {
                gui.start();
            }
        }
    }
}
