package lk.rythmo.userauth.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClient(name = "rythmo", configuration = RibbonClientConfiguration.class)
public class RibbonConfig {
}
