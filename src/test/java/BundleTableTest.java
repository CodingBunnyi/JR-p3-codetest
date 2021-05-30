import exceptions.FormatException;
import exceptions.IncompatibleTypeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class BundleTableTest {
    private static final Logger logger = LogManager.getLogger(BundleTableTest.class);

    @Test
    public void typeIsExistTest() {
        BundleTable bundleTable = new BundleTable();
        HashMap<String, HashMap<Integer, Double>> tableMap = new HashMap<>();
        HashMap<Integer, Double> bundleMap = new HashMap<>();
        bundleMap.put(7, 300.0);
        tableMap.put("IMG", bundleMap);
        bundleTable.setBundleTable(tableMap);

        Assertions.assertThrows(IncompatibleTypeException.class, () -> {
            bundleTable.typeIsExist("ABC");
        });
        assertTrue(bundleTable.typeIsExist("IMG"));
    }

    @Test
    public void checkBundleTableFormatTest() {
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
            String orderStringLine = "123 3:570 5:900 9:1530";
            String[] orderArrayLine1 = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine1);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "NJS  3:570 5:900 9:1530";
            String[] orderArrayLine1 = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine1);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "  ";
            String[] orderArrayLine1 = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine1);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "NJS 3=570 5=900 9=1530";
            String[] orderArrayLine1 = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine1);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "NJS a=570 b=900 c=1530";
            String[] orderArrayLine1 = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine1);
        });

        Assertions.assertThrows(FormatException.class, () -> {
            String orderStringLine = "NJS 3=asd 5=asda 9=zxc";
            String[] orderArrayLine1 = orderStringLine.split(" ");
            order.checkOrderFormat(orderArrayLine1);
        });
    }

    @Test
    public void readAndSaveBundleTableTest() {
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

        HashMap<String, HashMap<Integer, Double>> exceptedTableMap = new HashMap<>();
        HashMap<Integer, Double> bundleMapIMG = new HashMap<>();
        bundleMapIMG.put(5, 450.0);
        bundleMapIMG.put(10, 800.0);
        exceptedTableMap.put("IMG", bundleMapIMG);

        HashMap<Integer, Double> bundleMapFLAC = new HashMap<>();
        bundleMapFLAC.put(3, 427.5);
        bundleMapFLAC.put(6, 810.0);
        bundleMapFLAC.put(9, 1147.5);
        exceptedTableMap.put("FLAC", bundleMapFLAC);

        HashMap<Integer, Double> bundleMapVID = new HashMap<>();
        bundleMapVID.put(3, 570.0);
        bundleMapVID.put(5, 900.0);
        bundleMapVID.put(9, 1530.0);
        exceptedTableMap.put("VID", bundleMapVID);

        assertEquals(exceptedTableMap, bundleTable.getTableMap());
    }
}
