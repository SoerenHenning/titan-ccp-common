package titan.ccp.common.cassandra;

import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;

public class TakeLoggingTimestampStrategy implements PrimaryKeySelectionStrategy {

  private static final String LOGGING_TIMESTAMP_COLUMN = "loggingTimestamp";

  @Override
  public List<String> selectPartitionKeys(final String tableName,
      final List<String> possibleColumns) {
    if (possibleColumns.contains(LOGGING_TIMESTAMP_COLUMN)) {
      return ImmutableList.of(LOGGING_TIMESTAMP_COLUMN);
    } else {
      throw new IllegalArgumentException("There is no column 'loggingTimestamp'");
    }
  }

  @Override
  public List<String> selectClusteringColumns(final String tableName,
      final List<String> possibleColumns) {
    return Collections.emptyList();
  }

}
