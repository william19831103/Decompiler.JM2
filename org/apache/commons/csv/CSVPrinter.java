package org.apache.commons.csv;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public final class CSVPrinter implements Flushable, Closeable {
   private final Appendable out;
   private final CSVFormat format;
   private boolean newRecord = true;

   public CSVPrinter(Appendable out, CSVFormat format) throws IOException {
      Assertions.notNull(out, "out");
      Assertions.notNull(format, "format");
      this.out = out;
      this.format = format;
      if (format.getHeaderComments() != null) {
         String[] arr$ = format.getHeaderComments();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String line = arr$[i$];
            if (line != null) {
               this.printComment(line);
            }
         }
      }

      if (format.getHeader() != null) {
         this.printRecord((Object[])format.getHeader());
      }

   }

   public void close() throws IOException {
      if (this.out instanceof Closeable) {
         ((Closeable)this.out).close();
      }

   }

   public void flush() throws IOException {
      if (this.out instanceof Flushable) {
         ((Flushable)this.out).flush();
      }

   }

   public Appendable getOut() {
      return this.out;
   }

   public void print(Object value) throws IOException {
      String strValue;
      if (value == null) {
         String nullString = this.format.getNullString();
         strValue = nullString == null ? "" : nullString;
      } else {
         strValue = value.toString();
      }

      this.print(value, strValue, 0, strValue.length());
   }

   private void print(Object object, CharSequence value, int offset, int len) throws IOException {
      if (!this.newRecord) {
         this.out.append(this.format.getDelimiter());
      }

      if (this.format.isQuoteCharacterSet()) {
         this.printAndQuote(object, value, offset, len);
      } else if (this.format.isEscapeCharacterSet()) {
         this.printAndEscape(value, offset, len);
      } else {
         this.out.append(value, offset, offset + len);
      }

      this.newRecord = false;
   }

   private void printAndEscape(CharSequence value, int offset, int len) throws IOException {
      int start = offset;
      int pos = offset;
      int end = offset + len;
      char delim = this.format.getDelimiter();

      for(char escape = this.format.getEscapeCharacter(); pos < end; ++pos) {
         char c = value.charAt(pos);
         if (c == '\r' || c == '\n' || c == delim || c == escape) {
            if (pos > start) {
               this.out.append(value, start, pos);
            }

            if (c == '\n') {
               c = 'n';
            } else if (c == '\r') {
               c = 'r';
            }

            this.out.append(escape);
            this.out.append(c);
            start = pos + 1;
         }
      }

      if (pos > start) {
         this.out.append(value, start, pos);
      }

   }

   private void printAndQuote(Object object, CharSequence value, int offset, int len) throws IOException {
      boolean quote = false;
      int start = offset;
      int pos = offset;
      int end = offset + len;
      char delimChar = this.format.getDelimiter();
      char quoteChar = this.format.getQuoteCharacter();
      QuoteMode quoteModePolicy = this.format.getQuoteMode();
      if (quoteModePolicy == null) {
         quoteModePolicy = QuoteMode.MINIMAL;
      }

      char c;
      switch(quoteModePolicy) {
      case ALL:
         quote = true;
         break;
      case NON_NUMERIC:
         quote = !(object instanceof Number);
         break;
      case NONE:
         this.printAndEscape(value, offset, len);
         return;
      case MINIMAL:
         if (len <= 0) {
            if (this.newRecord) {
               quote = true;
            }
         } else {
            c = value.charAt(offset);
            if (!this.newRecord || c >= '0' && (c <= '9' || c >= 'A') && (c <= 'Z' || c >= 'a') && c <= 'z') {
               if (c <= '#') {
                  quote = true;
               } else {
                  while(pos < end) {
                     c = value.charAt(pos);
                     if (c == '\n' || c == '\r' || c == quoteChar || c == delimChar) {
                        quote = true;
                        break;
                     }

                     ++pos;
                  }

                  if (!quote) {
                     pos = end - 1;
                     c = value.charAt(pos);
                     if (c <= ' ') {
                        quote = true;
                     }
                  }
               }
            } else {
               quote = true;
            }
         }

         if (!quote) {
            this.out.append(value, offset, end);
            return;
         }
         break;
      default:
         throw new IllegalStateException("Unexpected Quote value: " + quoteModePolicy);
      }

      if (!quote) {
         this.out.append(value, offset, end);
      } else {
         this.out.append(quoteChar);

         for(; pos < end; ++pos) {
            c = value.charAt(pos);
            if (c == quoteChar) {
               this.out.append(value, start, pos + 1);
               start = pos;
            }
         }

         this.out.append(value, start, pos);
         this.out.append(quoteChar);
      }
   }

   public void printComment(String comment) throws IOException {
      if (this.format.isCommentMarkerSet()) {
         if (!this.newRecord) {
            this.println();
         }

         this.out.append(this.format.getCommentMarker());
         this.out.append(' ');

         for(int i = 0; i < comment.length(); ++i) {
            char c = comment.charAt(i);
            switch(c) {
            case '\r':
               if (i + 1 < comment.length() && comment.charAt(i + 1) == '\n') {
                  ++i;
               }
            case '\n':
               this.println();
               this.out.append(this.format.getCommentMarker());
               this.out.append(' ');
               break;
            default:
               this.out.append(c);
            }
         }

         this.println();
      }
   }

   public void println() throws IOException {
      String recordSeparator = this.format.getRecordSeparator();
      if (recordSeparator != null) {
         this.out.append(recordSeparator);
      }

      this.newRecord = true;
   }

   public void printRecord(Iterable<?> values) throws IOException {
      Iterator i$ = values.iterator();

      while(i$.hasNext()) {
         Object value = i$.next();
         this.print(value);
      }

      this.println();
   }

   public void printRecord(Object... values) throws IOException {
      Object[] arr$ = values;
      int len$ = values.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Object value = arr$[i$];
         this.print(value);
      }

      this.println();
   }

   public void printRecords(Iterable<?> values) throws IOException {
      Iterator i$ = values.iterator();

      while(i$.hasNext()) {
         Object value = i$.next();
         if (value instanceof Object[]) {
            this.printRecord((Object[])((Object[])value));
         } else if (value instanceof Iterable) {
            this.printRecord((Iterable)value);
         } else {
            this.printRecord(value);
         }
      }

   }

   public void printRecords(Object... values) throws IOException {
      Object[] arr$ = values;
      int len$ = values.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Object value = arr$[i$];
         if (value instanceof Object[]) {
            this.printRecord((Object[])((Object[])value));
         } else if (value instanceof Iterable) {
            this.printRecord((Iterable)value);
         } else {
            this.printRecord(value);
         }
      }

   }

   public void printRecords(ResultSet resultSet) throws SQLException, IOException {
      int columnCount = resultSet.getMetaData().getColumnCount();

      while(resultSet.next()) {
         for(int i = 1; i <= columnCount; ++i) {
            this.print(resultSet.getObject(i));
         }

         this.println();
      }

   }
}
