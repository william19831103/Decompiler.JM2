package jm2lib.blizzard.common.types;

import java.io.IOException;
import java.util.BitSet;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class FixedPoint implements Marshalable {
   private int integerBits;
   private int decimalBits;
   private int signBits = 1;
   private BitSet bits;

   public FixedPoint(int integerBits, int decimalBits) {
      this.integerBits = integerBits;
      this.decimalBits = decimalBits;
      this.bits = new BitSet();
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      byte[] array = new byte[(this.signBits + this.integerBits + this.decimalBits) / 8];
      in.read(array);
      this.bits = BitSet.valueOf(array);
   }

   public BitSet getBits() {
      return this.bits;
   }

   public void marshal(MarshalingStream out) throws IOException {
      byte[] array = new byte[(this.signBits + this.integerBits + this.decimalBits) / 8];

      for(int i = 0; i < this.bits.length(); ++i) {
         if (this.bits.get(i)) {
            array[array.length - i / 8 - 1] = (byte)(array[array.length - i / 8 - 1] | 1 << i % 8);
         }
      }

      out.write(array);
   }

   public float toFloat() {
      boolean sign = this.bits.get(this.signBits - 1);
      int integer = this.bitSetToInt(this.bits.get(this.signBits, this.signBits + this.integerBits - 1));
      int decimal = this.bitSetToInt(this.bits.get(this.signBits + this.integerBits, this.signBits + this.integerBits + this.decimalBits - 1));
      return (sign ? -1.0F : 1.0F) * ((float)integer + (float)decimal / (float)(1 << this.decimalBits));
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      String sign = this.bits.get(this.signBits - 1) ? "-" : "+";
      int integer = this.bitSetToInt(this.bits.get(this.signBits, this.signBits + this.integerBits - 1));
      int decimal = this.bitSetToInt(this.bits.get(this.signBits + this.integerBits, this.signBits + this.integerBits + this.decimalBits - 1));
      builder.append("FixedPoint [sign=").append(sign).append(", integer=").append(integer).append(", decimal=").append(decimal).append("]");
      return builder.toString();
   }

   private int bitSetToInt(BitSet bits) {
      int result = 0;

      for(int i = 0; i < bits.length(); ++i) {
         if (bits.get(i)) {
            result |= 1 << i;
         }
      }

      result &= Integer.MAX_VALUE;
      return result;
   }
}
