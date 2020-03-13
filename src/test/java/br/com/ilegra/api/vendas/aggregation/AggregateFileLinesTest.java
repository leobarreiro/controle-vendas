package br.com.ilegra.api.vendas.aggregation;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ilegra.api.vendas.utils.ExchangeTestUtils;
import br.com.ilegra.api.vendas.utils.BuilderTestUtils;

@RunWith(SpringRunner.class)
public class AggregateFileLinesTest {

	private Exchange oldExchange;
	private Exchange newExchange;
	
	@InjectMocks
	private AggregateFileLines aggregator;
	
	@Before
	public void setUp() throws Exception {
		oldExchange = ExchangeTestUtils.criarExchange(BuilderTestUtils.linhaRegistroVendedorPadrao());
		newExchange = ExchangeTestUtils.criarExchange(BuilderTestUtils.linhaRegistroClientePadrao());
	}

	@Test
	public final void testAggregate() throws Exception {
		assertThat(aggregator.aggregate(oldExchange, newExchange)).isEqualTo(newExchange);
	}

}
