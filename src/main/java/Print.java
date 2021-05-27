import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Print {
    private static Print print = new Print();

    private Print () {
    }

    public static Print getInstance(){
        return print;
    }

    public void printEach(Integer inputNum, String inputType, Set<Integer> bundleSet) {
        HashMap<Integer,Integer> bundleMethod = BundleCalculator.getInstance().calculateBundle(inputNum, bundleSet);
        double total = BundleCalculator.getInstance().calculateTotalByType(inputType,bundleMethod);

        System.out.println(inputNum + " " + inputType + " " + total);

        bundleMethod.forEach((k,v) -> {
            if (v != 0) {
                double price = BundleTable.getInstance().getBundleMapByType(inputType).get(k);
                double individualTotal = price * v;
                System.out.println("  "  + v + " * " + k + " " + individualTotal);
            }
        });
    }

    public void printAll(Order order) {
        HashMap<String, Integer> orderMap = order.getOrderMap();

        for (String inputType : orderMap.keySet()) {
            Set<Integer> bundleSet  = BundleTable.getInstance().getBundleMapByType(inputType).keySet();

            int inputNum = orderMap.get(inputType);
            if(BundleTable.getInstance().typeIsExist(inputType)) {
                printEach(inputNum, inputType, bundleSet);
            }
        }
    }


}
