package com.johnny.cs.core.aws;

import com.johnny.cs.core.configuration.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:aws.yml", factory = YamlPropertySourceFactory.class)
public class AwsConfiguration {

}
