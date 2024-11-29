package jm2lib.blizzard.common.types;

import java.io.IOException;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class QuatF implements BlizzardVector {
   private float x;
   private float y;
   private float z;
   private float w;

   public QuatF(float x, float y, float z, float w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
   }

   public QuatF() {
      this(0.0F, 0.0F, 0.0F, 0.0F);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.x = in.readFloat();
      this.y = in.readFloat();
      this.z = in.readFloat();
      this.w = in.readFloat();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeFloat(this.x);
      out.writeFloat(this.y);
      out.writeFloat(this.z);
      out.writeFloat(this.w);
   }

   public QuatS toQuatS() {
      return new QuatS(this.fts(this.x), this.fts(this.y), this.fts(this.z), this.fts(this.w));
   }

   private short fts(float value) {
      return (short)((int)(value > 0.0F ? (double)value * 32767.0D - 32768.0D : (double)value * 32767.0D + 32768.0D));
   }

   public String toString() {
      return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
   }
}
