package jm2lib.blizzard.wow.lichking;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class SubmeshAnimation implements AnimFilesHandler {
   public AnimationBlock<Vec3F> color = new AnimationBlock(Vec3F.class);
   public AnimationBlock<Short> alpha;

   public SubmeshAnimation() {
      this.alpha = new AnimationBlock(Short.TYPE, 1);
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

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.color.setAnimFiles(animFiles);
      this.alpha.setAnimFiles(animFiles);
   }

   public jm2lib.blizzard.wow.classic.SubmeshAnimation downConvert(ArrayRef<jm2lib.blizzard.wow.classic.Animation> animations) throws Exception {
      jm2lib.blizzard.wow.classic.SubmeshAnimation output = new jm2lib.blizzard.wow.classic.SubmeshAnimation();
      output.color = this.color.downConvert(animations);
      output.alpha = this.alpha.downConvert(animations);
      return output;
   }
}
