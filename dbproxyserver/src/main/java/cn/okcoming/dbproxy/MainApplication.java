package cn.okcoming.dbproxy;

import cn.okcoming.dbproxy.util.DBUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author bluces
 */
@EnableAsync
@SpringBootApplication
//@EnableConfigurationProperties({DBUtils.class})
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
