package com.csharma.socialmedia;


import com.csharma.socialmedia.repository.MediaRepository;
import com.csharma.socialmedia.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {UserRepository.class, MediaRepository.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
