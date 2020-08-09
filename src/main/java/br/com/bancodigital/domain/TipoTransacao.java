package br.com.bancodigital.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TipoTransacao {
	
	@Id
	private Integer id;
	
	@Column(nullable=false)
	private String nome;
		
	public TipoTransacao(Integer id) {
		this.id = id;
	}

}