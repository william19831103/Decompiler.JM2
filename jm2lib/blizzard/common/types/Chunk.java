package jm2lib.blizzard.common.types;

import java.io.IOException;
import java.io.InvalidClassException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Chunk<T> extends ArrayList<T> implements Marshalable {
   private static final long serialVersionUID = -4836096267381071184L;
   private final Class<T> type;
   private final String magic;

   public Chunk(Class<T> type, String magic) {
      this.type = type;

      assert magic.length() == 4;

      this.magic = magic;
   }

   public final void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      int size = in.readInt();
      long endOffset = in.getFilePointer() + (long)size;

      while(in.getFilePointer() < endOffset) {
         if (Marshalable.class.isAssignableFrom(this.type) && this.type.getDeclaredConstructors()[0].getParameterCount() > 0) {
            throw new InvalidClassException("\nNo 0-Arg constructor found for " + this.type + ".\nIf nested class, make sure to declare it static.");
         }

         this.add(in.readGeneric(this.type));
      }

   }

   public final void marshal(MarshalingStream out) throws IOException {
      out.write(this.magic.getBytes(StandardCharsets.UTF_8));
      long sizeOffset = out.getFilePointer();
      out.writeInt(0);

      int size;
      for(size = 0; size < this.size(); ++size) {
         out.writeGeneric(this.type, this.get(size));
      }

      size = (int)(out.getFilePointer() - sizeOffset);
      long currentOffset = out.getFilePointer();
      out.seek(sizeOffset);
      out.writeInt(size);
      out.seek(currentOffset);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();

      for(int i = 0; i < this.size(); ++i) {
         builder.append("\n\t [" + i + "] " + this.get(i));
      }

      return builder.toString();
   }
}
