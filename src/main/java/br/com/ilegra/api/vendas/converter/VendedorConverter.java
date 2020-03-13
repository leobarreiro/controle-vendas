package br.com.ilegra.api.vendas.converter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.domain.Vendedor;

@Component
public class VendedorConverter implements SimpleConverter<Vendedor> {

	@Override
	public Vendedor convert(String conteudo) {
		List<String> parts = Arrays.asList(StringUtils.split(conteudo, "ç"));
		return Vendedor.builder()
				.cpf(parts.get(1))
				.nome(parts.get(2))
				.salario(BigDecimal.valueOf(Double.valueOf(parts.get(3))))
				.build();
	}

}
