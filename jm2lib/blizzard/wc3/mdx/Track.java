package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import jm2lib.blizzard.wow.classic.AnimationBlock;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Track<T> implements Marshalable {
   private final Class<T> type;
   private final String magic;
   public int interpolationType;
   public int globalSequence;
   public ArrayList<Track<T>.KeyFrame> keyFrames;

   public Track(Class<T> type, String magic) {
      this.type = type;
      this.magic = magic;
      this.interpolationType = 0;
      this.globalSequence = -1;
      this.keyFrames = new ArrayList();
   }

   public ArrayList<Integer> getTimestamps() {
      ArrayList<Integer> timestamps = new ArrayList();
      Iterator var3 = this.keyFrames.iterator();

      while(var3.hasNext()) {
         Track<T>.KeyFrame keyFrame = (Track.KeyFrame)var3.next();
         timestamps.add(keyFrame.timestamp);
      }

      return timestamps;
   }

   public ArrayList<T> getValues() {
      ArrayList<T> values = new ArrayList();
      Iterator var3 = this.keyFrames.iterator();

      while(var3.hasNext()) {
         Track<T>.KeyFrame keyFrame = (Track.KeyFrame)var3.next();
         values.add(keyFrame.value);
      }

      return values;
   }

   public final void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      int tracksCount = in.readInt();
      this.interpolationType = in.readInt();
      this.globalSequence = in.readInt();

      for(int i = 0; i < tracksCount; ++i) {
         this.keyFrames.add(new Track.KeyFrame((Track.KeyFrame)null));
         ((Track.KeyFrame)this.keyFrames.get(i)).unmarshal(in);
      }

   }

   public final void marshal(MarshalingStream out) throws IOException {
      out.write(this.magic.getBytes(StandardCharsets.UTF_8));
      out.writeInt(this.keyFrames.size());
      out.writeInt(this.interpolationType);
      out.writeInt(this.globalSequence);

      for(int i = 0; i < this.keyFrames.size(); ++i) {
         ((Track.KeyFrame)this.keyFrames.get(i)).marshal(out);
      }

   }

   public AnimationBlock<T> upConvert() {
      AnimationBlock<T> output = new AnimationBlock(this.type);
      output.interpolationType = (short)this.interpolationType;
      output.globalSequence = (short)this.globalSequence;
      output.timestamps.addAll(this.getTimestamps());
      output.values.addAll(this.getValues());
      return output;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\ttype: ").append(this.type).append("\n\tinterpolationType: ").append(this.interpolationType).append("\n\tglobalSequence: ").append(this.globalSequence).append("\n\tkeyFrames: ").append(this.keyFrames).append("\n}");
      return builder.toString();
   }

   private class KeyFrame implements Marshalable {
      int timestamp;
      T value;
      T inTan;
      T outTan;

      private KeyFrame() {
      }

      public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
         this.timestamp = in.readInt();
         this.value = in.readGeneric(Track.this.type);
         if (Track.this.interpolationType > 1) {
            this.inTan = in.readGeneric(Track.this.type);
            this.outTan = in.readGeneric(Track.this.type);
         }

      }

      public void marshal(MarshalingStream out) throws IOException {
         out.writeInt(this.timestamp);
         out.writeGeneric(Track.this.type, this.value);
         if (Track.this.interpolationType > 1) {
            out.writeGeneric(Track.this.type, this.inTan);
            out.writeGeneric(Track.this.type, this.outTan);
         }

      }

      public String toString() {
         StringBuilder builder = new StringBuilder();
         builder.append(" \tkf(").append(this.timestamp).append(":").append(this.value).append(") \tinTan: ").append(this.inTan).append("\toutTan: ").append(this.outTan).append("\n");
         return builder.toString();
      }

      // $FF: synthetic method
      KeyFrame(Track.KeyFrame var2) {
         this();
      }
   }
}
