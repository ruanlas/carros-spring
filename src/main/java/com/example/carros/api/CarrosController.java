package com.example.carros.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
	
	@Autowired
	private CarroService service;
	
	@GetMapping
	public ResponseEntity<List<CarroDTO>> get() {
//		return new ResponseEntity<>(service.getCarros(), HttpStatus.OK);
		return ResponseEntity.ok(service.getCarros());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable("id") Long id) {
//		Optional<CarroDTO> optional = service.getCarroById(id).map(CarroDTO::new);
		Optional<CarroDTO> optional = service.getCarroById(id).map(CarroDTO::create);
//		if (optional.isPresent()) {
//			Carro carro = optional.get();
//			return ResponseEntity.ok(carro);
//		}else {
//			return ResponseEntity.notFound().build();
//		}
		
//		return optional.isPresent() ? 
//				ResponseEntity.ok(optional.get()) :
//				ResponseEntity.notFound().build();
		
//		return optional.map( c -> ResponseEntity.ok(c))
//				.orElse(ResponseEntity.notFound().build());
		
		return optional.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("tipo/{tipo}")
	public ResponseEntity<List<CarroDTO>> getCarrosByTipo(@PathVariable("tipo") String tipo) {
		List<CarroDTO> carros = service.getCarroByTipo(tipo);
		return carros.isEmpty() ? 
				ResponseEntity.noContent().build() :
					ResponseEntity.ok(carros);
	}
	
	@PostMapping
	public String post(@RequestBody Carro carro) {
		Carro c = service.insert(carro);
		
		return "Carro salvo com sucesso: " + c.getId();
	}

	@PutMapping("/{id}")
	public String put(@PathVariable("id") Long id, @RequestBody Carro carro) {
		Carro c = service.update(carro, id);
		
		return "Carro atualizado com sucesso: " + c.getId();
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) {
		service.delete(id);
		
		return "Carro deletado com sucesso";
	}
}
