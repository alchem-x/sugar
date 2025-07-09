package sugar;

import java.util.*;

public abstract class SugarExtra {
    @SafeVarargs
    public static <T> List<T> listOf(T... ts) {
        return new ArrayList<>(Arrays.asList(ts));
    }

    @SafeVarargs
    public static <T> Set<T> setOf(T... ts) {
        return new HashSet<>(Arrays.asList(ts));
    }

    public static <K, V> Map<K, V> mapOf() {
        return new HashMap<>();
    }

    public static <K, V> Map<K, V> mapOf(K k, V v) {
        return buildMap(k, v);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1,
                                         K k2, V v2) {
        return buildMap(k1, v1, k2, v2);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1,
                                         K k2, V v2,
                                         K k3, V v3) {
        return buildMap(k1, v1, k2, v2, k3, v3);
    }


    public static <K, V> Map<K, V> mapOf(K k1, V v1,
                                         K k2, V v2,
                                         K k3, V v3,
                                         K k4, V v4) {
        return buildMap(k1, v1, k2, v2, k3, v3, k4, v4);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1,
                                         K k2, V v2,
                                         K k3, V v3,
                                         K k4, V v4,
                                         K k5, V v5) {
        return buildMap(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1,
                                         K k2, V v2,
                                         K k3, V v3,
                                         K k4, V v4,
                                         K k5, V v5,
                                         K k6, V v6) {
        return buildMap(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1,
                                         K k2, V v2,
                                         K k3, V v3,
                                         K k4, V v4,
                                         K k5, V v5,
                                         K k6, V v6,
                                         K k7, V v7) {
        return buildMap(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1,
                                         K k2, V v2,
                                         K k3, V v3,
                                         K k4, V v4,
                                         K k5, V v5,
                                         K k6, V v6,
                                         K k7, V v7,
                                         K k8, V v8) {
        return buildMap(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1,
                                         K k2, V v2,
                                         K k3, V v3,
                                         K k4, V v4,
                                         K k5, V v5,
                                         K k6, V v6,
                                         K k7, V v7,
                                         K k8, V v8,
                                         K k9, V v9) {
        return buildMap(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1,
                                         K k2, V v2,
                                         K k3, V v3,
                                         K k4, V v4,
                                         K k5, V v5,
                                         K k6, V v6,
                                         K k7, V v7,
                                         K k8, V v8,
                                         K k9, V v9,
                                         K k10, V v10) {
        return buildMap(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10);
    }

    private static <K, V> Map<K, V> buildMap(Object... input) {
        if ((input.length & 1) == 0) {
            Map<Object, Object> map = new HashMap<>();
            for (int i = 0; i < input.length; i += 2) {
                map.put(input[i], input[i + 1]);
            }
            return Sugar.cast(map);
        } else {
            throw new IllegalArgumentException("length is odd");
        }
    }
}
