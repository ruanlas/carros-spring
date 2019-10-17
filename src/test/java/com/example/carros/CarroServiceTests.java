package com.example.carros;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.carros.api.carros.Carro;
import com.example.carros.api.carros.CarroDTO;
import com.example.carros.api.carros.CarroService;
import com.example.carros.exception.ObjectNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarroServiceTests {

	@Autowired
	private CarroService service;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testeSave() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivos");
		
		CarroDTO carroDTO = service.insert(carro);
		
		assertNotNull(carroDTO);
		
		Long id = carroDTO.getId();
		assertNotNull(id);
		
//		Optional<CarroDTO> optional = service.getCarroById(id);
		carroDTO = service.getCarroById(id);
		assertNotNull(carroDTO);
//		assertTrue(optional.isPresent());
		
//		carroDTO = optional.get();
		assertEquals("Ferrari", carro.getNome());
		assertEquals("esportivos", carro.getTipo());
		
		service.delete(id);
		
//		assertFalse(service.getCarroById(id).isPresent());
		try {
			assertNull(service.getCarroById(id));
			fail("O carro não foi excluído");
		} catch (ObjectNotFoundException e) {
//			assertTrue(true);
		}
		
	}
	
	@Test
	public void testLista() {
		List<CarroDTO> carros = service.getCarros();
		
		assertEquals(30, carros.size());
	}
	
	@Test
	public void testGet() {
//		Optional<CarroDTO> carrosOptional = service.getCarroById(11L);
		CarroDTO c = service.getCarroById(11L);
		
		assertNotNull(c);
//		assertTrue(carrosOptional.isPresent());
		
//		CarroDTO c = carrosOptional.get();
		
		assertEquals("Ferrari FF", c.getNome());
	}

	@Test
	public void testListaPorTipo() {
		
		assertEquals(10, service.getCarroByTipo("classicos").size());
		assertEquals(10, service.getCarroByTipo("esportivos").size());
		assertEquals(10, service.getCarroByTipo("luxo").size());
		assertEquals(0, service.getCarroByTipo("xxx").size());
	}
}
