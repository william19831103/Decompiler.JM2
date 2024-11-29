package jm2lib.blizzard.wow.lichking;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import java.util.Collection;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.Vec2I;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class AnimationBlock<T> implements AnimFilesHandler {
   public short interpolationType;
   public short globalSequence;
   public ArrayRef<ArrayRef<Integer>> timestamps;
   public ArrayRef<ArrayRef<T>> values;
   private int hint;

   public AnimationBlock(Class<T> type) {
      this(type, 0);
   }

   public AnimationBlock(Class<T> type, int hint) {
      this.hint = 0;
      this.hint = hint;
      this.interpolationType = 0;
      this.globalSequence = 0;
      this.timestamps = new ArrayRef(ArrayRef.class, Integer.TYPE);
      this.values = new ArrayRef(ArrayRef.class, type);
   }

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.timestamps.setAnimFiles(animFiles);
      this.values.setAnimFiles(animFiles);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.interpolationType = in.readShort();
      this.globalSequence = in.readShort();
      this.timestamps.unmarshal(in);
      this.values.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeShort(this.interpolationType);
      out.writeShort(this.globalSequence);
      this.timestamps.marshal(out);
      this.values.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws IOException, InstantiationException, IllegalAccessException {
      this.timestamps.writeContent(out);
      this.values.writeContent(out);
   }

   private String printInterpolationType() {
      return printInterpolationType(this.interpolationType);
   }

   public static String printInterpolationType(int interpolationType) {
      String[] map = new String[]{"None (static value)", "Linear", "Hermite", "Bezier"};
      return interpolationType <= 3 && interpolationType >= 0 ? map[interpolationType] : "Unknown (" + interpolationType + ")";
   }

   public jm2lib.blizzard.wow.classic.AnimationBlock<T> downConvert(ArrayRef<jm2lib.blizzard.wow.classic.Animation> animations) throws Exception {
      jm2lib.blizzard.wow.classic.AnimationBlock<T> output = new jm2lib.blizzard.wow.classic.AnimationBlock(this.values.getSubType());
      output.interpolationType = this.interpolationType;
      output.globalSequence = this.globalSequence;
      if (!this.timestamps.isEmpty()) {
         if (this.timestamps.size() != this.values.size()) {
            throw new Exception("Number of timestamps (" + this.timestamps.size() + ") different than number of values (" + this.values.size() + ")");
         }

         if (this.globalSequence >= 0) {
            output.timestamps.addAll((Collection)this.timestamps.get(0));
            output.values.addAll((Collection)this.values.get(0));
         } else if (this.timestamps.size() == animations.size()) {
            int rangeTime;
            int j;
            for(rangeTime = 0; rangeTime < this.timestamps.size(); ++rangeTime) {
               j = ((jm2lib.blizzard.wow.classic.Animation)animations.get(rangeTime)).timeStart;
               int timeEnd = ((jm2lib.blizzard.wow.classic.Animation)animations.get(rangeTime)).timeEnd;
               if (((ArrayRef)this.timestamps.get(rangeTime)).size() == 1) {
                  output.timestamps.add((Integer)((ArrayRef)this.timestamps.get(rangeTime)).get(0) + j);
                  output.timestamps.add((Integer)((ArrayRef)this.timestamps.get(rangeTime)).get(0) + timeEnd);
                  output.values.add(((ArrayRef)this.values.get(rangeTime)).get(0));
                  output.values.add(((ArrayRef)this.values.get(rangeTime)).get(0));
               } else if (((ArrayRef)this.timestamps.get(rangeTime)).size() > 1) {
                  for(int j = 0; j < ((ArrayRef)this.timestamps.get(rangeTime)).size(); ++j) {
                     output.timestamps.add((Integer)((ArrayRef)this.timestamps.get(rangeTime)).get(j) + j);
                     output.values.add(((ArrayRef)this.values.get(rangeTime)).get(j));
                  }
               } else {
                  output.timestamps.add(j);
                  output.timestamps.add(timeEnd);
                  output.values.addNew(this.hint);
                  output.values.addNew(this.hint);
               }
            }

            rangeTime = 0;

            for(j = 0; j < this.timestamps.size(); ++j) {
               Vec2I range = new Vec2I();
               range.setX(rangeTime);
               if (((ArrayRef)this.timestamps.get(j)).size() == 1) {
                  ++rangeTime;
               } else if (((ArrayRef)this.timestamps.get(j)).size() == 0) {
                  ++rangeTime;
               } else {
                  rangeTime += ((ArrayRef)this.timestamps.get(j)).size() - 1;
               }

               range.setY(rangeTime);
               output.ranges.add(range);
               ++rangeTime;
            }

            output.ranges.add(new Vec2I());
         } else {
            if (this.timestamps.size() != 1) {
               StringBuilder builder = new StringBuilder();
               builder.append("Unknown animation block conversion error.\n");
               builder.append(this.toString());
               throw new Exception(builder.toString());
            }

            output.timestamps.addAll((Collection)this.timestamps.get(0));
            output.values.addAll((Collection)this.values.get(0));
         }
      }

      return output;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append("\n\tinterpolationType: ").append(this.printInterpolationType()).append("\n\tglobalSequence: ").append(this.globalSequence).append("\n\ttimestamps: ").append(this.timestamps).append("\n\tvalues: ").append(this.values).append("\n}");
      return builder.toString();
   }

   public boolean isEmpty() {
      return this.timestamps.isEmpty();
   }
}
