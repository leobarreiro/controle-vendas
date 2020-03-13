package br.com.ilegra.api.vendas.config;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@Getter
public class ControleVendasConfig {

	private static final String OUT = "out";

	private static final String IN = "in";

	private static final String HOMEPATH = "HOMEPATH";

	private String homePath;
	private String routeEntrada;
	private String routeSaida;

	public ControleVendasConfig() {
		testHomePath();
	}

	private void testHomePath() throws RuntimeException {
		if (System.getenv(HOMEPATH) == null) {
			throw new RuntimeException("A variável de ambiente 'HOMEPATH' não está definida. Impossível prosseguir");
		}

		homePath = FilenameUtils.normalize(System.getenv(HOMEPATH).concat(File.separator));
		if (homePath == null) {
			throw new RuntimeException(MessageFormat.format("Erro ao normalizar a variável de ambiente 'HOMEPATH'. [{0}]", System.getenv(HOMEPATH)));
		}
		File diretorio = new File(homePath);
		try {
			FileUtils.forceMkdir(diretorio);
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
			throw new RuntimeException("Não foi possível verificar a existência do diretório 'HOMEPATH'");
		}

		routeEntrada = "file:".concat(homePath).concat(IN).concat("?readLock=changed&readLockTimeout=100&readLockCheckInterval=20&maxMessagesPerPoll=1");
		routeSaida = "file:".concat(homePath).concat(OUT);

	}

}
