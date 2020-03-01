package com.johnny.cs.alarm.configuration;

import com.johnny.cs.core.configuration.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:sixshop.yml", factory = YamlPropertySourceFactory.class)
public class SixshopConfiguration {

}
