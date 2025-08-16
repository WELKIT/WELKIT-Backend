package welkit_server.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("WELKIT API")
                                .version("v1.0")
                                .description("신입사원 성장을 돕는 온보딩 키트 WELKIT API")
                );
    }

}
