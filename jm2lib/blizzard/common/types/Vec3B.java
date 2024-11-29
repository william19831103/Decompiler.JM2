package jm2lib.blizzard.common.types;

import java.io.IOException;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Vec3B implements BlizzardVector {
   private byte x;
   private byte y;
   private byte z;

   public Vec3B(byte x, byte y, byte z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vec3B() {
      this((byte)0, (byte)0, (byte)0);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.x = in.readByte();
      this.y = in.readByte();
      this.z = in.readByte();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeByte(this.x);
      out.writeByte(this.y);
      out.writeByte(this.z);
   }

   public String toString() {
      return "(" + this.x + "," + this.y + "," + this.z + ")";
   }
}
