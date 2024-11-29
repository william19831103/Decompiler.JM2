package jm2lib.blizzard.io;

import java.io.NotSerializableException;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class ObjectTypeResolver {
   public static final ObjectTypeResolver resolver = new ObjectTypeResolver();
   private final ServiceLoader<ObjectTypeProvider> providers = ServiceLoader.load(ObjectTypeProvider.class);
   private final Map<FileMagic, String> magicToClass = new HashMap();
   private final Map<String, FileMagic> classToMagic = new HashMap();

   private ObjectTypeResolver() {
      this.load();
   }

   private void load() {
      this.providers.forEach((A) -> {
         A.getMappings().forEach((B) -> {
            this.magicToClass.put(B.magic, B.classname);
            this.classToMagic.put(B.classname, B.magic);
         });
      });
   }

   public Class<?> resolveClass(FileMagic magic) throws ClassNotFoundException {
      String classname = (String)this.magicToClass.get(magic);
      if (classname == null) {
         throw new ClassNotFoundException(String.format("object class for %S is not known or not a Blizzard object", magic.toString()));
      } else {
         return Class.forName(classname);
      }
   }

   public FileMagic resolveMagic(Class<?> clazz) throws NotSerializableException {
      String classname = clazz.getName();
      FileMagic magic = (FileMagic)this.classToMagic.get(classname);
      if (magic == null) {
         throw new NotSerializableException(String.format("file magic identifier for %s was not found", clazz.getName()));
      } else {
         return magic;
      }
   }

   public void reload() {
      this.providers.reload();
      this.magicToClass.clear();
      this.classToMagic.clear();
   }
}
