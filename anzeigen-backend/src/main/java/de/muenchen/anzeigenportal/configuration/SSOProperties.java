package de.muenchen.anzeigenportal.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "sso")
@Validated
public record SSOProperties(String client) {
}
