package com.hirepulse.frontend;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme("hirepulse")
public class HirePulseFrontendApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(HirePulseFrontendApplication.class, args);
    }
}
