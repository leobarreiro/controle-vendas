package br.com.ilegra.api.vendas.bean;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ilegra.api.vendas.utils.BuilderTestUtils;
import br.com.ilegra.api.vendas.utils.ExchangeTestUtils;

@RunWith(SpringRunner.class)
public class ConsolidadorBeanTest {

	private static final String ARQUIVO_IN = "arquivo.txt";
	private static final String ARQUIVO_OUT = "arquivo.out";

	@InjectMocks
	private ConsolidadorBean bean;

	Exchange excVendedor;
	Exchange excCliente;
	Exchange excVenda;

	Exchange exchange;

	@Before
	public void setUp() throws Exception {
		Map<String, Object> mapHeader = new HashMap<String, Object>();
		mapHeader.put(Exchange.FILE_NAME, ARQUIVO_IN);
		excVendedor = ExchangeTestUtils.criarExchange(mapHeader, BuilderTestUtils.vendedorPadrao());
		excCliente = ExchangeTestUtils.criarExchange(BuilderTestUtils.clientePadrao());
		excVenda = ExchangeTestUtils.criarExchange(BuilderTestUtils.vendaPadrao());
		exchange = ExchangeTestUtils.criarExchange(Arrays.asList(excVendedor, excCliente, excVenda));
	}

	@Test
	public void testTransformConteudo() throws Exception {
		bean.transformConteudo(exchange, Arrays.asList(excVendedor, excCliente, excVenda));
		assertThat(exchange.getIn().getBody(String.class)).contains("Quantidade de Clientes: 1", "Quantidade de Vendedores: 1", "ID da Venda mais cara: 01");
		assertThat(exchange.getIn().getHeader(Exchange.FILE_NAME)).isEqualTo(ARQUIVO_OUT);
	}
}
