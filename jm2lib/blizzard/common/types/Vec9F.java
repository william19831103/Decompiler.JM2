package jm2lib.blizzard.common.types;

import java.io.IOException;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Vec9F implements BlizzardVector {
   private Vec3F x = new Vec3F();
   private Vec3F y = new Vec3F();
   private Vec3F z = new Vec3F();

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.x.unmarshal(in);
      this.y.unmarshal(in);
      this.z.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      this.x.marshal(out);
      this.y.marshal(out);
      this.z.marshal(out);
   }

   public String toString() {
      return "(" + this.x + "," + this.y + "," + this.z + ")";
   }
}
