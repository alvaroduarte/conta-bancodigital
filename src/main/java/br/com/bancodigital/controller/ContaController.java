package br.com.bancodigital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bancodigital.controller.dto.ContaDto;
import br.com.bancodigital.controller.dto.converter.ContaConverterContaDto;
import br.com.bancodigital.controller.request.AbrirContaRequest;
import br.com.bancodigital.controller.request.converter.AbrirContaRequestConverterCliente;
import br.com.bancodigital.service.ContaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("conta")
public class ContaController {
		
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ContaConverterContaDto contaConverterContaDto;
	
	@Autowired
	private AbrirContaRequestConverterCliente clienteRequestConverterCliente;
	
	@PostMapping
	public ResponseEntity<ContaDto> abrirConta(@RequestBody @Valid AbrirContaRequest abrirContaRequest) {
		
		log.info("abrirConta {}", abrirContaRequest);

		final var conta = contaService.abrirConta(clienteRequestConverterCliente.convert(abrirContaRequest));
		
		log.debug("{}", conta);
		
		return new ResponseEntity<>( contaConverterContaDto.convert( conta ), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ContaDto> conta(@PathVariable Long id) {	
		
		log.info("conta id {}", id);

		final var conta = contaService.buscarContaPorId(id);
		
		log.debug("{}", conta);
		
		return new ResponseEntity<>( contaConverterContaDto.convert( conta ), HttpStatus.OK);
	}
	
	@GetMapping("/{agencia}/{numeroConta}")
	public ResponseEntity<ContaDto> conta(@PathVariable Integer agencia, @PathVariable Long numeroConta) {	
		
		log.info("conta agencia {}, numeroConta {}", agencia, numeroConta);

		final var conta = contaService.buscarConta(agencia, numeroConta);
		
		log.debug("{}", conta);
		
		return new ResponseEntity<>( contaConverterContaDto.convert( conta ), HttpStatus.OK);
	}
}