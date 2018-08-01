import beans.configuration.views.JsonViewResolver;
import beans.daos.UserAccountRepository;
import beans.services.security.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
        scanBasePackages = {"beans","controllers"})
@ComponentScan(basePackages = {"beans","controllers","endpoints"})
@EnableJpaRepositories(basePackageClasses = {UserRepository.class,UserAccountRepository.class})
public class BookingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingAppApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean securityFilterChainRegistration() {
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        delegatingFilterProxy.setTargetBeanName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(delegatingFilterProxy);
        registrationBean.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);

        List< ViewResolver > resolvers = new ArrayList< ViewResolver >();

        resolvers.add(jsonViewResolver());
//        resolvers.add(pdfViewResolver());

        resolver.setViewResolvers(resolvers);
        return resolver;
    }

//    @Bean
//    public ViewResolver pdfViewResolver() {
//        return new PdfViewResolver();
//    }



    @Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }

}
