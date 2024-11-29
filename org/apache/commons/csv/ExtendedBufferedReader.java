package org.apache.commons.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

final class ExtendedBufferedReader extends BufferedReader {
   private int lastChar = -2;
   private long eolCounter;
   private long position;
   private boolean closed;

   ExtendedBufferedReader(Reader reader) {
      super(reader);
   }

   public int read() throws IOException {
      int current = super.read();
      if (current == 13 || current == 10 && this.lastChar != 13) {
         ++this.eolCounter;
      }

      this.lastChar = current;
      ++this.position;
      return this.lastChar;
   }

   int getLastChar() {
      return this.lastChar;
   }

   public int read(char[] buf, int offset, int length) throws IOException {
      if (length == 0) {
         return 0;
      } else {
         int len = super.read(buf, offset, length);
         if (len > 0) {
            for(int i = offset; i < offset + len; ++i) {
               char ch = buf[i];
               if (ch == '\n') {
                  if (13 != (i > 0 ? buf[i - 1] : this.lastChar)) {
                     ++this.eolCounter;
                  }
               } else if (ch == '\r') {
                  ++this.eolCounter;
               }
            }

            this.lastChar = buf[offset + len - 1];
         } else if (len == -1) {
            this.lastChar = -1;
         }

         this.position += (long)len;
         return len;
      }
   }

   public String readLine() throws IOException {
      String line = super.readLine();
      if (line != null) {
         this.lastChar = 10;
         ++this.eolCounter;
      } else {
         this.lastChar = -1;
      }

      return line;
   }

   int lookAhead() throws IOException {
      super.mark(1);
      int c = super.read();
      super.reset();
      return c;
   }

   long getCurrentLineNumber() {
      return this.lastChar != 13 && this.lastChar != 10 && this.lastChar != -2 && this.lastChar != -1 ? this.eolCounter + 1L : this.eolCounter;
   }

   long getPosition() {
      return this.position;
   }

   public boolean isClosed() {
      return this.closed;
   }

   public void close() throws IOException {
      this.closed = true;
      this.lastChar = -1;
      super.close();
   }
}
