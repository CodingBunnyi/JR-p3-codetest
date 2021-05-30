import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class BundleCalculatorTest {
    private static final Logger logger = LogManager.getLogger(PrintTest.class);
    private static BundleTable bundleTable;
    private static Order order;
    private static BundleCalculator bundleCalculator;

    @BeforeAll
    public static void setUp() {
        try {
            bundleTable = new BundleTable();
            FileReader bundleTableConfig = new FileReader("src/main/resources/bundleTableConfig");
            bundleTable.setBundleTable(bundleTable.readTableConfig(bundleTableConfig));
            bundleTableConfig.close();

            order = new Order(bundleTable);
            FileReader orderInput = new FileReader("src/main/resources/order");
            order.saveOrder(order.readOrder(orderInput));
            orderInput.close();

            bundleCalculator = new BundleCalculator(order);

        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("Can not find order or bundleTableConfig file");
        } catch (IOException IOException) {
            logger.error("Error closing file");
        }
    }

    @Test
    public void calculateTotalByTypeTest() {
        HashMap<Integer, Integer> bundleMethodFLAC = new HashMap<>();
        bundleMethodFLAC.put(6, 1);
        bundleMethodFLAC.put(9, 1);
        assertEquals(1957.5, bundleCalculator.calculateTotalByType("FLAC", bundleMethodFLAC));
    }

    @Test
    public void calculateBundleTest() {
        // To do
        // This function is wrong.
    }
}
