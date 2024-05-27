package umc.th.juinjang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class JuinjangApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuinjangApplication.class, args);
	}

}
