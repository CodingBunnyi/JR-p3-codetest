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
    public void checkOrderFormatTest(){
        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "2 asmdkamsdkalsm smaksmfkasld";
            String[] orderArrayLine1 = orderStringLine.split(" ");
            Order.getInstance().checkOrderFormat(orderArrayLine1);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "KSJF 2";
            String[] orderArrayLine = orderStringLine.split(" ");
            Order.getInstance().checkOrderFormat(orderArrayLine);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "SFKLASK";
            String[] orderArrayLine = orderStringLine.split(" ");
            Order.getInstance().checkOrderFormat(orderArrayLine);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "15  FLAC";
            String[] orderArrayLine = orderStringLine.split(" ");
            Order.getInstance().checkOrderFormat(orderArrayLine);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = " ";
            String[] orderArrayLine = orderStringLine.split(" ");
            Order.getInstance().checkOrderFormat(orderArrayLine);
        });
    }

    @Test
    public void readAndSaveOrder() {
        // test FileNotFoundException
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            FileReader order = new FileReader("src/test/resources/order1");
            Order.getInstance().readOrder(order);
        });

        //test read and save Order
        HashMap<String, Integer> exceptedOrderMap = new HashMap<>();
        exceptedOrderMap.put("FLAC",15);
        exceptedOrderMap.put("IMG",10);
        exceptedOrderMap.put("VID",13);

        try {
            FileReader bundleTableConfig = new FileReader("src/test/resources/bundleTableConfig");
            BundleTable.getInstance().setBundleTable(BundleTable.getInstance().readTableConfig(bundleTableConfig));
            bundleTableConfig.close();
            FileReader order = new FileReader("src/test/resources/order");
            Order.getInstance().saveOrder(Order.getInstance().readOrder(order));
            order.close();
            assertEquals(exceptedOrderMap,Order.getInstance().getOrderMap());
        } catch(FileNotFoundException fileNotFoundException) {
            logger.error("Can not find order file");
        } catch (IOException IOException) {
            logger.error("Error closing file");
        }
    }
}
