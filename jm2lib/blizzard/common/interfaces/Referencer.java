package jm2lib.blizzard.common.interfaces;

import java.io.IOException;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;

public interface Referencer extends Marshalable {
   void writeContent(MarshalingStream var1) throws IOException, InstantiationException, IllegalAccessException;
}
