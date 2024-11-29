package jm2lib.blizzard.common.types;

import java.io.IOException;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Vec2C implements BlizzardVector {
   private char x;
   private char y;

   public char getX() {
      return this.x;
   }

   public void setX(char x) {
      this.x = x;
   }

   public char getY() {
      return this.y;
   }

   public void setY(char y) {
      this.y = y;
   }

   public Vec2C() {
      this('\u0000', '\u0000');
   }

   public Vec2C(char x, char y) {
      this.x = x;
      this.y = y;
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.x = in.readChar();
      this.y = in.readChar();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeChar(this.x);
      out.writeChar(this.y);
   }

   public String toString() {
      return "(" + this.x + "," + this.y + ")";
   }
}
