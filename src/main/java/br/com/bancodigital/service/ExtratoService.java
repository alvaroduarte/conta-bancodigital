package br.com.bancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.bancodigital.domain.Conta;
import br.com.bancodigital.domain.Transacao;
import br.com.bancodigital.exception.ExtratoNotFoundException;
import br.com.bancodigital.repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExtratoService extends ContaService {

	@Autowired
	private TransacaoRepository transacaoRepository;

	public Page<Transacao> buscarTransacoes(Integer agencia, Long numeroConta,  Pageable pageable) {

		final var conta = buscarConta(agencia, numeroConta);

		return buscarTransacoes( conta , pageable );

	}

	public Page<Transacao> buscarTransacoes(Long id,  Pageable pageable) {

		final var conta = buscarContaPorId(id);

		return buscarTransacoes( conta , pageable );

	}

	public Page<Transacao> buscarTransacoes(Conta conta, Pageable pageable) {

		log.info("buscarTransacoes {}", conta);
		
		return transacaoRepository.findByContaOrderByDataDesc(conta, pageable)
				.orElseThrow(() -> new ExtratoNotFoundException());
	}	

}