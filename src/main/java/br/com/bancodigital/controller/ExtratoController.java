package br.com.bancodigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancodigital.controller.dto.ExtratoDto;
import br.com.bancodigital.controller.dto.converter.TransacoesConverterExtratoDto;
import br.com.bancodigital.service.ExtratoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("conta")
public class ExtratoController {

	@Autowired
	private ExtratoService extratoService;

	@Autowired
	private TransacoesConverterExtratoDto transacoesConverterExtratoDto;

	@GetMapping("/extrato/{agencia}/{numeroConta}")
	public ResponseEntity<Page<ExtratoDto>> extrato(@PathVariable Integer agencia, @PathVariable(required = true ) Long numeroConta, Pageable pageable) {	

		log.info("extrato agencia {}, numeroConta {}", agencia, numeroConta);

		final var transacoes = extratoService.buscarTransacoes(agencia, numeroConta, pageable);
		
		return new ResponseEntity<>( transacoesConverterExtratoDto.convert( transacoes ), HttpStatus.OK);
	}

	@GetMapping("/extrato/{id}")
	public ResponseEntity<Page<ExtratoDto>> extrato(@PathVariable  Long id, Pageable pageable) {	

		log.info("extrato id {}", id);

		final var transacoes = extratoService.buscarTransacoes(id, pageable);

		return new ResponseEntity<>( transacoesConverterExtratoDto.convert( transacoes ), HttpStatus.OK);
	}
}