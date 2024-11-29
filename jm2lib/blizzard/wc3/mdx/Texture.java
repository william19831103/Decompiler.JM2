package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Texture implements Marshalable {
   public int replaceableID;
   public String fileName = "Skin01.blp";
   public int flags;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.replaceableID = in.readInt();
      this.fileName = in.readString(260);
      this.flags = in.readInt();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeInt(this.replaceableID);
      out.writeString(this.fileName, 260);
      out.writeInt(this.flags);
   }

   public jm2lib.blizzard.wow.classic.Texture upConvert() {
      jm2lib.blizzard.wow.classic.Texture output = new jm2lib.blizzard.wow.classic.Texture();
      output.type = this.replaceableID;
      output.flags = this.flags;
      output.fileName = new ArrayRef(this.fileName);
      return output;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\treplaceableID: ").append(this.replaceableID).append("\n\tfileName: ").append(this.fileName).append("\n\tflags: ").append(this.flags).append("\n}");
      return builder.toString();
   }
}
