package ca.gbc.comp3095.courseprogressservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseProgressServiceApplication {

    public static void main(String[] args) {

        System.out.println("CourseProgress service started!");

        SpringApplication.run(CourseProgressServiceApplication.class, args);
    }
}
