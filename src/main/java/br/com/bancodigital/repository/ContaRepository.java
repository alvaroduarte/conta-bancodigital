package br.com.bancodigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.bancodigital.domain.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	Optional<Conta> findByAgenciaAndNumeroConta(Integer agencia, Long conta);
	
	//Optional<Conta> findByClienteCpf(String cpf);
	
	@Query(value = "SELECT nextval('NUMERO_CONTA')", nativeQuery = true)
    Long getNumeroConta();

}