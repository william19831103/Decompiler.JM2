package com.mindprod.ledatastream;

import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class LERandomAccessFile implements DataInput, DataOutput, Closeable {
   private static final int FIRST_COPYRIGHT_YEAR = 1999;
   private static final String EMBEDDED_COPYRIGHT = "Copyright: (c) 1999-2015 Roedy Green, Canadian Mind Products, http://mindprod.com";
   protected RandomAccessFile raf;
   protected byte[] work;
   private String fileName;

   public LERandomAccessFile(File file, String rw) throws FileNotFoundException {
      this.fileName = file.getAbsolutePath();
      this.raf = new RandomAccessFile(file, rw);
      this.work = new byte[8];
   }

   public LERandomAccessFile(String file, String rw) throws FileNotFoundException {
      this.fileName = file;
      this.raf = new RandomAccessFile(file, rw);
      this.work = new byte[8];
   }

   public String getFileName() {
      return this.fileName;
   }

   public final void close() throws IOException {
      this.raf.close();
   }

   public final FileDescriptor getFD() throws IOException {
      return this.raf.getFD();
   }

   public long getFilePointer() throws IOException {
      return this.raf.getFilePointer();
   }

   public final long length() throws IOException {
      return this.raf.length();
   }

   public final int read() throws IOException {
      return this.raf.read();
   }

   public final int read(byte[] ba) throws IOException {
      return this.raf.read(ba);
   }

   public final int read(byte[] ba, int off, int len) throws IOException {
      return this.raf.read(ba, off, len);
   }

   public final boolean readBoolean() throws IOException {
      return this.raf.readBoolean();
   }

   public final byte readByte() throws IOException {
      return this.raf.readByte();
   }

   public final char readChar() throws IOException {
      this.raf.readFully(this.work, 0, 2);
      return (char)((this.work[1] & 255) << 8 | this.work[0] & 255);
   }

   public final double readDouble() throws IOException {
      return Double.longBitsToDouble(this.readLong());
   }

   public final float readFloat() throws IOException {
      return Float.intBitsToFloat(this.readInt());
   }

   public final void readFully(byte[] ba) throws IOException {
      this.raf.readFully(ba, 0, ba.length);
   }

   public final void readFully(byte[] ba, int off, int len) throws IOException {
      this.raf.readFully(ba, off, len);
   }

   public final int readInt() throws IOException {
      this.raf.readFully(this.work, 0, 4);
      return this.work[3] << 24 | (this.work[2] & 255) << 16 | (this.work[1] & 255) << 8 | this.work[0] & 255;
   }

   public final String readLine() throws IOException {
      return this.raf.readLine();
   }

   public final long readLong() throws IOException {
      this.raf.readFully(this.work, 0, 8);
      return (long)this.work[7] << 56 | (long)(this.work[6] & 255) << 48 | (long)(this.work[5] & 255) << 40 | (long)(this.work[4] & 255) << 32 | (long)(this.work[3] & 255) << 24 | (long)(this.work[2] & 255) << 16 | (long)(this.work[1] & 255) << 8 | (long)(this.work[0] & 255);
   }

   public final short readShort() throws IOException {
      this.raf.readFully(this.work, 0, 2);
      return (short)((this.work[1] & 255) << 8 | this.work[0] & 255);
   }

   public final String readUTF() throws IOException {
      return this.raf.readUTF();
   }

   public final int readUnsignedByte() throws IOException {
      return this.raf.readUnsignedByte();
   }

   public final int readUnsignedShort() throws IOException {
      this.raf.readFully(this.work, 0, 2);
      return (this.work[1] & 255) << 8 | this.work[0] & 255;
   }

   public void seek(long pos) throws IOException {
      this.raf.seek(pos);
   }

   public final int skipBytes(int n) throws IOException {
      return this.raf.skipBytes(n);
   }

   public final synchronized void write(int ib) throws IOException {
      this.raf.write(ib);
   }

   public final void write(byte[] ba) throws IOException {
      this.raf.write(ba, 0, ba.length);
   }

   public final synchronized void write(byte[] ba, int off, int len) throws IOException {
      this.raf.write(ba, off, len);
   }

   public final void writeBoolean(boolean v) throws IOException {
      this.raf.writeBoolean(v);
   }

   public final void writeByte(int v) throws IOException {
      this.raf.writeByte(v);
   }

   public final void writeBytes(String s) throws IOException {
      this.raf.writeBytes(s);
   }

   public final void writeChar(int v) throws IOException {
      this.work[0] = (byte)v;
      this.work[1] = (byte)(v >> 8);
      this.raf.write(this.work, 0, 2);
   }

   public final void writeChars(String s) throws IOException {
      int len = s.length();

      for(int i = 0; i < len; ++i) {
         this.writeChar(s.charAt(i));
      }

   }

   public final void writeDouble(double v) throws IOException {
      this.writeLong(Double.doubleToLongBits(v));
   }

   public final void writeFloat(float v) throws IOException {
      this.writeInt(Float.floatToIntBits(v));
   }

   public final void writeInt(int v) throws IOException {
      this.work[0] = (byte)v;
      this.work[1] = (byte)(v >> 8);
      this.work[2] = (byte)(v >> 16);
      this.work[3] = (byte)(v >> 24);
      this.raf.write(this.work, 0, 4);
   }

   public final void writeLong(long v) throws IOException {
      this.work[0] = (byte)((int)v);
      this.work[1] = (byte)((int)(v >> 8));
      this.work[2] = (byte)((int)(v >> 16));
      this.work[3] = (byte)((int)(v >> 24));
      this.work[4] = (byte)((int)(v >> 32));
      this.work[5] = (byte)((int)(v >> 40));
      this.work[6] = (byte)((int)(v >> 48));
      this.work[7] = (byte)((int)(v >> 56));
      this.raf.write(this.work, 0, 8);
   }

   public final void writeShort(int v) throws IOException {
      this.work[0] = (byte)v;
      this.work[1] = (byte)(v >> 8);
      this.raf.write(this.work, 0, 2);
   }

   public final void writeUTF(String s) throws IOException {
      this.raf.writeUTF(s);
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("LERandomAccessFile [raf=").append(this.raf).append(", work=").append(Arrays.toString(this.work)).append(", fileName=").append(this.fileName).append(", getFilePointer()=");

      try {
         builder.append(this.getFilePointer());
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      builder.append("]");
      return builder.toString();
   }
}
