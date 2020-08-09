package br.com.bancodigital.service;

import static br.com.bancodigital.eum.AgenciaEnum.AGENCIA_PADRAO;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bancodigital.domain.Cliente;
import br.com.bancodigital.domain.Conta;
import br.com.bancodigital.exception.ContaNotFoundException;
import br.com.bancodigital.exception.ContaPorIdNotFoundException;
import br.com.bancodigital.exception.SaldoInsuficienteBadRequestException;
import br.com.bancodigital.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ClienteService clienteService;

	public Conta buscarContaPorId(Long id) {

		log.info("buscarConta id {}", id);
		
		return contaRepository.findById(id)
				.orElseThrow(() -> new ContaPorIdNotFoundException());
	}

	public Conta buscarConta(Integer agencia, Long numeroConta) {

		log.info("buscarConta agencia {}, numeroConta {}", agencia, numeroConta);

		return contaRepository.findByAgenciaAndNumeroConta(agencia, numeroConta)
				.orElseThrow(() -> new ContaNotFoundException());
	}

	@Transactional
	public Conta abrirConta(Cliente cliente) {

		log.info("abrirConta {}", cliente);
		
		cliente = clienteService.salvar(cliente);
		
		var numeroConta = contaRepository.getNumeroConta();

		final var conta = contaRepository.save(new Conta(AGENCIA_PADRAO.getCodigo(), numeroConta, cliente));

		log.debug("Conta aberta com suecesso! {}", conta);

		return conta;
	}
	
	@Transactional
	public Conta salvar(Conta conta) {
		
		log.info("salvar {}", conta);
		
		var retorno = contaRepository.save(conta);
		
		log.debug("{}", retorno);
		
		return conta;
	}

	protected void validaValorSaldoConta(BigDecimal valor) {

		log.info("validaValorSaldo valor {}", valor);

		if(valor.signum() < 0){

			log.error("A Conta não tem saldo suficiente para efetuar essa transação!");

			throw new SaldoInsuficienteBadRequestException();
		}

	}
}