package com.jobportal.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "APNT TECHNOLABS",
						email = "apnt@gmail.com",
						url = "https://apnttechnolabs.com"
						),
				description = "OpeanApi Documentation for Job Portal",
				title = "Job Portal - APNT",
				version = "1.0",
				license = @License(
						name = "Apnt Technolabs",
						url = "https://apnttechnolabs.com"
						
						),
				termsOfService = "Terms and Conditions"
				),
			servers = {
					@Server(
						description = "Local Env",
						url = "http://localhost:9090"
			)
			},
			security = {
					@SecurityRequirement(
							name = "bearerAuth"
							)
			}
		)

@SecurityScheme(
	    name = "bearerAuth",
	    type = SecuritySchemeType.HTTP,
	    bearerFormat = "JWT",
	    in = SecuritySchemeIn.HEADER,
	    scheme = "bearer"
	)
public class OpeanAPIConfig {

}
