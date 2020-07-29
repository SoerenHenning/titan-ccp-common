package titan.ccp.common.cassandra;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link PrimaryKeySelectionStrategy} that requires explicit registration of partition keys and
 * clustering columns.
 */
public class ExplicitPrimaryKeySelectionStrategy implements PrimaryKeySelectionStrategy {

  private final Map<String, List<String>> partitionKeys = new HashMap<>();

  private final Map<String, List<String>> clusteringColumns = new HashMap<>();

  private final PrimaryKeySelectionStrategy fallbackStrategy;

  public ExplicitPrimaryKeySelectionStrategy() {
    this.fallbackStrategy = new TakeLoggingTimestampStrategy();
  }

  public ExplicitPrimaryKeySelectionStrategy(final PrimaryKeySelectionStrategy fallbackStrategy) {
    this.fallbackStrategy = fallbackStrategy;
  }

  @Override
  public List<String> selectPartitionKeys(final String tableName,
      final List<String> possibleColumns) {
    final List<String> partionKeys = this.partitionKeys.get(tableName);
    if (partionKeys == null) {
      return this.fallbackStrategy.selectPartitionKeys(tableName, possibleColumns);
    } else {
      return partionKeys;
    }
  }

  @Override
  public List<String> selectClusteringColumns(final String tableName,
      final List<String> possibleColumns) {
    final List<String> clusteringColumns = this.clusteringColumns.get(tableName);
    if (clusteringColumns == null) {
      return this.fallbackStrategy.selectClusteringColumns(tableName, possibleColumns);
    } else {
      return clusteringColumns;
    }
  }

  public void registerPartitionKeys(final String tableName, final String... partitionKeys) {
    // this.partitionKeys.put(tableName, List.of(partitionKeys)); // Java 9
    this.partitionKeys.put(tableName, ImmutableList.copyOf(partitionKeys));
  }

  public void registerPartitionKeys(final String tableName,
      final Collection<String> partitionKeys) {
    // this.partitionKeys.put(tableName, List.copyOf(partitionKeys)); // Java 10
    this.partitionKeys.put(tableName, ImmutableList.copyOf(partitionKeys));
  }

  public void registerClusteringColumns(final String tableName, final String... clusteringColumns) {
    // this.clusteringColumns.put(tableName, List.of(clusteringColumns)); // Java 9
    this.clusteringColumns.put(tableName, ImmutableList.copyOf(clusteringColumns));
  }

  public void registerClusteringColumns(final String tableName,
      final Collection<String> clusteringColumns) {
    // this.clusteringColumns.put(tableName, List.copyOf(clusteringColumns)); // Java 10
    this.clusteringColumns.put(tableName, ImmutableList.copyOf(clusteringColumns));
  }

}
