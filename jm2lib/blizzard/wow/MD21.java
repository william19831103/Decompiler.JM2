package jm2lib.blizzard.wow;

import java.io.IOException;
import jm2lib.blizzard.common.types.Chunk;
import jm2lib.blizzard.io.BlizzardFile;
import jm2lib.blizzard.io.BlizzardInputStream;
import jm2lib.blizzard.io.BlizzardOutputStream;
import jm2lib.blizzard.wow.legion.AnimFileID;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class MD21 implements BlizzardFile {
   private M2 model;
   public Chunk<Integer> physFileID;
   public Chunk<Integer> skinFileID;
   public Chunk<AnimFileID> animFileID;
   public Chunk<Integer> boneFileID;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      int size = in.readInt();
      long endOffset = in.getFilePointer() + (long)size;
      this.model = (M2)((BlizzardInputStream)in).readTared();
      in.seek(endOffset);

      while(true) {
         while(in.getFilePointer() < in.length()) {
            String magic = new String(new byte[]{in.readByte(), in.readByte(), in.readByte(), in.readByte()});
            switch(magic.hashCode()) {
            case 2006016:
               if (magic.equals("AFID")) {
                  this.animFileID = new Chunk(AnimFileID.class, magic);
                  this.animFileID.unmarshal(in);
                  continue;
               }
               break;
            case 2035807:
               if (magic.equals("BFID")) {
                  this.boneFileID = new Chunk(Integer.TYPE, magic);
                  this.boneFileID.unmarshal(in);
                  continue;
               }
               break;
            case 2452881:
               if (magic.equals("PFID")) {
                  this.physFileID = new Chunk(Integer.TYPE, magic);
                  this.physFileID.unmarshal(in);
                  continue;
               }
               break;
            case 2542254:
               if (magic.equals("SFID")) {
                  this.skinFileID = new Chunk(Integer.TYPE, magic);
                  this.skinFileID.unmarshal(in);
                  continue;
               }
            }

            in.seek(in.getFilePointer() + (long)in.readInt());
         }

         return;
      }
   }

   public void marshal(MarshalingStream out) throws IOException {
      if (this.model != null) {
         long sizeOffset = out.getFilePointer();
         out.writeInt(0);
         ((BlizzardOutputStream)out).writeTared(this.model);
         int size = (int)(out.getFilePointer() - sizeOffset);
         long currentOffset = out.getFilePointer();
         out.seek(sizeOffset);
         out.writeInt(size);
         out.seek(currentOffset);
      }

      if (this.physFileID != null) {
         this.physFileID.marshal(out);
      }

   }

   public M2 getM2() {
      return this.model;
   }

   public void setM2(M2 model) {
      this.model = model;
   }
}
