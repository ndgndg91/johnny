package com.johnny.cs.line.configuration;

import com.johnny.cs.core.configuration.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:line.yml", factory = YamlPropertySourceFactory.class)
public class LineConfiguration {
}
