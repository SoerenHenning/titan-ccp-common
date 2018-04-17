package titan.ccp.common.kieker.cassandra;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class ExplicitPrimaryKeySelectionStrategy implements PrimaryKeySelectionStrategy {

	private final Map<String, Set<String>> partitionKeys = new HashMap<>();

	private final Map<String, Set<String>> clusteringColumns = new HashMap<>();

	private final PrimaryKeySelectionStrategy fallbackStrategy;

	public ExplicitPrimaryKeySelectionStrategy() {
		this.fallbackStrategy = new TakeLoggingTimestampStrategy();
	}

	public ExplicitPrimaryKeySelectionStrategy(final PrimaryKeySelectionStrategy fallbackStrategy) {
		this.fallbackStrategy = fallbackStrategy;
	}

	@Override
	public Set<String> selectPartitionKeys(final String tableName, final List<String> possibleColumns) {
		final Set<String> partionKeys = this.partitionKeys.get(tableName);
		if (partionKeys == null) {
			return this.fallbackStrategy.selectPartitionKeys(tableName, possibleColumns);
		} else {
			return partionKeys;
		}
	}

	@Override
	public Set<String> selectClusteringColumns(final String tableName, final List<String> possibleColumns) {
		final Set<String> clusteringColumns = this.clusteringColumns.get(tableName);
		if (clusteringColumns == null) {
			return this.fallbackStrategy.selectClusteringColumns(tableName, possibleColumns);
		} else {
			return clusteringColumns;
		}
	}

	public void registerPartitionKeys(final String tableName, final String... partitionKeys) {
		// this.partitionKeys.put(tableName, Set.of(partitionKeys)); // Java 9
		this.partitionKeys.put(tableName, ImmutableSet.copyOf(partitionKeys));
	}

	public void registerPartitionKeys(final String tableName, final Collection<String> partitionKeys) {
		// this.partitionKeys.put(tableName, Set.copyOf(partitionKeys)); // Java 10
		this.partitionKeys.put(tableName, ImmutableSet.copyOf(partitionKeys));
	}

	public void registerClusteringColumns(final String tableName, final String... clusteringColumns) {
		// this.clusteringColumns.put(tableName, Set.of(clusteringColumns)); // Java 9
		this.clusteringColumns.put(tableName, ImmutableSet.copyOf(clusteringColumns));
	}

	public void registerClusteringColumns(final String tableName, final Collection<String> clusteringColumns) {
		// this.clusteringColumns.put(tableName, Set.copyOf(clusteringColumns)); // Java
		// 10
		this.clusteringColumns.put(tableName, ImmutableSet.copyOf(clusteringColumns));
	}

}
