package lk.rythmo.userauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableDiscoveryClient
@EnableTransactionManagement
public class UserauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserauthApplication.class, args);
    }

}
