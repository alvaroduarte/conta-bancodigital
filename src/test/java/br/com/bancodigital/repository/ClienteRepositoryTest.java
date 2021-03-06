package br.com.bancodigital.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bancodigital.domain.Cliente;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Test
	public void saveAndfindByCpfTest() {
		
		var clienteMock1 = new Cliente("Alvaro Duarte", "15723132008");
		var clienteMock2 = new Cliente("Ana Carolina dos Santos", "71631373030");
		
		clienteRepository.saveAll(Arrays.asList(clienteMock1, clienteMock2));
		
		var clienteMock3 = clienteRepository.findByCpf("15723132008");
		
		var clienteMock4 = clienteRepository.findByCpf("71631373030");
				
		assertThat(clienteMock3.get().getCpf()).isEqualTo("15723132008");
		
		assertThat(clienteMock4.get().getCpf()).isEqualTo("71631373030");
		
	}
}
