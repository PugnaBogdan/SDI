package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"Repository", "Controller", "Ui", "Entities.Validators"})
public class RentalConfig {
}