import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintTest {
    private static final Logger logger = LogManager.getLogger(PrintTest.class);
    private static BundleTable bundleTable;
    private static Order order;
    private static BundleCalculator bundleCalculator;
    private static Print print;

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
            print = new Print(bundleCalculator);

        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("Can not find order or bundleTableConfig file");
        } catch (IOException IOException) {
            logger.error("Error closing file");
        }
    }

    @Test
    public void printEachTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expectedOutput = "15 FLAC 1957.5\n  1 * 6 810.0\n  1 * 9 1147.5\n";
        Set<Integer> bundleSet = bundleTable.getBundleMapByType("FLAC").keySet();
        print.printEach(15, "FLAC", bundleSet);
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void printAllTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expectedOutput = "13 VID 2670.0\n  2 * 3 1140.0\n  1 * 9 1530.0\n10 IMG 800.0\n  1 * 10 800.0\n15 " +
                "FLAC 1957.5\n  1 * 6 810.0\n  1 * 9 1147.5\n";
        print.printAll(order);
        assertEquals(expectedOutput, outContent.toString());
    }
}
