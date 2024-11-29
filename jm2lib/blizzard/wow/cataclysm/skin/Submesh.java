package jm2lib.blizzard.wow.cataclysm.skin;

import java.io.IOException;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Submesh implements Marshalable {
   public short submeshID = 0;
   public short level = 0;
   public short startVertex = 0;
   public short nVertices = 0;
   public short startTriangle = 0;
   public short nTriangles = 0;
   public short nBones = 0;
   public short startBones = 0;
   public short boneInfluences = 0;
   public short rootBone = 0;
   public Vec3F centerMass = new Vec3F();
   public Vec3F centerBoundingBox = new Vec3F();
   public float radius = 0.0F;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.submeshID = in.readShort();
      this.level = in.readShort();
      this.startVertex = in.readShort();
      this.nVertices = in.readShort();
      this.startTriangle = in.readShort();
      this.nTriangles = in.readShort();
      this.nBones = in.readShort();
      this.startBones = in.readShort();
      this.boneInfluences = in.readShort();
      this.rootBone = in.readShort();
      this.centerMass.unmarshal(in);
      this.centerBoundingBox.unmarshal(in);
      this.radius = in.readFloat();
   }

   public void marshal(MarshalingStream out) throws IOException {
      if (this.level > 0) {
         System.err.format("WARNING : Submesh Level higher than 0. Hash : " + this.hashCode() + "\n" + "If you're using a client older than Mists of Pandaria, the model may be bugged. Untested.%n" + "See : http://modcraft.superparanoid.de/viewtopic.php?f=20&t=9389 \n");
      }

      out.writeShort(this.submeshID);
      out.writeShort(this.level);
      out.writeShort(this.startVertex);
      out.writeShort(this.nVertices);
      out.writeShort(this.startTriangle);
      out.writeShort(this.nTriangles);
      out.writeShort(this.nBones);
      out.writeShort(this.startBones);
      out.writeShort(this.boneInfluences);
      out.writeShort(this.rootBone);
      this.centerMass.marshal(out);
      this.centerBoundingBox.marshal(out);
      out.writeFloat(this.radius);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tsubmeshID: ").append(this.submeshID).append("\n\tlevel: ").append(this.level).append("\n\tstartVertex: ").append(this.startVertex).append("\n\tnVertices: ").append(this.nVertices).append("\n\tstartTriangle: ").append(this.startTriangle).append("\n\tnTriangles: ").append(this.nTriangles).append("\n\tnBones: ").append(this.nBones).append("\n\tstartBones: ").append(this.startBones).append("\n\tboneInfluences: ").append(this.boneInfluences).append("\n\trootBone: ").append(this.rootBone).append("\n\tcenterMass: ").append(this.centerMass).append("\n\tcenterBoundingBox: ").append(this.centerBoundingBox).append("\n\tradius: ").append(this.radius).append("\n}");
      return builder.toString();
   }

   public jm2lib.blizzard.wow.burningcrusade.Submesh downConvert() throws Exception {
      jm2lib.blizzard.wow.burningcrusade.Submesh output = new jm2lib.blizzard.wow.burningcrusade.Submesh();
      if (this.level > 0) {
         throw new Exception("Submesh level > 0. Your model has too much triangles and can't be converted.\nSee : http://modcraft.superparanoid.de/viewtopic.php?f=7&t=8859 \n");
      } else {
         output.submeshID = this.submeshID;
         output.startVertex = this.startVertex;
         output.nVertices = this.nVertices;
         output.startTriangle = this.startTriangle;
         output.nTriangles = this.nTriangles;
         output.nBones = this.nBones;
         output.startBones = this.startBones;
         output.boneInfluences = this.boneInfluences;
         output.rootBone = this.rootBone;
         output.centerMass = this.centerMass;
         output.centerBoundingBox = this.centerBoundingBox;
         output.radius = this.radius;
         if (output.boneInfluences == 0) {
            output.boneInfluences = 1;
         }

         return output;
      }
   }
}
