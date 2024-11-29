package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Ribbon implements Referencer {
   public int unknown0 = 0;
   public int bone = 0;
   public Vec3F position = new Vec3F();
   public ArrayRef<Character> textures;
   public ArrayRef<Character> blendRef;
   public AnimationBlock<Vec3F> color;
   public AnimationBlock<Character> opacity;
   public AnimationBlock<Float> heightAbove;
   public AnimationBlock<Float> heightBelow;
   public float resolution;
   public float length;
   public float emissionAngle;
   public char[] renderFlags;
   public AnimationBlock<Character> unknown1;
   public AnimationBlock<Byte> unknown2;

   public Ribbon() {
      this.textures = new ArrayRef(Character.TYPE);
      this.blendRef = new ArrayRef(Character.TYPE);
      this.color = new AnimationBlock(Vec3F.class);
      this.opacity = new AnimationBlock(Character.TYPE);
      this.heightAbove = new AnimationBlock(Float.TYPE);
      this.heightBelow = new AnimationBlock(Float.TYPE);
      this.resolution = 0.0F;
      this.length = 0.0F;
      this.emissionAngle = 0.0F;
      this.renderFlags = new char[2];
      this.unknown1 = new AnimationBlock(Character.TYPE);
      this.unknown2 = new AnimationBlock(Byte.TYPE);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.unknown0 = in.readInt();
      this.bone = in.readInt();
      this.position.unmarshal(in);
      this.textures.unmarshal(in);
      this.blendRef.unmarshal(in);
      this.color.unmarshal(in);
      this.opacity.unmarshal(in);
      this.heightAbove.unmarshal(in);
      this.heightBelow.unmarshal(in);
      this.resolution = in.readFloat();
      this.length = in.readFloat();
      this.emissionAngle = in.readFloat();

      for(byte i = 0; i < this.renderFlags.length; ++i) {
         this.renderFlags[i] = in.readChar();
      }

      this.unknown1.unmarshal(in);
      this.unknown2.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeInt(this.unknown0);
      out.writeInt(this.bone);
      this.position.marshal(out);
      this.textures.marshal(out);
      this.blendRef.marshal(out);
      this.color.marshal(out);
      this.opacity.marshal(out);
      this.heightAbove.marshal(out);
      this.heightBelow.marshal(out);
      out.writeFloat(this.resolution);
      out.writeFloat(this.length);
      out.writeFloat(this.emissionAngle);

      for(byte i = 0; i < this.renderFlags.length; ++i) {
         out.writeChar(this.renderFlags[i]);
      }

      this.unknown1.marshal(out);
      this.unknown2.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.textures.writeContent(out);
      this.blendRef.writeContent(out);
      this.color.writeContent(out);
      this.opacity.writeContent(out);
      this.heightAbove.writeContent(out);
      this.heightBelow.writeContent(out);
      this.unknown1.writeContent(out);
      this.unknown2.writeContent(out);
   }
}
