package br.com.bancodigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancodigital.domain.TipoTransacao;

public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long> {
	
}