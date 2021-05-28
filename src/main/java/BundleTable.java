import exceptions.IncompatibleTypeException;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class BundleTable {
    private HashMap<String, HashMap<Integer, Double>> tableMap;
    private static BundleTable bundleTable = new BundleTable();
    private static final Logger logger = LogManager.getLogger(BundleTable.class);

    private BundleTable() {
        this.tableMap = new HashMap<>();
    }

    public static BundleTable getInstance(){
        return bundleTable;
    }

    public boolean typeIsExist(String inputType) {
        if (!this.tableMap.containsKey(inputType)) {
            throw new IncompatibleTypeException();
        }
        return true;
    }

    public HashMap<Integer, Double> getBundleMapByType (String inputType) {
        return this.tableMap.get(inputType);
    }

    public HashMap<String, HashMap<Integer, Double>> readTableConfig(FileReader fr) {
        BufferedReader br = new BufferedReader(fr);
        HashMap<String, HashMap<Integer, Double>> bundleMap= new HashMap<>();

        String line;
        try {
            while((line = br.readLine()) != null) {
                List<String> lineArrayList = new ArrayList<>(Arrays.asList(line.split(" ")));
                String bundleType = lineArrayList.get(0);
                lineArrayList.remove(0);
                HashMap<Integer, Double> bundleMapByType = new HashMap<>();

                lineArrayList.forEach(bundleMapItem -> {
                    String[] item = bundleMapItem.split(":");
                    int key = Integer.parseInt(item[0]);
                    double value = Double.parseDouble(item[1]);
                    bundleMapByType.put(key,value);
                });
                bundleMap.put(bundleType,bundleMapByType);
            }
        } catch (IOException ioException) {
            System.out.println("Something went wrong");
        }
        return bundleMap;
    }

    public void setBundleTable(HashMap<String, HashMap<Integer, Double>> bundleMap) {
        this.tableMap = bundleMap;
    }

}