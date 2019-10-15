package com.example.carros.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import lombok.AllArgsConstructor;

// ******* https://dicasdejava.com.br/como-configurar-o-lombok-no-eclipse/

import lombok.*;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;

@Entity(name = "carro")
@Data
//@Getter @Setter
//@ToString
//@EqualsAndHashCode
//@NoArgsConstructor
//@AllArgsConstructor
public class Carro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String tipo;
    private String descricao;
    
    @Column(name = "url_foto")
    private String urlFoto;
    
    @Column(name = "url_video")
    private String urlVideo;
    private String latitude;
    private String longitude;
	
//	public Carro() {
//	}
//	
//	public Carro(Long id, String nome) {
//		this.id = id;
//		this.nome = nome;
//	}
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getNome() {
//		return nome;
//	}
//	public void setNome(String nome) {
//		this.nome = nome;
//	}
//	public String getTipo() {
//		return tipo;
//	}
//	public void setTipo(String tipo) {
//		this.tipo = tipo;
//	}
}
