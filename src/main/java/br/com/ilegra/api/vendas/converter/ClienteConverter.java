package br.com.ilegra.api.vendas.converter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.domain.Cliente;
import br.com.ilegra.api.vendas.exception.ControleVendasException;
import br.com.ilegra.api.vendas.utils.Constants;

@Component
public class ClienteConverter implements SimpleConverter<Cliente> {

	@Override
	public Cliente convert(String conteudo) throws ControleVendasException {
		if (!Pattern.matches(Constants.CLIENTE_PATTERN, conteudo)) {
			throw new ControleVendasException("Registro de Cliente está fora do padrão esperado");
		}
		List<String> parts = Arrays.asList(StringUtils.split(conteudo, "ç"));
		return Cliente.builder()
				.cnpj(parts.get(1))
				.nome(parts.get(2))
				.area(parts.get(3))
				.build();
	}

}
