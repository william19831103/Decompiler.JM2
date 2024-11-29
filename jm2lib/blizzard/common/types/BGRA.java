package jm2lib.blizzard.common.types;

import java.io.IOException;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class BGRA implements BlizzardVector {
   private Vec3B bgr;
   private byte alpha;

   public BGRA(byte blue, byte green, byte red, byte alpha) {
      this.bgr = new Vec3B(blue, green, red);
      this.alpha = alpha;
   }

   public BGRA() {
      this((byte)0, (byte)0, (byte)0, (byte)0);
   }

   public BGRA(Vec3F rgb, short alpha) {
      this((byte)((int)rgb.getZ()), (byte)((int)rgb.getY()), (byte)((int)rgb.getX()), (byte)0);
      this.alpha = (byte)(alpha / 128);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.bgr.unmarshal(in);
      this.alpha = in.readByte();
   }

   public void marshal(MarshalingStream out) throws IOException {
      this.bgr.marshal(out);
      out.writeByte(this.alpha);
   }

   public String toString() {
      return "(" + this.bgr + ", " + this.alpha + ")";
   }
}
