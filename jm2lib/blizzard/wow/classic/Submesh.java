package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import jm2lib.blizzard.common.types.Vec3F;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Submesh implements Marshalable {
   public int submeshID = 0;
   public short startVertex = 0;
   public short nVertices = 0;
   public short startTriangle = 0;
   public short nTriangles = 0;
   public short nBones = 0;
   public short startBones = 0;
   public short boneInfluences = 0;
   public short rootBone = 0;
   public Vec3F centerMass = new Vec3F();

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.submeshID = in.readInt();
      this.startVertex = in.readShort();
      this.nVertices = in.readShort();
      this.startTriangle = in.readShort();
      this.nTriangles = in.readShort();
      this.nBones = in.readShort();
      this.startBones = in.readShort();
      this.boneInfluences = in.readShort();
      this.rootBone = in.readShort();
      this.centerMass.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      out.writeInt(this.submeshID);
      out.writeShort(this.startVertex);
      out.writeShort(this.nVertices);
      out.writeShort(this.startTriangle);
      out.writeShort(this.nTriangles);
      out.writeShort(this.nBones);
      out.writeShort(this.startBones);
      out.writeShort(this.boneInfluences);
      out.writeShort(this.rootBone);
      this.centerMass.marshal(out);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Submesh [submeshID=");
      builder.append(this.submeshID);
      builder.append(", startVertex=");
      builder.append(this.startVertex);
      builder.append(", nVertices=");
      builder.append(this.nVertices);
      builder.append(", startTriangle=");
      builder.append(this.startTriangle);
      builder.append(", nTriangles=");
      builder.append(this.nTriangles);
      builder.append(", nBones=");
      builder.append(this.nBones);
      builder.append(", startBones=");
      builder.append(this.startBones);
      builder.append(", boneInfluences=");
      builder.append(this.boneInfluences);
      builder.append(", rootBone=");
      builder.append(this.rootBone);
      builder.append(", centerMass=");
      builder.append(this.centerMass);
      return builder.toString();
   }

   public jm2lib.blizzard.wow.burningcrusade.Submesh upConvert() {
      jm2lib.blizzard.wow.burningcrusade.Submesh output = new jm2lib.blizzard.wow.burningcrusade.Submesh();
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
      return output;
   }
}
