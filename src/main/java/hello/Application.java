package hello;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
public class Application implements WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		/*
		 * FilterRegistration.Dynamic fr = servletContext.addFilter(
		 * "encodingFilter", new CharacterEncodingFilter());
		 * fr.setInitParameter("encoding", "utf-8");
		 * fr.setInitParameter("forceEncoding", "true");
		 * fr.addMappingForUrlPatterns(null, true, "/*");
		 */

	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {

			@Override
			public void customize(ConfigurableEmbeddedServletContainer factory) {
				if (factory instanceof TomcatEmbeddedServletContainerFactory) {
					TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
					containerFactory
							.addConnectorCustomizers(new TomcatConnectorCustomizer() {

								@Override
								public void customize(Connector connector) {
									connector.setAttribute(
											"acceptorThreadCount", 2);
									connector.setAttribute("maxThreads", 500);

								}
							});
				}
			}
		};
	}

}
