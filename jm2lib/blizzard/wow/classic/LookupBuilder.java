package jm2lib.blizzard.wow.classic;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jm2lib.blizzard.common.types.ArrayRef;
import jm2lib.blizzard.wow.M2Format;
import org.apache.commons.csv.CSVRecord;

public final class LookupBuilder {
   public static short[] fallback;
   private static final int ID_COLUMN = 0;
   private static final int FALLBACK_COLUMN = 3;
   private static final short NUMBER_OF_ACTIONS = 226;
   private static Set<Short> playThenStop;
   private static Set<Short> playBackwards;
   private static final short PLAY_THEN_STOP = 3;
   private static final short PLAY_BACKWARDS = 1;
   private static final short DEAD = 6;
   private static final short SIT_GROUND = 97;
   private static final short SLEEP = 100;
   private static final short KNEEL_LOOP = 115;
   private static final short USE_STANDING_LOOP = 123;
   private static final short DROWNED = 132;
   private static final short LOOT_HOLD = 188;
   private static final short WALK_BACKWARDS = 13;
   private static final short SWIM_BACKWARDS = 45;
   private static final short SLEEP_UP = 101;
   private static final short LOOT_UP = 189;

   static {
      try {
         List<CSVRecord> lines = M2Format.openCSV("AnimationData");
         int maxID = Integer.parseInt(((CSVRecord)lines.get(lines.size() - 1)).get(0));
         fallback = new short[maxID + 1];

         for(int i = 1; i < lines.size(); ++i) {
            fallback[Integer.parseInt(((CSVRecord)lines.get(i)).get(0))] = Short.parseShort(((CSVRecord)lines.get(i)).get(3));
         }

         fallback[146] = 0;
         fallback[375] = 0;
      } catch (IOException var3) {
         System.err.println("Library bugged : Missing DBC file");
         var3.printStackTrace();
         System.exit(1);
      }

      playThenStop = new HashSet(Arrays.asList(Short.valueOf((short)6), Short.valueOf((short)97), Short.valueOf((short)100), Short.valueOf((short)115), Short.valueOf((short)123), 132, 188));
      playBackwards = new HashSet(Arrays.asList(Short.valueOf((short)13), Short.valueOf((short)45), Short.valueOf((short)101), 189));
   }

   private LookupBuilder() {
   }

   private static short getRealID(short id, ArrayRef<Short> animationLookup) {
      return id < animationLookup.size() && (Short)animationLookup.get(id) > -1 ? id : getRealID(fallback[id], animationLookup);
   }

   public static ArrayRef<Short> buildAnimLookup(ArrayRef<Animation> animations) {
      Short[] ids = new Short[animations.size()];

      for(int i = 0; i < animations.size(); ++i) {
         ids[i] = ((Animation)animations.get(i)).animationID;
      }

      return buildLookup(ids);
   }

   public static ArrayRef<Short> buildLookup(Short[] ids) {
      ArrayRef<Short> lookup = new ArrayRef(Short.TYPE);
      short maxID = (Short)Arrays.stream(ids).max(Short::compare).get();

      for(int i = 0; i < maxID + 1; ++i) {
         lookup.add(Short.valueOf((short)-1));
      }

      for(short i = 0; i < ids.length; ++i) {
         if ((Short)lookup.get(ids[i]) == -1) {
            lookup.set(ids[i], i);
         }
      }

      return lookup;
   }

   public static ArrayRef<PlayableRecord> buildPlayAnimLookup(ArrayRef<Short> animationLookup) {
      ArrayRef<PlayableRecord> lookup = new ArrayRef(PlayableRecord.class);

      for(short i = 0; i < 226; ++i) {
         PlayableRecord record = new PlayableRecord(getRealID(i, animationLookup), (short)0);
         if (record.fallbackID != i) {
            if (playThenStop.contains(i)) {
               record.flags = 3;
            } else if (playBackwards.contains(i)) {
               record.flags = 1;
            }
         }

         lookup.add(record);
      }

      return lookup;
   }
}
