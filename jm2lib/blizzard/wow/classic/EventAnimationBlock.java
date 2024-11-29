package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.Vec2I;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class EventAnimationBlock implements Referencer {
   public short interpolationType = 0;
   public short globalSequence = 0;
   public ArrayRef<Vec2I> ranges = new ArrayRef(Vec2I.class);
   public ArrayRef<Integer> timestamps;

   public EventAnimationBlock() {
      this.timestamps = new ArrayRef(Integer.TYPE);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.interpolationType = in.readShort();
      this.globalSequence = in.readShort();
      this.ranges.unmarshal(in);
      this.timestamps.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeShort(this.interpolationType);
      out.writeShort(this.globalSequence);
      this.ranges.marshal(out);
      this.timestamps.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws IOException, InstantiationException, IllegalAccessException {
      this.ranges.writeContent(out);
      this.timestamps.writeContent(out);
   }

   private String printInterpolationType() {
      return AnimationBlock.printInterpolationType(this.interpolationType);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tinterpolationType: ").append(this.printInterpolationType()).append("\n\tglobalSequence: ").append(this.globalSequence).append("\n\tranges: ").append(this.ranges).append("\n\ttimestamps: ").append(this.timestamps).append("\n}");
      return builder.toString();
   }
}
