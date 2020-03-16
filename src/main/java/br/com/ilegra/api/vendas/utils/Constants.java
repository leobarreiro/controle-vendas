package br.com.ilegra.api.vendas.utils;

public class Constants {

	public static final String ROUTE_PROCESSA_LINHAS = "direct:processa-linhas";
	public static final String ROUTE_ENCODING = "direct:codificacao-arquivo-valida";
	public static final String ROUTE_SPLITER = "direct:spliter";
	public static final String ROUTE_JOINER = "direct:joiner";
	public static final String ROUTE_CONSOLIDADOR = "direct:consolidador";

	public static final String ROUTEID_ENTRADA = "routeEntradaId";
	public static final String ROUTEID_PROCESSA_LINHAS = "processaLinhasId";
	public static final String ROUTEID_ENCODING = "routeEncodingId";
	public static final String ROUTEID_SPLITER = "routeSpliterId";
	public static final String ROUTEID_JOINER = "routeJoinerId";
	public static final String ROUTEID_CONSOLIDADOR = "routeConsolidadorId";
	public static final String ROUTEID_SAIDA = "routeSaidaId";

	public static final String SEPARATOR = "ç";

	public static final String REGISTRO_VENDEDOR_PATTERN = "^(001)ç[0-9]{11}ç[a-zçãáàéêíóôúA-Z ]{3,}[a-z]{1,}ç[0-9]{1,}(\\.){0,1}[0-9]{0,2}$";
	public static final String REGISTRO_CLIENTE_PATTERN = "^(002)ç[0-9]{14}ç[a-zA-Z ]{3,}ç[a-zA-Z ]{3,}$";
	public static final String REGISTRO_VENDA_PATTERN = "^(003)ç[0-9]{1,}ç(\\[){1}(([0-9]{1,}-[0-9]{1,}-[0-9\\.]{1,})(\\,){0,}){1,}(\\]){1}ç[a-zçãáàéêíóôúA-Z ]{3,}$";

	public static final String VENDEDOR_SPLIT = "^(001)|ç|[0-9]{11}|ç|[a-zçãáàéêíóôúA-Z ]{3,}[a-z]{1,}|ç|[0-9]{1,}(\\.){0,1}[0-9]{0,2}$";
	public static final String CLIENTE_SPLIT = "^(002)|ç|[0-9]{14}|ç|[a-zA-Z ]{3,}|ç|[a-zA-Z ]{3,}$";
	public static final String VENDA_SPLIT = "^(003)|ç|[0-9]{1,}|ç|(\\[){1}(([0-9]{1,}-[0-9]{1,}-[0-9\\.]{1,})(\\,){0,}){1,}(\\]){1}|ç|[a-zçãáàéêíóôúA-Z ]{3,}$";

}
