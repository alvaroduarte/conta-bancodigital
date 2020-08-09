package br.com.bancodigital.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.bancodigital.domain.Conta;


public interface Movimentacao {
	
	public Conta movimentacao(Conta conta, BigDecimal valorMovimentacao, Conta contaMovimentacao);

	
	public default BigDecimal calculaSoma(BigDecimal valorSaldo, BigDecimal valorMovimentacao) {

		final var saldoAtualizado = valorSaldo.add(valorMovimentacao).setScale(2, RoundingMode.HALF_UP);

		return saldoAtualizado;
	}
	
	public default BigDecimal calculaRetirada(BigDecimal valorSaldo, BigDecimal valorMovimentacao) {

		final var saldoAtualizado = valorSaldo.subtract( valorMovimentacao ).setScale(2, RoundingMode.HALF_UP);;

		return saldoAtualizado;
	}

}
