import java.util.HashMap;
public class BundleTable {
    private String bundleType;
    private Integer bundleNum;
    private Integer bundlePrice;
    private HashMap<String, HashMap<Integer, Double>> tableMap;
    private static BundleTable bundleTable = new BundleTable();
//    private HashMap<Integer, Integer> bundleMap;

    private BundleTable() {
//        this.bundleType = bundleType;
//        this.bundleNum = bundleNum;
//        this.bundlePrice = bundlePrice;
        this.tableMap = new HashMap<>();
//        this.bundleMap = new HashMap<>();
    }

    public static BundleTable getInstance(){
        return bundleTable;
    }


    public String getInputType(String inputType) {
        if (typeIsExist(inputType)) {
            return inputType;
        } else {
            return null;
        }
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
    public void setBundleRecord (String setType, Integer setNum, Double setPrice) {
        HashMap<Integer,Double> bundleMapRecord = new HashMap<>();

        if (!this.typeIsExist(setType)) {
            bundleMapRecord.put(setNum,setPrice);
            this.tableMap.put(setType,bundleMapRecord);
        } else {
            getBundleMapByType(setType).put(setNum,setPrice);
        }
    }

}