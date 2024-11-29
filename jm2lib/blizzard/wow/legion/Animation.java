package jm2lib.blizzard.wow.legion;

import java.io.IOException;
import java.util.ArrayList;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Animation implements Marshalable {
   public short animationID = 0;
   public short subAnimationID = 0;
   public int length = 0;
   public float movingSpeed = 0.0F;
   public int flags = 0;
   public short probability = 0;
   public short padding = 0;
   public int[] repetitions = new int[2];
   public char startBlendTime = 150;
   public char endBlendTime = 150;
   public Vec3F minimumExtent = new Vec3F();
   public Vec3F maximumExtent = new Vec3F();
   public float boundRadius = 0.0F;
   public short nextAnimation = -1;
   public short aliasNext = -1;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.animationID = in.readShort();
      this.subAnimationID = in.readShort();
      this.length = in.readInt();
      this.movingSpeed = in.readFloat();
      this.flags = in.readInt();
      this.probability = in.readShort();
      this.padding = in.readShort();

      for(byte i = 0; i < 2; ++i) {
         this.repetitions[i] = in.readInt();
      }

      this.startBlendTime = in.readChar();
      this.endBlendTime = in.readChar();
      this.minimumExtent.unmarshal(in);
      this.maximumExtent.unmarshal(in);
      this.boundRadius = in.readFloat();
      this.nextAnimation = in.readShort();
      this.aliasNext = in.readShort();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeShort(this.animationID);
      out.writeShort(this.subAnimationID);
      out.writeInt(this.length);
      out.writeFloat(this.movingSpeed);
      out.writeInt(this.flags);
      out.writeShort(this.probability);
      out.writeShort(this.padding);

      for(byte i = 0; i < 2; ++i) {
         out.writeInt(this.repetitions[i]);
      }

      out.writeChar(this.startBlendTime);
      out.writeChar(this.endBlendTime);
      this.minimumExtent.marshal(out);
      this.maximumExtent.marshal(out);
      out.writeFloat(this.boundRadius);
      out.writeShort(this.nextAnimation);
      out.writeShort(this.aliasNext);
   }

   public boolean isAlias() {
      return (this.flags & 64) != 0;
   }

   public boolean isExtern() {
      return (this.flags & 304) == 0;
   }

   public static int getRealPos(int i, ArrayList<Animation> list) {
      return !((Animation)list.get(i)).isAlias() ? i : getRealPos(((Animation)list.get(i)).aliasNext, list);
   }

   public jm2lib.blizzard.wow.lichking.Animation downConvert() {
      jm2lib.blizzard.wow.lichking.Animation output = new jm2lib.blizzard.wow.lichking.Animation();
      output.animationID = this.animationID;
      output.subAnimationID = this.subAnimationID;
      output.length = this.length;
      output.movingSpeed = this.movingSpeed;
      output.flags = this.flags;
      output.probability = this.probability;
      output.padding = this.padding;
      output.repetitions = this.repetitions;
      output.blendTime = this.startBlendTime;
      output.minimumExtent = this.minimumExtent;
      output.maximumExtent = this.maximumExtent;
      output.boundRadius = this.boundRadius;
      output.nextAnimation = this.nextAnimation;
      output.aliasNext = this.aliasNext;
      return output;
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      String NEW_LINE = System.getProperty("line.separator");
      result.append("animationID : " + this.animationID + NEW_LINE);
      result.append("subAnimationID : " + this.subAnimationID + NEW_LINE);
      result.append("length : " + this.length + NEW_LINE);
      result.append("movingSpeed : " + this.movingSpeed + NEW_LINE);
      result.append("flags : " + Integer.toBinaryString(this.flags) + NEW_LINE);
      result.append("probability : " + this.probability + NEW_LINE);
      result.append("padding : " + this.padding + NEW_LINE);
      result.append("unknown : (" + this.repetitions[0] + ',' + this.repetitions[1] + ')' + NEW_LINE);
      result.append("startBlendTime : " + this.startBlendTime + NEW_LINE);
      result.append("endBlendTime : " + this.endBlendTime + NEW_LINE);
      result.append("minimumExtent : " + this.minimumExtent.toString() + NEW_LINE);
      result.append("maximumExtent : " + this.maximumExtent.toString() + NEW_LINE);
      result.append("boundRadius : " + this.boundRadius + NEW_LINE);
      result.append("nextAnimation : " + this.nextAnimation + NEW_LINE);
      result.append("aliasNext : " + this.aliasNext + NEW_LINE);
      return result.toString();
   }
}
