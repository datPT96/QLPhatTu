package pt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QlPhatTuApplication {

    public static void main(String[] args) {
        SpringApplication.run(QlPhatTuApplication.class, args);
    }


}
