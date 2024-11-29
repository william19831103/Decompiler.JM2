package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Material implements Marshalable {
   public int priorityPlane;
   public int flags;
   ArrayList<Material.Layer> layers;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      long endOffset = in.getFilePointer() + (long)in.readInt();
      this.priorityPlane = in.readInt();
      this.flags = in.readInt();
      if (in.getFilePointer() < endOffset) {
         in.readInt();
         this.layers = new ArrayList();
         int layersCount = in.readInt();

         for(int i = 0; i < layersCount; ++i) {
            this.layers.add(new Material.Layer());
            ((Material.Layer)this.layers.get(i)).unmarshal(in);
         }
      }

   }

   public void marshal(MarshalingStream out) throws IOException {
      long sizeOffset = out.getFilePointer();
      out.writeInt(0);
      out.writeInt(this.priorityPlane);
      out.writeInt(this.flags);
      if (this.layers != null) {
         out.write("LAYS".getBytes(StandardCharsets.UTF_8));
         out.writeInt(this.layers.size());
         Iterator var5 = this.layers.iterator();

         while(var5.hasNext()) {
            Material.Layer layer = (Material.Layer)var5.next();
            layer.marshal(out);
         }
      }

      int size = (int)(out.getFilePointer() - sizeOffset);
      long currentOffset = out.getFilePointer();
      out.seek(sizeOffset);
      out.writeInt(size);
      out.seek(currentOffset);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tpriorityPlane: ").append(this.priorityPlane).append("\n\tflags: ").append(this.flags).append("\n\tlayers: ").append(this.layers).append("\n}");
      return builder.toString();
   }

   public static class Layer implements Marshalable {
      public int filterMode;
      public int shadingFlags;
      public int textureID;
      public int textureAnimationID;
      public int coordID;
      public float alpha;
      public Track<Integer> materialTextureID;
      public Track<Float> materialTextureAlpha;

      public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
         long endOffset = in.getFilePointer() + (long)in.readInt();
         this.filterMode = in.readInt();
         this.shadingFlags = in.readInt();
         this.textureID = in.readInt();
         this.textureAnimationID = in.readInt();
         this.coordID = in.readInt();
         this.alpha = in.readFloat();
         if (in.getFilePointer() < endOffset) {
            String magic = new String(new byte[]{in.readByte(), in.readByte(), in.readByte(), in.readByte()});
            switch(magic.hashCode()) {
            case 2310991:
               if (!magic.equals("KMTA")) {
                  throw new ClassNotFoundException(magic + " is not referenced in the code.");
               }
               break;
            case 2310996:
               if (magic.equals("KMTF")) {
                  this.materialTextureID = new Track(Integer.TYPE, magic);
                  break;
               }

               throw new ClassNotFoundException(magic + " is not referenced in the code.");
            default:
               throw new ClassNotFoundException(magic + " is not referenced in the code.");
            }

            this.materialTextureAlpha = new Track(Float.TYPE, magic);
            throw new ClassNotFoundException(magic + " is not referenced in the code.");
         }
      }

      public void marshal(MarshalingStream out) throws IOException {
         long sizeOffset = out.getFilePointer();
         out.writeInt(0);
         out.writeInt(this.filterMode);
         out.writeInt(this.shadingFlags);
         out.writeInt(this.textureID);
         out.writeInt(this.textureAnimationID);
         out.writeInt(this.coordID);
         out.writeFloat(this.alpha);
         if (this.materialTextureID != null) {
            this.materialTextureID.marshal(out);
         }

         if (this.materialTextureAlpha != null) {
            this.materialTextureAlpha.marshal(out);
         }

         int size = (int)(out.getFilePointer() - sizeOffset);
         long currentOffset = out.getFilePointer();
         out.seek(sizeOffset);
         out.writeInt(size);
         out.seek(currentOffset);
      }

      public String toString() {
         StringBuilder builder = new StringBuilder();
         builder.append(this.getClass().getName()).append(" {\n\tfilterMode: ").append(this.filterMode).append("\n\tshadingFlags: ").append(this.shadingFlags).append("\n\ttextureID: ").append(this.textureID).append("\n\ttextureAnimationID: ").append(this.textureAnimationID).append("\n\tcoordID: ").append(this.coordID).append("\n\talpha: ").append(this.alpha).append("\n\tmaterialTextureID: ").append(this.materialTextureID).append("\n\tmaterialTextureAlpha: ").append(this.materialTextureAlpha).append("\n}");
         return builder.toString();
      }
   }
}
