import core.RestaurantSystem;
import gui.auth.HomeGUI;

public class MainGUI {
    public static void main(String[] args) {
        RestaurantSystem system = new RestaurantSystem();
        new HomeGUI(system);
    }
}
