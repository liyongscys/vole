package com.github.vole.common.bean.config;

import com.github.vole.common.constants.CommonConstant;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * token 转发到下级服务
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "security", name = "token-forward", havingValue = "true")
public class FeignConfiguration {
    public static int connectTimeOutMillis = 5000;//超时时间
    public static int readTimeOutMillis = 90000;


   /* @Bean
    public MyConcurrencyStrategy feignHystrixConcurrencyStrategy() {
        return new MyConcurrencyStrategy();
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringMultipartEncoder(new SpringEncoder(messageConverters));
    }*/

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

    @Bean
    public Retryer feignRetryer() {
        Retryer retryer = new Retryer.Default(100, 1000, 4);
        return retryer;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = (requestTemplate) -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
//            Enumeration<String> headerNames = request.getHeaderNames();
            log.error("{}");
            requestTemplate.header(CommonConstant.REQ_HEADER, request.getHeader(CommonConstant.REQ_HEADER));

        };
        return requestInterceptor;
    }

}
