package org.acme.account.constants;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classes implementing this interface guarantee that for each instance of this class,
 * there exists an mutually unique integer which is stable in time, and identifies
 * always the same instance of this class.
 * The index might be used for persistence, hence the index of a particular item
 * cannot be changed.
 * This is mostly usable for @{code enum}s.
 */
public interface EnumWithStableIndex {
  /**
   * @return Unique numeric index which is stable in time and identifies an instance.
   *   Reusing the same index for two distinct entries of the same class is forbidden even
   *   if they cannot exist at the same time (e.g. one is deleted before other is introduced).
   */
  public int getStableIndex();

  public static <E extends EnumWithStableIndex> Map<Integer, E> getReverseIndex(E[] values) {
    return Stream.of(values).collect(Collectors.toMap(EnumWithStableIndex::getStableIndex, Function.identity()));
  }
}