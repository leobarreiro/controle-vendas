package br.com.ilegra.api.vendas.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.camel.Exchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ilegra.api.vendas.utils.BuilderTestUtils;
import br.com.ilegra.api.vendas.utils.ExchangeTestUtils;

@RunWith(SpringRunner.class)
public class RegistroVendedorPredicateTest {

	private String registroVendedorInvalido;
	private String registroVendedorValido;
	private Exchange excConteudoValido;
	private Exchange excConteudoInvalido;

	@InjectMocks
	private RegistroVendedorPredicate predicate;

	@Before
	public void setUp() throws Exception {
		registroVendedorInvalido = BuilderTestUtils.linhaRegistroVendedor("898551556", "Heber J Grant", 5000d);
		registroVendedorValido = BuilderTestUtils.linhaRegistroVendedor("89855155602", "Heber J Grant", 7500d);
		excConteudoValido = ExchangeTestUtils.criarExchange(registroVendedorValido);
		excConteudoInvalido = ExchangeTestUtils.criarExchange(registroVendedorInvalido);
	}

	@Test
	public void deveDarMatchComRegistroDeVendedorValido() {
		assertTrue(predicate.matches(excConteudoValido));
	}

	@Test
	public void naoDeveDarMatchComRegistroDeVendedorInvalido() {
		assertFalse(predicate.matches(excConteudoInvalido));
	}

}
