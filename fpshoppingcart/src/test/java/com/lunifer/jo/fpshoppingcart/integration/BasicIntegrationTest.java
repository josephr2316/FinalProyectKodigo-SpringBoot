package com.lunifer.jo.fpshoppingcart.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BasicIntegrationTest {

    @Test
    void contextLoads() {
        // Test básico para verificar que el contexto de Spring carga correctamente
        assertThat(true).isTrue();
    }

    @Test
    void applicationStartsSuccessfully() {
        // Test para verificar que la aplicación inicia sin errores
        assertThat(1 + 1).isEqualTo(2);
    }
}
