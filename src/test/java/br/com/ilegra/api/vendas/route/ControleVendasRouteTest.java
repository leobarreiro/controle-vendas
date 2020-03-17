package br.com.ilegra.api.vendas.route;

import static org.mockito.Mockito.when;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
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
import org.springframework.test.context.ActiveProfiles;

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

@RunWith(CamelSpringRunner.class)
@ActiveProfiles("test")
public class ControleVendasRouteTest extends CamelTestSupport {

	private static final String MOCK_OUTPUT = "mock:output";

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

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@EndpointInject(uri = MOCK_OUTPUT)
	private MockEndpoint mockEndpoint;

	@EndpointInject(uri = Constants.ROUTE_ENCODING)
	private ProducerTemplate mockRouteEncoding;

	@Override
	protected RoutesBuilder[] createRouteBuilders() throws Exception {
		return new RoutesBuilder[] { controleVendasRoute };
	}

	@Test
	public void naoDeveAcusarErroParaArquivoComConteudo() throws Exception {
		when(config.getRouteSaida()).thenReturn(MOCK_OUTPUT);
		template.sendBodyAndHeaders(Constants.ROUTE_ENCODING, BuilderTestUtils.conteudoMinimoArquivo(), BuilderTestUtils.headerFileLength());
		mockEndpoint.getName();
		mockEndpoint.assertIsSatisfied();
	}

}
