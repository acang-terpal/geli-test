package main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Hasan
 */
@SpringBootApplication
@ComponentScan(basePackages = {"rest_resource", "configuration", "router", "controller", "implement", "helper", "service"})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan("entity")
public class Main {
    public static String workDir;
    @Value("${http.port}")
    private static String httpPort;

    public static void main(String[] args) throws Exception {
        //---------------------------springboot-------------------------------------
        HashMap<String, Object> props = new HashMap<>();
        props.put("spring.application.name", "Geli");
        props.put("server.ssl.enabled", false);
        props.put("server.compression.enabled", true);
        props.put("server.compression.mime-types", "application/json, application/xml, text/css, text/html, text/xml, text/plain, text/javascript, application/javascript, application/font-woff2, application/x-font-woff, font/woff2, application/octet-stream, image/x-icon, image/bmp, image/jpeg, image/jpg, image/png");
        props.put("server.compression.min-response-size", 1024); //Compress the response only if the response size is at least 1KB (value 1024 = 1Kb)
        SpringApplication springApps = new SpringApplicationBuilder()
                .sources(Main.class)
                .properties(props)
                .build();
        ConfigurableApplicationContext server = springApps.run(args);

        ConfigurableEnvironment env = server.getEnvironment();
        System.out.println("Server Ready At : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
