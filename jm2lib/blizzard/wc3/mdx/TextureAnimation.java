package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import jm2lib.blizzard.common.types.QuatF;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class TextureAnimation implements Marshalable {
   public Track<Vec3F> translation;
   public Track<QuatF> rotation;
   public Track<Vec3F> scale;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      long endOffset = in.getFilePointer() + (long)in.readInt();

      while(in.getFilePointer() < endOffset) {
         String magic = new String(new byte[]{in.readByte(), in.readByte(), in.readByte(), in.readByte()});
         switch(magic.hashCode()) {
         case 2317146:
            if (!magic.equals("KTAR")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.rotation = new Track(QuatF.class, magic);
            this.rotation.unmarshal(in);
            break;
         case 2317147:
            if (!magic.equals("KTAS")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.scale = new Track(Vec3F.class, magic);
            this.scale.unmarshal(in);
            break;
         case 2317148:
            if (magic.equals("KTAT")) {
               this.translation = new Track(Vec3F.class, magic);
               this.translation.unmarshal(in);
               break;
            }

            throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
         default:
            throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
         }
      }

   }

   public void marshal(MarshalingStream out) throws IOException {
      long sizeOffset = out.getFilePointer();
      out.writeInt(0);
      if (this.translation != null) {
         this.translation.marshal(out);
      }

      if (this.rotation != null) {
         this.rotation.marshal(out);
      }

      if (this.scale != null) {
         this.scale.marshal(out);
      }

      int size = (int)(out.getFilePointer() - sizeOffset);
      long currentOffset = out.getFilePointer();
      out.seek(sizeOffset);
      out.writeInt(size);
      out.seek(currentOffset);
   }
}
