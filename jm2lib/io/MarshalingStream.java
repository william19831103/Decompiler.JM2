package jm2lib.io;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MarshalingStream extends LERandomAccessFile {
   public MarshalingStream(String file) throws FileNotFoundException {
      super(file, "rw");
   }

   protected void marshalObject(Marshalable obj) throws IOException {
      obj.marshal(this);
   }

   public void writeGeneric(Class<?> type, Object value) throws IOException {
      if (Marshalable.class.isAssignableFrom(type)) {
         this.marshalObject((Marshalable)value);
      } else if (type == Integer.TYPE) {
         this.writeInt((Integer)value);
      } else if (type == Float.TYPE) {
         this.writeFloat((Float)value);
      } else if (type == Short.TYPE) {
         this.writeShort((Short)value);
      } else if (type == Long.TYPE) {
         this.writeLong((Long)value);
      } else if (type == Byte.TYPE) {
         this.writeByte((Byte)value);
      } else {
         if (type != Character.TYPE) {
            throw new InvalidClassException("Class not recognized : " + type);
         }

         this.writeChar((Character)value);
      }

   }

   public void writeString(String str, int n) throws IOException {
      if (n < str.length()) {
         throw new IOException("String is too long to be written on " + n + " bytes.");
      } else {
         this.write(Arrays.copyOf(str.getBytes(StandardCharsets.UTF_8), n));
      }
   }
}
