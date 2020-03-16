package br.com.ilegra.api.vendas.utils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;

import br.com.ilegra.api.vendas.domain.Cliente;
import br.com.ilegra.api.vendas.domain.ItemVenda;
import br.com.ilegra.api.vendas.domain.Venda;
import br.com.ilegra.api.vendas.domain.Vendedor;

public class BuilderTestUtils {

	public static final String CPF_VENDEDOR = "42166998046";
	public static final String NOME_VENDEDOR = "Elder Ulisses Soares";
	public static final Double SALARIO_VENDEDOR = Double.valueOf(4500d);
	public static final String CNPJ_CLIENTE = "20223448000181";
	public static final String NOME_CLIENTE = "Hamburgueria Da Brasa";
	public static final String AREA_CLIENTE = "Gastronomia";

	public static String linhaRegistroVendedorPadrao() {
		return linhaRegistroVendedor(CPF_VENDEDOR, NOME_VENDEDOR, SALARIO_VENDEDOR);
	}

	public static Vendedor vendedorPadrao() {
		return vendedor(CPF_VENDEDOR, NOME_VENDEDOR, SALARIO_VENDEDOR);
	}

	public static String linhaRegistroClientePadrao() {
		return linhaRegistroCliente(CNPJ_CLIENTE, NOME_CLIENTE, AREA_CLIENTE);
	}

	public static Cliente clientePadrao() {
		return cliente(CNPJ_CLIENTE, NOME_CLIENTE, AREA_CLIENTE);
	}

	public static Cliente cliente(String cnpj, String nome, String area) {
		return Cliente.builder().cnpj(cnpj).nome(nome).area(area).build();
	}

	public static String linhaRegistroCliente(String cnpj, String nome, String area) {
		return MessageFormat.format("002ç{0}ç{1}ç{2}", cnpj, nome, area);
	}

	public static Vendedor vendedor(String cpf, String nome, double salario) {
		return Vendedor.builder().cpf(cpf).nome(nome).salario(BigDecimal.valueOf(salario).setScale(2)).build();
	}

	public static String linhaRegistroVendedor(String cpf, String nome, double salario) {
		return MessageFormat.format("001ç{0}ç{1}ç{2}", cpf, nome, BigDecimal.valueOf(salario).setScale(2).toString());
	}

	public static Venda venda(String saleId, String vendedor, List<ItemVenda> itensVenda) {
		return Venda.builder().saleId(saleId).salesmanName(vendedor).itensVenda(itensVenda).build();
	}

	public static String linhaRegistroVenda(String saleId, List<String> itensVenda, String nomeVendedor) {
		String itens = StringUtils.join(itensVenda, ",");
		return MessageFormat.format("003ç{0}ç[{1}]ç{2}", saleId, itens, nomeVendedor);
	}

	public static ItemVenda itemVenda(Integer idItem, Integer quantidade, double valorUnitario) {
		return ItemVenda.builder().itemId(idItem).quantidade(quantidade).preco(BigDecimal.valueOf(valorUnitario).setScale(2)).build();
	}

	public static String linhaRegistroItemVenda(Integer idItem, Integer quantidade, double valorUnitario) {
		return MessageFormat.format("{0}-{1}-{2}", Integer.toString(idItem), Integer.toString(quantidade), BigDecimal.valueOf(valorUnitario).setScale(2).toString());
	}

	public static String conteudoMinimoArquivo() {
		return linhaRegistroVendedorPadrao().concat("\n").concat(linhaRegistroClientePadrao());
	}

	public static Map<String, Object> headerFileLength() {
		Map<String, Object> mapHeaders = new HashMap<>();
		mapHeaders.put(Exchange.FILE_LENGTH, 61);
		return mapHeaders;
	}

}
