package br.com.ilegra.api.vendas.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

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
import br.com.ilegra.api.vendas.utils.BuilderTestUtils;
import br.com.ilegra.api.vendas.utils.Constants;
import br.com.ilegra.api.vendas.utils.RouteBuildersTest;

@RunWith(CamelSpringRunner.class)
public class ControleVendasRouteTest extends CamelTestSupport {

	private static final String MOCK_OUTPUT = "mock:output";

	private static final String URI_MOCK_OUTPUT = MOCK_OUTPUT;

	@Spy
	private ControleVendasConfig config;

	@Mock
	private RegistroVendedorPredicate isVendedor;

	@Mock
	private RegistroClientePredicate isCliente;

	@Mock
	private RegistroVendaPredicate isVenda;

	@Spy
	private TamanhoArquivoValido isTamanhoArquivoValido;

	@Mock
	private VendedorProcessor vendedorProcessor;

	@Mock
	private ClienteProcessor clienteProcessor;

	@Mock
	private VendaProcessor vendaProcessor;

	@Mock
	private AggregateFileLines aggregateFileLines;

	@Mock
	private ConsolidadorBean consolidadorBean;

	@InjectMocks
	private ControleVendasRoute controleVendasRoute;

	// private Exchange exchange;

	private void doMocks() throws Exception {
		RouteBuildersTest.createInterceptedRoute(template.getCamelContext(),
				Constants.ROUTEID_ENCODING, URI_MOCK_OUTPUT);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		doMocks();
	}

	@EndpointInject(uri = URI_MOCK_OUTPUT)
	private MockEndpoint mockEndpointResult;

	@Override
	protected RoutesBuilder[] createRouteBuilders() throws Exception {
		return new RoutesBuilder[] { controleVendasRoute, 
					RouteBuildersTest.createSimpleMockRoute(Constants.ROUTEID_ENCODING, MOCK_OUTPUT) };
	}

	@Test
	public void naoDeveAcusarErroParaArquivoComConteudo() throws Exception {
		// when(cooperativaService.buscarCooperativasAtivas()).thenReturn(new ArrayList<Integer>());
		template.sendBodyAndHeaders(Constants.ROUTE_ENCODING, BuilderTestUtils.conteudoMinimoArquivo(), BuilderTestUtils.headerFileLength());
		mockEndpointResult.getName();
		mockEndpointResult.assertExchangeReceived(0);

		// mockEndpointResult.expectedHeaderReceived(Constants.ERROS_PROPERTY, ExportacaoDebitoRoute.NENHUMA_COOPERATIVA_ATIVA);
	}

}
