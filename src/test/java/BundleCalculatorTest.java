import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

public class BundleCalculatorTest {
    @Test
    public void calculateTotalByTypeTest() {
        HashMap<String, HashMap<Integer, Double>> tableMap = new HashMap<>();
        HashMap<Integer, Double> bundleMapIMG = new HashMap<>();
        bundleMapIMG.put(5,450.0);
        bundleMapIMG.put(10,800.0);
        tableMap.put("IMG",bundleMapIMG);

        HashMap<Integer, Double> bundleMapFLAC = new HashMap<>();
        bundleMapFLAC.put(3,427.5);
        bundleMapFLAC.put(6,810.0);
        bundleMapFLAC.put(9,1147.5);
        tableMap.put("FLAC",bundleMapFLAC);

        HashMap<Integer, Double> bundleMapVID = new HashMap<>();
        bundleMapVID.put(3,570.0);
        bundleMapVID.put(5,900.0);
        bundleMapVID.put(9,1530.0);
        tableMap.put("VID",bundleMapVID);

        BundleTable.getInstance().setBundleTable(tableMap);

        HashMap<Integer, Integer> bundleMethodFLAC = new HashMap<>();
        bundleMethodFLAC.put(6,1);
        bundleMethodFLAC.put(9,1);

        assertEquals(1957.5,BundleCalculator.getInstance().calculateTotalByType("FLAC",bundleMethodFLAC));
    }

    @Test
    public void calculateBundleTest() {
        // To do
        // This function is wrong.
    }
}
