package rs.raf.cafe.menuorder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "RafCafe Menu & Order Service", version = "1.0", description = "Meni i upravljanje narudžbinama"))
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class MenuOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MenuOrderServiceApplication.class, args);
    }
}
