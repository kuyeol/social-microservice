package org.account.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MultivaluedMap < K, V >extends Map<K, List<V>>
{

  public default void putSingle(K key, V value) {
    List<V> list = createListInstance();
    list.add(value);
    put(key, list); // Just override with new List instance
  }

  public default void addAll(K key, V... newValues) {
    for (V value : newValues) {
      add(key, value);
    }
  }

  public default void addAll(K key, List<V> valueList) {
    for (V value : valueList) {
      add(key, value);
    }
  }

  public default void addFirst(K key, V value) {
    getList(key).add(0, value);
  }

  public default void add(K key, V value) {
    getList(key).add(value);
  }

  public default void addMultiple(K key, Collection<V> values) {
    getList(key).addAll(values);
  }

  public default V getFirst(K key) {
    return Optional.ofNullable(get(key)).filter(l -> !l.isEmpty()).map(l -> l.get(0)).orElse(null);
  }

  public default List<V> getList(K key) {
    return compute(key, (k, v) -> v != null ? v : createListInstance());
  }

  public default void addAll(MultivaluedMap<K, V> other) {
    for (Entry<K, List<V>> entry : other.entrySet()) {
      getList(entry.getKey()).addAll(entry.getValue());
    }
  }

  public default boolean equalsIgnoreValueOrder(MultivaluedMap<K, V> omap) {
    if (this == omap) {
      return true;
    }
    if (!keySet().equals(omap.keySet())) {
      return false;
    }
    for (Entry<K, List<V>> e : entrySet()) {
      List<V> list = e.getValue();
      List<V> olist = omap.get(e.getKey());
      if (!CollectionUtil.collectionEquals(list, olist)) {
        return false;
      }
    }
    return true;
  }

  public default List<V> createListInstance() {
    return new ArrayList<>();
  }
}
