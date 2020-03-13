package br.com.ilegra.api.vendas.utils;

public class Constants {

	public static final String VENDEDOR_PATTERN = "^(001)ç[0-9]{11}ç[a-zA-Z ]{3,}ç[0-9]{1,}(.){0,1}[0-9]{0,2}$";
	public static final String CLIENTE_PATTERN = "^(002)ç[0-9]{14}ç[a-zA-Z ]{3,}ç[a-zA-Z ]{3,}$";
	public static final String VENDA_PATTERN = "^(003)ç[0-9]{1,}ç(\\[){1}(([0-9]{1,}-[0-9]{1,}-[0-9\\.]{1,})(\\,){0,}){1,}(\\]){1}ç[a-zA-Z ]{3,}$";
	

}
