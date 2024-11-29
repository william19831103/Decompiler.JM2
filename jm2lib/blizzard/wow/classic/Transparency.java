package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Transparency implements Referencer {
   public AnimationBlock<Short> weight;

   public Transparency() {
      this.weight = new AnimationBlock(Short.TYPE);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.weight.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      this.weight.marshal(out);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tweight: ").append(this.weight).append("\n}");
      return builder.toString();
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.weight.writeContent(out);
   }
}
