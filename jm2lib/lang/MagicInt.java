package jm2lib.lang;

public abstract class MagicInt implements Comparable<MagicInt> {
   private final int value;

   protected MagicInt(int value) {
      this.value = value;
   }

   protected MagicInt(String value) {
      this.value = stringToMagic(value);
   }

   public static int stringToMagic(String value) {
      if (value.length() != 4) {
         throw new StringIndexOutOfBoundsException(String.format("'%s' is not a valid type string (must be exactly 4 characters long)", value));
      } else {
         return value.charAt(0) & 255 | (value.charAt(1) & 255) << 8 | (value.charAt(2) & 255) << 16 | (value.charAt(3) & 255) << 24;
      }
   }

   public static String magicToString(int value) {
      char[] chars = new char[]{(char)(value & 255), (char)(value >> 8 & 255), (char)(value >> 16 & 255), (char)(value >> 24 & 255)};
      return new String(chars);
   }

   public int hashCode() {
      return this.value;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         MagicInt other = (MagicInt)obj;
         return this.value == other.value;
      }
   }

   public boolean equals(int value) {
      return this.value == value;
   }

   public String toString() {
      return magicToString(this.value);
   }

   public int toInt() {
      return this.value;
   }

   public int compareTo(MagicInt o) {
      return this.value - o.value;
   }
}
