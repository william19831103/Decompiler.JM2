package jm2lib.blizzard.wow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jm2lib.blizzard.common.types.Chunk;
import jm2lib.blizzard.io.BlizzardFile;
import jm2lib.blizzard.wow.adt.MCNK;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class ADT implements BlizzardFile {
   public Chunk<MCNK> mcnk;
   public ArrayList<Chunk<?>> chunks;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      while(in.getFilePointer() < in.length()) {
         String magic = in.readString(4);
         switch(magic.hashCode()) {
         case 2360787:
            if (magic.equals("MCNK")) {
               this.mcnk = new Chunk(MCNK.class, magic);
               this.mcnk.unmarshal(in);
               this.chunks.add(this.mcnk);
               break;
            }
         default:
            Chunk<Byte> unknown = new Chunk(Byte.TYPE, magic);
            unknown.unmarshal(in);
            this.chunks.add(unknown);
         }
      }

   }

   public void marshal(MarshalingStream out) throws IOException {
      Iterator var3 = this.chunks.iterator();

      while(var3.hasNext()) {
         Chunk<?> chunk = (Chunk)var3.next();
         chunk.marshal(out);
      }

   }
}
