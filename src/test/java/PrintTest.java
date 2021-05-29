import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintTest {
    private static final Logger logger = LogManager.getLogger(PrintTest.class);
    @BeforeAll
    public static void setUp() {
        try {
            FileReader bundleTableConfig = new FileReader("src/main/resources/bundleTableConfig");
            BundleTable.getInstance().setBundleTable(BundleTable.getInstance().readTableConfig(bundleTableConfig));
            bundleTableConfig.close();

            FileReader order = new FileReader("src/main/resources/order");
            Order.getInstance().saveOrder(Order.getInstance().readOrder(order));
            order.close();
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("Can not find order or bundleTableConfig file");
        } catch (java.io.IOException IOException) {
            logger.error("Error closing file");
        }
    }

    @Test
    public void printEachTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expectedOutput = "15 FLAC 1957.5\n  1 * 6 810.0\n  1 * 9 1147.5\n";
        Set<Integer> bundleSet = BundleTable.getInstance().getBundleMapByType("FLAC").keySet();
        Print.getInstance().printEach(15, "FLAC", bundleSet);
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void printAllTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expectedOutput = "13 VID 2670.0\n  2 * 3 1140.0\n  1 * 9 1530.0\n10 IMG 800.0\n  1 * 10 800.0\n15 " +
                "FLAC 1957.5\n  1 * 6 810.0\n  1 * 9 1147.5\n";
        Print.getInstance().printAll(Order.getInstance());
        assertEquals(expectedOutput, outContent.toString());
    }
}
