import lombok.Getter;
import lombok.Setter;

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

    private BundleTable() {
        this.tableMap = new HashMap<>();
    }

    public static BundleTable getInstance(){
        return bundleTable;
    }

    public boolean typeIsExist(String inputType) {
        if (this.tableMap.containsKey(inputType)) {
            return true;
        } else {
            return false;
        }
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
                String[] lineString = line.split(" ");
                List<String> lineList = Arrays.asList(lineString);
                List<String> lineArrayList = new ArrayList<>(lineList);
                String bundleType = lineArrayList.get(0);
                lineArrayList.remove(0);
                HashMap<Integer, Double> bundleMapByType = new HashMap<>();
                for(String bundleMapItem:lineArrayList) {
                    String[] item = bundleMapItem.split(":");
                    Integer key = Integer.parseInt(item[0]);
                    double value = Double.parseDouble(item[1]);
                    bundleMapByType.put(key,value);
                }
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