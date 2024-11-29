package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Model implements Marshalable {
   public String name = "NullModel";
   public String animationFileName = "";
   public Extent extent = new Extent();
   public int blendTime = 0;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.name = in.readString(80);
      this.animationFileName = in.readString(260);
      this.extent.unmarshal(in);
      this.blendTime = in.readInt();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeString(this.name, 80);
      out.writeString(this.animationFileName, 260);
      this.extent.marshal(out);
      out.writeInt(this.blendTime);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tname: ").append(this.name).append("\n\tanimationFileName: ").append(this.animationFileName).append("\n\textent: ").append(this.extent).append("\n\tblendTime: ").append(this.blendTime).append("\n}");
      return builder.toString();
   }
}
