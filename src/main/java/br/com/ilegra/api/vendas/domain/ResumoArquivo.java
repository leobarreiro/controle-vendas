package br.com.ilegra.api.vendas.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResumoArquivo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Default
	private List<Vendedor> vendedores = new ArrayList<>();
	
	@Default
	private List<Venda> vendas = new ArrayList<>();
	
	@Default
	private List<Cliente> clientes = new ArrayList<>();
	
	private Vendedor piorVendedor;
	
	private Venda maiorVenda;

}
