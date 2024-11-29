package jm2lib.blizzard.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;

public class BlizzardOutputStream extends MarshalingStream {
   private long taring = 0L;

   public BlizzardOutputStream(String file) throws FileNotFoundException {
      super(file);
   }

   public void writeObject(Object obj) throws IOException {
      if (!(obj instanceof BlizzardFile)) {
         throw new NotSerializableException(String.format("%s is not a Blizzard object so cannot be marshaled", obj.getClass().getName()));
      } else {
         this.writeInt(ObjectTypeResolver.resolver.resolveMagic(obj.getClass()).toInt());
         this.marshalObject((Marshalable)obj);
      }
   }

   public void writeTared(Object obj) throws IOException {
      this.taring = this.getFilePointer();
      this.writeObject(obj);
      this.taring = 0L;
   }

   public void seek(long pos) throws IOException {
      super.seek(pos + this.taring);
   }

   public long getFilePointer() throws IOException {
      return super.getFilePointer() - this.taring;
   }
}
