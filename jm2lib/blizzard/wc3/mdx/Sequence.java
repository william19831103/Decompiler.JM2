package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Sequence implements Marshalable {
   public String name = "Stand";
   public int timeStart;
   public int timeEnd;
   public float moveSpeed;
   public int flags;
   public float rarity;
   public int syncPoint;
   public Extent extent = new Extent();

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.name = in.readString(80);
      this.timeStart = in.readInt();
      this.timeEnd = in.readInt();
      this.moveSpeed = in.readFloat();
      this.flags = in.readInt();
      this.rarity = in.readFloat();
      this.syncPoint = in.readInt();
      this.extent.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeString(this.name, 80);
      out.writeInt(this.timeStart);
      out.writeInt(this.timeEnd);
      out.writeFloat(this.moveSpeed);
      out.writeInt(this.flags);
      out.writeFloat(this.rarity);
      out.writeInt(this.syncPoint);
      this.extent.marshal(out);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tname: ").append(this.name).append("\n\ttimeStart: ").append(this.timeStart).append("\n\ttimeEnd: ").append(this.timeEnd).append("\n\tmoveSpeed: ").append(this.moveSpeed).append("\n\tflags: ").append(this.flags).append("\n\trarity: ").append(this.rarity).append("\n\tsyncPoint: ").append(this.syncPoint).append("\n\textent: ").append(this.extent).append("\n}");
      return builder.toString();
   }
}
