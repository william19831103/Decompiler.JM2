package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Texture implements Referencer {
   public int type = 1;
   public int flags = 0;
   public ArrayRef<Byte> fileName;

   public Texture() {
      this.fileName = new ArrayRef(Byte.TYPE);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.type = in.readInt();
      this.flags = in.readInt();
      this.fileName.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeInt(this.type);
      out.writeInt(this.flags);
      this.fileName.marshal(out);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\ttype: ").append(this.type).append("\n\tflags: ").append(Integer.toBinaryString(this.flags));
      if (this.type == 0) {
         builder.append("\n\tfileName: ").append(this.fileName.toNameString());
      }

      builder.append("\n}");
      return builder.toString();
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.fileName.writeContent(out);
   }
}
