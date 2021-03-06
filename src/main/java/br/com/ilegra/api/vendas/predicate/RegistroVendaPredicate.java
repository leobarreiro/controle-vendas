package br.com.ilegra.api.vendas.predicate;

import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.utils.Constants;

@Component
public class RegistroVendaPredicate implements Predicate {

	@Override
	public boolean matches(Exchange exchange) {
		return Pattern.matches(Constants.REGISTRO_VENDA_PATTERN, exchange.getIn().getBody(String.class));
	}

}
