package jm2lib.blizzard.wow.lateburningcrusade;

import java.io.IOException;
import java.util.Arrays;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.BGRA;
import jm2lib.blizzard.common.types.QuatF;
import jm2lib.blizzard.common.types.Vec2F;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.blizzard.wow.classic.AnimationBlock;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Particle implements Referencer {
   public int unknown = 0;
   public int flags = 0;
   public Vec3F position = new Vec3F();
   public char bone = 0;
   public char texture = 0;
   public ArrayRef<Byte> modelFileName;
   public ArrayRef<Byte> childEmitterFileName;
   public byte blendingType;
   public byte emitterType;
   public char particleColorIndex;
   public byte particleType;
   public byte headOrTail;
   public char textureTileRotation;
   public char textureDimensionsRows;
   public char textureDimensionsColumns;
   public AnimationBlock<Float> emissionSpeed;
   public AnimationBlock<Float> speedVariation;
   public AnimationBlock<Float> verticalRange;
   public AnimationBlock<Float> horizontalRange;
   public AnimationBlock<Float> gravity;
   public AnimationBlock<Float> lifespan;
   public AnimationBlock<Float> emissionRate;
   public AnimationBlock<Float> emissionAreaLength;
   public AnimationBlock<Float> emissionAreaWidth;
   public AnimationBlock<Float> gravity2;
   public float midPoint;
   public BGRA[] colorTrack;
   public float[] scaleTrack;
   public char[] headCellTrack1;
   public short between1;
   public char[] headCellTrack2;
   public short between2;
   public short[] tiles;
   public float somethingParticleStyle;
   public Vec2F unknownFloats1;
   public Vec2F twinkleScale;
   public float blank;
   public float drag;
   public float rotation;
   public float[] manyFloats;
   public QuatF followParams;
   public ArrayRef<Vec3F> unknownReference;
   public AnimationBlock<Byte> enabledIn;

   public Particle() {
      this.modelFileName = new ArrayRef(Byte.TYPE);
      this.childEmitterFileName = new ArrayRef(Byte.TYPE);
      this.blendingType = 0;
      this.emitterType = 0;
      this.particleColorIndex = 0;
      this.particleType = 0;
      this.headOrTail = 0;
      this.textureTileRotation = 0;
      this.textureDimensionsRows = 0;
      this.textureDimensionsColumns = 0;
      this.emissionSpeed = new AnimationBlock(Float.TYPE);
      this.speedVariation = new AnimationBlock(Float.TYPE);
      this.verticalRange = new AnimationBlock(Float.TYPE);
      this.horizontalRange = new AnimationBlock(Float.TYPE);
      this.gravity = new AnimationBlock(Float.TYPE);
      this.lifespan = new AnimationBlock(Float.TYPE);
      this.emissionRate = new AnimationBlock(Float.TYPE);
      this.emissionAreaLength = new AnimationBlock(Float.TYPE);
      this.emissionAreaWidth = new AnimationBlock(Float.TYPE);
      this.gravity2 = new AnimationBlock(Float.TYPE);
      this.midPoint = 0.0F;
      this.colorTrack = new BGRA[3];

      for(byte i = 0; i < 3; ++i) {
         this.colorTrack[i] = new BGRA();
      }

      this.scaleTrack = new float[3];
      this.headCellTrack1 = new char[2];
      this.between1 = 1;
      this.headCellTrack2 = new char[2];
      this.between2 = 1;
      this.tiles = new short[4];
      this.somethingParticleStyle = 0.0F;
      this.unknownFloats1 = new Vec2F();
      this.twinkleScale = new Vec2F();
      this.blank = 0.0F;
      this.drag = 0.0F;
      this.rotation = 0.0F;
      this.manyFloats = new float[10];
      this.followParams = new QuatF();
      this.unknownReference = new ArrayRef(Vec3F.class);
      this.enabledIn = new AnimationBlock(Byte.TYPE);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.unknown = in.readInt();
      this.flags = in.readInt();
      this.position.unmarshal(in);
      this.bone = in.readChar();
      this.texture = in.readChar();
      this.modelFileName.unmarshal(in);
      this.childEmitterFileName.unmarshal(in);
      this.blendingType = in.readByte();
      this.emitterType = in.readByte();
      this.particleColorIndex = in.readChar();
      this.particleType = in.readByte();
      this.headOrTail = in.readByte();
      this.textureTileRotation = in.readChar();
      this.textureDimensionsRows = in.readChar();
      this.textureDimensionsColumns = in.readChar();
      this.emissionSpeed.unmarshal(in);
      this.speedVariation.unmarshal(in);
      this.verticalRange.unmarshal(in);
      this.horizontalRange.unmarshal(in);
      this.gravity.unmarshal(in);
      this.lifespan.unmarshal(in);
      this.emissionRate.unmarshal(in);
      this.emissionAreaLength.unmarshal(in);
      this.emissionAreaWidth.unmarshal(in);
      this.gravity2.unmarshal(in);
      this.midPoint = in.readFloat();

      byte i;
      for(i = 0; i < this.colorTrack.length; ++i) {
         this.colorTrack[i].unmarshal(in);
      }

      for(i = 0; i < this.scaleTrack.length; ++i) {
         this.scaleTrack[i] = in.readFloat();
      }

      for(i = 0; i < this.headCellTrack1.length; ++i) {
         this.headCellTrack1[i] = in.readChar();
      }

      this.between1 = in.readShort();

      for(i = 0; i < this.headCellTrack2.length; ++i) {
         this.headCellTrack2[i] = in.readChar();
      }

      this.between2 = in.readShort();

      for(i = 0; i < this.tiles.length; ++i) {
         this.tiles[i] = in.readShort();
      }

      this.somethingParticleStyle = in.readFloat();
      this.unknownFloats1.unmarshal(in);
      this.twinkleScale.unmarshal(in);
      this.blank = in.readFloat();
      this.drag = in.readFloat();
      this.rotation = in.readFloat();

      for(i = 0; i < 10; ++i) {
         this.manyFloats[i] = in.readFloat();
      }

      this.followParams.unmarshal(in);
      this.unknownReference.unmarshal(in);
      this.enabledIn.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeInt(this.unknown);
      out.writeInt(this.flags);
      this.position.marshal(out);
      out.writeChar(this.bone);
      out.writeChar(this.texture);
      this.modelFileName.marshal(out);
      this.childEmitterFileName.marshal(out);
      out.writeByte(this.blendingType);
      out.writeByte(this.emitterType);
      out.writeChar(this.particleColorIndex);
      out.writeByte(this.particleType);
      out.writeByte(this.headOrTail);
      out.writeChar(this.textureTileRotation);
      out.writeChar(this.textureDimensionsRows);
      out.writeChar(this.textureDimensionsColumns);
      this.emissionSpeed.marshal(out);
      this.speedVariation.marshal(out);
      this.verticalRange.marshal(out);
      this.horizontalRange.marshal(out);
      this.gravity.marshal(out);
      this.lifespan.marshal(out);
      this.emissionRate.marshal(out);
      this.emissionAreaLength.marshal(out);
      this.emissionAreaWidth.marshal(out);
      this.gravity2.marshal(out);
      out.writeFloat(this.midPoint);

      byte i;
      for(i = 0; i < this.colorTrack.length; ++i) {
         this.colorTrack[i].marshal(out);
      }

      for(i = 0; i < this.scaleTrack.length; ++i) {
         out.writeFloat(this.scaleTrack[i]);
      }

      for(i = 0; i < this.headCellTrack1.length; ++i) {
         out.writeChar(this.headCellTrack1[i]);
      }

      out.writeShort(this.between1);

      for(i = 0; i < this.headCellTrack2.length; ++i) {
         out.writeChar(this.headCellTrack2[i]);
      }

      out.writeShort(this.between2);

      for(i = 0; i < this.tiles.length; ++i) {
         out.writeShort(this.tiles[i]);
      }

      out.writeFloat(this.somethingParticleStyle);
      this.unknownFloats1.marshal(out);
      this.twinkleScale.marshal(out);
      out.writeFloat(this.blank);
      out.writeFloat(this.drag);
      out.writeFloat(this.rotation);

      for(i = 0; i < 10; ++i) {
         out.writeFloat(this.manyFloats[i]);
      }

      this.followParams.marshal(out);
      this.unknownReference.marshal(out);
      this.enabledIn.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.modelFileName.writeContent(out);
      this.childEmitterFileName.writeContent(out);
      this.emissionSpeed.writeContent(out);
      this.speedVariation.writeContent(out);
      this.verticalRange.writeContent(out);
      this.horizontalRange.writeContent(out);
      this.gravity.writeContent(out);
      this.lifespan.writeContent(out);
      this.emissionRate.writeContent(out);
      this.emissionAreaLength.writeContent(out);
      this.emissionAreaWidth.writeContent(out);
      this.gravity2.writeContent(out);
      this.unknownReference.writeContent(out);
      this.enabledIn.writeContent(out);
   }

   public jm2lib.blizzard.wow.classic.Particle downConvert() {
      jm2lib.blizzard.wow.classic.Particle output = new jm2lib.blizzard.wow.classic.Particle();
      output.unknown = this.unknown;
      output.flags = this.flags;
      output.position = this.position;
      output.bone = this.bone;
      output.texture = this.texture;
      output.modelFileName = this.modelFileName;
      output.childEmitterFileName = this.childEmitterFileName;
      output.blendingType = (char)this.blendingType;
      output.emitterType = (char)this.emitterType;
      output.particleType = this.particleType;
      output.headOrTail = this.headOrTail;
      output.textureTileRotation = this.textureTileRotation;
      output.textureDimensionsRows = this.textureDimensionsRows;
      output.textureDimensionsColumns = this.textureDimensionsColumns;
      output.emissionSpeed = this.emissionSpeed;
      output.speedVariation = this.speedVariation;
      output.verticalRange = this.verticalRange;
      output.horizontalRange = this.horizontalRange;
      output.gravity = this.gravity;
      output.lifespan = this.lifespan;
      output.emissionRate = this.emissionRate;
      output.emissionAreaLength = this.emissionAreaLength;
      output.emissionAreaWidth = this.emissionAreaWidth;
      output.gravity2 = this.gravity2;
      output.midPoint = this.midPoint;
      output.colorTrack = this.colorTrack;
      output.scaleTrack = this.scaleTrack;
      output.headCellTrack1 = this.headCellTrack1;
      output.between1 = this.between1;
      output.headCellTrack2 = this.headCellTrack2;
      output.between2 = this.between2;
      output.tiles = this.tiles;
      output.somethingParticleStyle = this.somethingParticleStyle;
      output.unknownFloats1 = this.unknownFloats1;
      output.twinkleScale = this.twinkleScale;
      output.blank = this.blank;
      output.drag = this.drag;
      output.rotation = this.rotation;
      output.manyFloats = this.manyFloats;
      output.followParams = this.followParams;
      output.unknownReference = this.unknownReference;
      output.enabledIn = this.enabledIn;
      return output;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tunknown: ").append(this.unknown).append("\n\tflags: ").append(Integer.toBinaryString(this.flags)).append("\n\tposition: ").append(this.position).append("\n\tbone: ").append(this.bone).append("\n\ttexture: ").append(this.texture).append("\n\tmodelFileName: ").append(this.modelFileName).append("\n\tchildEmitterFileName: ").append(this.childEmitterFileName).append("\n\tblendingType: ").append(this.blendingType).append("\n\temitterType: ").append(this.emitterType).append("\n\tparticleColorIndex: ").append(this.particleColorIndex).append("\n\tparticleType: ").append(this.particleType).append("\n\theadOrTail: ").append(this.headOrTail).append("\n\ttextureTileRotation: ").append(this.textureTileRotation).append("\n\ttextureDimensionsRows: ").append(this.textureDimensionsRows).append("\n\ttextureDimensionsColumns: ").append(this.textureDimensionsColumns).append("\n\temissionSpeed: ").append(this.emissionSpeed).append("\n\tspeedVariation: ").append(this.speedVariation).append("\n\tverticalRange: ").append(this.verticalRange).append("\n\thorizontalRange: ").append(this.horizontalRange).append("\n\tgravity: ").append(this.gravity).append("\n\tlifespan: ").append(this.lifespan).append("\n\temissionRate: ").append(this.emissionRate).append("\n\temissionAreaLength: ").append(this.emissionAreaLength).append("\n\temissionAreaWidth: ").append(this.emissionAreaWidth).append("\n\tgravity2: ").append(this.gravity2).append("\n\tmidPoint: ").append(this.midPoint).append("\n\tcolorTrack: ").append(Arrays.toString(this.colorTrack)).append("\n\tscaleTrack: ").append(Arrays.toString(this.scaleTrack)).append("\n\theadCellTrack1: ").append(Arrays.toString(this.headCellTrack1)).append("\n\tbetween1: ").append(this.between1).append("\n\theadCellTrack2: ").append(Arrays.toString(this.headCellTrack2)).append("\n\tbetween2: ").append(this.between2).append("\n\ttiles: ").append(Arrays.toString(this.tiles)).append("\n\tsomethingParticleStyle: ").append(this.somethingParticleStyle).append("\n\tunknownFloats1: ").append(this.unknownFloats1).append("\n\ttwinkleScale: ").append(this.twinkleScale).append("\n\tblank: ").append(this.blank).append("\n\tdrag: ").append(this.drag).append("\n\trotation: ").append(this.rotation).append("\n\tmanyFloats: ").append(Arrays.toString(this.manyFloats)).append("\n\tfollowParams: ").append(this.followParams).append("\n\tunknownReference: ").append(this.unknownReference).append("\n\tenabledIn: ").append(this.enabledIn).append("\n}");
      return builder.toString();
   }
}
