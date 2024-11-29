package jm2lib.blizzard.wow.cataclysm.skin;

import java.io.IOException;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class ShadowBatch implements Marshalable {
   public byte flags;
   public byte flags2;
   public short unknown1;
   public short submeshID;
   public short textureID;
   public short colorID;
   public short transparencyID;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.flags = in.readByte();
      this.flags2 = in.readByte();
      this.unknown1 = in.readShort();
      this.submeshID = in.readShort();
      this.textureID = in.readShort();
      this.colorID = in.readShort();
      this.transparencyID = in.readShort();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeByte(this.flags);
      out.writeByte(this.flags2);
      out.writeShort(this.unknown1);
      out.writeShort(this.submeshID);
      out.writeShort(this.textureID);
      out.writeShort(this.colorID);
      out.writeShort(this.transparencyID);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tflags: ").append(this.flags).append("\n\tflags2: ").append(this.flags2).append("\n\tunknown1: ").append(this.unknown1).append("\n\tsubmeshID: ").append(this.submeshID).append("\n\ttextureID: ").append(this.textureID).append("\n\tcolorID: ").append(this.colorID).append("\n\ttransparencyID: ").append(this.transparencyID).append("\n}");
      return builder.toString();
   }
}
