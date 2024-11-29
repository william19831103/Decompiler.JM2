package jm2lib.blizzard.wow.lichking;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Light implements AnimFilesHandler {
   public short type = 0;
   public short bone = 0;
   public Vec3F position = new Vec3F();
   public AnimationBlock<Vec3F> ambientColor = new AnimationBlock(Vec3F.class);
   public AnimationBlock<Float> ambientIntensity;
   public AnimationBlock<Vec3F> diffuseColor;
   public AnimationBlock<Float> diffuseIntensity;
   public AnimationBlock<Float> attenuationStart;
   public AnimationBlock<Float> attenuationEnd;
   public AnimationBlock<Byte> unknown;

   public Light() {
      this.ambientIntensity = new AnimationBlock(Float.TYPE);
      this.diffuseColor = new AnimationBlock(Vec3F.class);
      this.diffuseIntensity = new AnimationBlock(Float.TYPE);
      this.attenuationStart = new AnimationBlock(Float.TYPE);
      this.attenuationEnd = new AnimationBlock(Float.TYPE);
      this.unknown = new AnimationBlock(Byte.TYPE);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.type = in.readShort();
      this.bone = in.readShort();
      this.position.unmarshal(in);
      this.ambientColor.unmarshal(in);
      this.ambientIntensity.unmarshal(in);
      this.diffuseColor.unmarshal(in);
      this.diffuseIntensity.unmarshal(in);
      this.attenuationStart.unmarshal(in);
      this.attenuationEnd.unmarshal(in);
      this.unknown.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeShort(this.type);
      out.writeShort(this.bone);
      this.position.marshal(out);
      this.ambientColor.marshal(out);
      this.ambientIntensity.marshal(out);
      this.diffuseColor.marshal(out);
      this.diffuseIntensity.marshal(out);
      this.attenuationStart.marshal(out);
      this.attenuationEnd.marshal(out);
      this.unknown.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.ambientColor.writeContent(out);
      this.ambientIntensity.writeContent(out);
      this.diffuseColor.writeContent(out);
      this.diffuseIntensity.writeContent(out);
      this.attenuationStart.writeContent(out);
      this.attenuationEnd.writeContent(out);
      this.unknown.writeContent(out);
   }

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.ambientColor.setAnimFiles(animFiles);
      this.ambientIntensity.setAnimFiles(animFiles);
      this.diffuseColor.setAnimFiles(animFiles);
      this.diffuseIntensity.setAnimFiles(animFiles);
      this.attenuationStart.setAnimFiles(animFiles);
      this.attenuationEnd.setAnimFiles(animFiles);
      this.unknown.setAnimFiles(animFiles);
   }

   public jm2lib.blizzard.wow.classic.Light downConvert(ArrayRef<jm2lib.blizzard.wow.classic.Animation> animations) throws Exception {
      jm2lib.blizzard.wow.classic.Light output = new jm2lib.blizzard.wow.classic.Light();
      output.type = this.type;
      output.bone = this.bone;
      output.position = this.position;
      output.ambientColor = this.ambientColor.downConvert(animations);
      output.ambientIntensity = this.ambientIntensity.downConvert(animations);
      output.diffuseColor = this.diffuseColor.downConvert(animations);
      output.diffuseIntensity = this.diffuseIntensity.downConvert(animations);
      output.attenuationStart = this.attenuationStart.downConvert(animations);
      output.attenuationEnd = this.attenuationEnd.downConvert(animations);
      output.unknown = this.unknown.downConvert(animations);
      return output;
   }
}
