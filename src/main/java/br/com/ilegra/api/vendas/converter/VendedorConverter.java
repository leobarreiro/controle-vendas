package br.com.ilegra.api.vendas.converter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.domain.Vendedor;
import br.com.ilegra.api.vendas.exception.ControleVendasException;
import br.com.ilegra.api.vendas.utils.Constants;

@Component
public class VendedorConverter implements SimpleConverter<Vendedor> {

	@Override
	public Vendedor convert(String conteudo) {
		Pattern pattern = Pattern.compile(Constants.VENDEDOR_SPLIT);
		Matcher matcher = pattern.matcher(conteudo);
		List<String> parts = new LinkedList<>();
		while (matcher.find()) {
			String part = matcher.group(0);
			if (!part.equals(Constants.SEPARATOR)) {
				parts.add(part);
			}
		}
		if (parts.size() != 4) {
			throw new ControleVendasException("Registro de Vendedor está fora do padrão esperado");
		}
		return Vendedor.builder()
				.cpf(parts.get(1))
				.nome(parts.get(2))
				.salario(BigDecimal.valueOf(Double.valueOf(parts.get(3))).setScale(2))
				.build();
	}

}
