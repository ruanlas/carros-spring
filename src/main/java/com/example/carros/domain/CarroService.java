package com.example.carros.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.carros.domain.dto.CarroDTO;
import com.example.carros.domain.exception.ObjectNotFoundException;

@Service
public class CarroService {

	@Autowired
	private CarroRepository rep;
	
	public List<CarroDTO> getCarros() {
//		return rep.findAll().stream().map(c -> new CarroDTO(c)).collect(Collectors.toList());
//		return rep.findAll().stream().map(CarroDTO::new).collect(Collectors.toList());
		return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
	}
	
	public List<Carro> getCarrosFake() {
		List<Carro> carros = new ArrayList<>();
		
//		carros.add(new Carro(1L, "Fusca"));
//		carros.add(new Carro(2L, "Brasilia"));
//		carros.add(new Carro(3L, "Chevette"));
		
		return carros;
	}

//	public Optional<CarroDTO> getCarroById(Long id) {
	public CarroDTO getCarroById(Long id) {
//		return rep.findById(id).map(c -> new CarroDTO(c));
//		return rep.findById(id).map(CarroDTO::create);
		return rep.findById(id).map(CarroDTO::create).orElseThrow( () -> new ObjectNotFoundException("Carro não encontrado") );
	}

	public List<CarroDTO> getCarroByTipo(String tipo) {
//		return rep.findByTipo(tipo).stream().map(c -> new CarroDTO(c)).collect(Collectors.toList());
//		return rep.findByTipo(tipo).stream().map(CarroDTO::new).collect(Collectors.toList());
		return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}
	
	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
		
		return CarroDTO.create(rep.save(carro));
	}

	public CarroDTO update(Carro carro, Long id) {
		Assert.notNull(id, "Não foi possível atualizar o registro");
		Optional<Carro> optional = rep.findById(id);
		if (optional.isPresent()) {
			Carro db = optional.get();
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id " + db.getId());
			
			rep.save(db);
			
			return CarroDTO.create(db);
		}else {
			throw new RuntimeException("Não foi possível atualizar o registro");
		}
		
//		getCarroById(id).map( (db) -> {
//			db.setNome(carro.getNome());
//			db.setTipo(carro.getTipo());
//			System.out.println("Carro id " + db.getId());
//			
//			rep.save(db);
//			
//			return db;
//		}).orElseThrow( () -> new RuntimeException("Não foi possível atualizar o registro")); 
		
	}
	
//	public boolean delete(Long id) {
	public void delete(Long id) {
		rep.deleteById(id);
//		Optional<CarroDTO> carro = getCarroById(id);
//		if (carro.isPresent()) {
//			rep.deleteById(id);
//			return true;
//		}
//		return false;
	}
}
