package sugar;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 增加一点Java☕的甜度
 *
 * @author cbdyzj
 * @since 2020.8.4
 */
public abstract class Sugar {

    public static void println(Object o) {
        System.out.println(o);
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    public static <T> void with(T target, @NotNull Sugar.ConsumerThrowsException<T> block) {
        if (target != null) {
            invoke(() -> block.invoke(target));
        }
    }

    public static <T extends Closeable> void use(T t, @NotNull Sugar.ActionThrowsException block) {
        invoke(() -> {
            try (T _ = t) {
                block.invoke();
            }
        });
    }

    public static <T1 extends Closeable, T2 extends Closeable> void use(T1 t1, T2 t2, @NotNull Sugar.ActionThrowsException block) {
        invoke(() -> {
            try (T1 _1 = t1; T2 _2 = t2) {
                block.invoke();
            }
        });
    }

    public static <L extends Lock> void use(L l, @NotNull Sugar.ActionThrowsException block) {
        l.lock();
        try {
            invoke(block);
        } finally {
            l.unlock();
        }
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }


    public static <T> List<T> toList(@NotNull Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public static <T, R> List<R> map(Collection<T> list, @NotNull Function<? super T, ? extends R> mapper) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        } else {
            return list.stream().map(mapper).collect(Collectors.toList());
        }
    }

    public static <T> void forEach(Collection<T> list, @NotNull Consumer<? super T> action) {
        if (!isEmpty(list)) {
            list.forEach(action);
        }
    }

    public static <T> @Nullable T findFirst(@Nullable Collection<T> list, @NotNull Predicate<? super T> predicate) {
        if (isEmpty(list)) {
            return null;
        } else {
            Optional<T> ot = list.stream().filter(predicate).findFirst();
            return ot.orElse(null);
        }
    }

    public static <T> boolean every(@Nullable Collection<T> list, @NotNull Predicate<? super T> predicate) {
        if (!isEmpty(list)) {
            return list.stream().allMatch(predicate);
        } else {
            return false;
        }
    }

    public static <T> List<T> distinct(Collection<T> list, @NotNull Function<? super T, ?> keyExtractor) {
        Set<Object> seen = new HashSet<>();
        return filter(list, (it) -> {
            Object key = keyExtractor.apply(it);
            if (seen.contains(key)) {
                return false;
            } else {
                seen.add(key);
                return true;
            }
        });
    }

    public static <T> List<T> distinct(Collection<T> list) {
        if (!isEmpty(list)) {
            return list.stream().distinct().collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public static <T> List<T> filter(Collection<T> list, @NotNull Predicate<? super T> predicate) {
        if (!isEmpty(list)) {
            return list.stream().filter(predicate).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public static <T, R> @NotNull R reduce(@Nullable Collection<T> list, @NotNull R identity, @NotNull BiFunction<R, ? super T, R> accumulator) {
        if (!isEmpty(list)) {
            return list.stream().reduce(identity, accumulator, (a, c) -> null);
        } else {
            return identity;
        }
    }

    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be positive integer");
        }
        if (!isEmpty(list)) {
            List<List<T>> result = new ArrayList<>(list.size() / size + 1);
            List<T> currentList = null;
            for (int i = 0; i < list.size(); i++) {
                if (i % size == 0) {
                    currentList = new ArrayList<>(size);
                    result.add(currentList);
                }
                currentList.add(list.get(i));
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    public static <T, K> Map<K, T> toMap(Collection<T> list, @NotNull Function<? super T, ? extends K> keyExtractor) {
        return toMap(list, keyExtractor, Function.identity());
    }

    public static <T, K, V> Map<K, V> toMap(Collection<T> list,
                                            @NotNull Function<? super T, ? extends K> keyExtractor,
                                            @NotNull Function<? super T, ? extends V> valueExtractor) {
        if (!isEmpty(list)) {
            return list.stream().collect(Collectors.toMap(keyExtractor, valueExtractor));
        } else {
            return new HashMap<>();
        }
    }

    public static <T, K> Map<K, List<T>> groupToMap(Collection<T> list, @NotNull Function<? super T, ? extends K> keyExtractor) {
        if (!isEmpty(list)) {
            return list.stream().collect(Collectors.groupingBy(keyExtractor));
        } else {
            return new HashMap<>();
        }
    }

    public static <T, K, V> Map<K, List<V>> groupToMap(Collection<T> list,
                                                       @NotNull Function<? super T, ? extends K> keyExtractor,
                                                       @NotNull Function<? super T, ? extends V> valueExtractor) {
        if (!isEmpty(list)) {
            return list.stream().collect(
                    Collectors.groupingBy(keyExtractor, Collectors.mapping(valueExtractor, Collectors.toList())));
        } else {
            return new HashMap<>();
        }
    }

    public static <T> boolean includes(Collection<T> list, @NotNull Predicate<? super T> predicate) {
        if (!isEmpty(list)) {
            return list.stream().anyMatch(predicate);
        } else {
            return false;
        }
    }

    public static <T> T[] ref(@NotNull T t) {
        T[] ref = cast(Array.newInstance(t.getClass(), 1));
        ref[0] = t;
        return ref;
    }

    public static Object[] ref() {
        return new Object[]{null};
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        return (T) o;
    }

    public interface ActionThrowsException {

        void invoke() throws Throwable;
    }

    public interface ConsumerThrowsException<T> {

        void invoke(T target) throws Throwable;
    }

    public static void repeat(int times, @NotNull Sugar.ActionThrowsException block) {
        if (times > 0) {
            for (int i = 0; i < times; i++) {
                invoke(block);
            }
        } else {
            throw new IllegalArgumentException("times requires positive integer");
        }
    }

    private static void invoke(ActionThrowsException action) {
        try {
            action.invoke();
        } catch (Throwable ex) {
            sneakyThrow(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void sneakyThrow(Throwable ex) throws E {
        throw (E) ex;
    }
}
