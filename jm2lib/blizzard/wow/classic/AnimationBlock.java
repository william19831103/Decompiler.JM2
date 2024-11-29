package jm2lib.blizzard.wow.classic;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.Vec2I;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class AnimationBlock<T> implements Referencer {
   public short interpolationType;
   public short globalSequence;
   public ArrayRef<Vec2I> ranges;
   public ArrayRef<Integer> timestamps;
   public ArrayRef<T> values;

   public AnimationBlock(Class<T> type) {
      this(type, (ArrayList)null);
   }

   public AnimationBlock(Class<T> type, ArrayList<LERandomAccessFile> animFiles) {
      this.interpolationType = 0;
      this.globalSequence = 0;
      this.ranges = new ArrayRef(Vec2I.class);
      this.timestamps = new ArrayRef(Integer.TYPE);
      this.values = new ArrayRef(type);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.interpolationType = in.readShort();
      this.globalSequence = in.readShort();
      this.ranges.unmarshal(in);
      this.timestamps.unmarshal(in);
      this.values.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeShort(this.interpolationType);
      out.writeShort(this.globalSequence);
      this.ranges.marshal(out);
      this.timestamps.marshal(out);
      this.values.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws IOException, InstantiationException, IllegalAccessException {
      this.ranges.writeContent(out);
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

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append("\n\tinterpolationType: ").append(this.printInterpolationType()).append("\n\tglobalSequence: ").append(this.globalSequence).append("\n\tranges: ").append(this.ranges).append("\n\ttimestamps: ").append(this.timestamps).append("\n\tvalues: ").append(this.values).append("\n}");
      return builder.toString();
   }

   public boolean isEmpty() {
      return this.timestamps.isEmpty();
   }

   public void addKeyframe(int timestamp, T value) {
      this.timestamps.add(timestamp);
      this.values.add(value);
   }

   public void computeRanges(List<Animation> animations) {
      if (this.timestamps.size() > 1) {
         Iterator var3 = animations.iterator();

         while(var3.hasNext()) {
            Animation animation = (Animation)var3.next();
            int firstTime = (Integer)Collections.max((Collection)this.timestamps.stream().filter((s) -> {
               return s <= animation.timeStart;
            }).collect(Collectors.toList()));
            int lastTime = (Integer)Collections.min((Collection)this.timestamps.stream().filter((s) -> {
               return s >= animation.timeEnd;
            }).collect(Collectors.toList()));
            this.ranges.add(new Vec2I(this.timestamps.indexOf(firstTime), this.timestamps.indexOf(lastTime)));
         }

         this.ranges.add(new Vec2I());
      }
   }
}
