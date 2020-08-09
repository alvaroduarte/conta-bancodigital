package br.com.bancodigital.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bancodigital.domain.Cliente;
import br.com.bancodigital.domain.Conta;
import br.com.bancodigital.domain.TipoTransacao;
import br.com.bancodigital.domain.Transacao;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransacaoRepositoryTest {

	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private TipoTransacaoRepository tipoTransacaoRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	
	@Test
	public void saveTransacaoTest() {
		
		var clienteMock1 = new Cliente("Alvaro Duarte", "15723132008");
		
		clienteMock1 = clienteRepository.save(clienteMock1);
		
		var contaClienteMock1 = new Conta(100, Long.valueOf(88888), clienteMock1);
		
		contaClienteMock1 = contaRepository.save(contaClienteMock1);
	
		assertThat(contaClienteMock1).isNotNull();
		
		var conta = contaRepository.findByAgenciaAndNumeroConta(contaClienteMock1.getAgencia(), contaClienteMock1.getNumeroConta()).get();
		
		assertThat(conta.getAgencia()).isEqualTo(contaClienteMock1.getAgencia());
		assertThat(conta.getNumeroConta()).isEqualTo(contaClienteMock1.getNumeroConta());
		assertThat(conta.getCliente().getNome()).isEqualTo("Alvaro Duarte");
		assertThat(conta.getCliente().getCpf()).isEqualTo("15723132008");
				
		var tipoTransacao = new TipoTransacao(); 
		tipoTransacao.setId(Integer.valueOf(1));
		tipoTransacao = tipoTransacaoRepository.save(tipoTransacao);	
				
		final var transacao =  Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(new BigDecimal(100))
				.valorMovimentacao(new BigDecimal(10))
				.valorSaldoAtualizado(new BigDecimal(200.5))
				.conta(conta)
				.tipoTransacao(tipoTransacao)
				.build();
		
		transacaoRepository.save(transacao);
		
		var transacoes = transacaoRepository.findByContaOrderByDataDesc(contaClienteMock1, null);
		
		var transacaoRetorno = transacoes.get().stream().findFirst().get();
		
		assertThat(transacaoRetorno.getValorSaldo()).isEqualTo(transacao.getValorSaldo());
		assertThat(transacaoRetorno.getValorMovimentacao()).isEqualTo(transacao.getValorMovimentacao());
		
 	}

}