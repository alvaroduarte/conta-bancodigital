package br.com.bancodigital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.bancodigital.domain.Cliente;
import br.com.bancodigital.domain.Conta;
import br.com.bancodigital.domain.Transacao;
import br.com.bancodigital.repository.TransacaoRepository;

@RunWith(SpringRunner.class)
public class DepositarServiceTest {
	
	@Mock
	private ContaService contaService;

	@Mock
	private TransacaoRepository transacaoRepository;
	
	@InjectMocks
    private DepositarService depositarService;
	
	@Test
	public void depositarTest() {
		
		final var conta = new Conta(Integer.valueOf(100), Long.valueOf(10378), new Cliente("Alvaro Duarte", "98933075038"));
		conta.setSaldo(new BigDecimal(100));
		
		final var transacao =  Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(new BigDecimal(100))
				.valorMovimentacao(new BigDecimal(10))
				.valorSaldoAtualizado(new BigDecimal(110.00))
				.conta(conta)
				.build();
		
		BDDMockito.when(contaService.buscarContaPorId(Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(contaService.buscarConta(Mockito.any(), Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(contaService.salvar(Mockito.any())).thenReturn(conta);
		
		BDDMockito.when(transacaoRepository.save(Mockito.any())).thenReturn(transacao);
		
		final var contaRtorno = depositarService.movimentacao(conta.getId(), new BigDecimal(10));
				
		Assertions.assertThat(contaRtorno.getSaldo()).isNotNull();
		assertEquals(contaRtorno.getSaldo().setScale(2, RoundingMode.HALF_UP), transacao.getValorSaldoAtualizado().setScale(2, RoundingMode.HALF_UP));
	}

}