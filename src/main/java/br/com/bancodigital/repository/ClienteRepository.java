package br.com.bancodigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bancodigital.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	 Optional<Cliente> findByCpf(String cpf);

}