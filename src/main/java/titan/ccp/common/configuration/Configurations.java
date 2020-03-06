package titan.ccp.common.configuration;

import org.apache.commons.configuration2.Configuration;

/**
 * Use {@link ServiceConfigurations} instead.
 */
@Deprecated
public final class Configurations {

  /**
   * Use {@link ServiceConfigurations} instead.
   */
  @Deprecated
  public static final Configuration create() {
    return ServiceConfigurations.createWithDefaults();
  }

}
