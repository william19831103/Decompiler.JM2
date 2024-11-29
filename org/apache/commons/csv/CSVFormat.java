package org.apache.commons.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class CSVFormat implements Serializable {
   private static final long serialVersionUID = 1L;
   private final char delimiter;
   private final Character quoteCharacter;
   private final QuoteMode quoteMode;
   private final Character commentMarker;
   private final Character escapeCharacter;
   private final boolean ignoreSurroundingSpaces;
   private final boolean allowMissingColumnNames;
   private final boolean ignoreEmptyLines;
   private final String recordSeparator;
   private final String nullString;
   private final String[] header;
   private final String[] headerComments;
   private final boolean skipHeaderRecord;
   public static final CSVFormat DEFAULT;
   public static final CSVFormat RFC4180;
   public static final CSVFormat EXCEL;
   public static final CSVFormat TDF;
   public static final CSVFormat MYSQL;

   private static boolean isLineBreak(char c) {
      return c == '\n' || c == '\r';
   }

   private static boolean isLineBreak(Character c) {
      return c != null && isLineBreak(c);
   }

   public static CSVFormat newFormat(char delimiter) {
      return new CSVFormat(delimiter, (Character)null, (QuoteMode)null, (Character)null, (Character)null, false, false, (String)null, (String)null, (Object[])null, (String[])null, false, false);
   }

   public static CSVFormat valueOf(String format) {
      return CSVFormat.Predefined.valueOf(format).getFormat();
   }

   private CSVFormat(char delimiter, Character quoteChar, QuoteMode quoteMode, Character commentStart, Character escape, boolean ignoreSurroundingSpaces, boolean ignoreEmptyLines, String recordSeparator, String nullString, Object[] headerComments, String[] header, boolean skipHeaderRecord, boolean allowMissingColumnNames) {
      this.delimiter = delimiter;
      this.quoteCharacter = quoteChar;
      this.quoteMode = quoteMode;
      this.commentMarker = commentStart;
      this.escapeCharacter = escape;
      this.ignoreSurroundingSpaces = ignoreSurroundingSpaces;
      this.allowMissingColumnNames = allowMissingColumnNames;
      this.ignoreEmptyLines = ignoreEmptyLines;
      this.recordSeparator = recordSeparator;
      this.nullString = nullString;
      this.headerComments = this.toStringArray(headerComments);
      this.header = header == null ? null : (String[])header.clone();
      this.skipHeaderRecord = skipHeaderRecord;
      this.validate();
   }

   private String[] toStringArray(Object[] values) {
      if (values == null) {
         return null;
      } else {
         String[] strings = new String[values.length];

         for(int i = 0; i < values.length; ++i) {
            Object value = values[i];
            strings[i] = value == null ? null : value.toString();
         }

         return strings;
      }
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         CSVFormat other = (CSVFormat)obj;
         if (this.delimiter != other.delimiter) {
            return false;
         } else if (this.quoteMode != other.quoteMode) {
            return false;
         } else {
            if (this.quoteCharacter == null) {
               if (other.quoteCharacter != null) {
                  return false;
               }
            } else if (!this.quoteCharacter.equals(other.quoteCharacter)) {
               return false;
            }

            if (this.commentMarker == null) {
               if (other.commentMarker != null) {
                  return false;
               }
            } else if (!this.commentMarker.equals(other.commentMarker)) {
               return false;
            }

            if (this.escapeCharacter == null) {
               if (other.escapeCharacter != null) {
                  return false;
               }
            } else if (!this.escapeCharacter.equals(other.escapeCharacter)) {
               return false;
            }

            if (this.nullString == null) {
               if (other.nullString != null) {
                  return false;
               }
            } else if (!this.nullString.equals(other.nullString)) {
               return false;
            }

            if (!Arrays.equals(this.header, other.header)) {
               return false;
            } else if (this.ignoreSurroundingSpaces != other.ignoreSurroundingSpaces) {
               return false;
            } else if (this.ignoreEmptyLines != other.ignoreEmptyLines) {
               return false;
            } else if (this.skipHeaderRecord != other.skipHeaderRecord) {
               return false;
            } else {
               if (this.recordSeparator == null) {
                  if (other.recordSeparator != null) {
                     return false;
                  }
               } else if (!this.recordSeparator.equals(other.recordSeparator)) {
                  return false;
               }

               return true;
            }
         }
      }
   }

   public String format(Object... values) {
      StringWriter out = new StringWriter();

      try {
         (new CSVPrinter(out, this)).printRecord(values);
         return out.toString().trim();
      } catch (IOException var4) {
         throw new IllegalStateException(var4);
      }
   }

   public Character getCommentMarker() {
      return this.commentMarker;
   }

   public char getDelimiter() {
      return this.delimiter;
   }

   public Character getEscapeCharacter() {
      return this.escapeCharacter;
   }

   public String[] getHeader() {
      return this.header != null ? (String[])this.header.clone() : null;
   }

   public String[] getHeaderComments() {
      return this.headerComments != null ? (String[])this.headerComments.clone() : null;
   }

   public boolean getAllowMissingColumnNames() {
      return this.allowMissingColumnNames;
   }

   public boolean getIgnoreEmptyLines() {
      return this.ignoreEmptyLines;
   }

   public boolean getIgnoreSurroundingSpaces() {
      return this.ignoreSurroundingSpaces;
   }

   public String getNullString() {
      return this.nullString;
   }

   public Character getQuoteCharacter() {
      return this.quoteCharacter;
   }

   public QuoteMode getQuoteMode() {
      return this.quoteMode;
   }

   public String getRecordSeparator() {
      return this.recordSeparator;
   }

   public boolean getSkipHeaderRecord() {
      return this.skipHeaderRecord;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      int result = 31 * result + this.delimiter;
      result = 31 * result + (this.quoteMode == null ? 0 : this.quoteMode.hashCode());
      result = 31 * result + (this.quoteCharacter == null ? 0 : this.quoteCharacter.hashCode());
      result = 31 * result + (this.commentMarker == null ? 0 : this.commentMarker.hashCode());
      result = 31 * result + (this.escapeCharacter == null ? 0 : this.escapeCharacter.hashCode());
      result = 31 * result + (this.nullString == null ? 0 : this.nullString.hashCode());
      result = 31 * result + (this.ignoreSurroundingSpaces ? 1231 : 1237);
      result = 31 * result + (this.ignoreEmptyLines ? 1231 : 1237);
      result = 31 * result + (this.skipHeaderRecord ? 1231 : 1237);
      result = 31 * result + (this.recordSeparator == null ? 0 : this.recordSeparator.hashCode());
      result = 31 * result + Arrays.hashCode(this.header);
      return result;
   }

   public boolean isCommentMarkerSet() {
      return this.commentMarker != null;
   }

   public boolean isEscapeCharacterSet() {
      return this.escapeCharacter != null;
   }

   public boolean isNullStringSet() {
      return this.nullString != null;
   }

   public boolean isQuoteCharacterSet() {
      return this.quoteCharacter != null;
   }

   public CSVParser parse(Reader in) throws IOException {
      return new CSVParser(in, this);
   }

   public CSVPrinter print(Appendable out) throws IOException {
      return new CSVPrinter(out, this);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Delimiter=<").append(this.delimiter).append('>');
      if (this.isEscapeCharacterSet()) {
         sb.append(' ');
         sb.append("Escape=<").append(this.escapeCharacter).append('>');
      }

      if (this.isQuoteCharacterSet()) {
         sb.append(' ');
         sb.append("QuoteChar=<").append(this.quoteCharacter).append('>');
      }

      if (this.isCommentMarkerSet()) {
         sb.append(' ');
         sb.append("CommentStart=<").append(this.commentMarker).append('>');
      }

      if (this.isNullStringSet()) {
         sb.append(' ');
         sb.append("NullString=<").append(this.nullString).append('>');
      }

      if (this.recordSeparator != null) {
         sb.append(' ');
         sb.append("RecordSeparator=<").append(this.recordSeparator).append('>');
      }

      if (this.getIgnoreEmptyLines()) {
         sb.append(" EmptyLines:ignored");
      }

      if (this.getIgnoreSurroundingSpaces()) {
         sb.append(" SurroundingSpaces:ignored");
      }

      sb.append(" SkipHeaderRecord:").append(this.skipHeaderRecord);
      if (this.headerComments != null) {
         sb.append(' ');
         sb.append("HeaderComments:").append(Arrays.toString(this.headerComments));
      }

      if (this.header != null) {
         sb.append(' ');
         sb.append("Header:").append(Arrays.toString(this.header));
      }

      return sb.toString();
   }

   private void validate() throws IllegalArgumentException {
      if (isLineBreak(this.delimiter)) {
         throw new IllegalArgumentException("The delimiter cannot be a line break");
      } else if (this.quoteCharacter != null && this.delimiter == this.quoteCharacter) {
         throw new IllegalArgumentException("The quoteChar character and the delimiter cannot be the same ('" + this.quoteCharacter + "')");
      } else if (this.escapeCharacter != null && this.delimiter == this.escapeCharacter) {
         throw new IllegalArgumentException("The escape character and the delimiter cannot be the same ('" + this.escapeCharacter + "')");
      } else if (this.commentMarker != null && this.delimiter == this.commentMarker) {
         throw new IllegalArgumentException("The comment start character and the delimiter cannot be the same ('" + this.commentMarker + "')");
      } else if (this.quoteCharacter != null && this.quoteCharacter.equals(this.commentMarker)) {
         throw new IllegalArgumentException("The comment start character and the quoteChar cannot be the same ('" + this.commentMarker + "')");
      } else if (this.escapeCharacter != null && this.escapeCharacter.equals(this.commentMarker)) {
         throw new IllegalArgumentException("The comment start and the escape character cannot be the same ('" + this.commentMarker + "')");
      } else if (this.escapeCharacter == null && this.quoteMode == QuoteMode.NONE) {
         throw new IllegalArgumentException("No quotes mode set but no escape character is set");
      } else {
         if (this.header != null) {
            Set<String> dupCheck = new HashSet();
            String[] arr$ = this.header;
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               String hdr = arr$[i$];
               if (!dupCheck.add(hdr)) {
                  throw new IllegalArgumentException("The header contains a duplicate entry: '" + hdr + "' in " + Arrays.toString(this.header));
               }
            }
         }

      }
   }

   public CSVFormat withCommentMarker(char commentMarker) {
      return this.withCommentMarker(commentMarker);
   }

   public CSVFormat withCommentMarker(Character commentMarker) {
      if (isLineBreak(commentMarker)) {
         throw new IllegalArgumentException("The comment start marker character cannot be a line break");
      } else {
         return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, this.header, this.skipHeaderRecord, this.allowMissingColumnNames);
      }
   }

   public CSVFormat withDelimiter(char delimiter) {
      if (isLineBreak(delimiter)) {
         throw new IllegalArgumentException("The delimiter cannot be a line break");
      } else {
         return new CSVFormat(delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, this.header, this.skipHeaderRecord, this.allowMissingColumnNames);
      }
   }

   public CSVFormat withEscape(char escape) {
      return this.withEscape(escape);
   }

   public CSVFormat withEscape(Character escape) {
      if (isLineBreak(escape)) {
         throw new IllegalArgumentException("The escape character cannot be a line break");
      } else {
         return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, escape, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, this.header, this.skipHeaderRecord, this.allowMissingColumnNames);
      }
   }

   public CSVFormat withHeader(String... header) {
      return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, header, this.skipHeaderRecord, this.allowMissingColumnNames);
   }

   public CSVFormat withHeader(ResultSet resultSet) throws SQLException {
      return this.withHeader(resultSet != null ? resultSet.getMetaData() : null);
   }

   public CSVFormat withHeader(ResultSetMetaData metaData) throws SQLException {
      String[] labels = null;
      if (metaData != null) {
         int columnCount = metaData.getColumnCount();
         labels = new String[columnCount];

         for(int i = 0; i < columnCount; ++i) {
            labels[i] = metaData.getColumnLabel(i + 1);
         }
      }

      return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, labels, this.skipHeaderRecord, this.allowMissingColumnNames);
   }

   public CSVFormat withHeaderComments(Object... headerComments) {
      return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, headerComments, this.header, this.skipHeaderRecord, this.allowMissingColumnNames);
   }

   public CSVFormat withAllowMissingColumnNames() {
      return this.withAllowMissingColumnNames(true);
   }

   public CSVFormat withAllowMissingColumnNames(boolean allowMissingColumnNames) {
      return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, this.header, this.skipHeaderRecord, allowMissingColumnNames);
   }

   public CSVFormat withIgnoreEmptyLines() {
      return this.withIgnoreEmptyLines(true);
   }

   public CSVFormat withIgnoreEmptyLines(boolean ignoreEmptyLines) {
      return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, this.header, this.skipHeaderRecord, this.allowMissingColumnNames);
   }

   public CSVFormat withIgnoreSurroundingSpaces() {
      return this.withIgnoreSurroundingSpaces(true);
   }

   public CSVFormat withIgnoreSurroundingSpaces(boolean ignoreSurroundingSpaces) {
      return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, this.escapeCharacter, ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, this.header, this.skipHeaderRecord, this.allowMissingColumnNames);
   }

   public CSVFormat withNullString(String nullString) {
      return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, nullString, this.headerComments, this.header, this.skipHeaderRecord, this.allowMissingColumnNames);
   }

   public CSVFormat withQuote(char quoteChar) {
      return this.withQuote(quoteChar);
   }

   public CSVFormat withQuote(Character quoteChar) {
      if (isLineBreak(quoteChar)) {
         throw new IllegalArgumentException("The quoteChar cannot be a line break");
      } else {
         return new CSVFormat(this.delimiter, quoteChar, this.quoteMode, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, this.header, this.skipHeaderRecord, this.allowMissingColumnNames);
      }
   }

   public CSVFormat withQuoteMode(QuoteMode quoteModePolicy) {
      return new CSVFormat(this.delimiter, this.quoteCharacter, quoteModePolicy, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, this.header, this.skipHeaderRecord, this.allowMissingColumnNames);
   }

   public CSVFormat withRecordSeparator(char recordSeparator) {
      return this.withRecordSeparator(String.valueOf(recordSeparator));
   }

   public CSVFormat withRecordSeparator(String recordSeparator) {
      return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, recordSeparator, this.nullString, this.headerComments, this.header, this.skipHeaderRecord, this.allowMissingColumnNames);
   }

   public CSVFormat withSkipHeaderRecord() {
      return this.withSkipHeaderRecord(true);
   }

   public CSVFormat withSkipHeaderRecord(boolean skipHeaderRecord) {
      return new CSVFormat(this.delimiter, this.quoteCharacter, this.quoteMode, this.commentMarker, this.escapeCharacter, this.ignoreSurroundingSpaces, this.ignoreEmptyLines, this.recordSeparator, this.nullString, this.headerComments, this.header, skipHeaderRecord, this.allowMissingColumnNames);
   }

   static {
      DEFAULT = new CSVFormat(',', Constants.DOUBLE_QUOTE_CHAR, (QuoteMode)null, (Character)null, (Character)null, false, true, "\r\n", (String)null, (Object[])null, (String[])null, false, false);
      RFC4180 = DEFAULT.withIgnoreEmptyLines(false);
      EXCEL = DEFAULT.withIgnoreEmptyLines(false).withAllowMissingColumnNames();
      TDF = DEFAULT.withDelimiter('\t').withIgnoreSurroundingSpaces();
      MYSQL = DEFAULT.withDelimiter('\t').withEscape('\\').withIgnoreEmptyLines(false).withQuote((Character)null).withRecordSeparator('\n');
   }

   public static enum Predefined {
      Default(CSVFormat.DEFAULT),
      Excel(CSVFormat.EXCEL),
      MySQL(CSVFormat.MYSQL),
      RFC4180(CSVFormat.RFC4180),
      TDF(CSVFormat.TDF);

      private final CSVFormat format;

      private Predefined(CSVFormat format) {
         this.format = format;
      }

      public CSVFormat getFormat() {
         return this.format;
      }
   }
}
