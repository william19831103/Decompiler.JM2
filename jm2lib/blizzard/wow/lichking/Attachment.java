package jm2lib.blizzard.wow.lichking;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Attachment implements AnimFilesHandler {
   int id = 0;
   int bone = 0;
   Vec3F position = new Vec3F();
   AnimationBlock<Byte> animateAttached;

   public Attachment() {
      this.animateAttached = new AnimationBlock(Byte.TYPE, 1);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.id = in.readInt();
      this.bone = in.readInt();
      this.position.unmarshal(in);
      this.animateAttached.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeInt(this.id);
      out.writeInt(this.bone);
      this.position.marshal(out);
      this.animateAttached.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.animateAttached.writeContent(out);
   }

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.animateAttached.setAnimFiles(animFiles);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tid: ").append(this.id).append("\n\tbone: ").append(this.bone).append("\n\tposition: ").append(this.position).append("\n\tanimateAttached: ").append(this.animateAttached).append("\n}");
      return builder.toString();
   }

   public jm2lib.blizzard.wow.classic.Attachment downConvert(ArrayRef<jm2lib.blizzard.wow.classic.Animation> animations) throws Exception {
      jm2lib.blizzard.wow.classic.Attachment output = new jm2lib.blizzard.wow.classic.Attachment();
      output.id = this.id;
      output.bone = this.bone;
      output.position = this.position;
      output.animateAttached = this.animateAttached.downConvert(animations);
      if (this.animateAttached.isEmpty()) {
         output.animateAttached.addKeyframe(0, (byte)1);
      }

      return output;
   }
}
