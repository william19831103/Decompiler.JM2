package jm2lib.io;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.nio.charset.StandardCharsets;

public class UnmarshalingStream extends LERandomAccessFile {
   public UnmarshalingStream(String file) throws FileNotFoundException {
      super(file, "r");
   }

   protected Object unmarshalObject(Class<?> clazz) throws ClassNotFoundException, IOException {
      Object obj;
      try {
         obj = clazz.newInstance();
      } catch (InstantiationException var4) {
         throw new InvalidClassException(clazz.getName(), "missing no argument constructor");
      } catch (IllegalAccessException var5) {
         throw new InvalidClassException(clazz.getName(), "cannot access no argument constructor");
      }

      if (!(obj instanceof Marshalable)) {
         throw new InvalidClassException(clazz.getName(), "not unmarshalable");
      } else {
         ((Marshalable)obj).unmarshal(this);
         return obj;
      }
   }

   public Object readGeneric(Class<?> type) throws ClassNotFoundException, IOException {
      if (Marshalable.class.isAssignableFrom(type)) {
         return this.unmarshalObject(type);
      } else if (type == Integer.TYPE) {
         return this.readInt();
      } else if (type == Float.TYPE) {
         return this.readFloat();
      } else if (type == Short.TYPE) {
         return this.readShort();
      } else if (type == Long.TYPE) {
         return this.readLong();
      } else if (type == Byte.TYPE) {
         return this.readByte();
      } else if (type == Character.TYPE) {
         return this.readChar();
      } else {
         throw new InvalidClassException("Class not recognized : " + type);
      }
   }

   public byte[] readByteArray(int n) throws IOException {
      byte[] byteArray = new byte[n];
      this.read(byteArray);
      return byteArray;
   }

   public String readString(int n) throws IOException {
      return (new String(this.readByteArray(n), StandardCharsets.UTF_8)).trim();
   }
}
