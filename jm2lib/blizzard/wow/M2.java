package jm2lib.blizzard.wow;

import java.io.IOException;
import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Map;
import jm2lib.blizzard.io.BlizzardFile;
import jm2lib.blizzard.wow.cataclysm.Model;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class M2 implements BlizzardFile {
   private M2Format model;
   private Map<Integer, String> formats = new HashMap();
   public static final String FILE_SUFFIX = ".m2";

   public M2() {
      this.formats.put(256, "jm2lib.blizzard.wow.classic.Model");
      this.formats.put(260, "jm2lib.blizzard.wow.burningcrusade.Model");
      this.formats.put(261, "jm2lib.blizzard.wow.burningcrusade.Model");
      this.formats.put(262, "jm2lib.blizzard.wow.burningcrusade.Model");
      this.formats.put(263, "jm2lib.blizzard.wow.lateburningcrusade.Model");
      this.formats.put(264, "jm2lib.blizzard.wow.lichking.Model");
      this.formats.put(272, "jm2lib.blizzard.wow.cataclysm.Model");
      this.formats.put(272, "jm2lib.blizzard.wow.cataclysm.Model");
      this.formats.put(272, "jm2lib.blizzard.wow.cataclysm.Model");
      this.formats.put(274, "jm2lib.blizzard.wow.legion.Model");

      try {
         Object obj = Class.forName((String)this.formats.get(274)).newInstance();
         this.model = (M2Format)obj;
      } catch (IllegalAccessException | ClassNotFoundException | InstantiationException var2) {
         var2.printStackTrace();
      }

   }

   public M2Format getModel() {
      return this.model;
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      int version = in.readInt();

      try {
         Object obj = Class.forName((String)this.formats.get(version)).newInstance();
         if (!(obj instanceof M2Format)) {
            throw new InvalidClassException("not an M2 format");
         }

         this.model = (M2Format)obj;
      } catch (IllegalAccessException | ClassNotFoundException | InstantiationException var5) {
         System.err.println("Error : Could not find a matching M2 format for your version. Trying to force load.");
         var5.printStackTrace();
         this.model = new Model();
      }

      this.model.version = version;
      this.model.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeInt(this.model.version);
      this.model.marshal(out);
   }

   public void convert(int newVersion) throws Exception {
      do {
         if (this.formats.get(this.model.version) == this.formats.get(newVersion)) {
            this.model.version = newVersion;
            return;
         }

         if (this.model.version < newVersion) {
            this.model = this.model.upConvert();
         } else if (this.model.version > newVersion) {
            this.model = this.model.downConvert();
         } else if (this.model.version == newVersion) {
            throw new Exception("Error : Equal versions but different class.");
         }
      } while(this.model != null);

      throw new UnsupportedOperationException("Not implemented.");
   }

   public String toString() {
      return this.model.toString();
   }

   public int getVersion() {
      return this.model.version;
   }
}
