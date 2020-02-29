package titan.ccp.common.configuration;

import org.apache.commons.configuration2.Configuration;

/**
 * Use {@link ServiceConfiguration} instead.
 */
@Deprecated
public final class Configurations {

  /**
   * Use {@link ServiceConfiguration} instead.
   */
  @Deprecated
  public static final Configuration create() {
    return ServiceConfiguration.createWithDefaults();
  }

}
