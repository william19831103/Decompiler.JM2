package jm2lib.blizzard.wc3;

import jm2lib.blizzard.io.FileMagic;
import jm2lib.blizzard.io.ObjectTypeProvider;

public class Wc3Files extends ObjectTypeProvider {
   public Wc3Files() {
      this.addMapping(new FileMagic("MDLX"), "jm2lib.blizzard.wc3.MDX");
   }
}
