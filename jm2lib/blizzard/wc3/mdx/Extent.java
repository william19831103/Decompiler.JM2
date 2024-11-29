package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Extent implements Marshalable {
   float boundsRadius = 0.0F;
   Vec3F minimum = new Vec3F();
   Vec3F maximum = new Vec3F();

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.boundsRadius = in.readFloat();
      this.minimum.unmarshal(in);
      this.maximum.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeFloat(this.boundsRadius);
      this.minimum.marshal(out);
      this.maximum.marshal(out);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(" {\n\tboundsRadius: ").append(this.boundsRadius).append("\n\tminimum: ").append(this.minimum).append("\n\tmaximum: ").append(this.maximum).append("\n}");
      return builder.toString();
   }
}
