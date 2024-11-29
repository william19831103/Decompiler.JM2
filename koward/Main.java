package koward;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import jm2lib.blizzard.io.BlizzardInputStream;
import jm2lib.blizzard.io.BlizzardOutputStream;
import jm2lib.blizzard.wow.M2;
import jm2lib.blizzard.wow.MD21;
import jm2lib.io.Marshalable;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class Main {
   private static final String HELP = "(example with Frog.m2 to Classic)\njava -jar jm2converter.jar -in Frog.m2 -out FrogConverted.m2 -cl\n";
   private static final Options options = new Options();
   private static final Map<String, Integer> map;

   static {
      options.addOption("in", "input", true, "path to input file");
      options.addOption("out", "output", true, "path to output file");
      options.addOption("cl", "classic", false, "convert to Classic");
      options.addOption("bc", "burningcrusade", false, "convert to The Burning Crusade");
      options.addOption("lbc", "lateburningcrusade", false, "convert to The Burning Crusade (late versions), better for particles");
      options.addOption("lk", "lichking", false, "convert to Wrath of the Lich King");
      options.addOption("cata", "cataclysm", false, "convert to Cataclysm");
      options.addOption("mop", "pandaria", false, "convert to Mists of Pandaria");
      options.addOption("wod", "draenor", false, "convert to Warlords of Draenor");
      options.addOption("leg", "legion", false, "convert to Legion (Build 20810)");
      map = new HashMap();
      map.put("classic", 256);
      map.put("burningcrusade", 260);
      map.put("lateburningcrusade", 263);
      map.put("lichking", 264);
      map.put("cataclysm", 272);
      map.put("pandaria", 272);
      map.put("draenor", 272);
      map.put("legion", 274);
   }

   public static void main(String[] args) throws Exception {
      System.out.println("[[ Java M2 Converter by Koward v1.0.8b-beta ]]");
      HelpFormatter formatter = new HelpFormatter();
      CommandLineParser parser = new DefaultParser();
      CommandLine cmd = parser.parse(options, args);
      if (!cmd.hasOption("input") || !cmd.hasOption("output")) {
         System.err.println("Error : No input or/and output specified.");
         formatter.printHelp("(example with Frog.m2 to Classic)\njava -jar jm2converter.jar -in Frog.m2 -out FrogConverted.m2 -cl\n", options);
         System.exit(1);
      }

      BlizzardInputStream in = new BlizzardInputStream(cmd.getOptionValue("input"));
      Marshalable obj = (Marshalable)in.readObject();
      in.close();
      M2 model;
      if (obj instanceof M2) {
         model = (M2)obj;
      } else {
         if (!(obj instanceof MD21)) {
            throw new Exception("Unknown structure");
         }

         model = ((MD21)obj).getM2();
      }

      System.out.println(cmd.getOptionValue("input") + " read.");
      int newVersion = convertModel(model, cmd);
      System.out.println("Conversion completed.");
      BlizzardOutputStream out = new BlizzardOutputStream(cmd.getOptionValue("output"));
      if (newVersion == 274) {
         MD21 pack = new MD21();
         pack.setM2(model);
         out.writeObject(pack);
      } else {
         out.writeObject(model);
      }

      System.out.println(cmd.getOptionValue("output") + " written.");
      out.close();
   }

   private static int convertModel(M2 model, CommandLine cmd) throws Exception {
      boolean converted = false;
      int oldVersion = model.getVersion();
      int newVersion = oldVersion;
      Iterator var6 = map.entrySet().iterator();

      while(var6.hasNext()) {
         Entry<String, Integer> entry = (Entry)var6.next();
         String option = (String)entry.getKey();
         Integer version = (Integer)entry.getValue();
         if (cmd.hasOption(option)) {
            model.convert(version);
            converted = true;
            newVersion = version;
         }
      }

      if (!converted) {
         System.err.println("Warning : no version specified. The model has not been converted.");
      } else if (oldVersion == newVersion) {
         System.err.println("Warning : original version and new version are identical.");
      }

      return newVersion;
   }
}
