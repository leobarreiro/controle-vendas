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
public class RegistroClientePredicateTest {

	@InjectMocks
	private RegistroClientePredicate predicate;

	private String registroClienteInvalido;
	private String registroClienteValido;
	private Exchange excConteudoValido;
	private Exchange excConteudoInvalido;

	@Before
	public void setUp() throws Exception {
		registroClienteInvalido = BuilderTestUtils.linhaRegistroCliente("997898000146", "777", "Cozinha");
		registroClienteValido = BuilderTestUtils.linhaRegistroCliente("99789865000146", "Hamburgueria Da Brasa", "Cozinha");
		excConteudoValido = ExchangeTestUtils.criarExchange(registroClienteValido);
		excConteudoInvalido = ExchangeTestUtils.criarExchange(registroClienteInvalido);
	}

	@Test
	public void deveDarMatchComRegistroDeClienteValido() {
		assertTrue(predicate.matches(excConteudoValido));
	}

	@Test
	public void naoDeveDarMatchComRegistroDeClienteInvalido() {
		assertFalse(predicate.matches(excConteudoInvalido));
	}

}
