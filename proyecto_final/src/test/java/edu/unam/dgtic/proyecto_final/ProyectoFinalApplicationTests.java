package edu.unam.dgtic.proyecto_final;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/database/schema.sql","/database/data.sql"})
class ProyectoFinalApplicationTests {
	@Test
	void contextLoads() {
		System.out.println("Creando base");
	}
}
