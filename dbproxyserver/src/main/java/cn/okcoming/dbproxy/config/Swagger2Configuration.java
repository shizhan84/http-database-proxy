package cn.okcoming.dbproxy.config;

import cn.okcoming.baseconstants.HeaderConstants;
import cn.okcoming.dbproxy.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bluces
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket buildDocket() {
        //添加head参数
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(Constants.X_UUID).description("每次响应原样返回，方便请求方异步响应").modelRef(new ModelRef("string")).parameterType("header").build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("cn.okcoming.dbproxy.controller")).paths(PathSelectors.any()).build().globalOperationParameters(pars);
    }

    @SuppressWarnings("deprecation")
    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("接口管理")
                .description("在线接口文档")
                .build();
    }
}
