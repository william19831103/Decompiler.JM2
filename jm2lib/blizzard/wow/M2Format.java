package jm2lib.blizzard.wow;

import com.mindprod.ledatastream.LERandomAccessFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Formatter;
import java.util.List;
import jm2lib.io.Marshalable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public abstract class M2Format implements Marshalable, Cloneable {
   public int version;
   public static final int CLASSIC = 256;
   public static final int BURNING_CRUSADE = 260;
   public static final int LICH_KING = 264;
   public static final int CATACLYSM = 272;
   public static final int PANDARIA = 272;
   public static final int DRAENOR = 272;
   public static final int LEGION = 274;

   public M2Format downConvert() throws Exception {
      return null;
   }

   public M2Format upConvert() throws Exception {
      return null;
   }

   public static List<CSVRecord> openCSV(String name) throws IOException {
      StringBuilder pathBuilder = new StringBuilder();
      pathBuilder.append("jm2lib/blizzard/wow/common/");
      pathBuilder.append(name);
      pathBuilder.append(".dbc.csv");
      String path = pathBuilder.toString();
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      InputStream input = classLoader.getResourceAsStream(path);
      InputStreamReader reader = new InputStreamReader(input);
      CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
      List<CSVRecord> lines = csvParser.getRecords();
      reader.close();
      csvParser.close();
      return lines;
   }

   public static int align(LERandomAccessFile out) throws IOException {
      long pos = out.getFilePointer();
      int count = (int)(16L - (pos & 15L));
      byte[] zeros = new byte[count];
      out.write(zeros);
      return count;
   }

   public static String removeExtension(String s) {
      int extensionIndex = s.lastIndexOf(".");
      return extensionIndex == -1 ? s : s.substring(0, extensionIndex);
   }

   public static String getSkinName(String str, int i) {
      return removeExtension(str) + "0" + i + ".skin";
   }

   public static String getAnimFileName(String modelPath, short animationID, short subAnimationID) {
      StringBuilder sb = new StringBuilder();
      Formatter formatter = new Formatter(sb);
      formatter.format("%s%04d-%02d.anim", removeExtension(modelPath), animationID, subAnimationID);
      formatter.close();
      return sb.toString();
   }

   public static void closeFiles(boolean needPadding, LERandomAccessFile[] files) throws IOException {
      for(int i = 0; i < files.length; ++i) {
         if (files[i] != null) {
            if (needPadding) {
               align(files[i]);
            }

            files[i].close();
         }
      }

   }
}
