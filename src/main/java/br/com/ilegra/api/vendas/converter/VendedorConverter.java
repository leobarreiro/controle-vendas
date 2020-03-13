package br.com.ilegra.api.vendas.converter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.domain.Vendedor;
import br.com.ilegra.api.vendas.exception.ControleVendasException;
import br.com.ilegra.api.vendas.utils.Constants;

@Component
public class VendedorConverter implements SimpleConverter<Vendedor> {

	@Override
	public Vendedor convert(String conteudo) {
		if (!Pattern.matches(Constants.VENDEDOR_PATTERN, conteudo)) {
			throw new ControleVendasException("Registro de Vendedor está fora do padrão esperado");
		}
		List<String> parts = Arrays.asList(StringUtils.split(conteudo, "ç"));
		return Vendedor.builder()
				.cpf(parts.get(1))
				.nome(parts.get(2))
				.salario(BigDecimal.valueOf(Double.valueOf(parts.get(3))).setScale(2))
				.build();
	}

}
