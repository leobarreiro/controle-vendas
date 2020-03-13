package br.com.ilegra.api.vendas.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ilegra.api.vendas.domain.Cliente;
import br.com.ilegra.api.vendas.utils.BuilderTestUtils;

@RunWith(SpringRunner.class)
public class ClienteConverterTest {

	private Cliente cliente;
	private String linhaRegistro;

	@InjectMocks
	private ClienteConverter clienteConverter;

	@Before
	public void setUp() throws Exception {
		cliente = BuilderTestUtils.cliente("99789865000146", "Hamburgueria Da Brasa", "Cozinha");
		linhaRegistro = BuilderTestUtils.linhaRegistroCliente("99789865000146", "Hamburgueria Da Brasa", "Cozinha");
	}

	@Test
	public final void testConvert() throws Exception {
		assertThat(clienteConverter.convert(linhaRegistro)).isEqualTo(cliente);
	}

}
