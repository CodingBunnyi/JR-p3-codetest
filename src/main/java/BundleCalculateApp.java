import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class BundleCalculateApp {
    private static final Logger logger = LogManager.getLogger(BundleCalculateApp.class);
    public static void main(String[] args) {
        try {
            FileReader bundleTableConfig = new FileReader("src/main/resources/bundleTableConfig");
            BundleTable.getInstance().setBundleTable(BundleTable.getInstance().readTableConfig(bundleTableConfig));
            bundleTableConfig.close();

            FileReader order = new FileReader("src/main/resources/order");
            Order.getInstance().saveOrder(Order.getInstance().readOrder(order));
            Print.getInstance().printAll(Order.getInstance());
            order.close();
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("Can not find order or bundleTableConfig file");
        } catch (IOException IOException) {
            logger.error("Error closing file");
        }
    }
}
