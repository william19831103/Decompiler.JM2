package jm2lib.blizzard.wow.legion;

import java.io.IOException;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class AnimFileID implements Marshalable {
   public char animID;
   public char subAnimID;
   public int fileID;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.animID = in.readChar();
      this.subAnimID = in.readChar();
      this.fileID = in.readInt();
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeChar(this.animID);
      out.writeChar(this.subAnimID);
      out.writeInt(this.fileID);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("AnimFileID [animID=").append(this.animID).append(", subAnimID=").append(this.subAnimID).append(", fileID=").append(this.fileID).append("]");
      return builder.toString();
   }
}
