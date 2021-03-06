package br.com.ilegra.api.vendas.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemVenda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer itemId;
	private Integer quantidade;
	private BigDecimal preco;

}
