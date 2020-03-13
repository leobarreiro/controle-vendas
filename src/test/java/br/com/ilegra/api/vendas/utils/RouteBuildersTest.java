package br.com.ilegra.api.vendas.utils;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;

public class RouteBuildersTest {

	private static final String MOCK_DIRECT = "mock:direct";

	public static void createQuartzInterceptorRoute(CamelContext camelContext) throws Exception {
		camelContext.getRouteDefinitions().get(0)
				.adviceWith(camelContext, new AdviceWithRouteBuilder() {
					@Override
					public void configure() throws Exception {
						intercept()
								.log("Mocking route ")
								.to(MOCK_DIRECT);
					}
				});

	}

	public static RoutesBuilder createSimpleMockRoute(final String endpointOrigin, final String endpointDestin) throws Exception {
		return new RoutesBuilder() {
			@Override
			public void addRoutesToCamelContext(CamelContext context) throws Exception {
				RouteBuilder routeBuilder = new RouteBuilder() {
					@Override
					public void configure() throws Exception {
						from(endpointOrigin)
								.log("Body: \n${body} Headers:\n ${headers} ")
								.to(endpointDestin)
								.end();
					}
				};
				context.addRoutes(routeBuilder);
			}
		};
	}

	public static void createInterceptedRoute(CamelContext camelContext, String originalRouteId, String destinEndpoint) throws Exception {
		camelContext.getRouteDefinitions()
				.stream()
				.filter(routeDefinition -> originalRouteId.equals(routeDefinition.getId()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Route with id " + originalRouteId + "not found."))
				.adviceWith(camelContext, new AdviceWithRouteBuilder() {
					@Override
					public void configure() throws Exception {
						intercept()
								.log("Intercepted route: " + destinEndpoint + "\nBody: ${body}")
								.to(destinEndpoint);
					}
				});

	}
}