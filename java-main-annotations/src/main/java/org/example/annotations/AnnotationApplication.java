// AnnotationDemoApplication.java
package org.example.annotations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class annotated with @SpringBootApplication,
 * which includes @Configuration, @EnableAutoConfiguration, and @ComponentScan.
 */
@SpringBootApplication
public class AnnotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnnotationApplication.class, args);
    }
}
