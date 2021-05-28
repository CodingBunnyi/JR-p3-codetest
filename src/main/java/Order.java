import exceptions.IncompatibleTypeException;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

@Getter
@Setter
public class Order {
    private static final Logger logger = LogManager.getLogger(Order.class);

    private HashMap<String, Integer> orderMap;

    private static Order order = new Order();

    private Order() {
        this.orderMap = new HashMap<>();
    }

    public static Order getInstance(){
        return order;
    }

    public HashMap<String, Integer> readOrder(FileReader fr) {
        BufferedReader br = new BufferedReader(fr);
        HashMap<String, Integer> orderMap = new HashMap<>();

        String line;
        try {
            while((line = br.readLine()) != null && !line.equals("")) {
                String[] orderByType = line.split(" ");

                int orderNum = Integer.parseInt(orderByType[0]);
                String orderType = orderByType[1];
                try {
                    if (BundleTable.getInstance().typeIsExist(orderType)) {
                        orderMap.put(orderType, orderNum);
                    }
                } catch (IncompatibleTypeException incompatibleTypeException) {
                    logger.error("bundle table does not contain social media type" + orderType);
                }
            }
        } catch (IOException ioException) {
            System.out.println("Something went wrong");
        }

        return orderMap;
    }

    public void saveOrder(HashMap<String, Integer> orderMap) {
        this.orderMap = orderMap;
    }


}
