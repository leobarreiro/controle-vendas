package br.com.ilegra.api.vendas.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.domain.ItemVenda;
import br.com.ilegra.api.vendas.domain.Venda;

@Component
public class VendaConverter implements SimpleConverter<Venda> {

	@Autowired
	private ItemVendaConverter itemVendaConverter;
	
	@Override
	public Venda convert(String conteudo) {
		List<String> parts = Arrays.asList(StringUtils.split(conteudo, "ç"));
		List<ItemVenda> itens = new ArrayList<>();
		String itensStr = StringUtils.replaceEach(parts.get(2), new String[] {"[", "]"}, new String[] {"", ""});
		Arrays.asList(itensStr.split(",")).stream().forEach(it -> {
			itens.add(itemVendaConverter.convert(it));
		});
		return Venda.builder()
				.saleId(parts.get(1))
				.itemsVenda(itens)
				.salesmanName(parts.get(3))
				.build();
	}

}
