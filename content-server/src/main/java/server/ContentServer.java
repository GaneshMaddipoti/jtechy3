package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import persistance.audit.AuditorAwareImpl;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = {"persistance.repository"})
@EntityScan(basePackages = {"domain.model"})
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
public class ContentServer extends SpringBootServletInitializer {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ContentServer.class);
    }


	public static void main(String[] args) {
		SpringApplication.run(ContentServer.class, args);
	}

}
