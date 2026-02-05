package com.unveil.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Data
@Configuration
@ConfigurationProperties(prefix = "art.apis")
public class MuseumApiConfig {

    private MuseumConfig museum1 = new MuseumConfig();
    private MuseumConfig museum2 = new MuseumConfig();
    private MuseumConfig museum3 = new MuseumConfig();
    private MuseumConfig museum4 = new MuseumConfig();
    private MuseumConfig museum5 = new MuseumConfig();

    @Data
    public static class MuseumConfig {
        private String baseUrl;
        private String apiKey;

        /**
         * Check if this museum API is configured and ready to use
         */
        public boolean isConfigured() {
            return StringUtils.hasText(baseUrl);
        }

        /**
         * Check if this museum has an API key configured
         */
        public boolean hasApiKey() {
            return StringUtils.hasText(apiKey);
        }
    }
}
