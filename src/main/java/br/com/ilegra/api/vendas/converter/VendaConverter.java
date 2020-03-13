package br.com.ilegra.api.vendas.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.domain.ItemVenda;
import br.com.ilegra.api.vendas.domain.Venda;
import br.com.ilegra.api.vendas.exception.ControleVendasException;
import br.com.ilegra.api.vendas.utils.Constants;

@Component
public class VendaConverter implements SimpleConverter<Venda> {

	@Autowired
	private ItemVendaConverter itemVendaConverter;

	@Override
	public Venda convert(String conteudo) {
		if (!Pattern.matches(Constants.VENDA_PATTERN, conteudo)) {
			throw new ControleVendasException("Registro de Venda está fora do padrão esperado");
		}
		List<String> parts = Arrays.asList(StringUtils.split(conteudo, "ç"));
		List<ItemVenda> itens = new ArrayList<>();
		String itensStr = StringUtils.replaceEach(parts.get(2), new String[] { "[", "]" }, new String[] { "", "" });
		Arrays.asList(itensStr.split(",")).stream().forEach(it -> {
			itens.add(itemVendaConverter.convert(it));
		});
		return Venda.builder()
				.saleId(parts.get(1))
				.itensVenda(itens)
				.salesmanName(parts.get(3))
				.build();
	}

}
