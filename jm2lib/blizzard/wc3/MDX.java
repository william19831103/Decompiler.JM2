package jm2lib.blizzard.wc3;

import java.io.IOException;
import java.util.Iterator;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.common.types.Chunk;
import jm2lib.blizzard.io.BlizzardFile;
import jm2lib.blizzard.wc3.mdx.Bone;
import jm2lib.blizzard.wc3.mdx.Geoset;
import jm2lib.blizzard.wc3.mdx.GeosetAnimation;
import jm2lib.blizzard.wc3.mdx.Light;
import jm2lib.blizzard.wc3.mdx.Material;
import jm2lib.blizzard.wc3.mdx.Model;
import jm2lib.blizzard.wc3.mdx.Sequence;
import jm2lib.blizzard.wc3.mdx.SoundTrack;
import jm2lib.blizzard.wc3.mdx.Texture;
import jm2lib.blizzard.wc3.mdx.TextureAnimation;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class MDX implements BlizzardFile {
   public Chunk<Integer> version;
   public Chunk<Model> model;
   public Chunk<Sequence> sequences;
   public Chunk<Integer> globalSequences;
   public Chunk<Texture> textures;
   public Chunk<SoundTrack> sounds;
   public Chunk<Material> materials;
   public Chunk<TextureAnimation> textureAnimations;
   public Chunk<Geoset> geosets;
   public Chunk<GeosetAnimation> geosetAnimations;
   public Chunk<Bone> bones;
   public Chunk<Light> lights;

   public void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      while(in.getFilePointer() < in.length()) {
         String magic = in.readString(4);
         switch(magic.hashCode()) {
         case 2044612:
            if (magic.equals("BONE")) {
               this.bones = new Chunk(Bone.class, magic);
               this.bones.unmarshal(in);
               continue;
            }
            break;
         case 2183984:
            if (magic.equals("GEOA")) {
               this.geosetAnimations = new Chunk(GeosetAnimation.class, magic);
               this.geosetAnimations.unmarshal(in);
               continue;
            }
            break;
         case 2184002:
            if (magic.equals("GEOS")) {
               this.geosets = new Chunk(Geoset.class, magic);
               this.geosets.unmarshal(in);
               continue;
            }
            break;
         case 2190326:
            if (magic.equals("GLBS")) {
               this.globalSequences = new Chunk(Integer.TYPE, magic);
               this.globalSequences.unmarshal(in);
               continue;
            }
            break;
         case 2336942:
            if (magic.equals("LITE")) {
               this.lights = new Chunk(Light.class, magic);
               this.lights.unmarshal(in);
               continue;
            }
            break;
         case 2372010:
            if (magic.equals("MODL")) {
               this.model = new Chunk(Model.class, magic);
               this.model.unmarshal(in);
               continue;
            }
            break;
         case 2377070:
            if (magic.equals("MTLS")) {
               this.materials = new Chunk(Material.class, magic);
               this.materials.unmarshal(in);
               continue;
            }
            break;
         case 2541556:
            if (magic.equals("SEQS")) {
               this.sequences = new Chunk(Sequence.class, magic);
               this.sequences.unmarshal(in);
               continue;
            }
            break;
         case 2549802:
            if (magic.equals("SNDS")) {
               this.sounds = new Chunk(SoundTrack.class, magic);
               this.sounds.unmarshal(in);
               continue;
            }
            break;
         case 2571564:
            if (magic.equals("TEXS")) {
               this.textures = new Chunk(Texture.class, magic);
               this.textures.unmarshal(in);
               continue;
            }
            break;
         case 2589105:
            if (magic.equals("TXAN")) {
               this.textureAnimations = new Chunk(TextureAnimation.class, magic);
               this.textureAnimations.unmarshal(in);
               continue;
            }
            break;
         case 2630960:
            if (magic.equals("VERS")) {
               this.version = new Chunk(Integer.TYPE, magic);
               this.version.unmarshal(in);
               continue;
            }
         }

         System.err.println("Skipping Chunk [" + magic + "]");
         in.seek((long)in.readInt() + in.getFilePointer());
      }

   }

   public void marshal(MarshalingStream out) throws IOException {
      if (this.version != null) {
         this.version.marshal(out);
      }

      if (this.model != null) {
         this.model.marshal(out);
      }

      if (this.sequences != null) {
         this.sequences.marshal(out);
      }

      if (this.globalSequences != null) {
         this.globalSequences.marshal(out);
      }

      if (this.textures != null) {
         this.textures.marshal(out);
      }

      if (this.sounds != null) {
         this.sounds.marshal(out);
      }

      if (this.materials != null) {
         this.materials.marshal(out);
      }

      if (this.textureAnimations != null) {
         this.textureAnimations.marshal(out);
      }

      if (this.geosets != null) {
         this.geosets.marshal(out);
      }

      if (this.geosetAnimations != null) {
         this.geosetAnimations.marshal(out);
      }

      if (this.bones != null) {
         this.bones.marshal(out);
      }

      if (this.lights != null) {
         this.lights.marshal(out);
      }

   }

   public jm2lib.blizzard.wow.classic.Model upConvert() {
      jm2lib.blizzard.wow.classic.Model output = new jm2lib.blizzard.wow.classic.Model();
      if (this.model.size() > 0) {
         output.name = new ArrayRef(((Model)this.model.get(0)).name);
      }

      output.globalSequences.addAll(this.globalSequences);
      Iterator var3 = this.textures.iterator();

      while(var3.hasNext()) {
         Texture item = (Texture)var3.next();
         output.textures.add(item.upConvert());
      }

      return output;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.getClass().getName()).append(" {\n\tversion: ").append(this.version).append("\n\tmodel: ").append(this.model).append("\n\tsequences: ").append(this.sequences).append("\n\tglobalSequences: ").append(this.globalSequences).append("\n\ttextures: ").append(this.textures).append("\n\tsounds: ").append(this.sounds).append("\n\tmaterials: ").append(this.materials).append("\n\ttextureAnimations: ").append(this.textureAnimations).append("\n\tgeosets: ").append(this.geosets).append("\n}");
      builder.append("\n\tgeosetAnimations: ").append(this.geosetAnimations).append("\n}");
      builder.append("\n\tbones: ").append(this.bones).append("\n}");
      builder.append("\n\tlights: ").append(this.lights).append("\n}");
      return builder.toString();
   }
}
