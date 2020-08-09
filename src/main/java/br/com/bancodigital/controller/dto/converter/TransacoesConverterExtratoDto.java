package br.com.bancodigital.controller.dto.converter;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import br.com.bancodigital.controller.dto.ClienteDto;
import br.com.bancodigital.controller.dto.ContaExtratoDto;
import br.com.bancodigital.controller.dto.ExtratoDto;
import br.com.bancodigital.controller.dto.TipoExtratoDto;
import br.com.bancodigital.domain.Transacao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class TransacoesConverterExtratoDto implements Converter<Page<Transacao>, Page<ExtratoDto>> {

	@Override
	public Page<ExtratoDto> convert(Page<Transacao> source) {

		log.debug("TransacoesConverterExtratoDto convert {}", source);
		
		return new PageImpl<ExtratoDto>(
				 source
				.stream()
				.map(t -> ExtratoDto.builder()
					.data(t.getData())
					.valorSaldo(t.getValorSaldo())
					.valorMovimentacao(t.getValorMovimentacao())
					//.porcentagemMovimentacao(t.getPorcentagemMovimentacao())
					//.valorTransacao(t.getValorTransacao())
					.valorSaldoAtualizado(t.getValorSaldoAtualizado())
					.tipoTransacao(new TipoExtratoDto(t.getTipoTransacao().getNome()))
					.contaMovimentacao(getContaExtratoDto(t))
					.build())
				    .collect(Collectors.toList()), source.getPageable(), source.getTotalElements());
	}
	
	private ContaExtratoDto getContaExtratoDto(Transacao t) {

		if(Objects.nonNull(t.getContaMovimentacao())) {

			return new ContaExtratoDto(
					
					new ClienteDto(
					
							t.getContaMovimentacao().getCliente().getNome(), 
							t.getContaMovimentacao().getCliente().getCpf()),  
					
					t.getContaMovimentacao().getAgencia(), 
					t.getContaMovimentacao().getNumeroConta());

		}

		return null;
	}
}