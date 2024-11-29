package jm2lib.blizzard.wow.cataclysm.skin;

import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.wow.classic.TextureUnit;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class View implements Marshalable {
   public byte[] magic = new byte[]{83, 75, 73, 78};
   public ArrayRef<Short> indices;
   public ArrayRef<Short> triangles;
   public ArrayRef<Integer> properties;
   public ArrayRef<Submesh> submeshes;
   public ArrayRef<TextureUnit> textureUnits;
   public int bones;
   public ArrayRef<ShadowBatch> shadowBatches;

   public View() {
      this.indices = new ArrayRef(Short.TYPE);
      this.triangles = new ArrayRef(Short.TYPE);
      this.properties = new ArrayRef(Integer.TYPE);
      this.submeshes = new ArrayRef(Submesh.class);
      this.textureUnits = new ArrayRef(TextureUnit.class);
      this.bones = 0;
      this.shadowBatches = new ArrayRef(ShadowBatch.class);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      in.read(this.magic);
      this.indices.unmarshal(in);
      this.triangles.unmarshal(in);
      this.properties.unmarshal(in);
      this.submeshes.unmarshal(in);
      this.textureUnits.unmarshal(in);
      this.bones = in.readInt();
      this.shadowBatches.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.write(this.magic);
      this.indices.marshal(out);
      this.triangles.marshal(out);
      this.properties.marshal(out);
      this.submeshes.marshal(out);
      this.textureUnits.marshal(out);
      out.writeInt(this.bones);
      this.shadowBatches.marshal(out);

      try {
         this.indices.writeContent(out);
         this.triangles.writeContent(out);
         this.properties.writeContent(out);
         this.submeshes.writeContent(out);
         this.textureUnits.writeContent(out);
         this.shadowBatches.writeContent(out);
      } catch (IllegalAccessException | InstantiationException var3) {
         var3.printStackTrace();
      }

   }

   public jm2lib.blizzard.wow.lichking.skin.View downConvert() throws Exception {
      jm2lib.blizzard.wow.lichking.skin.View output = new jm2lib.blizzard.wow.lichking.skin.View();
      output.indices = this.indices;
      output.triangles = this.triangles;
      output.properties = this.properties;

      int i;
      for(i = 0; i < this.submeshes.size(); ++i) {
         output.submeshes.add(((Submesh)this.submeshes.get(i)).downConvert());
      }

      output.textureUnits = this.textureUnits;

      for(i = 0; i < output.textureUnits.size(); ++i) {
         ((TextureUnit)output.textureUnits.get(i)).opCount = 1;
         if (((TextureUnit)output.textureUnits.get(i)).flags != 0 || ((TextureUnit)output.textureUnits.get(i)).flags != 16) {
            ((TextureUnit)output.textureUnits.get(i)).flags = 16;
         }

         if (((TextureUnit)output.textureUnits.get(i)).shaderID > '2') {
            ((TextureUnit)output.textureUnits.get(i)).shaderID = 1;
         }
      }

      output.bones = this.bones;
      return output;
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      String NEW_LINE = System.getProperty("line.separator");
      result.append("Indices : " + this.indices + NEW_LINE);
      result.append("Triangles : " + this.triangles + NEW_LINE);
      result.append("Properties : " + this.properties + NEW_LINE);
      result.append("Submeshes : " + this.submeshes + NEW_LINE);
      result.append("Texture Units : " + this.textureUnits + NEW_LINE);
      result.append("Bones : " + this.bones + NEW_LINE);
      return result.toString();
   }
}
