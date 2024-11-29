package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Attachment implements Referencer {
   public int id = 0;
   public int bone = 0;
   public Vec3F position = new Vec3F();
   public AnimationBlock<Byte> animateAttached;

   public Attachment() {
      this.animateAttached = new AnimationBlock(Byte.TYPE);
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

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tid: ").append(this.id).append("\n\tbone: ").append(this.bone).append("\n\tposition: ").append(this.position).append("\n\tanimateAttached: ").append(this.animateAttached).append("\n}");
      return builder.toString();
   }
}
