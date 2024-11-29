package jm2lib.blizzard.wow.lichking;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.QuatS;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class UVAnimation implements AnimFilesHandler {
   public AnimationBlock<Vec3F> translation = new AnimationBlock(Vec3F.class);
   public AnimationBlock<QuatS> rotation = new AnimationBlock(QuatS.class, 1);
   public AnimationBlock<Vec3F> scale = new AnimationBlock(Vec3F.class, 1);

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

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.translation.setAnimFiles(animFiles);
      this.rotation.setAnimFiles(animFiles);
      this.scale.setAnimFiles(animFiles);
   }

   public jm2lib.blizzard.wow.burningcrusade.UVAnimation downConvert(ArrayRef<jm2lib.blizzard.wow.classic.Animation> animations) throws Exception {
      jm2lib.blizzard.wow.burningcrusade.UVAnimation output = new jm2lib.blizzard.wow.burningcrusade.UVAnimation();
      output.translation = this.translation.downConvert(animations);
      output.rotation = this.rotation.downConvert(animations);
      output.scale = this.scale.downConvert(animations);
      return output;
   }
}
