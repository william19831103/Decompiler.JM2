package jm2lib.blizzard.wow.cataclysm;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import java.util.Arrays;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.FixedPoint;
import jm2lib.blizzard.common.types.QuatF;
import jm2lib.blizzard.common.types.Vec2F;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.blizzard.wow.lichking.AnimationBlock;
import jm2lib.blizzard.wow.lichking.FakeAnimationBlock;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Particle implements AnimFilesHandler {
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
   public FixedPoint[] multiTextureParamX;
   public char textureTileRotation;
   public char textureDimensionsRows;
   public char textureDimensionsColumns;
   public AnimationBlock<Float> emissionSpeed;
   public AnimationBlock<Float> speedVariation;
   public AnimationBlock<Float> verticalRange;
   public AnimationBlock<Float> horizontalRange;
   public AnimationBlock<Float> gravity;
   public AnimationBlock<Float> lifespan;
   public int unknownPadding;
   public AnimationBlock<Float> emissionRate;
   public int unknownPadding2;
   public AnimationBlock<Float> emissionAreaLength;
   public AnimationBlock<Float> emissionAreaWidth;
   public AnimationBlock<Float> gravity2;
   public FakeAnimationBlock<Vec3F> colorTrack;
   public FakeAnimationBlock<Short> alphaTrack;
   public FakeAnimationBlock<Vec2F> scaleTrack;
   public Vec2F unknownFields;
   public FakeAnimationBlock<Character> headCellTrack;
   public FakeAnimationBlock<Character> tailCellTrack;
   public float somethingParticleStyle;
   public Vec2F unknownFloats1;
   public Vec2F twinkleScale;
   public float blank;
   public float drag;
   public Vec2F unknownFloats2;
   public float rotation;
   public Vec2F unknownFloats3;
   public Vec3F rot1;
   public Vec3F rot2;
   public Vec3F trans;
   public QuatF followParams;
   public ArrayRef<Vec3F> unknownReference;
   public AnimationBlock<Byte> enabledIn;
   public FixedPoint[][] multiTextureParam0;
   public FixedPoint[][] multiTextureParam1;

   public Particle() {
      this.modelFileName = new ArrayRef(Byte.TYPE);
      this.childEmitterFileName = new ArrayRef(Byte.TYPE);
      this.blendingType = 0;
      this.emitterType = 0;
      this.particleColorIndex = 0;
      this.multiTextureParamX = new FixedPoint[]{new FixedPoint(2, 5), new FixedPoint(2, 5)};
      this.textureTileRotation = 0;
      this.textureDimensionsRows = 0;
      this.textureDimensionsColumns = 0;
      this.emissionSpeed = new AnimationBlock(Float.TYPE);
      this.speedVariation = new AnimationBlock(Float.TYPE);
      this.verticalRange = new AnimationBlock(Float.TYPE);
      this.horizontalRange = new AnimationBlock(Float.TYPE);
      this.gravity = new AnimationBlock(Float.TYPE);
      this.lifespan = new AnimationBlock(Float.TYPE);
      this.unknownPadding = 0;
      this.emissionRate = new AnimationBlock(Float.TYPE);
      this.unknownPadding2 = 0;
      this.emissionAreaLength = new AnimationBlock(Float.TYPE);
      this.emissionAreaWidth = new AnimationBlock(Float.TYPE);
      this.gravity2 = new AnimationBlock(Float.TYPE);
      this.colorTrack = new FakeAnimationBlock(Vec3F.class);
      this.alphaTrack = new FakeAnimationBlock(Short.TYPE);
      this.scaleTrack = new FakeAnimationBlock(Vec2F.class);
      this.unknownFields = new Vec2F();
      this.headCellTrack = new FakeAnimationBlock(Character.TYPE);
      this.tailCellTrack = new FakeAnimationBlock(Character.TYPE);
      this.somethingParticleStyle = 0.0F;
      this.unknownFloats1 = new Vec2F();
      this.twinkleScale = new Vec2F();
      this.blank = 0.0F;
      this.drag = 0.0F;
      this.unknownFloats2 = new Vec2F();
      this.rotation = 0.0F;
      this.unknownFloats3 = new Vec2F();
      this.rot1 = new Vec3F();
      this.rot2 = new Vec3F();
      this.trans = new Vec3F();
      this.followParams = new QuatF();
      this.unknownReference = new ArrayRef(Vec3F.class);
      this.enabledIn = new AnimationBlock(Byte.TYPE);
      this.multiTextureParam0 = new FixedPoint[][]{{new FixedPoint(6, 9), new FixedPoint(6, 9)}, {new FixedPoint(6, 9), new FixedPoint(6, 9)}};
      this.multiTextureParam1 = new FixedPoint[][]{{new FixedPoint(6, 9), new FixedPoint(6, 9)}, {new FixedPoint(6, 9), new FixedPoint(6, 9)}};
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

      byte i;
      for(i = 0; i < this.multiTextureParamX.length; ++i) {
         this.multiTextureParamX[i].unmarshal(in);
      }

      this.textureTileRotation = in.readChar();
      this.textureDimensionsRows = in.readChar();
      this.textureDimensionsColumns = in.readChar();
      this.emissionSpeed.unmarshal(in);
      this.speedVariation.unmarshal(in);
      this.verticalRange.unmarshal(in);
      this.horizontalRange.unmarshal(in);
      this.gravity.unmarshal(in);
      this.lifespan.unmarshal(in);
      this.unknownPadding = in.readInt();
      this.emissionRate.unmarshal(in);
      this.unknownPadding2 = in.readInt();
      this.emissionAreaLength.unmarshal(in);
      this.emissionAreaWidth.unmarshal(in);
      this.gravity2.unmarshal(in);
      this.colorTrack.unmarshal(in);
      this.alphaTrack.unmarshal(in);
      this.scaleTrack.unmarshal(in);
      this.unknownFields.unmarshal(in);
      this.headCellTrack.unmarshal(in);
      this.tailCellTrack.unmarshal(in);
      this.somethingParticleStyle = in.readFloat();
      this.unknownFloats1.unmarshal(in);
      this.twinkleScale.unmarshal(in);
      this.blank = in.readFloat();
      this.drag = in.readFloat();
      this.unknownFloats2.unmarshal(in);
      this.rotation = in.readFloat();
      this.unknownFloats3.unmarshal(in);
      this.rot1.unmarshal(in);
      this.rot2.unmarshal(in);
      this.trans.unmarshal(in);
      this.followParams.unmarshal(in);
      this.unknownReference.unmarshal(in);
      this.enabledIn.unmarshal(in);

      byte j;
      for(i = 0; i < this.multiTextureParam0.length; ++i) {
         for(j = 0; j < this.multiTextureParam0[i].length; ++j) {
            this.multiTextureParam0[i][j].unmarshal(in);
         }
      }

      for(i = 0; i < this.multiTextureParam1.length; ++i) {
         for(j = 0; j < this.multiTextureParam1[i].length; ++j) {
            this.multiTextureParam1[i][j].unmarshal(in);
         }
      }

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

      byte i;
      for(i = 0; i < this.multiTextureParamX.length; ++i) {
         this.multiTextureParamX[i].marshal(out);
      }

      out.writeChar(this.textureTileRotation);
      out.writeChar(this.textureDimensionsRows);
      out.writeChar(this.textureDimensionsColumns);
      this.emissionSpeed.marshal(out);
      this.speedVariation.marshal(out);
      this.verticalRange.marshal(out);
      this.horizontalRange.marshal(out);
      this.gravity.marshal(out);
      this.lifespan.marshal(out);
      out.writeInt(this.unknownPadding);
      this.emissionRate.marshal(out);
      out.writeInt(this.unknownPadding2);
      this.emissionAreaLength.marshal(out);
      this.emissionAreaWidth.marshal(out);
      this.gravity2.marshal(out);
      this.colorTrack.marshal(out);
      this.alphaTrack.marshal(out);
      this.scaleTrack.marshal(out);
      this.unknownFields.marshal(out);
      this.headCellTrack.marshal(out);
      this.tailCellTrack.marshal(out);
      out.writeFloat(this.somethingParticleStyle);
      this.unknownFloats1.marshal(out);
      this.twinkleScale.marshal(out);
      out.writeFloat(this.blank);
      out.writeFloat(this.drag);
      this.unknownFloats2.marshal(out);
      out.writeFloat(this.rotation);
      this.unknownFloats3.marshal(out);
      this.rot1.marshal(out);
      this.rot2.marshal(out);
      this.trans.marshal(out);
      this.followParams.marshal(out);
      this.unknownReference.marshal(out);
      this.enabledIn.marshal(out);

      byte j;
      for(i = 0; i < this.multiTextureParam0.length; ++i) {
         for(j = 0; j < this.multiTextureParam0[i].length; ++j) {
            this.multiTextureParam0[i][j].marshal(out);
         }
      }

      for(i = 0; i < this.multiTextureParam1.length; ++i) {
         for(j = 0; j < this.multiTextureParam1[i].length; ++j) {
            this.multiTextureParam1[i][j].marshal(out);
         }
      }

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
      this.colorTrack.writeContent(out);
      this.alphaTrack.writeContent(out);
      this.scaleTrack.writeContent(out);
      this.headCellTrack.writeContent(out);
      this.tailCellTrack.writeContent(out);
      this.unknownReference.writeContent(out);
      this.enabledIn.writeContent(out);
   }

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.emissionSpeed.setAnimFiles(animFiles);
      this.speedVariation.setAnimFiles(animFiles);
      this.verticalRange.setAnimFiles(animFiles);
      this.horizontalRange.setAnimFiles(animFiles);
      this.gravity.setAnimFiles(animFiles);
      this.lifespan.setAnimFiles(animFiles);
      this.emissionRate.setAnimFiles(animFiles);
      this.emissionAreaLength.setAnimFiles(animFiles);
      this.emissionAreaWidth.setAnimFiles(animFiles);
      this.gravity2.setAnimFiles(animFiles);
      this.enabledIn.setAnimFiles(animFiles);
   }

   public jm2lib.blizzard.wow.lichking.Particle downConvert() throws Exception {
      jm2lib.blizzard.wow.lichking.Particle output = new jm2lib.blizzard.wow.lichking.Particle();
      output.unknown = this.unknown;
      output.flags = this.flags;
      output.position = this.position;
      output.bone = this.bone;
      output.texture = this.texture;
      if ((this.flags & 268435456) != 0) {
         output.texture = (char)(this.texture & 31);
         output.flags &= 268435455;
      }

      output.modelFileName = this.modelFileName;
      output.childEmitterFileName = this.childEmitterFileName;
      output.blendingType = this.blendingType;
      if (output.blendingType > 4) {
         output.blendingType = 4;
      }

      output.emitterType = this.emitterType;
      output.particleColorIndex = this.particleColorIndex;
      output.textureTileRotation = this.textureTileRotation;
      output.textureDimensionsRows = this.textureDimensionsRows;
      output.textureDimensionsColumns = this.textureDimensionsColumns;
      output.emissionSpeed = this.emissionSpeed;
      output.speedVariation = this.speedVariation;
      output.verticalRange = this.verticalRange;
      output.horizontalRange = this.horizontalRange;
      output.gravity = this.gravity;
      output.lifespan = this.lifespan;
      output.unknownPadding = this.unknownPadding;
      output.emissionRate = this.emissionRate;
      output.unknownPadding2 = this.unknownPadding2;
      output.emissionAreaLength = this.emissionAreaLength;
      output.emissionAreaWidth = this.emissionAreaWidth;
      output.gravity2 = this.gravity2;
      output.colorTrack = this.colorTrack;
      output.alphaTrack = this.alphaTrack;
      output.scaleTrack = this.scaleTrack;
      output.unknownFields = this.unknownFields;
      output.headCellTrack = this.headCellTrack;
      output.tailCellTrack = this.tailCellTrack;
      output.somethingParticleStyle = this.somethingParticleStyle;
      output.unknownFloats1 = this.unknownFloats1;
      output.twinkleScale = this.twinkleScale;
      output.blank = this.blank;
      output.drag = this.drag;
      output.unknownFloats2 = this.unknownFloats2;
      output.rotation = this.rotation;
      output.unknownFloats3 = this.unknownFloats3;
      output.rot1 = this.rot1;
      output.rot2 = this.rot2;
      output.trans = this.trans;
      output.followParams = this.followParams;
      output.unknownReference = this.unknownReference;
      output.enabledIn = this.enabledIn;
      return output;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tunknown: ").append(this.unknown).append("\n\tflags: ").append(this.flags).append("\n\tposition: ").append(this.position).append("\n\tbone: ").append(this.bone).append("\n\ttexture: ").append(this.texture).append("\n\tmodelFileName: ").append(this.modelFileName.toNameString()).append("\n\tchildEmitterFileName: ").append(this.childEmitterFileName.toNameString()).append("\n\tblendingType: ").append(this.blendingType).append("\n\temitterType: ").append(this.emitterType).append("\n\tparticleColorIndex: ").append(this.particleColorIndex).append("\n\tmultiTextureParamX: ").append(Arrays.toString(this.multiTextureParamX)).append("\n\ttextureTileRotation: ").append(this.textureTileRotation).append("\n\ttextureDimensionsRows: ").append(this.textureDimensionsRows).append("\n\ttextureDimensionsColumns: ").append(this.textureDimensionsColumns).append("\n\temissionSpeed: ").append(this.emissionSpeed).append("\n\tspeedVariation: ").append(this.speedVariation).append("\n\tverticalRange: ").append(this.verticalRange).append("\n\thorizontalRange: ").append(this.horizontalRange).append("\n\tgravity: ").append(this.gravity).append("\n\tlifespan: ").append(this.lifespan).append("\n\tunknownPadding: ").append(this.unknownPadding).append("\n\temissionRate: ").append(this.emissionRate).append("\n\tunknownPadding2: ").append(this.unknownPadding2).append("\n\temissionAreaLength: ").append(this.emissionAreaLength).append("\n\temissionAreaWidth: ").append(this.emissionAreaWidth).append("\n\tgravity2: ").append(this.gravity2).append("\n\tcolorTrack: ").append(this.colorTrack).append("\n\talphaTrack: ").append(this.alphaTrack).append("\n\tscaleTrack: ").append(this.scaleTrack).append("\n\tunknownFields: ").append(this.unknownFields).append("\n\theadCellTrack: ").append(this.headCellTrack).append("\n\ttailCellTrack: ").append(this.tailCellTrack).append("\n\tsomethingParticleStyle: ").append(this.somethingParticleStyle).append("\n\tunknownFloats1: ").append(this.unknownFloats1).append("\n\ttwinkleScale: ").append(this.twinkleScale).append("\n\tblank: ").append(this.blank).append("\n\tdrag: ").append(this.drag).append("\n\tunknownFloats2: ").append(this.unknownFloats2).append("\n\trotation: ").append(this.rotation).append("\n\tunknownFloats3: ").append(this.unknownFloats3).append("\n\trot1: ").append(this.rot1).append("\n\trot2: ").append(this.rot2).append("\n\ttrans: ").append(this.trans).append("\n\tfollowParams: ").append(this.followParams).append("\n\tunknownReference: ").append(this.unknownReference).append("\n\tenabledIn: ").append(this.enabledIn).append("\n\tmultiTextureParam0: ").append(Arrays.toString(this.multiTextureParam0)).append("\n\tmultiTextureParam1: ").append(Arrays.toString(this.multiTextureParam1)).append("\n}");
      return builder.toString();
   }
}