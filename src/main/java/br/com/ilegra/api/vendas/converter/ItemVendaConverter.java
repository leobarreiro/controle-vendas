package br.com.ilegra.api.vendas.converter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.domain.ItemVenda;

@Component
public class ItemVendaConverter implements SimpleConverter<ItemVenda> {

	@Override
	public ItemVenda convert(String conteudo) {
		List<String> parts = Arrays.asList(StringUtils.split(conteudo, "-"));
		return ItemVenda.builder()
				.itemId(Integer.valueOf(parts.get(0)))
				.quantidade(Integer.valueOf(parts.get(1)))
				.preco(BigDecimal.valueOf(Double.valueOf(parts.get(2))).setScale(2))
				.build();
	}

}
