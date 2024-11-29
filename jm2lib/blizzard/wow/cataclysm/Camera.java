package jm2lib.blizzard.wow.cataclysm;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.blizzard.common.types.Vec9F;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.blizzard.wow.lichking.AnimationBlock;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Camera implements AnimFilesHandler {
   public int type = 0;
   public float farClip = 0.0F;
   public float nearClip = 0.0F;
   public AnimationBlock<Vec9F> positions = new AnimationBlock(Vec9F.class);
   public Vec3F positionBase = new Vec3F();
   public AnimationBlock<Vec9F> targetPositions = new AnimationBlock(Vec9F.class);
   public Vec3F targetPositionBase = new Vec3F();
   public AnimationBlock<Vec3F> roll = new AnimationBlock(Vec3F.class);
   public AnimationBlock<Vec3F> fov = new AnimationBlock(Vec3F.class);

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\ttype: ").append(this.type).append("\n\tfarClip: ").append(this.farClip).append("\n\tnearClip: ").append(this.nearClip).append("\n\tpositions: ").append(this.positions).append("\n\tpositionBase: ").append(this.positionBase).append("\n\ttargetPositions: ").append(this.targetPositions).append("\n\ttargetPositionBase: ").append(this.targetPositionBase).append("\n\troll: ").append(this.roll).append("\n\tfov: ").append(this.fov).append("\n}");
      return builder.toString();
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.type = in.readInt();
      this.farClip = in.readFloat();
      this.nearClip = in.readFloat();
      this.positions.unmarshal(in);
      this.positionBase.unmarshal(in);
      this.targetPositions.unmarshal(in);
      this.targetPositionBase.unmarshal(in);
      this.roll.unmarshal(in);
      this.fov.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeInt(this.type);
      out.writeFloat(this.farClip);
      out.writeFloat(this.nearClip);
      this.positions.marshal(out);
      this.positionBase.marshal(out);
      this.targetPositions.marshal(out);
      this.targetPositionBase.marshal(out);
      this.roll.marshal(out);
      this.fov.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws InstantiationException, IllegalAccessException, IOException {
      this.positions.writeContent(out);
      this.targetPositions.writeContent(out);
      this.roll.writeContent(out);
      this.fov.writeContent(out);
   }

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.positions.setAnimFiles(animFiles);
      this.targetPositions.setAnimFiles(animFiles);
      this.roll.setAnimFiles(animFiles);
      this.fov.setAnimFiles(animFiles);
   }

   public jm2lib.blizzard.wow.lichking.Camera downConvert() throws Exception {
      jm2lib.blizzard.wow.lichking.Camera output = new jm2lib.blizzard.wow.lichking.Camera();
      output.type = this.type;
      output.fov = this.type == 0 ? 0.7F : 0.97F;
      if (this.fov.values.size() == 1) {
         output.fov = ((Vec3F)((ArrayRef)this.fov.values.get(0)).get(0)).getX();
      }

      output.farClip = this.farClip;
      output.nearClip = this.nearClip;
      output.positions = this.positions;
      output.positionBase = this.positionBase;
      output.targetPositions = this.targetPositions;
      output.targetPositionBase = this.targetPositionBase;
      output.roll = this.roll;
      return output;
   }

   public void setFieldOfView(float oldFov) {
      ArrayRef<Integer> times = new ArrayRef(Integer.TYPE);
      ArrayRef<Vec3F> values = new ArrayRef(Vec3F.class);
      times.add(0);
      values.add(new Vec3F(oldFov, 0.0F, 0.0F));
      this.fov.timestamps.add(times);
      this.fov.values.add(values);
   }
}
