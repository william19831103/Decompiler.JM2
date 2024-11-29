package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.blizzard.common.types.Vec2F;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Vertex implements Marshalable {
   public Vec3F pos = new Vec3F();
   public byte[] boneWeights = new byte[4];
   public byte[] boneIndices = new byte[4];
   public Vec3F normal = new Vec3F();
   public Vec2F[] texCoords = new Vec2F[2];

   public Vertex() {
      for(byte i = 0; i < 2; ++i) {
         this.texCoords[i] = new Vec2F();
      }

   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.pos.unmarshal(in);

      byte i;
      for(i = 0; i < 4; ++i) {
         this.boneWeights[i] = in.readByte();
      }

      for(i = 0; i < 4; ++i) {
         this.boneIndices[i] = in.readByte();
      }

      this.normal.unmarshal(in);

      for(i = 0; i < 2; ++i) {
         this.texCoords[i].unmarshal(in);
      }

   }

   public void marshal(MarshalingStream out) throws IOException {
      this.pos.marshal(out);

      byte i;
      for(i = 0; i < 4; ++i) {
         out.writeByte(this.boneWeights[i]);
      }

      for(i = 0; i < 4; ++i) {
         out.writeByte(this.boneIndices[i]);
      }

      this.normal.marshal(out);

      for(i = 0; i < 2; ++i) {
         this.texCoords[i].marshal(out);
      }

   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      String NEW_LINE = System.getProperty("line.separator");
      result.append("pos : " + this.pos + NEW_LINE);
      result.append("boneWeights : (" + this.boneWeights[0] + "," + this.boneWeights[1] + "," + this.boneWeights[2] + "," + this.boneWeights[3] + "," + ")" + NEW_LINE);
      result.append("boneIndices : (" + this.boneIndices[0] + "," + this.boneIndices[1] + "," + this.boneIndices[2] + "," + this.boneIndices[3] + "," + ")" + NEW_LINE);
      result.append("Normal : " + this.normal + NEW_LINE);
      result.append("texCoords : (" + this.texCoords[0] + "," + this.texCoords[1] + ")" + NEW_LINE);
      return result.toString();
   }
}
