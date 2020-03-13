package br.com.ilegra.api.vendas.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ilegra.api.vendas.domain.Vendedor;
import br.com.ilegra.api.vendas.utils.BuilderTestUtils;

@RunWith(SpringRunner.class)
public class VendedorConverterTest {

	private Vendedor vendedor;
	private String linhaRegistro;

	@InjectMocks
	private VendedorConverter vendedorConverter;

	@Before
	public void setUp() throws Exception {
		vendedor = BuilderTestUtils.vendedorPadrao();
		linhaRegistro = BuilderTestUtils.linhaRegistroVendedorPadrao();
	}

	@Test
	public final void testConvert() throws Exception {
		assertThat(vendedorConverter.convert(linhaRegistro)).isEqualTo(vendedor);
	}

}
