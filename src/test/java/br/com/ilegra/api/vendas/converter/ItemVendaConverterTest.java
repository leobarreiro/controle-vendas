package br.com.ilegra.api.vendas.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ilegra.api.vendas.domain.ItemVenda;
import br.com.ilegra.api.vendas.utils.BuilderTestUtils;

@RunWith(SpringRunner.class)
public class ItemVendaConverterTest {

	private ItemVenda itemVenda;
	private String linhaRegistro;
	
	@InjectMocks
	private ItemVendaConverter itemVendaConverter;
	
	@Before
	public void setUp() throws Exception {
		itemVenda = BuilderTestUtils.itemVenda(1, 4, 14.5d);
		linhaRegistro = BuilderTestUtils.linhaRegistroItemVenda(1, 4, 14.5d);
	}

	@Test
	public final void testConvert() throws Exception {
		assertThat(itemVendaConverter.convert(linhaRegistro)).isEqualTo(itemVenda);
	}

}
