package jm2lib.blizzard.wow.burningcrusade;

import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.wow.M2Format;
import jm2lib.blizzard.wow.classic.Particle;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Model extends jm2lib.blizzard.wow.classic.Model implements Marshalable {
   public ArrayRef<Bone> bones;
   public ArrayRef<View> views;
   public ArrayRef<UVAnimation> uvAnimations;

   public Model() {
      this.version = 260;
      this.bones = new ArrayRef(Bone.class);
      this.views = new ArrayRef(View.class);
      this.uvAnimations = new ArrayRef(UVAnimation.class);
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.name.unmarshal(in);
      this.globalModelFlags = in.readInt();
      this.globalSequences.unmarshal(in);
      this.animations.unmarshal(in);
      this.animationLookup.unmarshal(in);
      this.playableAnimationLookup.unmarshal(in);
      this.bones.unmarshal(in);
      this.keyBoneLookup.unmarshal(in);
      this.vertices.unmarshal(in);
      this.views.unmarshal(in);
      this.submeshAnimations.unmarshal(in);
      this.textures.unmarshal(in);
      this.transparency.unmarshal(in);
      this.unknownRef.unmarshal(in);
      this.uvAnimations.unmarshal(in);
      this.texReplace.unmarshal(in);
      this.renderFlags.unmarshal(in);
      this.boneLookupTable.unmarshal(in);
      this.texLookup.unmarshal(in);
      this.texUnits.unmarshal(in);
      this.transLookup.unmarshal(in);
      this.uvAnimLookup.unmarshal(in);
      this.boundingBox[0].unmarshal(in);
      this.boundingBox[1].unmarshal(in);
      this.boundingSphereRadius = in.readFloat();
      this.collisionBox[0].unmarshal(in);
      this.collisionBox[1].unmarshal(in);
      this.collisionSphereRadius = in.readFloat();
      this.boundingTriangles.unmarshal(in);
      this.boundingVertices.unmarshal(in);
      this.boundingNormals.unmarshal(in);
      this.attachments.unmarshal(in);
      this.attachLookup.unmarshal(in);
      this.events.unmarshal(in);
      this.lights.unmarshal(in);
      this.cameras.unmarshal(in);
      this.cameraLookup.unmarshal(in);
      this.ribbonEmitters.unmarshal(in);
      this.particleEmitters.unmarshal(in);
   }

   public void marshal(MarshalingStream out) throws IOException {
      this.name.marshal(out);
      out.writeInt(this.globalModelFlags);
      this.globalSequences.marshal(out);
      this.animations.marshal(out);
      this.animationLookup.marshal(out);
      this.playableAnimationLookup.marshal(out);
      this.bones.marshal(out);
      this.keyBoneLookup.marshal(out);
      this.vertices.marshal(out);
      this.views.marshal(out);
      this.submeshAnimations.marshal(out);
      this.textures.marshal(out);
      this.transparency.marshal(out);
      this.unknownRef.marshal(out);
      this.uvAnimations.marshal(out);
      this.texReplace.marshal(out);
      this.renderFlags.marshal(out);
      this.boneLookupTable.marshal(out);
      this.texLookup.marshal(out);
      this.texUnits.marshal(out);
      this.transLookup.marshal(out);
      this.uvAnimLookup.marshal(out);
      this.boundingBox[0].marshal(out);
      this.boundingBox[1].marshal(out);
      out.writeFloat(this.boundingSphereRadius);
      this.collisionBox[0].marshal(out);
      this.collisionBox[1].marshal(out);
      out.writeFloat(this.collisionSphereRadius);
      this.boundingTriangles.marshal(out);
      this.boundingVertices.marshal(out);
      this.boundingNormals.marshal(out);
      this.attachments.marshal(out);
      this.attachLookup.marshal(out);
      this.events.marshal(out);
      this.lights.marshal(out);
      this.cameras.marshal(out);
      this.cameraLookup.marshal(out);
      this.ribbonEmitters.marshal(out);
      this.particleEmitters.marshal(out);

      try {
         this.name.writeContent(out);
         this.globalSequences.writeContent(out);
         this.animations.writeContent(out);
         this.animationLookup.writeContent(out);
         this.playableAnimationLookup.writeContent(out);
         this.bones.writeContent(out);
         this.keyBoneLookup.writeContent(out);
         this.vertices.writeContent(out);
         this.views.writeContent(out);
         this.submeshAnimations.writeContent(out);
         this.textures.writeContent(out);
         this.transparency.writeContent(out);
         this.uvAnimations.writeContent(out);
         this.texReplace.writeContent(out);
         this.renderFlags.writeContent(out);
         this.boneLookupTable.writeContent(out);
         this.texLookup.writeContent(out);
         this.texUnits.writeContent(out);
         this.transLookup.writeContent(out);
         this.uvAnimLookup.writeContent(out);
         this.boundingTriangles.writeContent(out);
         this.boundingVertices.writeContent(out);
         this.boundingNormals.writeContent(out);
         this.attachments.writeContent(out);
         this.attachLookup.writeContent(out);
         this.events.writeContent(out);
         this.lights.writeContent(out);
         this.cameras.writeContent(out);
         this.cameraLookup.writeContent(out);
         this.ribbonEmitters.writeContent(out);
         this.particleEmitters.writeContent(out);
      } catch (IllegalAccessException | InstantiationException var3) {
         System.err.println("Error in Block writing");
         var3.printStackTrace();
         System.exit(1);
      }

      align(out);
   }

   public M2Format upConvert() throws Exception {
      jm2lib.blizzard.wow.lateburningcrusade.Model output = new jm2lib.blizzard.wow.lateburningcrusade.Model();
      output.name = this.name;
      output.globalModelFlags = this.globalModelFlags;
      output.globalSequences = this.globalSequences;
      output.animations = this.animations;
      output.animationLookup = this.animationLookup;
      output.playableAnimationLookup = this.playableAnimationLookup;
      output.bones = this.bones;
      output.keyBoneLookup = this.keyBoneLookup;
      output.vertices = this.vertices;
      output.views = this.views;
      output.submeshAnimations = this.submeshAnimations;
      output.textures = this.textures;
      output.transparency = this.transparency;
      output.uvAnimations = this.uvAnimations;
      output.texReplace = this.texReplace;
      output.renderFlags = this.renderFlags;
      output.boneLookupTable = this.boneLookupTable;
      output.texLookup = this.texLookup;
      output.texUnits = this.texUnits;
      output.transLookup = this.transLookup;
      output.uvAnimLookup = this.uvAnimLookup;
      output.boundingBox = this.boundingBox;
      output.boundingSphereRadius = this.boundingSphereRadius;
      output.collisionBox = this.collisionBox;
      output.collisionSphereRadius = this.collisionSphereRadius;
      output.boundingTriangles = this.boundingTriangles;
      output.boundingVertices = this.boundingVertices;
      output.boundingNormals = this.boundingNormals;
      output.attachments = this.attachments;
      output.attachLookup = this.attachLookup;
      output.events = this.events;
      output.lights = this.lights;
      output.cameras = this.cameras;
      output.cameraLookup = this.cameraLookup;
      output.ribbonEmitters = this.ribbonEmitters;

      for(int i = 0; i < this.particleEmitters.size(); ++i) {
         output.particleEmitters.add(((Particle)this.particleEmitters.get(i)).upConvert());
      }

      return output;
   }

   public M2Format downConvert() throws Exception {
      jm2lib.blizzard.wow.classic.Model output = super.clone();

      int i;
      for(i = 0; i < this.bones.size(); ++i) {
         output.bones.add(((Bone)this.bones.get(i)).downConvert());
      }

      for(i = 0; i < this.views.size(); ++i) {
         output.views.add(((View)this.views.get(i)).downConvert());
      }

      for(i = 0; i < this.uvAnimations.size(); ++i) {
         output.uvAnimations.add(((UVAnimation)this.uvAnimations.get(i)).downConvert());
      }

      return output;
   }

   public Model clone() {
      Model clone = new Model();
      clone.version = 260;
      clone.name = this.name;
      clone.globalModelFlags = this.globalModelFlags;
      clone.globalSequences = this.globalSequences;
      clone.animations = this.animations;
      clone.animationLookup = this.animationLookup;
      clone.playableAnimationLookup = this.playableAnimationLookup;
      clone.bones = this.bones;
      clone.keyBoneLookup = this.keyBoneLookup;
      clone.vertices = this.vertices;
      clone.views = this.views;
      clone.submeshAnimations = this.submeshAnimations;
      clone.textures = this.textures;
      clone.transparency = this.transparency;
      clone.uvAnimations = this.uvAnimations;
      clone.texReplace = this.texReplace;
      clone.renderFlags = this.renderFlags;
      clone.boneLookupTable = this.boneLookupTable;
      clone.texLookup = this.texLookup;
      clone.texUnits = this.texUnits;
      clone.transLookup = this.transLookup;
      clone.uvAnimLookup = this.uvAnimLookup;
      clone.boundingBox = this.boundingBox;
      clone.boundingSphereRadius = this.boundingSphereRadius;
      clone.collisionBox = this.collisionBox;
      clone.collisionSphereRadius = this.collisionSphereRadius;
      clone.boundingTriangles = this.boundingTriangles;
      clone.boundingVertices = this.boundingVertices;
      clone.boundingNormals = this.boundingNormals;
      clone.attachments = this.attachments;
      clone.attachLookup = this.attachLookup;
      clone.events = this.events;
      clone.lights = this.lights;
      clone.cameras = this.cameras;
      clone.cameraLookup = this.cameraLookup;
      clone.ribbonEmitters = this.ribbonEmitters;
      clone.particleEmitters = this.particleEmitters;
      return clone;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tattachments: ").append(this.attachments);
      builder.append("\n}");
      return builder.toString();
   }
}
