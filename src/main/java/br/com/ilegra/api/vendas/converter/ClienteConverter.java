package br.com.ilegra.api.vendas.converter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.domain.Cliente;
import br.com.ilegra.api.vendas.exception.ControleVendasException;
import br.com.ilegra.api.vendas.utils.Constants;

@Component
public class ClienteConverter implements SimpleConverter<Cliente> {

	@Override
	public Cliente convert(String conteudo) throws ControleVendasException {
		Pattern pattern = Pattern.compile(Constants.CLIENTE_SPLIT);
		Matcher matcher = pattern.matcher(conteudo);
		List<String> parts = new LinkedList<>();
		while (matcher.find()) {
			String part = matcher.group(0);
			if (!part.equals(Constants.SEPARATOR)) {
				parts.add(part);
			}
		}
		if (parts.size() != 4) {
			throw new ControleVendasException("Registro de Cliente está fora do padrão esperado");
		}
		return Cliente.builder()
				.cnpj(parts.get(1))
				.nome(parts.get(2))
				.area(parts.get(3))
				.build();
	}

}
