package br.com.ilegra.api.vendas.converter;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.domain.Cliente;

@Component
public class ClienteConverter implements SimpleConverter<Cliente> {

	@Override
	public Cliente convert(String conteudo) {
		List<String> parts = Arrays.asList(StringUtils.split(conteudo, "ç"));
		return Cliente.builder()
				.cnpj(parts.get(1))
				.nome(parts.get(2))
				.area(parts.get(3))
				.build();
	}

}
