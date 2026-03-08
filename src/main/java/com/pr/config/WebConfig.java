package com.pr.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${employee.profile.images-dir}")
	private String imagesDir;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get(imagesDir).toAbsolutePath().normalize();
		String uploadPath = uploadDir.toUri().toString();

		registry.addResourceHandler("/profile-images/**").addResourceLocations(uploadPath);
	}
}

