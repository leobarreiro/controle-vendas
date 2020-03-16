package br.com.ilegra.api.vendas.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.internal.matchers.Any;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ilegra.api.vendas.converter.VendedorConverter;
import br.com.ilegra.api.vendas.domain.Vendedor;
import br.com.ilegra.api.vendas.exception.ControleVendasException;
import br.com.ilegra.api.vendas.utils.BuilderTestUtils;
import br.com.ilegra.api.vendas.utils.ExchangeTestUtils;

@RunWith(SpringRunner.class)
public class VendedorProcessorTest {

	private Vendedor vendedor;
	private String linhaRegistroVendedor;
	private String linhaRegistroCliente;
	private Exchange exchange;
	private Map<String, Object> mapHeaders;

	@InjectMocks
	private VendedorProcessor processor;

	@Spy
	private VendedorConverter converter;

	@Before
	public void setUp() throws Exception {
		linhaRegistroVendedor = BuilderTestUtils.linhaRegistroVendedorPadrao();
		vendedor = BuilderTestUtils.vendedorPadrao();
		linhaRegistroCliente = BuilderTestUtils.linhaRegistroClientePadrao();
		mapHeaders = new HashMap<>();
		mapHeaders.put(Exchange.FILE_NAME, "arquivo.txt");
	}

	@Test
	public void deveProcessarSemErros() throws Exception {
		exchange = ExchangeTestUtils.criarExchange(mapHeaders, linhaRegistroVendedor);
		processor.process(exchange);
		verify(converter).convert(linhaRegistroVendedor);
		assertThat(exchange.getIn().getBody(Vendedor.class)).isEqualTo(vendedor);
	}

	@Test(expected = ControleVendasException.class)
	public void deveLancarExceptionComConteudoInvalido() throws Exception {
		exchange = ExchangeTestUtils.criarExchange(mapHeaders, linhaRegistroCliente);
		processor.process(exchange);
	}

}
