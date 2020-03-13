package br.com.ilegra.api.vendas.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.converter.VendaConverter;
import br.com.ilegra.api.vendas.domain.Venda;

@Component
public class VendaProcessor implements Processor {
	
	@Autowired
	private VendaConverter converter;

	@Override
	public void process(Exchange exchange) throws Exception {
		exchange.getIn().setBody(converter.convert(exchange.getIn().getBody(String.class)), Venda.class);
		exchange.getIn().setHeader("arquivo_original", exchange.getIn().getHeader(Exchange.FILE_NAME));
	}

}
