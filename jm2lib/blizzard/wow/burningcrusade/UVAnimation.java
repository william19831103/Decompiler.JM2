package jm2lib.blizzard.wow.burningcrusade;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.QuatS;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.blizzard.wow.classic.AnimationBlock;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class UVAnimation implements Referencer {
   public AnimationBlock<Vec3F> translation = new AnimationBlock(Vec3F.class);
   public AnimationBlock<QuatS> rotation = new AnimationBlock(QuatS.class);
   public AnimationBlock<Vec3F> scale = new AnimationBlock(Vec3F.class);

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.translation.unmarshal(in);
      this.rotation.unmarshal(in);
      this.scale.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      this.translation.marshal(out);
      this.rotation.marshal(out);
      this.scale.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.translation.writeContent(out);
      this.rotation.writeContent(out);
      this.scale.writeContent(out);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\ttranslation: ").append(this.translation).append("\n\trotation: ").append(this.rotation).append("\n\tscale: ").append(this.scale).append("\n}");
      return builder.toString();
   }

   public jm2lib.blizzard.wow.classic.UVAnimation downConvert() {
      jm2lib.blizzard.wow.classic.UVAnimation output = new jm2lib.blizzard.wow.classic.UVAnimation();
      output.translation = this.translation;
      output.rotation.interpolationType = this.rotation.interpolationType;
      output.rotation.globalSequence = this.rotation.globalSequence;
      output.rotation.ranges = this.rotation.ranges;
      output.rotation.timestamps = this.rotation.timestamps;

      for(int i = 0; i < this.rotation.values.size(); ++i) {
         output.rotation.values.add(((QuatS)this.rotation.values.get(i)).toQuatF());
      }

      output.scale = this.scale;
      return output;
   }
}
