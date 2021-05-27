import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BundleCalculator {

    private static BundleCalculator bundleCalculator = new BundleCalculator();


    private BundleCalculator() {

    }

    public static BundleCalculator getInstance(){
        return bundleCalculator;
    }

//    public void iterate(Order order) {
//        HashMap<String, Integer> orderMap = order.getOrderMap();
//
//        for (String inputType : orderMap.keySet()) {
//            Set<Integer> bundleSet  = BundleTable.getInstance().getBundleMapByType(inputType).keySet();
//
//            int inputNum = orderMap.get(inputType);
//            if(BundleTable.getInstance().typeIsExist(inputType)) {
//                print(inputNum, inputType, bundleSet);
//            }
//        }
//    }
//
//    public void print(Integer inputNum, String inputType, Set<Integer> bundleSet) {
//        HashMap<Integer,Integer> bundleMethod = calculateBundle(inputNum, bundleSet);
//        double total = calculateTotalByType(inputType,bundleMethod);
//
//        System.out.println(inputNum + " " + inputType + " " + total);
//
//        bundleMethod.forEach((k,v) -> {
//            if (v != 0) {
//                double price = BundleTable.getInstance().getBundleMapByType(inputType).get(k);
//                double individualTotal = price * v;
//                System.out.println("  "  + v + " * " + k + " " + individualTotal);
//            }
//        });
//    }

    public double calculateTotalByType(String inputType, HashMap<Integer,Integer> bundleMethod) {
        List<Double> container = new ArrayList<>();
        bundleMethod.forEach((k,v) ->
            container.add(v * BundleTable.getInstance().getBundleMapByType(inputType).get(k))
        );
        return container.stream().mapToDouble(i -> i).sum();
    }

    public HashMap<Integer,Integer> calculateBundle (Integer current, Set<Integer> bundleSet) {
        List<Integer> bundleList = new ArrayList<>(bundleSet);
        HashMap<Integer,Integer> bundleMethod = new HashMap<>();
        for (Integer key : bundleSet) {
            bundleMethod.put(key, 0);
        }

        for(int i = bundleList.size() - 1; i >= 0; i--) {
            int divideResult = current / bundleList.get(i);
            int remainder = current % bundleList.get(i);

            bundleMethod.put(bundleList.get(i),divideResult);
            current = remainder;

            if (remainder != 0) {
                if (remainder < Collections.min(bundleList)) {
                    int previous = bundleMethod.get(Collections.min(bundleList));
                    bundleMethod.put(Collections.min(bundleList),previous + 1);
                }
            } else {
                break;
            }
        }
        return bundleMethod;
    }

    public static void main(String[] args) {
//        try {
//
//            BundleTable bundleTable = BundleTable.getInstance();
//            bundleTable.setBundleRecord("IMG",5,450.0);
//            bundleTable.setBundleRecord("IMG",10,800.0);
//
//            bundleTable.setBundleRecord("FLAC",3,427.5);
//            bundleTable.setBundleRecord("FLAC",6,810.0);
//            bundleTable.setBundleRecord("FLAC",9,1147.5);
//
//            bundleTable.setBundleRecord("VID",3,570.0);
//            bundleTable.setBundleRecord("VID",5,900.0);
//            bundleTable.setBundleRecord("VID",9,1530.0);
////
////
////            HashMap<Integer,Integer> bundleMethod = new HashMap<>();
////            Set<Integer> bundleSet  = bundleTable.getBundleMapByType("IMG").keySet();
//
//            Order order = new Order();
//            FileReader fr = new FileReader("src/main/resources/order");
//            order.saveOrder(order.readOrder(fr));
//            fr.close();
//
//            BundleCalculator bundleCalculator = new BundleCalculator(order);
//
//
////            for (Integer key : bundleSet) {
////                bundleMethod.put(key, 0);
////            }
////
////            HashMap bundle = bundleCalculator.calculateBundle(10,bundleSet);
////            System.out.println(bundle);
//
//
//            bundleCalculator.iterate(order);
//
//        } catch (FileNotFoundException var3) {
//            System.err.println("Couldn't find file");
//        } catch (IOException var4) {
//            System.err.println("Error closing file");
//        }
    }
}
