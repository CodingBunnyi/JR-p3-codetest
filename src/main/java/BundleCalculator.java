import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class BundleCalculator {
    private static BundleCalculator bundleCalculator = new BundleCalculator();

    private BundleCalculator() {
    }

    public static BundleCalculator getInstance(){
        return bundleCalculator;
    }

    public double calculateTotalByType(String inputType, HashMap<Integer, Integer> bundleMethod) {
        List<Double> container = new ArrayList<>();
        bundleMethod.forEach((k,v) ->
            container.add(v * BundleTable.getInstance().getBundleMapByType(inputType).get(k))
        );
        return container.stream().mapToDouble(i -> i).sum();
    }

    public HashMap<Integer, Integer> calculateBundle (Integer inputNum, Set<Integer> bundleSet) {
        List<Integer> bundleList = new ArrayList<>(bundleSet);
        HashMap<Integer, Integer> bundleMethod = new HashMap<>();

        bundleSet.forEach(key -> bundleMethod.put(key, 0));

        for(int i = bundleList.size() - 1; i >= 0; i--) {
            int divideResult = inputNum / bundleList.get(i);
            int remainder = inputNum % bundleList.get(i);

            bundleMethod.put(bundleList.get(i), divideResult);
            inputNum = remainder;
            if (remainder != 0) {
                if (remainder < Collections.min(bundleList)) {
                    int previous = bundleMethod.get(Collections.min(bundleList));
                    bundleMethod.put(Collections.min(bundleList), previous + 1);
                }
            } else {
                break;
            }
        }
        return bundleMethod;
    }
}
