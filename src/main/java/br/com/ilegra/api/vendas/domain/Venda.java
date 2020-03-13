package br.com.ilegra.api.vendas.domain;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venda implements Serializable {

	private static final long serialVersionUID = 1L;

	private String saleId;
	private List<ItemVenda> itemsVenda;
	private String salesmanName;

}
