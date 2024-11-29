package org.apache.commons.csv;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public final class CSVParser implements Iterable<CSVRecord>, Closeable {
   private final CSVFormat format;
   private final Map<String, Integer> headerMap;
   private final Lexer lexer;
   private final List<String> record;
   private long recordNumber;
   private final long characterOffset;
   private final Token reusableToken;

   public static CSVParser parse(File file, Charset charset, CSVFormat format) throws IOException {
      Assertions.notNull(file, "file");
      Assertions.notNull(format, "format");
      return new CSVParser(new InputStreamReader(new FileInputStream(file), charset), format);
   }

   public static CSVParser parse(String string, CSVFormat format) throws IOException {
      Assertions.notNull(string, "string");
      Assertions.notNull(format, "format");
      return new CSVParser(new StringReader(string), format);
   }

   public static CSVParser parse(URL url, Charset charset, CSVFormat format) throws IOException {
      Assertions.notNull(url, "url");
      Assertions.notNull(charset, "charset");
      Assertions.notNull(format, "format");
      return new CSVParser(new InputStreamReader(url.openStream(), charset), format);
   }

   public CSVParser(Reader reader, CSVFormat format) throws IOException {
      this(reader, format, 0L, 1L);
   }

   public CSVParser(Reader reader, CSVFormat format, long characterOffset, long recordNumber) throws IOException {
      this.record = new ArrayList();
      this.reusableToken = new Token();
      Assertions.notNull(reader, "reader");
      Assertions.notNull(format, "format");
      this.format = format;
      this.lexer = new Lexer(format, new ExtendedBufferedReader(reader));
      this.headerMap = this.initializeHeader();
      this.characterOffset = characterOffset;
      this.recordNumber = recordNumber - 1L;
   }

   private void addRecordValue() {
      String input = this.reusableToken.content.toString();
      String nullString = this.format.getNullString();
      if (nullString == null) {
         this.record.add(input);
      } else {
         this.record.add(input.equalsIgnoreCase(nullString) ? null : input);
      }

   }

   public void close() throws IOException {
      if (this.lexer != null) {
         this.lexer.close();
      }

   }

   public long getCurrentLineNumber() {
      return this.lexer.getCurrentLineNumber();
   }

   public Map<String, Integer> getHeaderMap() {
      return this.headerMap == null ? null : new LinkedHashMap(this.headerMap);
   }

   public long getRecordNumber() {
      return this.recordNumber;
   }

   public List<CSVRecord> getRecords() throws IOException {
      ArrayList records = new ArrayList();

      CSVRecord rec;
      while((rec = this.nextRecord()) != null) {
         records.add(rec);
      }

      return records;
   }

   private Map<String, Integer> initializeHeader() throws IOException {
      Map<String, Integer> hdrMap = null;
      String[] formatHeader = this.format.getHeader();
      if (formatHeader != null) {
         hdrMap = new LinkedHashMap();
         String[] headerRecord = null;
         if (formatHeader.length == 0) {
            CSVRecord nextRecord = this.nextRecord();
            if (nextRecord != null) {
               headerRecord = nextRecord.values();
            }
         } else {
            if (this.format.getSkipHeaderRecord()) {
               this.nextRecord();
            }

            headerRecord = formatHeader;
         }

         if (headerRecord != null) {
            for(int i = 0; i < headerRecord.length; ++i) {
               String header = headerRecord[i];
               boolean containsHeader = hdrMap.containsKey(header);
               boolean emptyHeader = header == null || header.trim().isEmpty();
               if (containsHeader && (!emptyHeader || emptyHeader && !this.format.getAllowMissingColumnNames())) {
                  throw new IllegalArgumentException("The header contains a duplicate name: \"" + header + "\" in " + Arrays.toString(headerRecord));
               }

               hdrMap.put(header, i);
            }
         }
      }

      return hdrMap;
   }

   public boolean isClosed() {
      return this.lexer.isClosed();
   }

   public Iterator<CSVRecord> iterator() {
      return new Iterator<CSVRecord>() {
         private CSVRecord current;

         private CSVRecord getNextRecord() {
            try {
               return CSVParser.this.nextRecord();
            } catch (IOException var2) {
               throw new RuntimeException(var2);
            }
         }

         public boolean hasNext() {
            if (CSVParser.this.isClosed()) {
               return false;
            } else {
               if (this.current == null) {
                  this.current = this.getNextRecord();
               }

               return this.current != null;
            }
         }

         public CSVRecord next() {
            if (CSVParser.this.isClosed()) {
               throw new NoSuchElementException("CSVParser has been closed");
            } else {
               CSVRecord next = this.current;
               this.current = null;
               if (next == null) {
                  next = this.getNextRecord();
                  if (next == null) {
                     throw new NoSuchElementException("No more CSV records available");
                  }
               }

               return next;
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   CSVRecord nextRecord() throws IOException {
      CSVRecord result = null;
      this.record.clear();
      StringBuilder sb = null;
      long startCharPosition = this.lexer.getCharacterPosition() + this.characterOffset;

      do {
         this.reusableToken.reset();
         this.lexer.nextToken(this.reusableToken);
         switch(this.reusableToken.type) {
         case TOKEN:
            this.addRecordValue();
            break;
         case EORECORD:
            this.addRecordValue();
            break;
         case EOF:
            if (this.reusableToken.isReady) {
               this.addRecordValue();
            }
            break;
         case INVALID:
            throw new IOException("(line " + this.getCurrentLineNumber() + ") invalid parse sequence");
         case COMMENT:
            if (sb == null) {
               sb = new StringBuilder();
            } else {
               sb.append('\n');
            }

            sb.append(this.reusableToken.content);
            this.reusableToken.type = Token.Type.TOKEN;
            break;
         default:
            throw new IllegalStateException("Unexpected Token type: " + this.reusableToken.type);
         }
      } while(this.reusableToken.type == Token.Type.TOKEN);

      if (!this.record.isEmpty()) {
         ++this.recordNumber;
         String comment = sb == null ? null : sb.toString();
         result = new CSVRecord((String[])this.record.toArray(new String[this.record.size()]), this.headerMap, comment, this.recordNumber, startCharPosition);
      }

      return result;
   }
}
