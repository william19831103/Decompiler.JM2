package jm2lib.blizzard.wow.adt;

import java.io.IOException;
import jm2lib.blizzard.common.types.Chunk;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class MCNK implements Marshalable {
   int flags;
   int indexX;
   int indexY;
   int nLayers;
   int nDoodadRefs;
   Chunk<Byte> height;
   Chunk<Byte> normal;
   Chunk<Byte> layer;
   Chunk<Byte> refs;
   Chunk<Byte> alpha;
   int sizeAlpha;
   Chunk<Byte> shadow;
   int sizeShadow;
   int areaID;
   int nMapObjRefs;
   char holesLowRes;
   char unknownButUsed;
   int[] ReallyLowQualityTexturingMap;
   int predTex;
   int noEffectDoodad;
   Chunk<Byte> sndEmitters;
   int nSndEmitters;
   Chunk<Byte> liquid;
   int sizeLiquid;
   Vec3F position;
   Chunk<Byte> mccv;
   Chunk<Byte> mclv;
   int unused;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.flags = in.readInt();
      this.indexX = in.readInt();
      this.indexY = in.readInt();
   }

   public void marshal(MarshalingStream out) throws IOException {
   }
}
