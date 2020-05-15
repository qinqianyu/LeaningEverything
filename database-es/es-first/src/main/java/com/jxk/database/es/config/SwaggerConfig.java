package com.jxk.database.es.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA
 * Description:
 * Author：cutes
 * Date:2020-02-23
 * Time:12:31
 * E-mail:cutesimba@163.com
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private Contact contact = new Contact("JXK","localhost:8080/swagger-ui.html", "2410992642@qq.com");

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }


    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("人员查询")
                //创建人
//                .contact(new Contact("Bryan", "http://blog.bianxh.top/", ""))
                //版本号
                .version("1.0")
                //描述
                .description("查询人员样例")
                .build();
    }
}
