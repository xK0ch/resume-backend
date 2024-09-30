package de.fynnkoch.core.configurations;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ApiPathFilter> apiPathFilterRegistrationBean() {
        final FilterRegistrationBean<ApiPathFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiPathFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("ApiPathFilter");
        return registrationBean;
    }
}
