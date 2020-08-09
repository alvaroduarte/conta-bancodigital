package br.com.bancodigital.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bancodigital.controller.dto.ContaDto;
import br.com.bancodigital.controller.request.AbrirContaRequest;
import br.com.bancodigital.controller.request.MovimentacaoContaRequest;
import br.com.bancodigital.controller.request.TransferenciaContaRequest;


@Sql(scripts = "classpath:delete_all_junit.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferirControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void  transferirTest() {
	
		var requestClienteMock1 = new AbrirContaRequest("Alvaro Duarte", "98933075038");

		var requestEntityClienteMock1 = new HttpEntity<>(requestClienteMock1);

		var responseClienteMock1 = restTemplate.exchange(
				"/conta",
				HttpMethod.POST,
				requestEntityClienteMock1,
				ContaDto.class);
		
		var contaClienteMock1 = responseClienteMock1.getBody();
		
		Assertions.assertThat(responseClienteMock1.getStatusCodeValue()).isEqualTo(201);
		Assertions.assertThat(contaClienteMock1.getCliente().getCpf()).isEqualTo("98933075038");
		Assertions.assertThat(contaClienteMock1.getCliente().getNome()).isEqualTo("Alvaro Duarte");
	
		
		
		
		
		
		
		var requestClienteMock2 = new AbrirContaRequest("Ana Carolina dos Santos", "43252637057");
		
		var requestEntityClienteMock2 = new HttpEntity<>(requestClienteMock2);
		
		var responseClienteMock2 = restTemplate.exchange(
				"/conta",
				HttpMethod.POST,
				requestEntityClienteMock2,
				ContaDto.class);
		
		var contaClienteMock2 = responseClienteMock2.getBody();
		
		Assertions.assertThat(responseClienteMock2.getStatusCodeValue()).isEqualTo(201);
		Assertions.assertThat(contaClienteMock2.getCliente().getCpf()).isEqualTo("43252637057");
		Assertions.assertThat(contaClienteMock2.getCliente().getNome()).isEqualTo("Ana Carolina dos Santos");
		
		
		
		
		
		

		
		
		
		
		
		var movimentacaoContaRequest = new MovimentacaoContaRequest(new BigDecimal(500.00));
		movimentacaoContaRequest.setValor(new BigDecimal(500.00));
		
		var requestEntityDespositarConta = new HttpEntity<>(movimentacaoContaRequest);

		var responseDepositarConta = restTemplate.exchange(
				"/conta/depositar/"+contaClienteMock1.getAgencia()+"/"+contaClienteMock1.getConta(),
				HttpMethod.PUT,
				requestEntityDespositarConta,
				ContaDto.class);
	
		Assertions.assertThat(responseDepositarConta.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(responseDepositarConta.getBody().getCliente().getCpf()).isEqualTo("98933075038");
		Assertions.assertThat(responseDepositarConta.getBody().getCliente().getNome()).isEqualTo("Alvaro Duarte");
		Assertions.assertThat(responseDepositarConta.getBody().getSaldo().setScale(2, RoundingMode.HALF_UP))
			.isEqualTo(new BigDecimal(500.00).setScale(2, RoundingMode.HALF_UP));
		
		
		
		
		
		
		
		
		
		
		var transferenciaContaRequest = new TransferenciaContaRequest(contaClienteMock2.getAgencia(), contaClienteMock2.getConta(), new BigDecimal(100.00));
		
		var requestEntityTransferencia = new HttpEntity<>(transferenciaContaRequest);

		var responseTransferencia = restTemplate.exchange(
				"/conta/transferir/"+contaClienteMock1.getAgencia()+"/"+contaClienteMock1.getConta(),
				HttpMethod.PUT,
				requestEntityTransferencia,
				ContaDto.class);
		
		
		Assertions.assertThat(responseTransferencia.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(responseTransferencia.getBody().getCliente().getCpf()).isEqualTo("98933075038");
		Assertions.assertThat(responseTransferencia.getBody().getCliente().getNome()).isEqualTo("Alvaro Duarte");
		Assertions.assertThat(responseTransferencia.getBody().getSaldo().setScale(2, RoundingMode.HALF_UP))
			.isEqualTo(new BigDecimal(400.00).setScale(2, RoundingMode.HALF_UP));
		
		
		
		
		
		
		
		
		
		
		
		var responseGetAgenciaConta = restTemplate.getForEntity(
				"/conta/"+contaClienteMock2.getAgencia()+"/"+contaClienteMock2.getConta(), 
				ContaDto.class);
		
		Assertions.assertThat(responseGetAgenciaConta.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(responseGetAgenciaConta.getBody().getCliente().getCpf()).isEqualTo("43252637057");
		Assertions.assertThat(responseGetAgenciaConta.getBody().getSaldo().setScale(2, RoundingMode.HALF_UP))
		.isEqualTo(new BigDecimal(100.00).setScale(2, RoundingMode.HALF_UP));
		
		
		
		var responseGetCpf = restTemplate.getForEntity("/conta/"+contaClienteMock2.getId(), ContaDto.class);
		Assertions.assertThat(responseGetCpf.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(responseGetCpf.getBody().getCliente().getCpf()).isEqualTo("43252637057");
		Assertions.assertThat(responseGetAgenciaConta.getBody().getSaldo().setScale(2, RoundingMode.HALF_UP))
		.isEqualTo(new BigDecimal(100.00).setScale(2, RoundingMode.HALF_UP));
		
	}

}