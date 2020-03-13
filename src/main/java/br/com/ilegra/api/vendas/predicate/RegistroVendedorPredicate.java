package br.com.ilegra.api.vendas.predicate;

import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.stereotype.Component;

@Component
public class RegistroVendedorPredicate implements Predicate {

	@Override
	public boolean matches(Exchange exchange) {
		return Pattern.matches("^(001)ç[0-9]{11}ç[a-zA-Z ]{3,}ç[0-9]{1,}$", exchange.getIn().getBody(String.class));
	}

}
