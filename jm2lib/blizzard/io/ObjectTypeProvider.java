package jm2lib.blizzard.io;

import java.util.Collection;
import java.util.LinkedList;

public abstract class ObjectTypeProvider {
   private final Collection<ObjectTypeProvider.MagicClassMapping> sources = new LinkedList();

   protected ObjectTypeProvider() {
   }

   protected void addMapping(FileMagic magic, String classname) {
      this.sources.add(new ObjectTypeProvider.MagicClassMapping(magic, classname, (ObjectTypeProvider.MagicClassMapping)null));
   }

   public Collection<ObjectTypeProvider.MagicClassMapping> getMappings() {
      return this.sources;
   }

   public static class MagicClassMapping {
      public final FileMagic magic;
      public final String classname;

      private MagicClassMapping(FileMagic magic, String classname) {
         this.magic = magic;
         this.classname = classname;
      }

      // $FF: synthetic method
      MagicClassMapping(FileMagic var1, String var2, ObjectTypeProvider.MagicClassMapping var3) {
         this(var1, var2);
      }
   }
}
