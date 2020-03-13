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

@RunWith(CamelSpringRunner.class)
public class ControleVendasRouteTest extends CamelTestSupport {

	private static final String URI_MOCK_OUTPUT = "mock:output";

	@InjectMocks
	private ControleVendasRoute controleVendasRoute;

	@Before
	public void setUp() throws Exception {
	}

	@EndpointInject(uri = URI_MOCK_OUTPUT)
	private MockEndpoint mockEndpointResult;

	protected RoutesBuilder[] createRouteBuilders() throws Exception {
		return new RoutesBuilder[] { controleVendasRoute };
	}
	
	@Test
	public void testRoute() {
		
	}

}
