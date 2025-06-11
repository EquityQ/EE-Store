package org.example.javawebv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class JavaWebV2Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(JavaWebV2Application.class, args);
		System.out.println("应用启动在：http://localhost:8081");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 配置 Swagger UI 资源路径
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/5.2.0/");
	}

}
