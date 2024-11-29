package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import jm2lib.blizzard.common.types.QuatF;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Node implements Marshalable {
   public String name = "";
   public int objectID;
   public int parentID;
   public int flags;
   public Track<Vec3F> translation;
   public Track<QuatF> rotation;
   public Track<Vec3F> scale;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      long endOffset = in.getFilePointer() + (long)in.readInt();
      this.name = in.readString(80);
      this.objectID = in.readInt();
      this.parentID = in.readInt();
      this.flags = in.readInt();

      while(in.getFilePointer() < endOffset) {
         String magic = new String(new byte[]{in.readByte(), in.readByte(), in.readByte(), in.readByte()});
         switch(magic.hashCode()) {
         case 2305182:
            if (!magic.equals("KGRT")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.rotation = new Track(QuatF.class, magic);
            this.rotation.unmarshal(in);
            break;
         case 2305196:
            if (!magic.equals("KGSC")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.scale = new Track(Vec3F.class, magic);
            this.scale.unmarshal(in);
            break;
         case 2305242:
            if (magic.equals("KGTR")) {
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
      out.writeString(this.name, 80);
      out.writeInt(this.objectID);
      out.writeInt(this.parentID);
      out.writeInt(this.flags);
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

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tname: ").append(this.name).append("\n\tobjectID: ").append(this.objectID).append("\n\tparentID: ").append(this.parentID).append("\n\tflags: ").append(this.flags).append("\n\ttranslation: ").append(this.translation).append("\n\trotation: ").append(this.rotation).append("\n\tscale: ").append(this.scale).append("\n}");
      return builder.toString();
   }
}
