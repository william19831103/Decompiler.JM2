package jm2lib.blizzard.wow.lichking;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.QuatS;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Bone implements AnimFilesHandler {
   public int keyBoneID = 0;
   public int flags = 0;
   public short parentBone = 0;
   public char submeshID = 0;
   public char[] unknown = new char[2];
   public AnimationBlock<Vec3F> translation = new AnimationBlock(Vec3F.class);
   public AnimationBlock<QuatS> rotation = new AnimationBlock(QuatS.class, 1);
   public AnimationBlock<Vec3F> scale = new AnimationBlock(Vec3F.class, 1);
   public Vec3F pivot = new Vec3F();

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.keyBoneID = in.readInt();
      this.flags = in.readInt();
      this.parentBone = in.readShort();
      this.submeshID = in.readChar();

      for(byte i = 0; i < 2; ++i) {
         this.unknown[i] = in.readChar();
      }

      this.translation.unmarshal(in);
      this.rotation.unmarshal(in);
      this.scale.unmarshal(in);
      this.pivot.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeInt(this.keyBoneID);
      out.writeInt(this.flags);
      out.writeShort(this.parentBone);
      out.writeShort(this.submeshID);

      for(byte i = 0; i < this.unknown.length; ++i) {
         out.writeShort(this.unknown[i]);
      }

      this.translation.marshal(out);
      this.rotation.marshal(out);
      this.scale.marshal(out);
      this.pivot.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.translation.writeContent(out);
      this.rotation.writeContent(out);
      this.scale.writeContent(out);
   }

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.translation.setAnimFiles(animFiles);
      this.rotation.setAnimFiles(animFiles);
      this.scale.setAnimFiles(animFiles);
   }

   public jm2lib.blizzard.wow.burningcrusade.Bone downConvert(ArrayRef<jm2lib.blizzard.wow.classic.Animation> animations) throws Exception {
      jm2lib.blizzard.wow.burningcrusade.Bone output = new jm2lib.blizzard.wow.burningcrusade.Bone();
      output.keyBoneID = this.keyBoneID;
      output.flags = this.flags;
      output.parentBone = this.parentBone;
      output.submeshID = this.submeshID;
      output.unknown = this.unknown;
      output.translation = this.translation.downConvert(animations);
      output.rotation = this.rotation.downConvert(animations);
      output.scale = this.scale.downConvert(animations);
      output.pivot = this.pivot;
      return output;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tkeyBoneID: ").append(this.keyBoneID).append("\n\tflags: ").append(Integer.toBinaryString(this.flags)).append("\n\tparentBone: ").append(this.parentBone).append("\n\tsubmeshID: ").append(Integer.toHexString(this.submeshID)).append("\n\tunknown: ");
      builder.append("[" + this.unknown[0] + " " + this.unknown[1] + "]");
      builder.append("\n\ttranslation: ").append(this.translation).append("\n\trotation: ").append(this.rotation).append("\n\tscale: ").append(this.scale).append("\n\tpivot: ").append(this.pivot).append("\n}");
      return builder.toString();
   }
}
