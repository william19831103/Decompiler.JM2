package jm2lib.blizzard.common.types;

import java.io.IOException;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class QuatS implements BlizzardVector {
   private short x;
   private short y;
   private short z;
   private short w;

   public QuatS(short x, short y, short z, short w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
   }

   public QuatS() {
      this((short)0, (short)0, (short)0, (short)0);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.x = in.readShort();
      this.y = in.readShort();
      this.z = in.readShort();
      this.w = in.readShort();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeShort(this.x);
      out.writeShort(this.y);
      out.writeShort(this.z);
      out.writeShort(this.w);
   }

   public QuatF toQuatF() {
      return new QuatF(this.stf(this.x), this.stf(this.y), this.stf(this.z), this.stf(this.w));
   }

   private float stf(short value) {
      return value == -1 ? 1.0F : (float)((double)(value > 0 ? value - 32767 : value + 32767) / 32767.0D);
   }

   public String toString() {
      return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
   }
}
