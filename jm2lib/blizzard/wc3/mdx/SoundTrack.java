package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import java.util.Arrays;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class SoundTrack implements Marshalable {
   byte[] fileName = new byte[260];
   float volume;
   float pitch;
   int flags;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      in.read(this.fileName);
      this.volume = in.readFloat();
      this.pitch = in.readFloat();
      this.flags = in.readInt();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.write(this.fileName);
      out.writeFloat(this.volume);
      out.writeFloat(this.pitch);
      out.writeInt(this.flags);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tfileName: ").append(Arrays.toString(this.fileName)).append("\n\tvolume: ").append(this.volume).append("\n\tpitch: ").append(this.pitch).append("\n\tflags: ").append(this.flags).append("\n}");
      return builder.toString();
   }
}
