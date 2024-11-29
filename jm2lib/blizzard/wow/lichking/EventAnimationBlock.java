package jm2lib.blizzard.wow.lichking;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import java.util.Collection;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.Vec2I;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class EventAnimationBlock implements AnimFilesHandler {
   public short interpolationType = 0;
   public short globalSequence = 0;
   public ArrayRef<ArrayRef<Integer>> timestamps;

   public EventAnimationBlock() {
      this.timestamps = new ArrayRef(ArrayRef.class, Integer.TYPE);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.interpolationType = in.readShort();
      this.globalSequence = in.readShort();
      this.timestamps.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeShort(this.interpolationType);
      out.writeShort(this.globalSequence);
      this.timestamps.marshal(out);
   }

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.timestamps.setAnimFiles(animFiles);
   }

   public void writeContent(MarshalingStream out) throws IOException, InstantiationException, IllegalAccessException {
      this.timestamps.writeContent(out);
   }

   private String printInterpolationType() {
      return AnimationBlock.printInterpolationType(this.interpolationType);
   }

   public jm2lib.blizzard.wow.classic.EventAnimationBlock downConvert(ArrayRef<jm2lib.blizzard.wow.classic.Animation> animations) throws Exception {
      jm2lib.blizzard.wow.classic.EventAnimationBlock output = new jm2lib.blizzard.wow.classic.EventAnimationBlock();
      output.interpolationType = this.interpolationType;
      output.globalSequence = this.globalSequence;
      if (!this.timestamps.isEmpty()) {
         if (this.interpolationType == 0 && this.timestamps.size() == 1) {
            output.timestamps.addAll((Collection)this.timestamps.get(0));
            output.ranges.add(new Vec2I(0, 1));
         } else if (this.globalSequence == 0) {
            output.timestamps.addAll((Collection)this.timestamps.get(0));
         } else {
            if (this.timestamps.size() != animations.size()) {
               StringBuilder builder = new StringBuilder();
               builder.append("Unknown event animation block conversion error.\n");
               builder.append(this.toString());
               throw new Exception(builder.toString());
            }

            int oldSize = 0;

            for(int i = 0; i < this.timestamps.size(); ++i) {
               int timeStart = ((jm2lib.blizzard.wow.classic.Animation)animations.get(i)).timeStart;
               int timeEnd = ((jm2lib.blizzard.wow.classic.Animation)animations.get(i)).timeEnd;
               if (((ArrayRef)this.timestamps.get(i)).size() == 1) {
                  output.timestamps.add((Integer)((ArrayRef)this.timestamps.get(i)).get(0) + timeStart);
                  output.timestamps.add((Integer)((ArrayRef)this.timestamps.get(i)).get(0) + timeEnd);
                  output.ranges.add(new Vec2I(oldSize, output.timestamps.size()));
               } else if (((ArrayRef)this.timestamps.get(i)).size() <= 1) {
                  output.ranges.add(new Vec2I(oldSize, oldSize + 0));
               } else {
                  for(int j = 0; j < ((ArrayRef)this.timestamps.get(i)).size(); ++j) {
                     output.timestamps.add((Integer)((ArrayRef)this.timestamps.get(i)).get(j) + timeStart);
                  }

                  output.ranges.add(new Vec2I(oldSize, output.timestamps.size()));
               }

               oldSize = output.timestamps.size();
            }

            output.ranges.add(new Vec2I());
         }
      }

      return output;
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      String NEW_LINE = System.getProperty("line.separator");
      result.append("Interpolation type : " + this.printInterpolationType() + NEW_LINE);
      result.append("Global Sequence : " + this.globalSequence + NEW_LINE);
      result.append("Timestamps : " + this.timestamps.toString() + NEW_LINE);
      return result.toString();
   }
}
