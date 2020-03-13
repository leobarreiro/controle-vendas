package br.com.ilegra.api.vendas.predicate;

import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.stereotype.Component;

@Component
public class RegistroVendaPredicate implements Predicate {

	@Override
	public boolean matches(Exchange exchange) {
		return Pattern.matches("^(003)ç[0-9]{1,}ç(\\[){1}(([0-9]{1,}-[0-9]{1,}-[0-9\\.]{1,})(\\,){0,}){1,}(\\]){1}ç[a-zA-Z ]{3,}$", exchange.getIn().getBody(String.class));
	}

}
