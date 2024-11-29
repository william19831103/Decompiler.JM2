package jm2lib.blizzard.common.types;

import java.io.IOException;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Vec2F implements BlizzardVector {
   private float x;
   private float y;

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

   public Vec2F(float x, float y) {
      this.x = x;
      this.y = y;
   }

   public Vec2F() {
      this(0.0F, 0.0F);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.x = in.readFloat();
      this.y = in.readFloat();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeFloat(this.x);
      out.writeFloat(this.y);
   }

   public String toString() {
      return "(" + this.x + "," + this.y + ")";
   }
}
