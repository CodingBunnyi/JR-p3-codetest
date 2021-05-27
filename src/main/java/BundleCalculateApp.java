import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BundleCalculateApp {
    public static void main(String[] args) {
        try {
            BundleTable.getInstance().setBundleRecord("IMG",5,450.0);
            BundleTable.getInstance().setBundleRecord("IMG",10,800.0);

            BundleTable.getInstance().setBundleRecord("FLAC",3,427.5);
            BundleTable.getInstance().setBundleRecord("FLAC",6,810.0);
            BundleTable.getInstance().setBundleRecord("FLAC",9,1147.5);

            BundleTable.getInstance().setBundleRecord("VID",3,570.0);
            BundleTable.getInstance().setBundleRecord("VID",5,900.0);
            BundleTable.getInstance().setBundleRecord("VID",9,1530.0);

            FileReader fr = new FileReader("src/main/resources/order");
            Order.getInstance().saveOrder(Order.getInstance().readOrder(fr));
            Print.getInstance().printAll(Order.getInstance());
            fr.close();
        } catch (FileNotFoundException var3) {
            System.err.println("Couldn't find file");
        } catch (IOException var4) {
            System.err.println("Error closing file");
        }
    }
}
