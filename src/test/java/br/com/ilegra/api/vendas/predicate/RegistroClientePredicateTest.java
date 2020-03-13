package br.com.ilegra.api.vendas.predicate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RegistroClientePredicateTest {

	@InjectMocks
	private RegistroClientePredicate predicate;

	private String registroClienteInvalido;
	private String registroClienteValido;

	@Before
	public void setUp() throws Exception {
		registroClienteInvalido = "";
	}

	@Test
	public void testMatches() {
		// predicate.matches(exchange);
	}

}
