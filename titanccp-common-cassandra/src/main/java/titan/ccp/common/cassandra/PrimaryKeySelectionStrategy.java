package titan.ccp.common.cassandra;

import java.util.List;

public interface PrimaryKeySelectionStrategy {

  public List<String> selectPartitionKeys(String tableName, List<String> possibleColumns);

  public List<String> selectClusteringColumns(String tableName, List<String> possibleColumns);

}
