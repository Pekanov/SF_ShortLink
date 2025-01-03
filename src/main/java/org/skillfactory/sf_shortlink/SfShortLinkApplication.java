package org.skillfactory.sf_shortlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SfShortLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SfShortLinkApplication.class, args);
    }

}
