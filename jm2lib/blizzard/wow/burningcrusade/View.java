package jm2lib.blizzard.wow.burningcrusade;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.wow.classic.TextureUnit;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class View implements Referencer {
   public ArrayRef<Short> indices;
   public ArrayRef<Short> triangles;
   public ArrayRef<Integer> properties;
   public ArrayRef<Submesh> submeshes;
   public ArrayRef<TextureUnit> textureUnits;
   public int bones;

   public View() {
      this.indices = new ArrayRef(Short.TYPE);
      this.triangles = new ArrayRef(Short.TYPE);
      this.properties = new ArrayRef(Integer.TYPE);
      this.submeshes = new ArrayRef(Submesh.class);
      this.textureUnits = new ArrayRef(TextureUnit.class);
      this.bones = 0;
   }

   public View(View v) {
      this();
      this.indices.addAll(v.indices);
      this.triangles.addAll(v.triangles);
      this.properties.addAll(v.properties);
      this.submeshes.addAll(v.submeshes);
      this.textureUnits.addAll(v.textureUnits);
      this.bones = v.bones;
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.indices.unmarshal(in);
      this.triangles.unmarshal(in);
      this.properties.unmarshal(in);
      this.submeshes.unmarshal(in);
      this.textureUnits.unmarshal(in);
      this.bones = in.readInt();
   }

   public void marshal(MarshalingStream out) throws IOException {
      this.indices.marshal(out);
      this.triangles.marshal(out);
      this.properties.marshal(out);
      this.submeshes.marshal(out);
      this.textureUnits.marshal(out);
      out.writeInt(this.bones);
   }

   public jm2lib.blizzard.wow.classic.View downConvert() {
      jm2lib.blizzard.wow.classic.View output = new jm2lib.blizzard.wow.classic.View();
      output.indices = this.indices;
      output.triangles = this.triangles;
      output.properties = this.properties;

      for(int i = 0; i < this.submeshes.size(); ++i) {
         output.submeshes.add(((Submesh)this.submeshes.get(i)).downConvert());
      }

      output.textureUnits = this.textureUnits;
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

   public void writeContent(MarshalingStream out) throws IOException, InstantiationException, IllegalAccessException {
      this.indices.writeContent(out);
      this.triangles.writeContent(out);
      this.properties.writeContent(out);
      this.submeshes.writeContent(out);
      this.textureUnits.writeContent(out);
   }
}
