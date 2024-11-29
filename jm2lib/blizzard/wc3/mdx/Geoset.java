package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import jm2lib.blizzard.common.types.Vec2F;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Geoset implements Marshalable {
   Geoset.ArrayMagic<Vec3F> vertexPositions = new Geoset.ArrayMagic(Vec3F.class, "VRTX");
   Geoset.ArrayMagic<Vec3F> vertexNormals = new Geoset.ArrayMagic(Vec3F.class, "NRMS");
   Geoset.ArrayMagic<Integer> faceTypeGroups;
   Geoset.ArrayMagic<Integer> faceGroups;
   Geoset.ArrayMagic<Character> faces;
   Geoset.ArrayMagic<Byte> vertexGroups;
   Geoset.ArrayMagic<Integer> matrixGroups;
   Geoset.ArrayMagic<Integer> matrixIndices;
   int materialId;
   int selectionGroup;
   int selectionFlags;
   Extent extent;
   ArrayList<Extent> extents;
   int textureCoordinateSetsCount;
   Geoset.ArrayMagic<Geoset.TextureCoordinateSet> textureCoordinateSets;

   public Geoset() {
      this.faceTypeGroups = new Geoset.ArrayMagic(Integer.TYPE, "PTYP");
      this.faceGroups = new Geoset.ArrayMagic(Integer.TYPE, "PCNT");
      this.faces = new Geoset.ArrayMagic(Character.TYPE, "PVTX");
      this.vertexGroups = new Geoset.ArrayMagic(Byte.TYPE, "GNDX");
      this.matrixGroups = new Geoset.ArrayMagic(Integer.TYPE, "MTGX");
      this.matrixIndices = new Geoset.ArrayMagic(Integer.TYPE, "MATS");
      this.extent = new Extent();
      this.extents = new ArrayList();
      this.textureCoordinateSets = new Geoset.ArrayMagic(Geoset.TextureCoordinateSet.class, "UVAS");
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      long endOffset = in.getFilePointer() + (long)in.readInt();
      this.vertexPositions.unmarshal(in);
      this.vertexNormals.unmarshal(in);
      this.faceTypeGroups.unmarshal(in);
      this.faceGroups.unmarshal(in);
      this.faces.unmarshal(in);
      this.vertexGroups.unmarshal(in);
      this.matrixGroups.unmarshal(in);
      this.matrixIndices.unmarshal(in);
      this.materialId = in.readInt();
      this.selectionGroup = in.readInt();
      this.selectionFlags = in.readInt();
      this.extent.unmarshal(in);
      int count = in.readInt();

      for(int i = 0; i < count; ++i) {
         this.extents.add((Extent)in.readGeneric(Extent.class));
      }

      this.textureCoordinateSets.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      long sizeOffset = out.getFilePointer();
      out.writeInt(0);
      this.vertexPositions.marshal(out);
      this.vertexNormals.marshal(out);
      this.faceTypeGroups.marshal(out);
      this.faceGroups.marshal(out);
      this.faces.marshal(out);
      this.vertexGroups.marshal(out);
      this.matrixGroups.marshal(out);
      this.matrixIndices.marshal(out);
      out.writeInt(this.materialId);
      out.writeInt(this.selectionGroup);
      out.writeInt(this.selectionFlags);
      this.extent.marshal(out);
      out.writeInt(this.extents.size());
      Iterator var5 = this.extents.iterator();

      while(var5.hasNext()) {
         Extent value = (Extent)var5.next();
         value.marshal(out);
      }

      this.textureCoordinateSets.marshal(out);
      int size = (int)(out.getFilePointer() - sizeOffset);
      long currentOffset = out.getFilePointer();
      out.seek(sizeOffset);
      out.writeInt(size);
      out.seek(currentOffset);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tvertexPositions: ").append(this.vertexPositions).append("\n\tvertexNormals: ").append(this.vertexNormals).append("\n\tfaceTypeGroups: ").append(this.faceTypeGroups).append("\n\tfaceGroups: ").append(this.faceGroups);
      builder.append("\nfaces:{");
      Iterator var3 = this.faces.iterator();

      while(var3.hasNext()) {
         char face = (Character)var3.next();
         builder.append(face + ":");
      }

      builder.append("}");
      builder.append("\n\tvertexGroups: ").append(this.vertexGroups).append("\n\tmatrixGroups: ").append(this.matrixGroups).append("\n\tmatrixIndices: ").append(this.matrixIndices).append("\n\tmaterialId: ").append(this.materialId).append("\n\tselectionGroup: ").append(this.selectionGroup).append("\n\tselectionFlags: ").append(this.selectionFlags).append("\n\textent: ").append(this.extent).append("\n\textents: ").append(this.extents).append("\n\ttextureCoordinateSetsCount: ").append(this.textureCoordinateSetsCount).append("\n\ttextureCoordinateSets: ").append(this.textureCoordinateSets).append("\n}");
      return builder.toString();
   }

   public static class ArrayMagic<T> extends ArrayList<T> implements Marshalable {
      private static final long serialVersionUID = 2934017788615761081L;
      private final Class<T> type;
      private final String magic;

      public ArrayMagic(Class<T> type, String magic) {
         this.type = type;

         assert magic.length() == 4;

         this.magic = magic;
      }

      public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
         in.readInt();
         int count = in.readInt();

         for(int i = 0; i < count; ++i) {
            this.add(in.readGeneric(this.type));
         }

      }

      public void marshal(MarshalingStream out) throws IOException {
         out.write(this.magic.getBytes(StandardCharsets.UTF_8));
         out.writeInt(this.size());
         Iterator var3 = this.iterator();

         while(var3.hasNext()) {
            T value = (Object)var3.next();
            out.writeGeneric(this.type, value);
         }

      }
   }

   public static class TextureCoordinateSet extends Geoset.ArrayMagic<Vec2F> {
      private static final long serialVersionUID = 1L;

      public TextureCoordinateSet() {
         super(Vec2F.class, "UVBS");
      }
   }
}
