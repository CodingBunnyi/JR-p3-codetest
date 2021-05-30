import exceptions.FormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private static final Logger logger = LogManager.getLogger(OrderTest.class);

    @Test
    public void checkOrderFormatTest() {
        BundleTable bundleTable = new BundleTable();
        try {
            FileReader bundleTableConfig = new FileReader("src/main/resources/bundleTableConfig");
            bundleTable.setBundleTable(bundleTable.readTableConfig(bundleTableConfig));
            bundleTableConfig.close();
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("Can not find order or bundleTableConfig file");
        } catch (IOException IOException) {
            logger.error("Error closing file");
        }

        Order order = new Order(bundleTable);

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "2 asmdkamsdkalsm smaksmfkasld";
            String[] orderArrayLine1 = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine1);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "KSJF 2";
            String[] orderArrayLine = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "SFKLASK";
            String[] orderArrayLine = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "15  FLAC";
            String[] orderArrayLine = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = " ";
            String[] orderArrayLine = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine);
        });
    }

    @Test
    public void readAndSaveOrder() {
        HashMap<String, Integer> exceptedOrderMap = new HashMap<>();
        exceptedOrderMap.put("FLAC", 15);
        exceptedOrderMap.put("IMG", 10);
        exceptedOrderMap.put("VID", 13);

        try {
            BundleTable bundleTable = new BundleTable();
            Order order = new Order(bundleTable);
            FileReader bundleTableConfig = new FileReader("src/test/resources/bundleTableConfig");
            bundleTable.setBundleTable(bundleTable.readTableConfig(bundleTableConfig));
            bundleTableConfig.close();
            FileReader orderInput = new FileReader("src/test/resources/order");
            order.saveOrder(order.readOrder(orderInput));
            orderInput.close();
            assertEquals(exceptedOrderMap, order.getOrderMap());
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("Can not find order file");
        } catch (IOException IOException) {
            logger.error("Error closing file");
        }
    }
}
