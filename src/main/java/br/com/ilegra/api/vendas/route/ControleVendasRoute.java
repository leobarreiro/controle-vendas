package br.com.ilegra.api.vendas.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
import org.apache.camel.processor.aggregate.GroupedMessageAggregationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.aggregation.AggregateFileLines;
import br.com.ilegra.api.vendas.bean.ConsolidadorBean;
import br.com.ilegra.api.vendas.config.ControleVendasConfig;
import br.com.ilegra.api.vendas.predicate.RegistroClientePredicate;
import br.com.ilegra.api.vendas.predicate.RegistroVendaPredicate;
import br.com.ilegra.api.vendas.predicate.RegistroVendedorPredicate;
import br.com.ilegra.api.vendas.predicate.TamanhoArquivoValido;
import br.com.ilegra.api.vendas.processor.ClienteProcessor;
import br.com.ilegra.api.vendas.processor.VendaProcessor;
import br.com.ilegra.api.vendas.processor.VendedorProcessor;

@Component
public class ControleVendasRoute extends RouteBuilder {

	private static final String WRAP_LINE_REGEX = "\\r\\n|\\n|\\r";

	@Autowired
	private ControleVendasConfig config;
	
	@Autowired
	private RegistroVendedorPredicate isVendedor;

	@Autowired
	private RegistroClientePredicate isCliente;

	@Autowired
	private RegistroVendaPredicate isVenda;

	@Autowired
	private TamanhoArquivoValido isTamanhoArquivoValido;

	@Autowired
	private VendedorProcessor vendedorProcessor;

	@Autowired
	private ClienteProcessor clienteProcessor;

	@Autowired
	private VendaProcessor vendaProcessor;

	@Autowired
	private AggregateFileLines aggregateFileLines;
	
	@Autowired
	private ConsolidadorBean consolidadorBean;

	@Override
	public void configure() throws Exception {

		from(config.getRouteEntrada())
			.routeId("{{app.routes-id.entrada}}")
			.threads(3, 3, "threadVendas")
			.log(LoggingLevel.INFO, "Iniciando processamento...")
			.to("{{app.routes.encoding}}")
			.end();

		from("{{app.routes.encoding}}")
			.routeId("{{app.routes-id.encoding}}")
			.choice()
			.when(isTamanhoArquivoValido)
				.log("Validado tamanho do arquivo")
				.to("{{app.routes.spliter}}")
			.otherwise()
				.log("Arquivo vazio.")
				.endChoice()
			.end();

		from("{{app.routes.spliter}}")
			.routeId("{{app.routes-id.spliter}}")
			.split(body().regexTokenize(WRAP_LINE_REGEX), aggregateFileLines)
			.to("{{app.routes.processa-linhas}}")
			.end();

		from("{{app.routes.processa-linhas}}")
			.routeId("{{app.routes-id.processa-linhas}}")
			.choice()
			.when(isVendedor)
				.log("Processando registro de Vendedor")
				.process(vendedorProcessor)
				.to("{{app.routes.joiner}}")
			.when(isCliente)
				.log("Processando registro de Cliente")
				.process(clienteProcessor)
				.to("{{app.routes.joiner}}")
			.when(isVenda)
				.log("Processando registro de Venda")
				.process(vendaProcessor)
				.to("{{app.routes.joiner}}")
			.otherwise()
				.log("Detectado um registro não identificado ou inválido")
			.endChoice()
			.end();

		from("{{app.routes.joiner}}")
			.routeId("{{app.routes-id.joiner}}")
			.aggregate(constant(true), new GroupedExchangeAggregationStrategy())
			.completionTimeout(100L)
			.to("{{app.routes.consolidador}}")
			.end();

		from("{{app.routes.consolidador}}")
			.routeId("{{app.routes-id.consolidador}}")
			.log("Consolidando Dados...")
			.bean(consolidadorBean)
			.convertBodyTo(byte[].class, "UTF-8")
			.log("Exportando dados de saída.")
			.id("consolidadorId")
			.to(config.getRouteSaida())
			.endParent();

	}

}
