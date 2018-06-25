package br.com.academiadev.reembolsoazul.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apis() {
		return new Docket(DocumentationType.SWAGGER_2).select()//
				.apis(RequestHandlerSelectors.basePackage("br.com.academiadev.reembolsoazul"))//
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		String descricao = "API com todos os endpoints utilizados na aplicacao ReembolsoAzul";
		return new ApiInfo("API ReembolsoAzul", descricao, "v1", "termos", null, "License", "MIT License",
				new ArrayList<>());
	}

}
