package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.QuatF;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Bone implements Referencer {
   public int keyBoneID = 0;
   public int flags = 0;
   public short parentBone = 0;
   public char submeshID = 0;
   public AnimationBlock<Vec3F> translation = new AnimationBlock(Vec3F.class);
   public AnimationBlock<QuatF> rotation = new AnimationBlock(QuatF.class);
   public AnimationBlock<Vec3F> scale = new AnimationBlock(Vec3F.class);
   public Vec3F pivot = new Vec3F();

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.keyBoneID = in.readInt();
      this.flags = in.readInt();
      this.parentBone = in.readShort();
      this.submeshID = in.readChar();
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

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tkeyBoneID: ").append(this.keyBoneID).append("\n\tflags: ").append(this.flags).append("\n\tparentBone: ").append(this.parentBone).append("\n\tsubmeshID: ").append(Integer.toHexString(this.submeshID)).append("\n\tunknown: ");
      builder.append("\n\ttranslation: ").append(this.translation).append("\n\trotation: ").append(this.rotation).append("\n\tscale: ").append(this.scale).append("\n\tpivot: ").append(this.pivot).append("\n}");
      return builder.toString();
   }

   public jm2lib.blizzard.wow.burningcrusade.Bone upConvert() {
      jm2lib.blizzard.wow.burningcrusade.Bone output = new jm2lib.blizzard.wow.burningcrusade.Bone();
      output.keyBoneID = this.keyBoneID;
      output.flags = this.flags;
      output.parentBone = this.parentBone;
      output.submeshID = this.submeshID;
      output.translation = this.translation;
      output.rotation.interpolationType = this.rotation.interpolationType;
      output.rotation.globalSequence = this.rotation.globalSequence;
      output.rotation.ranges = this.rotation.ranges;
      output.rotation.timestamps = this.rotation.timestamps;

      for(int i = 0; i < this.rotation.values.size(); ++i) {
         output.rotation.values.add(((QuatF)this.rotation.values.get(i)).toQuatS());
      }

      output.scale = this.scale;
      output.pivot = this.pivot;
      return output;
   }
}
