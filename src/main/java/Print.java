import exceptions.IncompatibleTypeException;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Set;

@Getter
@Setter
public class Print {
    private static Print print = new Print();
    private static final Logger logger = LogManager.getLogger(Print.class);

    private Print () {
    }

    public static Print getInstance(){
        return print;
    }

    public void printEach(Integer inputNum, String inputType, Set<Integer> bundleSet) {
        try {
            BundleTable.getInstance().typeIsExist(inputType);
        } catch (IncompatibleTypeException incompatibleTypeException) {
            logger.error("bundle table does not contain social media type" + inputType);

        }

        HashMap<Integer, Integer> bundleMethod = BundleCalculator.getInstance().calculateBundle(inputNum, bundleSet);
        double total = BundleCalculator.getInstance().calculateTotalByType(inputType, bundleMethod);

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


            orderMap.keySet().forEach(inputType -> {
            Set<Integer> bundleSet  = BundleTable.getInstance().getBundleMapByType(inputType).keySet();

            int inputNum = orderMap.get(inputType);
            if(BundleTable.getInstance().typeIsExist(inputType)) {
                printEach(inputNum, inputType, bundleSet);
            }
        });


    }
}
