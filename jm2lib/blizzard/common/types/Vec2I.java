package jm2lib.blizzard.common.types;

import java.io.IOException;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Vec2I implements BlizzardVector {
   private int x;
   private int y;

   public int getX() {
      return this.x;
   }

   public void setX(int x) {
      this.x = x;
   }

   public int getY() {
      return this.y;
   }

   public void setY(int y) {
      this.y = y;
   }

   public Vec2I() {
      this(0, 0);
   }

   public Vec2I(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.x = in.readInt();
      this.y = in.readInt();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeInt(this.x);
      out.writeInt(this.y);
   }

   public String toString() {
      return "(" + this.x + "," + this.y + ")";
   }
}
