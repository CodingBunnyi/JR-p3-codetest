import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class BundleCalculateApp {
    private static final Logger logger = LogManager.getLogger(BundleCalculateApp.class);

    public static void main(String[] args) {
        try {
            BundleTable bundleTable = new BundleTable();
            FileReader bundleTableConfig = new FileReader("src/main/resources/bundleTableConfig");
            bundleTable.setBundleTable(bundleTable.readTableConfig(bundleTableConfig));
            bundleTableConfig.close();

            Order order = new Order(bundleTable);
            FileReader orderInput = new FileReader("src/main/resources/order");
            order.saveOrder(order.readOrder(orderInput));
            orderInput.close();

            BundleCalculator bundleCalculator = new BundleCalculator(order);
            Print print = new Print(bundleCalculator);

            print.printAll(order);
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("Can not find order or bundleTableConfig file");
        } catch (IOException IOException) {
            logger.error("Error closing file");
        }
    }
}
