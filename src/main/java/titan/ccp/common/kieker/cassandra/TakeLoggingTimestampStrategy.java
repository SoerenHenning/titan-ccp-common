package titan.ccp.common.kieker.cassandra;

import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TakeLoggingTimestampStrategy implements PrimaryKeySelectionStrategy {

  private static final String LOGGING_TIMESTAMP_COLUMN = "loggingTimestamp";

  @Override
  public Set<String> selectPartitionKeys(final String tableName,
      final List<String> possibleColumns) {
    if (possibleColumns.contains(LOGGING_TIMESTAMP_COLUMN)) {
      return ImmutableSet.of(LOGGING_TIMESTAMP_COLUMN);
    } else {
      throw new IllegalArgumentException("There is no column 'loggingTimestamp'");
    }
  }

  @Override
  public Set<String> selectClusteringColumns(final String tableName,
      final List<String> possibleColumns) {
    return Collections.emptySet();
  }

}
