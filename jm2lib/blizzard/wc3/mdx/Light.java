package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Light implements Marshalable {
   public Node node = new Node();
   public int type;
   public int attenuationStart;
   public int attenuationEnd;
   public Vec3F color = new Vec3F();
   public float intensity;
   public Vec3F ambientColor = new Vec3F();
   public float ambientIntensity;
   public Track<Integer> attenuationStartTrack;
   public Track<Integer> attenuationEndTrack;
   public Track<Vec3F> colorTrack;
   public Track<Float> intensityTrack;
   public Track<Float> ambientIntensityTrack;
   public Track<Vec3F> ambientColorTrack;
   public Track<Float> visibility;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      long endOffset = in.getFilePointer() + (long)in.readInt();
      this.node.unmarshal(in);
      this.type = in.readInt();
      this.attenuationStart = in.readInt();
      this.attenuationEnd = in.readInt();
      this.color.unmarshal(in);
      this.intensity = in.readFloat();
      this.ambientColor.unmarshal(in);
      this.ambientIntensity = in.readFloat();

      while(in.getFilePointer() < endOffset) {
         String magic = new String(new byte[]{in.readByte(), in.readByte(), in.readByte(), in.readByte()});
         switch(magic.hashCode()) {
         case 2309443:
            if (!magic.equals("KLAC")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.colorTrack = new Track(Vec3F.class, magic);
            this.colorTrack.unmarshal(in);
            break;
         case 2309445:
            if (!magic.equals("KLAE")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.attenuationEndTrack = new Track(Integer.TYPE, magic);
            this.attenuationEndTrack.unmarshal(in);
            break;
         case 2309449:
            if (!magic.equals("KLAI")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.intensityTrack = new Track(Float.TYPE, magic);
            this.intensityTrack.unmarshal(in);
            break;
         case 2309459:
            if (!magic.equals("KLAS")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.attenuationStartTrack = new Track(Integer.TYPE, magic);
            this.attenuationStartTrack.unmarshal(in);
            break;
         case 2309462:
            if (!magic.equals("KLAV")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.visibility = new Track(Float.TYPE, magic);
            this.visibility.unmarshal(in);
            break;
         case 2309474:
            if (!magic.equals("KLBC")) {
               throw new ClassNotFoundException(magic + "not handled in " + this.getClass());
            }

            this.ambientColorTrack = new Track(Vec3F.class, magic);
            this.ambientColorTrack.unmarshal(in);
            break;
         case 2309480:
            if (magic.equals("KLBI")) {
               this.ambientIntensityTrack = new Track(Float.TYPE, magic);
               this.ambientIntensityTrack.unmarshal(in);
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
      this.node.marshal(out);
      out.writeInt(this.type);
      out.writeInt(this.attenuationStart);
      out.writeInt(this.attenuationEnd);
      this.color.marshal(out);
      out.writeFloat(this.intensity);
      this.ambientColor.marshal(out);
      out.writeFloat(this.ambientIntensity);
      if (this.attenuationStartTrack != null) {
         this.attenuationStartTrack.marshal(out);
      }

      if (this.attenuationEndTrack != null) {
         this.attenuationEndTrack.marshal(out);
      }

      if (this.colorTrack != null) {
         this.colorTrack.marshal(out);
      }

      if (this.intensityTrack != null) {
         this.intensityTrack.marshal(out);
      }

      if (this.ambientIntensityTrack != null) {
         this.ambientIntensityTrack.marshal(out);
      }

      if (this.ambientColorTrack != null) {
         this.ambientColorTrack.marshal(out);
      }

      if (this.visibility != null) {
         this.visibility.marshal(out);
      }

      int size = (int)(out.getFilePointer() - sizeOffset);
      long currentOffset = out.getFilePointer();
      out.seek(sizeOffset);
      out.writeInt(size);
      out.seek(currentOffset);
   }
}
