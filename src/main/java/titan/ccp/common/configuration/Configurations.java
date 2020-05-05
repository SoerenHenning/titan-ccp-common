package titan.ccp.common.configuration;

import org.apache.commons.configuration2.Configuration;

/**
 * Legacy class for {@link ServiceConfigurations}.
 *
 * @deprecated Use {@link ServiceConfigurations} instead
 */
@Deprecated
public final class Configurations {

  private Configurations() {}

  /**
   * Legacy factory method for {@code ServiceConfigurations.createWithDefaults()}.
   *
   * @deprecated Use {@link ServiceConfigurations} instead
   */
  @Deprecated
  public static Configuration create() {
    return ServiceConfigurations.createWithDefaults();
  }

}
