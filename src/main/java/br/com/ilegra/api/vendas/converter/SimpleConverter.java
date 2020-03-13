package br.com.ilegra.api.vendas.converter;

public interface SimpleConverter<T> {

	T convert(String conteudo);

}
