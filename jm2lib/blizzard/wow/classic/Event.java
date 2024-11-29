package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Event implements Referencer {
   public byte[] identifier = new byte[4];
   public int data = 0;
   public int bone = 0;
   public Vec3F position = new Vec3F();
   public EventAnimationBlock enabled = new EventAnimationBlock();

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      for(byte i = 0; i < 4; ++i) {
         this.identifier[i] = in.readByte();
      }

      this.data = in.readInt();
      this.bone = in.readInt();
      this.position.unmarshal(in);
      this.enabled.unmarshal(in);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tidentifier: ").append(new String(this.identifier)).append("\n\tdata: ").append(this.data).append("\n\tbone: ").append(this.bone).append("\n\tposition: ").append(this.position).append("\n\tenabled: ").append(this.enabled).append("\n}");
      return builder.toString();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.write(this.identifier);
      out.writeInt(this.data);
      out.writeInt(this.bone);
      this.position.marshal(out);
      this.enabled.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.enabled.writeContent(out);
   }
}
