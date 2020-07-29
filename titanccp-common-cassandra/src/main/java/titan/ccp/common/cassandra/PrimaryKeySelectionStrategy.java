package titan.ccp.common.cassandra;

import java.util.List;

/**
 * Provides methods to determine the partition keys and clustering columns for Cassandra tables
 * based on the table name and all available columns.
 */
public interface PrimaryKeySelectionStrategy {

  List<String> selectPartitionKeys(String tableName, List<String> possibleColumns);

  List<String> selectClusteringColumns(String tableName, List<String> possibleColumns);

}
