package jm2lib.blizzard.common.types;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import java.io.InvalidClassException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import jm2lib.blizzard.common.interfaces.Referencer;
import jm2lib.blizzard.wow.common.AnimFilesHandler;
import jm2lib.io.Marshalable;
import jm2lib.io.MarshalingStream;
import jm2lib.io.UnmarshalingStream;

public class ArrayRef<T> extends ArrayList<T> implements Referencer {
   private static final long serialVersionUID = -6755334429400482754L;
   private Class<T> type;
   private Class<?> subType;
   private long startOfs;
   private boolean shouldWriteContent;
   private LERandomAccessFile animFile;
   private LERandomAccessFile[] animFiles;
   private int ofs;

   public ArrayRef(Class<T> type) {
      this.type = type;
      if (type == ArrayRef.class) {
         System.err.println("WARNING : ArrayRef subType not specified");
      }

      this.subType = null;
      this.shouldWriteContent = false;
      this.ofs = 0;
   }

   public ArrayRef(Class<ArrayRef> type, Class<?> subType) {
      this.type = type;
      if (type == ArrayRef.class && subType == null) {
         System.err.println("WARNING : ArrayRef subType not specified");
      }

      this.subType = subType;
      this.shouldWriteContent = false;
      this.ofs = 0;
   }

   public ArrayRef(String str) {
      this(Byte.TYPE);
      byte[] array = Arrays.copyOf(str.getBytes(StandardCharsets.UTF_8), str.length() + 1);
      byte[] var6 = array;
      int var5 = array.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         byte character = var6[var4];
         this.add(character);
      }

   }

   public Class<T> getType() {
      return this.type;
   }

   public Class<?> getSubType() {
      return this.subType;
   }

   public void setAnimFiles(LERandomAccessFile[] animFiles) {
      this.animFiles = animFiles;
   }

   private void setAnimFile(LERandomAccessFile animFile) {
      assert this.type == ArrayRef.class;

      this.animFile = animFile;
   }

   public final void unmarshal(UnmarshalingStream in) throws IOException, ClassNotFoundException {
      int n = in.readInt();
      this.ofs = in.readInt();
      if (this.ofs < 0) {
         System.err.println("Error : Tried to read " + n + "elements at offset " + this.ofs);
      }

      try {
         this.readContent(in, n);
      } catch (IllegalAccessException | InstantiationException var4) {
         throw new InvalidClassException("Could not read ArrayRef content");
      }
   }

   private void readContent(UnmarshalingStream in, int n) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
      if (n != 0) {
         long currentOfs = in.getFilePointer();
         UnmarshalingStream mainStream = in;
         if (this.animFile != null) {
            in = (UnmarshalingStream)this.animFile;
         }

         in.seek((long)this.ofs);

         for(int i = 0; i < n; ++i) {
            if (this.type == ArrayRef.class) {
               this.add(new ArrayRef(this.subType));
               ((ArrayRef)this.get(i)).setAnimFile(this.animFiles[i]);
               ((ArrayRef)this.get(i)).unmarshal(in);
            } else if (AnimFilesHandler.class.isAssignableFrom(this.type)) {
               this.add(this.type.newInstance());
               ((AnimFilesHandler)this.get(i)).setAnimFiles(this.animFiles);
               ((AnimFilesHandler)this.get(i)).unmarshal(in);
            } else {
               this.add(in.readGeneric(this.type));
            }
         }

         if (this.animFile != null) {
            in = mainStream;
         }

         in.seek(currentOfs);
      }
   }

   public final void marshal(MarshalingStream out) throws IOException {
      this.startOfs = out.getFilePointer();
      out.writeInt(this.size());
      out.writeInt(this.ofs);
      this.shouldWriteContent = true;
   }

   public void writeContent(MarshalingStream out) throws IOException, InstantiationException, IllegalAccessException {
      if (!this.shouldWriteContent) {
         throw new IOException("ArrayRef<" + this.type + "> was not marshal() before writing content.\n" + "It needs it in order to update its n&ofs so the data can be found in the file again.");
      } else if (this.size() != 0) {
         MarshalingStream mainStream = out;
         if (this.animFile != null) {
            out = (MarshalingStream)this.animFile;
         }

         this.ofs = (int)out.getFilePointer();

         int i;
         for(i = 0; i < this.size(); ++i) {
            if (this.type == ArrayRef.class) {
               ((ArrayRef)this.get(i)).setAnimFile(this.animFiles[i]);
               ((ArrayRef)this.get(i)).marshal(out);
            } else if (AnimFilesHandler.class.isAssignableFrom(this.type)) {
               ((AnimFilesHandler)this.get(i)).setAnimFiles(this.animFiles);
               ((AnimFilesHandler)this.get(i)).marshal(out);
            } else {
               out.writeGeneric(this.type, this.get(i));
            }
         }

         if (Referencer.class.isAssignableFrom(this.type)) {
            for(i = 0; i < this.size(); ++i) {
               ((Referencer)this.get(i)).writeContent(out);
            }
         }

         if (this.animFile != null) {
            out = mainStream;
         }

         long currentOfs = out.getFilePointer();
         out.seek(this.startOfs);
         this.marshal(out);
         this.shouldWriteContent = false;
         out.seek(currentOfs);
      }
   }

   public String toNameString() {
      if (this.size() == 0) {
         return "";
      } else {
         int i;
         if (this.type == Byte.TYPE) {
            byte[] array = new byte[this.size()];

            for(i = 0; i < this.size(); ++i) {
               array[i] = (Byte)this.get(i);
            }

            return (new String(array, StandardCharsets.UTF_8)).trim();
         } else if (this.type != Character.TYPE) {
            throw new UnsupportedOperationException("Can't print an ArrayRef<" + this.type + "> as a readable String");
         } else {
            char[] array = new char[this.size()];

            for(i = 0; i < this.size(); ++i) {
               array[i] = (Character)this.get(i);
            }

            return (new String(array)).trim();
         }
      }
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      String NEW_LINE = System.getProperty("line.separator");
      result.append("[number: " + this.size() + "]");
      if (this.size() != 0) {
         if (!this.type.isPrimitive() && !BlizzardVector.class.isAssignableFrom(this.type)) {
            result.append(NEW_LINE);
         }

         for(int i = 0; i < this.size(); ++i) {
            if (!this.type.isPrimitive() && !BlizzardVector.class.isAssignableFrom(this.type)) {
               result.append("[" + i + "] ");
            } else {
               result.append(NEW_LINE);
            }

            if (this.type == Character.TYPE) {
               result.append((Character)this.get(i));
            } else {
               result.append(this.get(i));
            }
         }

         result.append(NEW_LINE);
      }

      return result.toString();
   }

   public void addNew(int hint) throws InstantiationException, IllegalAccessException, InvalidClassException {
      if (hint == 1) {
         if (this.type == Vec3F.class) {
            this.add(new Vec3F(1.0F, 1.0F, 1.0F));
         } else if (this.type == QuatS.class) {
            this.add(new QuatS((short)32767, (short)32767, (short)32767, (short)-1));
         } else if (this.type == Byte.TYPE) {
            this.add(new Byte((byte)1));
         } else {
            if (this.type != Short.TYPE) {
               throw new InvalidClassException("Hint 1 unsupported for class " + this.type);
            }

            this.add(new Short((short)32767));
         }
      } else if (hint == 0) {
         if (Marshalable.class.isAssignableFrom(this.type)) {
            this.add(this.type.newInstance());
         } else if (this.type == Integer.TYPE) {
            this.add(new Integer(0));
         } else if (this.type == Float.TYPE) {
            this.add(new Float(0.0F));
         } else if (this.type == Short.TYPE) {
            this.add(new Short((short)0));
         } else if (this.type == Long.TYPE) {
            this.add(new Long(0L));
         } else if (this.type == Byte.TYPE) {
            this.add(new Byte((byte)0));
         } else {
            if (this.type != Character.TYPE) {
               throw new InvalidClassException("Hint 0 unsupported for class " + this.type);
            }

            this.add(new Character('\u0000'));
         }
      } else {
         System.err.println("Hint value " + hint + " not recognized");
         System.exit(1);
      }

   }
}
