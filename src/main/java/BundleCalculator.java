import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BundleCalculator {
private Order order;
private BundleTable bundleTable;
private HashMap<Integer, Integer> bundleMethod;

    public BundleCalculator(Order order,BundleTable bundleTable) {
        this.order = order;
        this.bundleTable = bundleTable;
        this.bundleMethod = new HashMap<>();
    }

    public Order getOrder() {
        return order;
    }

    public void iterate(Order order) {
        HashMap<String, Integer> orderMap = order.getOrderMap();
        double total;

        for (String inputType : orderMap.keySet()) {
            Set<Integer> bundleSet  = this.bundleTable.getBundleMapByType(inputType).keySet();
            HashMap<Integer,Integer> initialMap= new HashMap<>();
            HashMap<Integer,Integer> bundleMethod;
            for (Integer key : bundleSet) {
                initialMap.put(key, 0);
                this.bundleMethod = initialMap;
            }

            int inputNum = orderMap.get(inputType);
            if(bundleTable.typeIsExist(inputType)) {
                bundleMethod = calculateBundle(inputNum, bundleSet); //{5 = 0 ; 10 =1}
                total = calculateTotalByType(inputType,bundleMethod);

                System.out.println(inputNum + " " + inputType + " " + total);

                bundleMethod.forEach((k,v) -> {
                    if (v != 0) {
                        double price = this.bundleTable.getBundleMapByType(inputType).get(k);
                        double indiTotal = price * v;
                        System.out.println(v + " * " + k + " " + indiTotal);
                    }
                });
            }
        }
    }

    public double calculateTotalByType(String inputType, HashMap<Integer,Integer> bundleMethod) {
        List<Double> container = new ArrayList<>();
        bundleMethod.forEach((k,v) ->
            container.add(v * this.bundleTable.getBundleMapByType(inputType).get(k))
        );
        return container.stream().mapToDouble(i -> i).sum();
    }


    public HashMap<Integer,Integer> calculateBundle (Integer current, Set<Integer> bundleSetNumByType) {
        List<Integer> bundleList = new ArrayList<>(bundleSetNumByType);
        for(int i = bundleList.size() - 1; i >= 0; i--) {
            int divideResult = current / bundleList.get(i);
            int remainder = current % bundleList.get(i);

            if (divideResult >= 1) {
                this.bundleMethod.put(bundleList.get(i),divideResult);
                current = remainder;
            } else {
                if (remainder != 0) {
                    if (remainder < Collections.min(bundleList)) {
                        int previous = this.bundleMethod.get(Collections.min(bundleList));
                        this.bundleMethod.put(Collections.min(bundleList),previous + 1);
                    }
                } else {
                    break;
                }
            }
        }
        return this.bundleMethod;

    }






    public static void main(String[] args) {
        try {

            BundleTable bundleTable = new BundleTable();
            bundleTable.setBundleRecord("IMG",5,450.0);
            bundleTable.setBundleRecord("IMG",10,800.0);

            bundleTable.setBundleRecord("FLAC",3,427.5);
            bundleTable.setBundleRecord("FLAC",6,810.0);
            bundleTable.setBundleRecord("FLAC",9,1147.5);

            bundleTable.setBundleRecord("VID",3,570.0);
            bundleTable.setBundleRecord("VID",5,900.0);
            bundleTable.setBundleRecord("VID",9,1530.0);

//
//            Set<Integer> bundleSet  = bundleTable.getBundleMapByType("IMG").keySet();
//
//
//            System.out.println(bundleTable.getBundleMapByType("FLAC"));
//            System.out.println(bundleSet);



            Order order = new Order();
            FileReader fr = new FileReader("src/main/resources/order");
            order.saveOrder(order.readOrder(fr));
            fr.close();

            BundleCalculator bundleCalculator = new BundleCalculator(order,bundleTable);
//
//            for (Integer key : bundleSet) {
//                bundleCalculator.bundleMethod.put(key, 0);
//            }
//
//            HashMap bundle = bundleCalculator.calculateBundle(10,bundleSet);
//            System.out.println(bundleCalculator.bundleMethod);

            bundleCalculator.iterate(order);
//            System.out.println(bundleCalculator.calculateTotalByType("IMG",bundle));






//            System.out.println(order.getNumByType("IMG"));

        } catch (FileNotFoundException var3) {
            System.err.println("Couldn't find file");
        } catch (IOException var4) {
            System.err.println("Error closing file");
        }
    }
}
