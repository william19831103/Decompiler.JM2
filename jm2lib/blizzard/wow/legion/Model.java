package jm2lib.blizzard.wow.legion;

import java.io.IOException;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.wow.M2Format;
import jm2lib.blizzard.wow.cataclysm.skin.View;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class Model extends jm2lib.blizzard.wow.cataclysm.Model implements Marshalable {
   public ArrayRef<Animation> animations;

   public Model() {
      this.version = 274;
      this.animations = new ArrayRef(Animation.class);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tparticleEmitters: ").append(this.particleEmitters).append("\n}");
      return builder.toString();
   }

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      this.name.unmarshal(in);
      this.globalModelFlags = in.readInt();
      this.globalSequences.unmarshal(in);
      this.animations.unmarshal(in);
      int[] aliasLookup = this.computeAliasLookup();
      UnmarshalingStream[] animFiles = new UnmarshalingStream[this.animations.size()];

      int nViews;
      for(nViews = 0; nViews < this.animations.size(); ++nViews) {
         int realIndex = aliasLookup[nViews];
         if (((Animation)this.animations.get(realIndex)).isExtern()) {
            animFiles[nViews] = new UnmarshalingStream(getAnimFileName(in.getFileName(), ((Animation)this.animations.get(realIndex)).animationID, ((Animation)this.animations.get(realIndex)).subAnimationID));
         }
      }

      if (animFiles.length > 0) {
         this.bones.setAnimFiles(animFiles);
         this.submeshAnimations.setAnimFiles(animFiles);
         this.transparency.setAnimFiles(animFiles);
         this.uvAnimations.setAnimFiles(animFiles);
         this.attachments.setAnimFiles(animFiles);
         this.events.setAnimFiles(animFiles);
         this.lights.setAnimFiles(animFiles);
         this.cameras.setAnimFiles(animFiles);
         this.ribbonEmitters.setAnimFiles(animFiles);
         this.particleEmitters.setAnimFiles(animFiles);
         if ((this.globalModelFlags & 8) != 0) {
            this.unknown.setAnimFiles(animFiles);
         }
      }

      this.animationLookup.unmarshal(in);
      this.bones.unmarshal(in);
      this.keyBoneLookup.unmarshal(in);
      this.vertices.unmarshal(in);
      nViews = in.readInt();

      for(byte i = 0; i < nViews; ++i) {
         UnmarshalingStream skin = new UnmarshalingStream(getSkinName(in.getFileName(), i));
         View view = new View();
         view.unmarshal(skin);
         this.views.add(view);
         skin.close();
      }

      this.submeshAnimations.unmarshal(in);
      this.textures.unmarshal(in);
      this.transparency.unmarshal(in);
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
      if ((this.globalModelFlags & 8) != 0) {
         this.unknown = new ArrayRef(Short.TYPE);
         this.unknown.unmarshal(in);
      }

      closeFiles(false, animFiles);
   }

   public void marshal(MarshalingStream out) throws IOException {
      this.name.marshal(out);
      out.writeInt(this.globalModelFlags);
      this.globalSequences.marshal(out);
      this.animations.marshal(out);
      int[] aliasLookup = this.computeAliasLookup();
      MarshalingStream[] animFiles = new MarshalingStream[this.animations.size()];

      for(int i = 0; i < this.animations.size(); ++i) {
         int realIndex = aliasLookup[i];
         if (((Animation)this.animations.get(realIndex)).isExtern()) {
            animFiles[i] = new MarshalingStream(getAnimFileName(out.getFileName(), ((Animation)this.animations.get(realIndex)).animationID, ((Animation)this.animations.get(realIndex)).subAnimationID));
         }
      }

      if (animFiles.length > 0) {
         this.bones.setAnimFiles(animFiles);
         this.submeshAnimations.setAnimFiles(animFiles);
         this.transparency.setAnimFiles(animFiles);
         this.uvAnimations.setAnimFiles(animFiles);
         this.attachments.setAnimFiles(animFiles);
         this.events.setAnimFiles(animFiles);
         this.lights.setAnimFiles(animFiles);
         this.cameras.setAnimFiles(animFiles);
         this.ribbonEmitters.setAnimFiles(animFiles);
         this.particleEmitters.setAnimFiles(animFiles);
         if ((this.globalModelFlags & 8) != 0) {
            this.unknown.setAnimFiles(animFiles);
         }
      }

      this.animationLookup.marshal(out);
      this.bones.marshal(out);
      this.keyBoneLookup.marshal(out);
      this.vertices.marshal(out);
      out.writeInt(this.views.size());

      for(byte i = 0; i < this.views.size(); ++i) {
         MarshalingStream skin = new MarshalingStream(getSkinName(out.getFileName(), i));
         ((View)this.views.get(i)).marshal(skin);
         align(skin);
         skin.close();
      }

      this.submeshAnimations.marshal(out);
      this.textures.marshal(out);
      this.transparency.marshal(out);
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
      if ((this.globalModelFlags & 8) != 0) {
         this.unknown.marshal(out);
      }

      try {
         this.name.writeContent(out);
         this.globalSequences.writeContent(out);
         this.animations.writeContent(out);
         this.animationLookup.writeContent(out);
         this.bones.writeContent(out);
         this.keyBoneLookup.writeContent(out);
         this.vertices.writeContent(out);
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
         if ((this.globalModelFlags & 8) != 0) {
            this.unknown.writeContent(out);
         }
      } catch (IllegalAccessException | InstantiationException var6) {
         System.err.println("Error in Block writing");
         var6.printStackTrace();
      }

      align(out);
      closeFiles(true, animFiles);
   }

   protected int[] computeAliasLookup() {
      int[] aliasLookup = new int[this.animations.size()];

      for(int i = 0; i < this.animations.size(); ++i) {
         aliasLookup[i] = Animation.getRealPos(i, this.animations);
         if (aliasLookup[i] == -1) {
            System.err.println("Error : alias to -1");
         }
      }

      return aliasLookup;
   }

   public M2Format downConvert() throws Exception {
      jm2lib.blizzard.wow.cataclysm.Model output = super.clone();

      for(int i = 0; i < this.animations.size(); ++i) {
         output.animations.add(((Animation)this.animations.get(i)).downConvert());
      }

      return output;
   }

   public Model clone() {
      Model clone = new Model();
      clone.version = 274;
      clone.name = this.name;
      clone.globalModelFlags = this.globalModelFlags;
      clone.globalSequences = this.globalSequences;
      clone.animations = this.animations;
      clone.animationLookup = this.animationLookup;
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
      clone.unknown = this.unknown;
      return clone;
   }
}