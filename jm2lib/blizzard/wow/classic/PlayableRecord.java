package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class PlayableRecord implements Marshalable {
   public short fallbackID;
   public short flags;

   public PlayableRecord() {
      this((short)0, (short)0);
   }

   public PlayableRecord(short fallbackID, short flags) {
      this.fallbackID = fallbackID;
      this.flags = flags;
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.fallbackID = in.readShort();
      this.flags = in.readShort();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeShort(this.fallbackID);
      out.writeShort(this.flags);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tfallbackID: ").append(this.fallbackID).append("\n\tflags: ").append(this.flags).append("\n}");
      return builder.toString();
   }
}
