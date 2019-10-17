package com.example.carros.api.carros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//public interface CarroRepository extends CrudRepository<Carro, Long>{
public interface CarroRepository extends JpaRepository<Carro, Long>{
	List<Carro> findByTipo(String tipo);

}
