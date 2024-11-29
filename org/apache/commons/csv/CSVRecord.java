package org.apache.commons.csv;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class CSVRecord implements Serializable, Iterable<String> {
   private static final String[] EMPTY_STRING_ARRAY = new String[0];
   private static final long serialVersionUID = 1L;
   private final long characterPosition;
   private final String comment;
   private final Map<String, Integer> mapping;
   private final long recordNumber;
   private final String[] values;

   CSVRecord(String[] values, Map<String, Integer> mapping, String comment, long recordNumber, long characterPosition) {
      this.recordNumber = recordNumber;
      this.values = values != null ? values : EMPTY_STRING_ARRAY;
      this.mapping = mapping;
      this.comment = comment;
      this.characterPosition = characterPosition;
   }

   public String get(Enum<?> e) {
      return this.get(e.toString());
   }

   public String get(int i) {
      return this.values[i];
   }

   public String get(String name) {
      if (this.mapping == null) {
         throw new IllegalStateException("No header mapping was specified, the record values can't be accessed by name");
      } else {
         Integer index = (Integer)this.mapping.get(name);
         if (index == null) {
            throw new IllegalArgumentException(String.format("Mapping for %s not found, expected one of %s", name, this.mapping.keySet()));
         } else {
            try {
               return this.values[index];
            } catch (ArrayIndexOutOfBoundsException var4) {
               throw new IllegalArgumentException(String.format("Index for header '%s' is %d but CSVRecord only has %d values!", name, index, this.values.length));
            }
         }
      }
   }

   public long getCharacterPosition() {
      return this.characterPosition;
   }

   public String getComment() {
      return this.comment;
   }

   public long getRecordNumber() {
      return this.recordNumber;
   }

   public boolean isConsistent() {
      return this.mapping == null || this.mapping.size() == this.values.length;
   }

   public boolean isMapped(String name) {
      return this.mapping != null && this.mapping.containsKey(name);
   }

   public boolean isSet(String name) {
      return this.isMapped(name) && (Integer)this.mapping.get(name) < this.values.length;
   }

   public Iterator<String> iterator() {
      return this.toList().iterator();
   }

   <M extends Map<String, String>> M putIn(M map) {
      if (this.mapping == null) {
         return map;
      } else {
         Iterator i$ = this.mapping.entrySet().iterator();

         while(i$.hasNext()) {
            Entry<String, Integer> entry = (Entry)i$.next();
            int col = (Integer)entry.getValue();
            if (col < this.values.length) {
               map.put(entry.getKey(), this.values[col]);
            }
         }

         return map;
      }
   }

   public int size() {
      return this.values.length;
   }

   private List<String> toList() {
      return Arrays.asList(this.values);
   }

   public Map<String, String> toMap() {
      return this.putIn(new HashMap(this.values.length));
   }

   public String toString() {
      return "CSVRecord [comment=" + this.comment + ", mapping=" + this.mapping + ", recordNumber=" + this.recordNumber + ", values=" + Arrays.toString(this.values) + "]";
   }

   String[] values() {
      return this.values;
   }
}
