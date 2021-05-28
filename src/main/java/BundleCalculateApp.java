import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BundleCalculateApp {
    public static void main(String[] args) {
        try {
            FileReader bundleTableConfig = new FileReader("src/main/resources/bundleTableConfig");
            BundleTable.getInstance().setBundleTable(BundleTable.getInstance().readTableConfig(bundleTableConfig));
            bundleTableConfig.close();

            FileReader order = new FileReader("src/main/resources/order");
            Order.getInstance().saveOrder(Order.getInstance().readOrder(order));
            Print.getInstance().printAll(Order.getInstance());
            order.close();
        } catch (FileNotFoundException var3) {
            System.err.println("Couldn't find file");
        } catch (IOException var4) {
            System.err.println("Error closing file");
        }
    }
}
