package jm2lib.blizzard.common.types;

import java.io.IOException;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Vec3F implements BlizzardVector {
   private float x;
   private float y;
   private float z;

   public float getX() {
      return this.x;
   }

   public void setX(float x) {
      this.x = x;
   }

   public float getY() {
      return this.y;
   }

   public void setY(float y) {
      this.y = y;
   }

   public float getZ() {
      return this.z;
   }

   public void setZ(float z) {
      this.z = z;
   }

   public Vec3F(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vec3F() {
      this(0.0F, 0.0F, 0.0F);
   }

   public boolean isValid() {
      return this.x == this.x && this.y == this.y && this.z == this.z;
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.x = in.readFloat();
      this.y = in.readFloat();
      this.z = in.readFloat();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeFloat(this.x);
      out.writeFloat(this.y);
      out.writeFloat(this.z);
   }

   public String toString() {
      return "(" + this.x + "," + this.y + "," + this.z + ")";
   }
}
