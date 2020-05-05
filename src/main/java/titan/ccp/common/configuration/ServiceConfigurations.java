package titan.ccp.common.configuration;

import com.google.common.io.Resources;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ServiceConfigurations {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfigurations.class);

  private static final String DEFAULT_PROPERTIES_LOCATION = "META-INF/application.properties";
  private static final String USER_PROPERTIES_LOCATION = "config/application.properties";

  private ServiceConfigurations() {}

  public static Configuration createWithDefaults() {
    return builder()
        .withEnvironmentVariables()
        .withUserConfigurationFile(USER_PROPERTIES_LOCATION)
        .withDefaultConfigurationFile(DEFAULT_PROPERTIES_LOCATION)
        .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final CompositeConfiguration configuration = new CompositeConfiguration();

    private Builder() {}

    public Builder withEnvironmentVariables() {
      this.configuration.addConfiguration(new NameResolvingEnvironmentConfiguration());
      return this;
    }

    public Builder withUserConfigurationFile(final String userPropertiesLocation) {
      final Path path = Paths.get(userPropertiesLocation);
      LOGGER.info("Looking for user configuration at {}", userPropertiesLocation);
      if (Files.exists(path)) {
        LOGGER.info("Found user configuration at {}", userPropertiesLocation);
        try {
          this.configuration.addConfiguration(configurations().properties(path.toFile()));
        } catch (final ConfigurationException e) {
          throw new IllegalArgumentException(
              "Cannot load configuration from file '" + userPropertiesLocation + "'", e);
        }
      } else {
        LOGGER.info("No user configuration found at {}", userPropertiesLocation);
      }
      return this;
    }

    public Builder withDefaultConfigurationFile(final String defaultPropertiesLocation) {
      if (resourceExists(defaultPropertiesLocation)) {
        try {
          this.configuration
              .addConfiguration(configurations().properties(defaultPropertiesLocation));
        } catch (final ConfigurationException e) {
          throw new IllegalArgumentException(
              "Cannot load configuration from ressource '" + defaultPropertiesLocation + "'", e);
        }
      }
      return this;
    }

    public Configuration build() {
      return this.configuration;
    }

  }

  /**
   * Shortcut for long class name.
   */
  private static org.apache.commons.configuration2.builder.fluent.Configurations configurations() {
    // TODO Refactor when Configurations class is removed
    return new org.apache.commons.configuration2.builder.fluent.Configurations();
  }

  private static boolean resourceExists(final String resourceName) {
    try {
      Resources.getResource(resourceName);
    } catch (final IllegalArgumentException e) {
      return false;
    }
    return true;
  }

}
