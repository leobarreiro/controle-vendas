package br.com.ilegra.api.vendas.bean;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import br.com.ilegra.api.vendas.domain.Cliente;
import br.com.ilegra.api.vendas.domain.ResumoArquivo;
import br.com.ilegra.api.vendas.domain.Venda;
import br.com.ilegra.api.vendas.domain.Vendedor;

@Component
public class ConsolidadorBean {

	public static final String NENHUMA_VENDA = "N/A";
	private static final String EXTENSAO_SAIDA = "out";
	private String idMaiorVenda = null;
	private Double maiorValor = 0d;
	private String piorVendedor = null;
	private Double piorSoma = null;

	public void transformConteudo(Exchange exchange, @Body List<Exchange> excs) {
		montaConteudoArquivoSaida(exchange, montaResumoArquivo(excs));
		defineNomeArquivo(exchange, excs.get(0));
	}

	private ResumoArquivo montaResumoArquivo(List<Exchange> excs) {
		ResumoArquivo resumo = ResumoArquivo.builder().build();
		excs.stream().forEach(ex -> {
			if (ex.getIn().getBody() instanceof Venda) {
				resumo.getVendas().add(ex.getIn().getBody(Venda.class));
			} else if (ex.getIn().getBody() instanceof Vendedor) {
				resumo.getVendedores().add(ex.getIn().getBody(Vendedor.class));
			} else if (ex.getIn().getBody() instanceof Cliente) {
				resumo.getClientes().add(ex.getIn().getBody(Cliente.class));
			}
		});
		return resumo;
	}

	private void defineNomeArquivo(Exchange exchange, Exchange childExchange) {
		String nomeOriginal = FilenameUtils.getBaseName((String) childExchange.getIn().getHeader(Exchange.FILE_NAME));
		exchange.getIn().setHeader(Exchange.FILE_NAME, nomeOriginal.concat(FilenameUtils.EXTENSION_SEPARATOR_STR).concat(EXTENSAO_SAIDA));
	}

	private void montaConteudoArquivoSaida(Exchange exchange, ResumoArquivo resumo) {
		calculaIdVendaMaisCara(resumo.getVendas());
		calculaPiorVendedor(resumo.getVendas());
		String texto = "Quantidade de Clientes: {0}\nQuantidade de Vendedores: {1}\nID da Venda mais cara: {2}\nPior vendedor: {3}";
		String saida = MessageFormat.format(texto, Integer.toString(resumo.getClientes().size()), Integer.toString(resumo.getVendedores().size()), idMaiorVenda, piorVendedor);
		exchange.getIn().setBody(saida);
	}

	private void calculaIdVendaMaisCara(List<Venda> vendas) {
		idMaiorVenda = null;
		maiorValor = 0d;
		Map<String, Double> mapVendas = new HashMap<>();
		vendas.stream().forEach(venda -> {
			Double soma = venda.getItemsVenda().stream()
					.map(item -> (item.getPreco().doubleValue() * item.getQuantidade()))
					.reduce(0d, Double::sum);
			mapVendas.put(venda.getSaleId(), soma);
		});

		mapVendas.keySet().forEach(saleId -> {
			if (mapVendas.get(saleId) > maiorValor) {
				idMaiorVenda = saleId;
				maiorValor = mapVendas.get(saleId);
			}
		});

		if (idMaiorVenda == null) {
			idMaiorVenda = NENHUMA_VENDA;
		}
	}

	public void calculaPiorVendedor(List<Venda> vendas) {
		piorSoma = null;
		piorVendedor = null;
		Map<String, Double> mapVendedorTotais = new HashMap<>();
		vendas.stream().forEach(venda -> {
			Double soma = venda.getItemsVenda().stream()
					.map(item -> (item.getPreco().doubleValue() * item.getQuantidade()))
					.reduce(0d, Double::sum);
			if (mapVendedorTotais.containsKey(venda.getSalesmanName())) {
				mapVendedorTotais.put(venda.getSalesmanName(), mapVendedorTotais.get(venda.getSalesmanName()) + soma);
			} else {
				mapVendedorTotais.put(venda.getSalesmanName(), soma);
			}
		});

		mapVendedorTotais.entrySet().stream().forEach(vt -> {
			if (piorSoma == null) {
				piorVendedor = vt.getKey();
				piorSoma = vt.getValue();
			} else {
				if (piorSoma > vt.getValue()) {
					piorVendedor = vt.getKey();
					piorSoma = vt.getValue();
				}
			}
		});

		if (piorVendedor == null) {
			piorVendedor = NENHUMA_VENDA;
		}
	}

}
