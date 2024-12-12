package som;
// Source code is decompiled from a .class file using FernFlower decompiler.


import java.lang.reflect.Array;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Arrays {
   private static final int MIN_ARRAY_SORT_GRAN = 8192;
   private static final int INSERTIONSORT_THRESHOLD = 7;

   private Arrays() {
   }


   public static <T> void setAll(T[] array, IntFunction<? extends T> generator) {
      Objects.requireNonNull(generator);

      for(int i = 0; i < array.length; ++i) {
         array[i] = generator.apply(i);
      }

   }

   public static <T> void parallelSetAll(T[] array, IntFunction<? extends T> generator) {
      Objects.requireNonNull(generator);
      IntStream.range(0, array.length).parallel().forEach((i) -> {
         array[i] = generator.apply(i);
      });
   }

   public static void setAll(int[] array, IntUnaryOperator generator) {
      Objects.requireNonNull(generator);

      for(int i = 0; i < array.length; ++i) {
         array[i] = generator.applyAsInt(i);
      }

   }

   public static void parallelSetAll(int[] array, IntUnaryOperator generator) {
      Objects.requireNonNull(generator);
      IntStream.range(0, array.length).parallel().forEach((i) -> {
         array[i] = generator.applyAsInt(i);
      });
   }

   public static void setAll(long[] array, IntToLongFunction generator) {
      Objects.requireNonNull(generator);

      for(int i = 0; i < array.length; ++i) {
         array[i] = generator.applyAsLong(i);
      }

   }

   public static void parallelSetAll(long[] array, IntToLongFunction generator) {
      Objects.requireNonNull(generator);
      IntStream.range(0, array.length).parallel().forEach((i) -> {
         array[i] = generator.applyAsLong(i);
      });
   }

   public static void setAll(double[] array, IntToDoubleFunction generator) {
      Objects.requireNonNull(generator);

      for(int i = 0; i < array.length; ++i) {
         array[i] = generator.applyAsDouble(i);
      }

   }

   public static void parallelSetAll(double[] array, IntToDoubleFunction generator) {
      Objects.requireNonNull(generator);
      IntStream.range(0, array.length).parallel().forEach((i) -> {
         array[i] = generator.applyAsDouble(i);
      });
   }

}
