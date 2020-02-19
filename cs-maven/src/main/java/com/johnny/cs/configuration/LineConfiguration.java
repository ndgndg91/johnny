package com.johnny.cs.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:line.yml", factory = YamlPropertySourceFactory.class)
public class LineConfiguration {
}
