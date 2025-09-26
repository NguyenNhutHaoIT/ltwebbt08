package vn.iotstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import vn.iotstar.config.SiteInfoProperties;
import vn.iotstar.storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, SiteInfoProperties.class}) 
public class SpringbootB8Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootB8Application.class, args);
	}
}


