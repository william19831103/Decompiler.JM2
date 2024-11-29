package jm2lib.blizzard.wow.lichking;

import java.io.IOException;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class FakeAnimationBlock<T> implements Referencer {
   public ArrayRef<Short> timestamps;
   public ArrayRef<T> values;

   public FakeAnimationBlock(Class<T> type) {
      this.timestamps = new ArrayRef(Short.TYPE);
      this.values = new ArrayRef(type);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.timestamps.unmarshal(in);
      this.values.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      this.timestamps.marshal(out);
      this.values.marshal(out);
   }

   public void writeContent(MarshalingStream out) throws IOException, InstantiationException, IllegalAccessException {
      this.timestamps.writeContent(out);
      this.values.writeContent(out);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append("\n\ttimestamps: ").append(this.timestamps).append("\n\tvalues: ").append(this.values).append("\n}");
      return builder.toString();
   }
}
