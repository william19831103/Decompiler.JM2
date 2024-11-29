package org.apache.commons.csv;

import java.io.Closeable;
import java.io.IOException;

final class Lexer implements Closeable {
   private static final char DISABLED = '\ufffe';
   private final char delimiter;
   private final char escape;
   private final char quoteChar;
   private final char commentStart;
   private final boolean ignoreSurroundingSpaces;
   private final boolean ignoreEmptyLines;
   private final ExtendedBufferedReader reader;

   Lexer(CSVFormat format, ExtendedBufferedReader reader) {
      this.reader = reader;
      this.delimiter = format.getDelimiter();
      this.escape = this.mapNullToDisabled(format.getEscapeCharacter());
      this.quoteChar = this.mapNullToDisabled(format.getQuoteCharacter());
      this.commentStart = this.mapNullToDisabled(format.getCommentMarker());
      this.ignoreSurroundingSpaces = format.getIgnoreSurroundingSpaces();
      this.ignoreEmptyLines = format.getIgnoreEmptyLines();
   }

   Token nextToken(Token token) throws IOException {
      int lastChar = this.reader.getLastChar();
      int c = this.reader.read();
      boolean eol = this.readEndOfLine(c);
      if (this.ignoreEmptyLines) {
         while(eol && this.isStartOfLine(lastChar)) {
            lastChar = c;
            c = this.reader.read();
            eol = this.readEndOfLine(c);
            if (this.isEndOfFile(c)) {
               token.type = Token.Type.EOF;
               return token;
            }
         }
      }

      if (this.isEndOfFile(lastChar) || !this.isDelimiter(lastChar) && this.isEndOfFile(c)) {
         token.type = Token.Type.EOF;
         return token;
      } else if (this.isStartOfLine(lastChar) && this.isCommentStart(c)) {
         String line = this.reader.readLine();
         if (line == null) {
            token.type = Token.Type.EOF;
            return token;
         } else {
            String comment = line.trim();
            token.content.append(comment);
            token.type = Token.Type.COMMENT;
            return token;
         }
      } else {
         while(token.type == Token.Type.INVALID) {
            if (this.ignoreSurroundingSpaces) {
               while(this.isWhitespace(c) && !eol) {
                  c = this.reader.read();
                  eol = this.readEndOfLine(c);
               }
            }

            if (this.isDelimiter(c)) {
               token.type = Token.Type.TOKEN;
            } else if (eol) {
               token.type = Token.Type.EORECORD;
            } else if (this.isQuoteChar(c)) {
               this.parseEncapsulatedToken(token);
            } else if (this.isEndOfFile(c)) {
               token.type = Token.Type.EOF;
               token.isReady = true;
            } else {
               this.parseSimpleToken(token, c);
            }
         }

         return token;
      }
   }

   private Token parseSimpleToken(Token token, int ch) throws IOException {
      while(true) {
         if (this.readEndOfLine(ch)) {
            token.type = Token.Type.EORECORD;
         } else if (this.isEndOfFile(ch)) {
            token.type = Token.Type.EOF;
            token.isReady = true;
         } else {
            if (!this.isDelimiter(ch)) {
               if (this.isEscape(ch)) {
                  int unescaped = this.readEscape();
                  if (unescaped == -1) {
                     token.content.append((char)ch).append((char)this.reader.getLastChar());
                  } else {
                     token.content.append((char)unescaped);
                  }

                  ch = this.reader.read();
                  continue;
               }

               token.content.append((char)ch);
               ch = this.reader.read();
               continue;
            }

            token.type = Token.Type.TOKEN;
         }

         if (this.ignoreSurroundingSpaces) {
            this.trimTrailingSpaces(token.content);
         }

         return token;
      }
   }

   private Token parseEncapsulatedToken(Token token) throws IOException {
      long startLineNumber = this.getCurrentLineNumber();

      while(true) {
         while(true) {
            int c = this.reader.read();
            if (this.isEscape(c)) {
               int unescaped = this.readEscape();
               if (unescaped == -1) {
                  token.content.append((char)c).append((char)this.reader.getLastChar());
               } else {
                  token.content.append((char)unescaped);
               }
            } else if (this.isQuoteChar(c)) {
               if (!this.isQuoteChar(this.reader.lookAhead())) {
                  do {
                     c = this.reader.read();
                     if (this.isDelimiter(c)) {
                        token.type = Token.Type.TOKEN;
                        return token;
                     }

                     if (this.isEndOfFile(c)) {
                        token.type = Token.Type.EOF;
                        token.isReady = true;
                        return token;
                     }

                     if (this.readEndOfLine(c)) {
                        token.type = Token.Type.EORECORD;
                        return token;
                     }
                  } while(this.isWhitespace(c));

                  throw new IOException("(line " + this.getCurrentLineNumber() + ") invalid char between encapsulated token and delimiter");
               }

               c = this.reader.read();
               token.content.append((char)c);
            } else {
               if (this.isEndOfFile(c)) {
                  throw new IOException("(startline " + startLineNumber + ") EOF reached before encapsulated token finished");
               }

               token.content.append((char)c);
            }
         }
      }
   }

   private char mapNullToDisabled(Character c) {
      return c == null ? '\ufffe' : c;
   }

   long getCurrentLineNumber() {
      return this.reader.getCurrentLineNumber();
   }

   long getCharacterPosition() {
      return this.reader.getPosition();
   }

   int readEscape() throws IOException {
      int ch = this.reader.read();
      switch(ch) {
      case -1:
         throw new IOException("EOF whilst processing escape sequence");
      case 8:
      case 9:
      case 10:
      case 12:
      case 13:
         return ch;
      case 98:
         return 8;
      case 102:
         return 12;
      case 110:
         return 10;
      case 114:
         return 13;
      case 116:
         return 9;
      default:
         return this.isMetaChar(ch) ? ch : -1;
      }
   }

   void trimTrailingSpaces(StringBuilder buffer) {
      int length;
      for(length = buffer.length(); length > 0 && Character.isWhitespace(buffer.charAt(length - 1)); --length) {
      }

      if (length != buffer.length()) {
         buffer.setLength(length);
      }

   }

   boolean readEndOfLine(int ch) throws IOException {
      if (ch == 13 && this.reader.lookAhead() == 10) {
         ch = this.reader.read();
      }

      return ch == 10 || ch == 13;
   }

   boolean isClosed() {
      return this.reader.isClosed();
   }

   boolean isWhitespace(int ch) {
      return !this.isDelimiter(ch) && Character.isWhitespace((char)ch);
   }

   boolean isStartOfLine(int ch) {
      return ch == 10 || ch == 13 || ch == -2;
   }

   boolean isEndOfFile(int ch) {
      return ch == -1;
   }

   boolean isDelimiter(int ch) {
      return ch == this.delimiter;
   }

   boolean isEscape(int ch) {
      return ch == this.escape;
   }

   boolean isQuoteChar(int ch) {
      return ch == this.quoteChar;
   }

   boolean isCommentStart(int ch) {
      return ch == this.commentStart;
   }

   private boolean isMetaChar(int ch) {
      return ch == this.delimiter || ch == this.escape || ch == this.quoteChar || ch == this.commentStart;
   }

   public void close() throws IOException {
      this.reader.close();
   }
}
