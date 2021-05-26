import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

        for (String inputType : orderMap.keySet()) {
            Set<Integer> bundleSet  = this.bundleTable.getBundleMapByType(inputType).keySet();
            for (Integer key : bundleSet) {
                this.bundleMethod.put(key, 0);
            }

            int inputNum = orderMap.get(inputType);
            if(bundleTable.typeIsExist(inputType)) {

                calculateBundle(inputNum, bundleSet);

                System.out.println(this.bundleMethod);
            }
        }
    }

    public HashMap<Integer,Integer> calculateBundle (Integer current, Set<Integer> bundleSetNumByType) {
        int max = Collections.max(bundleSetNumByType);
        int bundleNum = current/max;
        if (bundleNum != 0) {
            this.bundleMethod.put(max,bundleNum);
            bundleSetNumByType.remove(max);
            current = current % max;
            calculateBundle(current,bundleSetNumByType);
        } else {
            int remain = current % max;
            if (!bundleSetNumByType.isEmpty()){
                int min = Collections.min(bundleSetNumByType);
                if(remain != 0) {
                    if (remain < min) {
                        int previous = 0;
                        previous = this.bundleMethod.get(min);
                        this.bundleMethod.put(Collections.min(bundleSetNumByType),previous + 1);
                    } else {
                        calculateBundle(remain, bundleSetNumByType);
                    }
                }
            }
        }
        return this.bundleMethod;
    }






    public static void main(String[] args) {
        try {

            BundleTable bundleTable = new BundleTable();
            bundleTable.setBundleRecord("FLAC",3,300);
            bundleTable.setBundleRecord("FLAC",6,500);
            bundleTable.setBundleRecord("FLAC",9,600);


            Set<Integer> bundleSet  = bundleTable.getBundleMapByType("FLAC").keySet();


            System.out.println(bundleTable.getBundleMapByType("FLAC"));
            System.out.println(bundleSet);



            Order order = new Order();
            FileReader fr = new FileReader("src/main/resources/order");
            order.saveOrder(order.readOrder(fr));
            fr.close();

            BundleCalculator bundleCalculator = new BundleCalculator(order,bundleTable);

//            for (Integer key : bundleSet) {
//                bundleCalculator.bundleMethod.put(key, 0);
//            }
//
//            bundleCalculator.calculateBundle(20,bundleSet);
//            System.out.println(bundleCalculator.bundleMethod);

            bundleCalculator.iterate(order);





//            System.out.println(order.getNumByType("IMG"));

        } catch (FileNotFoundException var3) {
            System.err.println("Couldn't find file");
        } catch (IOException var4) {
            System.err.println("Error closing file");
        }
    }
}
