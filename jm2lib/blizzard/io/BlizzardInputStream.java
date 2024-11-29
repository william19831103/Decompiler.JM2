package jm2lib.blizzard.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import jm2lib.io.UnmarshalingStream;

public class BlizzardInputStream extends UnmarshalingStream {
   private long taring = 0L;

   public BlizzardInputStream(String file) throws FileNotFoundException {
      super(file);
   }

   public Object readObject() throws ClassNotFoundException, IOException {
      Class<?> oclass = ObjectTypeResolver.resolver.resolveClass(new FileMagic(this.readInt()));
      return this.unmarshalObject(oclass);
   }

   public Object readTared() throws IOException, ClassNotFoundException {
      this.taring = this.getFilePointer();
      Object res = this.readObject();
      this.taring = 0L;
      return res;
   }

   public void seek(long pos) throws IOException {
      super.seek(pos + this.taring);
   }

   public long getFilePointer() throws IOException {
      return super.getFilePointer() - this.taring;
   }
}
