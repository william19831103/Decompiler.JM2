package jm2lib.blizzard.wow.lichking;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Transparency implements AnimFilesHandler {
   public AnimationBlock<Short> weight;

   public Transparency() {
      this.weight = new AnimationBlock(Short.TYPE, 1);
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

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.weight.setAnimFiles(animFiles);
   }

   public jm2lib.blizzard.wow.classic.Transparency downConvert(ArrayRef<jm2lib.blizzard.wow.classic.Animation> animations) throws Exception {
      jm2lib.blizzard.wow.classic.Transparency output = new jm2lib.blizzard.wow.classic.Transparency();
      output.weight = this.weight.downConvert(animations);
      return output;
   }
}
