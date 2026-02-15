package com.content.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合工具类
 * 提供集合操作的常用方法，包括判断空、创建集合、转换集合等
 */
public class CollectionUtils {

    /**
     * 判断集合是否为空
     * @param collection 集合对象
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否非空
     * @param collection 集合对象
     * @return 是否非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断Map是否为空
     * @param map Map对象
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Map是否非空
     * @param map Map对象
     * @return 是否非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断数组是否为空
     * @param array 数组对象
     * @return 是否为空
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组是否非空
     * @param array 数组对象
     * @return 是否非空
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 获取集合大小
     * @param collection 集合对象
     * @return 集合大小
     */
    public static int size(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * 获取Map大小
     * @param map Map对象
     * @return Map大小
     */
    public static int size(Map<?, ?> map) {
        return map == null ? 0 : map.size();
    }

    /**
     * 获取数组大小
     * @param array 数组对象
     * @return 数组大小
     */
    public static int size(Object[] array) {
        return array == null ? 0 : array.length;
    }

    /**
     * 获取空List
     * @param <T> 元素类型
     * @return 空List
     */
    public static <T> List<T> emptyList() {
        return Collections.emptyList();
    }

    /**
     * 获取空Map
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 空Map
     */
    public static <K, V> Map<K, V> emptyMap() {
        return Collections.emptyMap();
    }

    /**
     * 获取空Set
     * @param <T> 元素类型
     * @return 空Set
     */
    public static <T> Set<T> emptySet() {
        return Collections.emptySet();
    }

    /**
     * 创建新的ArrayList
     * @param <T> 元素类型
     * @return 新的ArrayList
     */
    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * 创建指定初始容量的ArrayList
     * @param initialCapacity 初始容量
     * @param <T> 元素类型
     * @return 新的ArrayList
     */
    public static <T> List<T> newArrayList(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    /**
     * 创建包含指定元素的ArrayList
     * @param elements 元素数组
     * @param <T> 元素类型
     * @return 新的ArrayList
     */
    @SafeVarargs
    public static <T> List<T> newArrayList(T... elements) {
        List<T> list = new ArrayList<>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }

    /**
     * 创建新的HashSet
     * @param <T> 元素类型
     * @return 新的HashSet
     */
    public static <T> Set<T> newHashSet() {
        return new HashSet<>();
    }

    /**
     * 创建包含指定元素的HashSet
     * @param elements 元素数组
     * @param <T> 元素类型
     * @return 新的HashSet
     */
    @SafeVarargs
    public static <T> Set<T> newHashSet(T... elements) {
        Set<T> set = new HashSet<>(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    /**
     * 创建新的HashMap
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 新的HashMap
     */
    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * 创建指定初始容量的HashMap
     * @param initialCapacity 初始容量
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 新的HashMap
     */
    public static <K, V> Map<K, V> newHashMap(int initialCapacity) {
        return new HashMap<>(initialCapacity);
    }

    /**
     * 将Iterable转换为List
     * @param iterable 可迭代对象
     * @param <T> 元素类型
     * @return List对象
     */
    public static <T> List<T> toList(Iterable<T> iterable) {
        if (iterable == null) {
            return newArrayList();
        }
        List<T> list = newArrayList();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }

    /**
     * 将Iterable转换为Set
     * @param iterable 可迭代对象
     * @param <T> 元素类型
     * @return Set对象
     */
    public static <T> Set<T> toSet(Iterable<T> iterable) {
        if (iterable == null) {
            return newHashSet();
        }
        Set<T> set = newHashSet();
        for (T item : iterable) {
            set.add(item);
        }
        return set;
    }

    /**
     * 去重List
     * @param list 原始List
     * @param <T> 元素类型
     * @return 去重后的List
     */
    public static <T> List<T> distinct(List<T> list) {
        if (isEmpty(list)) {
            return newArrayList();
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 过滤List
     * @param list 原始List
     * @param predicate 过滤条件
     * @param <T> 元素类型
     * @return 过滤后的List
     */
    public static <T> List<T> filter(List<T> list, java.util.function.Predicate<T> predicate) {
        if (isEmpty(list)) {
            return newArrayList();
        }
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 转换List元素
     * @param list 原始List
     * @param mapper 转换函数
     * @param <T> 输入类型
     * @param <R> 输出类型
     * @return 转换后的List
     */
    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        if (isEmpty(list)) {
            return newArrayList();
        }
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 获取List第一个元素
     * @param list List对象
     * @param <T> 元素类型
     * @return 第一个元素
     */
    public static <T> T first(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取List最后一个元素
     * @param list List对象
     * @param <T> 元素类型
     * @return 最后一个元素
     */
    public static <T> T last(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * 获取List指定索引的元素
     * @param list List对象
     * @param index 索引
     * @param <T> 元素类型
     * @return 指定索引的元素
     */
    public static <T> T get(List<T> list, int index) {
        if (isEmpty(list) || index < 0 || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    /**
     * 获取List的子列表
     * @param list 原始List
     * @param fromIndex 开始索引
     * @param toIndex 结束索引
     * @param <T> 元素类型
     * @return 子列表
     */
    public static <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
        if (isEmpty(list)) {
            return newArrayList();
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        if (fromIndex >= toIndex) {
            return newArrayList();
        }
        return list.subList(fromIndex, toIndex);
    }

    /**
     * 反转List
     * @param list 原始List
     * @param <T> 元素类型
     * @return 反转后的List
     */
    public static <T> List<T> reverse(List<T> list) {
        if (isEmpty(list)) {
            return newArrayList();
        }
        List<T> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }

    /**
     * 打乱List顺序
     * @param list 原始List
     * @param <T> 元素类型
     * @return 打乱顺序后的List
     */
    public static <T> List<T> shuffle(List<T> list) {
        if (isEmpty(list)) {
            return newArrayList();
        }
        List<T> shuffled = new ArrayList<>(list);
        Collections.shuffle(shuffled);
        return shuffled;
    }

    /**
     * 排序List
     * @param list 原始List
     * @param comparator 比较器
     * @param <T> 元素类型
     * @return 排序后的List
     */
    public static <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
        if (isEmpty(list)) {
            return newArrayList();
        }
        List<T> sorted = new ArrayList<>(list);
        sorted.sort(comparator);
        return sorted;
    }

    /**
     * 检查集合是否包含指定元素
     * @param collection 集合对象
     * @param element 元素
     * @param <T> 元素类型
     * @return 是否包含
     */
    public static <T> boolean contains(Collection<T> collection, T element) {
        if (isEmpty(collection)) {
            return false;
        }
        return collection.contains(element);
    }

    /**
     * 检查集合是否包含任意指定元素
     * @param collection 集合对象
     * @param elements 元素集合
     * @param <T> 元素类型
     * @return 是否包含任意元素
     */
    public static <T> boolean containsAny(Collection<T> collection, Collection<T> elements) {
        if (isEmpty(collection) || isEmpty(elements)) {
            return false;
        }
        for (T element : elements) {
            if (collection.contains(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查集合是否包含所有指定元素
     * @param collection 集合对象
     * @param elements 元素集合
     * @param <T> 元素类型
     * @return 是否包含所有元素
     */
    public static <T> boolean containsAll(Collection<T> collection, Collection<T> elements) {
        if (isEmpty(collection) || isEmpty(elements)) {
            return false;
        }
        return collection.containsAll(elements);
    }

    /**
     * 获取两个集合的交集
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T> 元素类型
     * @return 交集
     */
    public static <T> List<T> intersection(Collection<T> collection1, Collection<T> collection2) {
        if (isEmpty(collection1) || isEmpty(collection2)) {
            return newArrayList();
        }
        List<T> result = newArrayList();
        for (T item : collection1) {
            if (collection2.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * 获取两个集合的并集
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T> 元素类型
     * @return 并集
     */
    public static <T> List<T> union(Collection<T> collection1, Collection<T> collection2) {
        Set<T> set = new HashSet<>();
        if (isNotEmpty(collection1)) {
            set.addAll(collection1);
        }
        if (isNotEmpty(collection2)) {
            set.addAll(collection2);
        }
        return new ArrayList<>(set);
    }

    /**
     * 获取两个集合的差集
     * @param collection1 第一个集合
     * @param collection2 第二个集合
     * @param <T> 元素类型
     * @return 差集
     */
    public static <T> List<T> difference(Collection<T> collection1, Collection<T> collection2) {
        if (isEmpty(collection1)) {
            return newArrayList();
        }
        List<T> result = new ArrayList<>(collection1);
        if (isNotEmpty(collection2)) {
            result.removeAll(collection2);
        }
        return result;
    }

    /**
     * 分割List
     * @param list 原始List
     * @param size 每个子List的大小
     * @param <T> 元素类型
     * @return 子List集合
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (isEmpty(list) || size <= 0) {
            return newArrayList();
        }
        List<List<T>> partitions = newArrayList();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }

    /**
     * 向集合添加元素集合
     * @param collection 目标集合
     * @param elements 元素集合
     * @param <T> 元素类型
     */
    public static <T> void addAll(Collection<T> collection, Collection<T> elements) {
        if (isEmpty(collection) || isEmpty(elements)) {
            return;
        }
        collection.addAll(elements);
    }

    /**
     * 向集合添加元素数组
     * @param collection 目标集合
     * @param elements 元素数组
     * @param <T> 元素类型
     */
    @SafeVarargs
    public static <T> void addAll(Collection<T> collection, T... elements) {
        if (isEmpty(collection) || elements == null || elements.length == 0) {
            return;
        }
        Collections.addAll(collection, elements);
    }

    /**
     * 从集合中移除元素
     * @param collection 目标集合
     * @param element 元素
     * @param <T> 元素类型
     * @return 是否移除成功
     */
    public static <T> boolean remove(Collection<T> collection, T element) {
        if (isEmpty(collection)) {
            return false;
        }
        return collection.remove(element);
    }

    /**
     * 从集合中移除元素集合
     * @param collection 目标集合
     * @param elements 元素集合
     * @param <T> 元素类型
     */
    public static <T> void removeAll(Collection<T> collection, Collection<T> elements) {
        if (isEmpty(collection) || isEmpty(elements)) {
            return;
        }
        collection.removeAll(elements);
    }

    /**
     * 保留集合中的指定元素
     * @param collection 目标集合
     * @param elements 要保留的元素
     * @param <T> 元素类型
     */
    public static <T> void retainAll(Collection<T> collection, Collection<T> elements) {
        if (isEmpty(collection) || isEmpty(elements)) {
            return;
        }
        collection.retainAll(elements);
    }

    /**
     * 清空集合
     * @param collection 目标集合
     * @param <T> 元素类型
     */
    public static <T> void clear(Collection<T> collection) {
        if (isNotEmpty(collection)) {
            collection.clear();
        }
    }

    /**
     * 将集合转换为数组
     * @param collection 目标集合
     * @param componentType 元素类型
     * @param <T> 元素类型
     * @return 数组
     */
    public static <T> T[] toArray(Collection<T> collection, Class<T> componentType) {
        if (isEmpty(collection)) {
            return (T[]) java.lang.reflect.Array.newInstance(componentType, 0);
        }
        return collection.toArray((T[]) java.lang.reflect.Array.newInstance(componentType, collection.size()));
    }

    /**
     * 获取Map中的值
     * @param map 目标Map
     * @param key 键
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 值
     */
    public static <K, V> V get(Map<K, V> map, K key) {
        if (isEmpty(map)) {
            return null;
        }
        return map.get(key);
    }

    /**
     * 获取Map中的值，如果不存在则返回默认值
     * @param map 目标Map
     * @param key 键
     * @param defaultValue 默认值
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 值
     */
    public static <K, V> V get(Map<K, V> map, K key, V defaultValue) {
        if (isEmpty(map)) {
            return defaultValue;
        }
        return map.getOrDefault(key, defaultValue);
    }

    /**
     * 检查Map是否包含指定键
     * @param map 目标Map
     * @param key 键
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 是否包含
     */
    public static <K, V> boolean containsKey(Map<K, V> map, K key) {
        if (isEmpty(map)) {
            return false;
        }
        return map.containsKey(key);
    }

    /**
     * 检查Map是否包含指定值
     * @param map 目标Map
     * @param value 值
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 是否包含
     */
    public static <K, V> boolean containsValue(Map<K, V> map, V value) {
        if (isEmpty(map)) {
            return false;
        }
        return map.containsValue(value);
    }

    /**
     * 获取Map的键集合
     * @param map 目标Map
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 键集合
     */
    public static <K, V> Set<K> keySet(Map<K, V> map) {
        if (isEmpty(map)) {
            return newHashSet();
        }
        return map.keySet();
    }

    /**
     * 获取Map的值集合
     * @param map 目标Map
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 值集合
     */
    public static <K, V> Collection<V> values(Map<K, V> map) {
        if (isEmpty(map)) {
            return newArrayList();
        }
        return map.values();
    }

    /**
     * 获取Map的Entry集合
     * @param map 目标Map
     * @param <K> 键类型
     * @param <V> 值类型
     * @return Entry集合
     */
    public static <K, V> Set<Map.Entry<K, V>> entrySet(Map<K, V> map) {
        if (isEmpty(map)) {
            return newHashSet();
        }
        return map.entrySet();
    }

    /**
     * 向Map添加另一个Map的所有元素
     * @param map 目标Map
     * @param other 另一个Map
     * @param <K> 键类型
     * @param <V> 值类型
     */
    public static <K, V> void putAll(Map<K, V> map, Map<K, V> other) {
        if (isEmpty(map) || isEmpty(other)) {
            return;
        }
        map.putAll(other);
    }

    /**
     * 从Map中移除指定键的元素
     * @param map 目标Map
     * @param key 键
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 移除的值
     */
    public static <K, V> V remove(Map<K, V> map, K key) {
        if (isEmpty(map)) {
            return null;
        }
        return map.remove(key);
    }

    /**
     * 清空Map
     * @param map 目标Map
     * @param <K> 键类型
     * @param <V> 值类型
     */
    public static <K, V> void clear(Map<K, V> map) {
        if (isNotEmpty(map)) {
            map.clear();
        }
    }

    /**
     * 过滤Map
     * @param map 目标Map
     * @param predicate 过滤条件
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 过滤后的Map
     */
    public static <K, V> Map<K, V> filter(Map<K, V> map, java.util.function.Predicate<Map.Entry<K, V>> predicate) {
        if (isEmpty(map)) {
            return newHashMap();
        }
        return map.entrySet().stream()
                .filter(predicate)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * 转换Map的值
     * @param map 目标Map
     * @param mapper 转换函数
     * @param <K> 键类型
     * @param <V> 原始值类型
     * @param <R> 转换后值类型
     * @return 转换后的Map
     */
    public static <K, V, R> Map<K, R> mapValues(Map<K, V> map, Function<V, R> mapper) {
        if (isEmpty(map)) {
            return newHashMap();
        }
        return map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> mapper.apply(entry.getValue())));
    }

    /**
     * 转换Map的键
     * @param map 目标Map
     * @param mapper 转换函数
     * @param <K> 原始键类型
     * @param <V> 值类型
     * @param <R> 转换后键类型
     * @return 转换后的Map
     */
    public static <K, V, R> Map<R, V> mapKeys(Map<K, V> map, Function<K, R> mapper) {
        if (isEmpty(map)) {
            return newHashMap();
        }
        return map.entrySet().stream()
                .collect(Collectors.toMap(entry -> mapper.apply(entry.getKey()), Map.Entry::getValue));
    }
}
