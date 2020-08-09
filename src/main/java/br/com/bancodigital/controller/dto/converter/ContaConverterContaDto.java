package br.com.bancodigital.controller.dto.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import br.com.bancodigital.controller.dto.ClienteDto;
import br.com.bancodigital.controller.dto.ContaDto;
import br.com.bancodigital.domain.Conta;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ContaConverterContaDto implements Converter<Conta, ContaDto> {
	
	@Override
	public ContaDto convert(Conta source) {
				
		log.debug("{}", source);
		
		final var clienteDto = new ClienteDto(source.getCliente().getNome(), source.getCliente().getCpf());
		
		final var contaDto = new ContaDto(source.getId(), clienteDto, source.getAgencia(), source.getNumeroConta(), source.getSaldo());
				
		log.debug("{}", contaDto);
		
		return contaDto;
	}
}