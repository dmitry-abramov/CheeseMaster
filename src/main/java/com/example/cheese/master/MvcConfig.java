package com.example.cheese.master;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home.html");
		registry.addViewController("/").setViewName("home.html");
		registry.addViewController("/login").setViewName("login");
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
	    var templateEngine = new SpringTemplateEngine();
	    templateEngine.addTemplateResolver(templateResolver);
	    templateEngine.addDialect(new LayoutDialect());
	    return templateEngine;
	}
	
	@Bean
	public ViewResolver htmlViewResolver(ApplicationContext applicationContext) {
	    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	    resolver.setTemplateEngine(templateEngine(htmlTemplateResolver(applicationContext)));
	    resolver.setContentType("text/html");
	    resolver.setCharacterEncoding("UTF-8");
	    resolver.setViewNames(new String[] {"*.html"});
	    return resolver;
	}

	private ITemplateResolver htmlTemplateResolver(ApplicationContext applicationContext) {
	    SpringResourceTemplateResolver resolver
	      = new SpringResourceTemplateResolver();
	    resolver.setApplicationContext(applicationContext);
	    resolver.setPrefix("/WEB-INF/views/");
	    resolver.setCacheable(false);
	    resolver.setTemplateMode(TemplateMode.HTML);
	    return resolver;
	}
}