package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.blizzard.wow.classic.SubmeshAnimation;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class GeosetAnimation implements Marshalable {
   float alpha;
   int flags;
   Vec3F color = new Vec3F();
   int geosetID;
   Track<Float> alphaTrack;
   Track<Vec3F> colorTrack;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      long endOffset = in.getFilePointer() + (long)in.readInt();
      this.alpha = in.readFloat();
      this.flags = in.readInt();
      this.color.unmarshal(in);
      this.geosetID = in.readInt();

      while(in.getFilePointer() < endOffset) {
         String magic = new String(new byte[]{in.readByte(), in.readByte(), in.readByte(), in.readByte()});
         switch(magic.hashCode()) {
         case 2304638:
            if (!magic.equals("KGAC")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.colorTrack = new Track(Vec3F.class, magic);
            this.colorTrack.unmarshal(in);
            break;
         case 2304650:
            if (magic.equals("KGAO")) {
               this.alphaTrack = new Track(Float.TYPE, magic);
               this.alphaTrack.unmarshal(in);
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
      out.writeFloat(this.alpha);
      out.writeInt(this.flags);
      this.color.marshal(out);
      out.writeInt(this.geosetID);
      if (this.alphaTrack != null) {
         this.alphaTrack.marshal(out);
      }

      if (this.colorTrack != null) {
         this.colorTrack.marshal(out);
      }

      int size = (int)(out.getFilePointer() - sizeOffset);
      long currentOffset = out.getFilePointer();
      out.seek(sizeOffset);
      out.writeInt(size);
      out.seek(currentOffset);
   }

   public SubmeshAnimation upConvert() {
      SubmeshAnimation output = new SubmeshAnimation();
      return output;
   }
}
