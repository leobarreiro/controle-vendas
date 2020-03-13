package br.com.ilegra.api.vendas.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ilegra.api.vendas.utils.BuilderTestUtils;
import br.com.ilegra.api.vendas.utils.ExchangeTestUtils;

@RunWith(SpringRunner.class)
public class RegistroVendaPredicateTest {

	private String registroVendaInvalido;
	private String registroVendaValido;
	private Exchange excConteudoValido;
	private Exchange excConteudoInvalido;

	@InjectMocks
	private RegistroVendaPredicate predicate;

	@Before
	public void setUp() throws Exception {
		registroVendaInvalido = BuilderTestUtils.linhaRegistroVenda("aa", Arrays.asList("1-34-10", "2-33-1.50", "3-40-0.10"), "123456789");
		registroVendaValido = BuilderTestUtils.linhaRegistroVenda("08", Arrays.asList("1-34-10","2-33-1.50","3-40-0.10"), "Lorenzo Snow");
		excConteudoValido = ExchangeTestUtils.criarExchange(registroVendaValido);
		excConteudoInvalido = ExchangeTestUtils.criarExchange(registroVendaInvalido);
	}

	@Test
	public void deveDarMatchComRegistroDeVendaValido() {
		assertTrue(predicate.matches(excConteudoValido));
	}

	@Test
	public void naoDeveDarMatchComRegistroDeVendaInvalido() {
		assertFalse(predicate.matches(excConteudoInvalido));
	}

}
