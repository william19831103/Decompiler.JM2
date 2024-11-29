package jm2lib.blizzard.wc3.mdx;

import java.io.IOException;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Bone implements Marshalable {
   Node node = new Node();
   int geosetID;
   int geosetAnimationID;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.node.unmarshal(in);
      this.geosetID = in.readInt();
      this.geosetAnimationID = in.readInt();
   }

   public void marshal(MarshalingStream out) throws IOException {
      this.node.marshal(out);
      out.writeInt(this.geosetID);
      out.writeInt(this.geosetAnimationID);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tnode: ").append(this.node).append("\n\tgeosetID: ").append(this.geosetID).append("\n\tgeosetAnimationID: ").append(this.geosetAnimationID).append("\n}");
      return builder.toString();
   }
}
