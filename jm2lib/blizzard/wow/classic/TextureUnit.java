package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class TextureUnit implements Marshalable {
   public byte flags;
   public byte flags2;
   public char shaderID;
   public char submeshIndex;
   public char submeshIndex2;
   public short colorIndex;
   public char renderFlags;
   public char texUnitNumber;
   public char opCount;
   public char texture;
   public char texUnitNumber2;
   public char transparency;
   public char textureAnim;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.flags = in.readByte();
      this.flags2 = in.readByte();
      this.shaderID = in.readChar();
      this.submeshIndex = in.readChar();
      this.submeshIndex2 = in.readChar();
      this.colorIndex = in.readShort();
      this.renderFlags = in.readChar();
      this.texUnitNumber = in.readChar();
      this.opCount = in.readChar();
      this.texture = in.readChar();
      this.texUnitNumber2 = in.readChar();
      this.transparency = in.readChar();
      this.textureAnim = in.readChar();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeByte(this.flags);
      out.writeByte(this.flags2);
      out.writeChar(this.shaderID);
      out.writeChar(this.submeshIndex);
      out.writeChar(this.submeshIndex2);
      out.writeShort(this.colorIndex);
      out.writeChar(this.renderFlags);
      out.writeChar(this.texUnitNumber);
      out.writeChar(this.opCount);
      out.writeChar(this.texture);
      out.writeChar(this.texUnitNumber2);
      out.writeChar(this.transparency);
      out.writeChar(this.textureAnim);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tflags: ").append(this.flags).append("\n\tflags2: ").append(this.flags2).append("\n\tshaderID: ").append(this.shaderID).append("\n\tsubmeshIndex: ").append(this.submeshIndex).append("\n\tsubmeshIndex2: ").append(this.submeshIndex2).append("\n\tcolorIndex: ").append(this.colorIndex).append("\n\trenderFlags: ").append(this.renderFlags).append("\n\ttexUnitNumber: ").append(this.texUnitNumber).append("\n\topCount: ").append(this.opCount).append("\n\ttexture: ").append(this.texture).append("\n\ttexUnitNumber2: ").append(this.texUnitNumber2).append("\n\ttransparency: ").append(this.transparency).append("\n\ttextureAnim: ").append(this.textureAnim).append("\n}");
      return builder.toString();
   }
}
