package br.com.ilegra.api.vendas.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ilegra.api.vendas.domain.ItemVenda;
import br.com.ilegra.api.vendas.domain.Venda;
import br.com.ilegra.api.vendas.utils.BuilderTestUtils;

@RunWith(SpringRunner.class)
public class VendaConverterTest {

	private Venda venda;
	private String linhaRegistro;

	@InjectMocks
	private VendaConverter vendaConverter;

	@Spy
	private ItemVendaConverter itemVendaConverter;

	@Before
	public void setUp() throws Exception {
		ItemVenda itemVenda = BuilderTestUtils.itemVenda(1, 4, 14.5d);
		String itemStr = BuilderTestUtils.linhaRegistroItemVenda(1, 4, 14.5d);
		venda = BuilderTestUtils.venda("01", "Lorenzo Snow", Arrays.asList(itemVenda));
		linhaRegistro = BuilderTestUtils.linhaRegistroVenda("01", Arrays.asList(itemStr), "Lorenzo Snow");
	}

	@Test
	public final void testConvert() throws Exception {
		assertThat(vendaConverter.convert(linhaRegistro)).isEqualTo(venda);
	}

}
