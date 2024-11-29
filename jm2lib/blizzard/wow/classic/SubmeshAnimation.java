package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class SubmeshAnimation implements Referencer {
   public AnimationBlock<Vec3F> color = new AnimationBlock(Vec3F.class);
   public AnimationBlock<Short> alpha;

   public SubmeshAnimation() {
      this.alpha = new AnimationBlock(Short.TYPE);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tcolor: ").append(this.color).append("\n\talpha: ").append(this.alpha).append("\n}");
      return builder.toString();
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.color.unmarshal(in);
      this.alpha.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      this.color.marshal(out);
      this.alpha.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.color.writeContent(out);
      this.alpha.writeContent(out);
   }
}
