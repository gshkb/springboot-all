package cn.gshkb.swagger.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /*@Bean
    AuthInterceptor localInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localInterceptor())
                .addPathPatterns("/auth/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }*/


   /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }*/

   /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFeatures(SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,//字符串null返回空字符串
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.PrettyFormat);
        converters.add(converter);
    }*/
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/**").addResourceLocations(
               "classpath:/static/");
       registry.addResourceHandler("swagger-ui.html").addResourceLocations(
               "classpath:/META-INF/resources/");
       registry.addResourceHandler("/webjars/**").addResourceLocations(
               "classpath:/META-INF/resources/webjars/");
       super.addResourceHandlers(registry);
   }

}
