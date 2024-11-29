package jm2lib.io;

import java.io.IOException;

public interface Marshalable {
   void unmarshal(UnmarshalingStream var1) throws IOException, ClassNotFoundException;

   void marshal(MarshalingStream var1) throws IOException;
}
