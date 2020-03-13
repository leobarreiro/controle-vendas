package br.com.ilegra.api.vendas.utils;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;

public class ExchangeTestUtils {

	public static Exchange criarExchange(Object body) {
		CamelContext context = new DefaultCamelContext();
		Exchange exchange = new DefaultExchange(context);
		Message message = new DefaultMessage();
		message.setBody(body);
		exchange.setIn(message);
		return exchange;
	}

	public static Exchange criarExchange(Map<String, Object> headers, Object body) {
		CamelContext context = new DefaultCamelContext();
		Exchange exchange = new DefaultExchange(context);
		Message message = new DefaultMessage();
		message.setHeaders(headers);
		message.setBody(body);
		exchange.setIn(message);
		return exchange;
	}

}