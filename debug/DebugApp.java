package debug;

import java.io.IOException;
import jm2lib.blizzard.io.BlizzardInputStream;
import jm2lib.blizzard.io.BlizzardOutputStream;
import jm2lib.blizzard.wow.M2;

public class DebugApp {
   public static void main(String[] args) throws Exception {
      System.out.println("Debug application");
      BlizzardInputStream in = new BlizzardInputStream("./models/Human/Male/humanmale_HD.m2");
      M2 model = (M2)in.readObject();
      in.close();
      System.out.println("Model read.");
      model.convert(256);
      System.out.println("Model converted.");
      BlizzardOutputStream out = new BlizzardOutputStream("./models/Human/Male/humanmale_HDCL.m2");
      out.writeObject(model);
      out.close();
      System.out.println("Model written.");
      readAndPrint("./models/Human/Male/humanmale_HDCL.m2");
   }

   private static void readAndPrint(String path) throws ClassNotFoundException, IOException {
      BlizzardInputStream in = new BlizzardInputStream(path);
      Object obj = in.readObject();
      System.out.println("Model read. Printing...");
      System.out.println(obj);
      in.close();
   }
}
