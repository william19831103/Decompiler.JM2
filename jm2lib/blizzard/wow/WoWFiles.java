package jm2lib.blizzard.wow;

import jm2lib.blizzard.io.FileMagic;
import jm2lib.blizzard.io.ObjectTypeProvider;

public class WoWFiles extends ObjectTypeProvider {
   public WoWFiles() {
      this.addMapping(new FileMagic("MD20"), "jm2lib.blizzard.wow.M2");
      this.addMapping(new FileMagic("MD21"), "jm2lib.blizzard.wow.MD21");
      this.addMapping(new FileMagic("PFID"), "jm2lib.blizzard.wow.legion.PFID");
      this.addMapping(new FileMagic("SFID"), "jm2lib.blizzard.wow.legion.SFID");
      this.addMapping(new FileMagic("AFID"), "jm2lib.blizzard.wow.legion.AFID");
      this.addMapping(new FileMagic("BFID"), "jm2lib.blizzard.wow.legion.BFID");
   }
}
