package br.com.ilegra.api.vendas.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
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
import br.com.ilegra.api.vendas.utils.Constants;

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
				.routeId(Constants.ROUTEID_ENTRADA)
				.threads(3, 3, "threadVendas")
				.log(LoggingLevel.INFO, "Iniciando processamento...")
				.to(Constants.ROUTE_ENCODING)
				.end();

		from(Constants.ROUTE_ENCODING)
				.routeId(Constants.ROUTEID_ENCODING)
				.choice()
				.when(isTamanhoArquivoValido)
				.log("Validado tamanho do arquivo")
				.to(Constants.ROUTE_SPLITER)
				.otherwise()
				.log("Arquivo vazio.")
				.endChoice()
				.end();

		from(Constants.ROUTE_SPLITER)
				.routeId(Constants.ROUTEID_SPLITER)
				.split(body().regexTokenize(WRAP_LINE_REGEX), aggregateFileLines)
				.to(Constants.ROUTE_PROCESSA_LINHAS)
				.end();

		from(Constants.ROUTE_PROCESSA_LINHAS)
				.routeId(Constants.ROUTEID_PROCESSA_LINHAS)
				.choice()
				.when(isVendedor)
				.log("Processando registro de Vendedor")
				.process(vendedorProcessor)
				.to(Constants.ROUTE_JOINER)
				.when(isCliente)
				.log("Processando registro de Cliente")
				.process(clienteProcessor)
				.to(Constants.ROUTE_JOINER)
				.when(isVenda)
				.log("Processando registro de Venda")
				.process(vendaProcessor)
				.to(Constants.ROUTE_JOINER)
				.otherwise()
				.log("Detectado um registro não identificado ou inválido")
				.endChoice()
				.end();

		from(Constants.ROUTE_JOINER)
				.routeId(Constants.ROUTEID_JOINER)
				.aggregate(constant(true), new GroupedExchangeAggregationStrategy())
				.completionTimeout(500L)
				.to(Constants.ROUTE_CONSOLIDADOR)
				.end();

		from(Constants.ROUTE_CONSOLIDADOR)
				.routeId(Constants.ROUTEID_CONSOLIDADOR)
				.log("Consolidando Dados...")
				.bean(consolidadorBean)
				.convertBodyTo(byte[].class, "UTF-8")
				.log("Exportando dados de saída.")
				.id(Constants.ROUTEID_CONSOLIDADOR)
				.to(config.getRouteSaida())
				.endParent();

	}

}
