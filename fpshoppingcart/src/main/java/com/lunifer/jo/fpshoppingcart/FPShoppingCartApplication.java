package com.lunifer.jo.fpshoppingcart;

import com.lunifer.jo.fpshoppingcart.service.UserService;
import com.lunifer.jo.fpshoppingcart.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class FPShoppingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(FPShoppingCartApplication.class, args);
	}

}
