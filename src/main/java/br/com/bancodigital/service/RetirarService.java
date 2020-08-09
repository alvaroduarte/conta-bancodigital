package br.com.bancodigital.service;

import static br.com.bancodigital.eum.TipoTransacaoEnum.RETIRAR_DINHEIRO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bancodigital.domain.Conta;
import br.com.bancodigital.domain.TipoTransacao;
import br.com.bancodigital.domain.Transacao;
import br.com.bancodigital.repository.TransacaoRepository;
import br.com.bancodigital.service.strategy.Movimentacao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RetirarService implements Movimentacao {

	@Autowired
	private ContaService contaService;

	@Autowired
	private TransacaoRepository transacaoRepository;
	
	public Conta movimentacao(Integer agencia, Long numeroConta, BigDecimal valorMovimentacao) {
		
		final var conta = contaService.buscarConta(agencia, numeroConta);
		
		return movimentacao( conta  , valorMovimentacao, null );
	
	}
	
	public Conta movimentacao(Long id, BigDecimal valorMovimentacao) {
		
		final var conta = contaService.buscarContaPorId(id);
		
		return movimentacao( conta , valorMovimentacao, null );
	}

	@Transactional
	public Conta movimentacao(Conta conta, BigDecimal valorMovimentacao, Conta contaMovimentacao) {

		log.info("retirar {}, valorMovimentacao {}", conta, valorMovimentacao);
		
		final var valorSaldo = conta.getSaldo();
		
		
		final var valorSaldoAtualizado = calculaRetirada( valorSaldo, valorMovimentacao );
		

		log.debug("{}, valorSaldo {}, valorSaldoAtualizado {}", 
				conta, 
				valorSaldo, 
				valorSaldoAtualizado);

		contaService.validaValorSaldoConta(valorSaldoAtualizado);

		conta.setSaldo(valorSaldoAtualizado);

		conta = contaService.salvar(conta);
		
		final var transacao = Transacao.builder()
				.data(LocalDateTime.now())
				.valorSaldo(valorSaldo)
				.valorMovimentacao(valorMovimentacao)
				.valorSaldoAtualizado(valorSaldoAtualizado)
				.conta(conta)
				.tipoTransacao(new TipoTransacao(RETIRAR_DINHEIRO.getCodigo()))
				.build();

		transacaoRepository.save(transacao);

		log.debug("retirada efetuado com sucesso na {}", conta);

		return conta;
	}
	
}