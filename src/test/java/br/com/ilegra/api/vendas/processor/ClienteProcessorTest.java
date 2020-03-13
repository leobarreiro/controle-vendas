package br.com.ilegra.api.vendas.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ilegra.api.vendas.converter.ClienteConverter;
import br.com.ilegra.api.vendas.domain.Cliente;
import br.com.ilegra.api.vendas.exception.ControleVendasException;
import br.com.ilegra.api.vendas.utils.BuilderTestUtils;
import br.com.ilegra.api.vendas.utils.ExchangeTestUtils;

@RunWith(SpringRunner.class)
public class ClienteProcessorTest {

	private Cliente cliente;
	private String linhaRegistroCliente;
	private String linhaRegistroVendedor;
	private Exchange exchange;
	private Map<String, Object> mapHeaders;

	@InjectMocks
	private ClienteProcessor processor;

	@Spy
	private ClienteConverter converter;

	@Before
	public void setUp() throws Exception {
		linhaRegistroCliente = BuilderTestUtils.linhaRegistroClientePadrao();
		cliente = BuilderTestUtils.clientePadrao();
		linhaRegistroVendedor = BuilderTestUtils.linhaRegistroVendedorPadrao();
		mapHeaders = new HashMap<>();
		mapHeaders.put(Exchange.FILE_NAME, "arquivo.txt");
	}

	@Test
	public void deveProcessarSemErros() throws Exception {
		exchange = ExchangeTestUtils.criarExchange(mapHeaders, linhaRegistroCliente);
		processor.process(exchange);
		verify(converter).convert(linhaRegistroCliente);
		assertThat(exchange.getIn().getBody(Cliente.class)).isEqualTo(cliente);
	}
	
	@Test(expected = ControleVendasException.class)
	public void deveLancarExceptionComConteudoInvalido() throws Exception {
		exchange = ExchangeTestUtils.criarExchange(mapHeaders, linhaRegistroVendedor);
		processor.process(exchange);
	}

}
