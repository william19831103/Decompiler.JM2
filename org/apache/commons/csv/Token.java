package org.apache.commons.csv;

final class Token {
   private static final int INITIAL_TOKEN_LENGTH = 50;
   Token.Type type;
   final StringBuilder content;
   boolean isReady;

   Token() {
      this.type = Token.Type.INVALID;
      this.content = new StringBuilder(50);
   }

   void reset() {
      this.content.setLength(0);
      this.type = Token.Type.INVALID;
      this.isReady = false;
   }

   public String toString() {
      return this.type.name() + " [" + this.content.toString() + "]";
   }

   static enum Type {
      INVALID,
      TOKEN,
      EOF,
      EORECORD,
      COMMENT;
   }
}
