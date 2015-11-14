package br.com.aprendendo.camel.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;

import br.com.aprendendo.camel.constants.TipoContato;

@ContextName
public class SimpleRoute extends RouteBuilder {
	
	final String CAMINHO_PROPERTIES_ENTRADA = "/data/entrada/";
	final String CAMINHO_PROPERTIES_SAIDA = "/data/contatos/";
	
	final String CAMINHO_REAL_ENTRADA = getClass().getResource(CAMINHO_PROPERTIES_ENTRADA).getPath();
	final String CAMINHO_REAL_SAIDA = getClass().getResource(CAMINHO_PROPERTIES_SAIDA).getPath();
	
	@Override
	public void configure() throws Exception {
		
		from("file:" + CAMINHO_REAL_ENTRADA + "?delay=5s")
		.log(LoggingLevel.INFO, "Movendo arquivo: ${file:name}")
		.choice()
			.when()
				.xpath("/person/tipoContato[text() = '" + TipoContato.ESCOLA.name() + "']")
				.to("file:" + CAMINHO_REAL_SAIDA + TipoContato.ESCOLA.name())
			.when()
				.xpath("/person/tipoContato[text() = '" + TipoContato.FAMILIA.name() + "']")
				.to("file:" + CAMINHO_REAL_SAIDA + TipoContato.FAMILIA.name())
			.when()
				.xpath("/person/tipoContato[text() = '" + TipoContato.TRABALHO.name() + "']")
				.to("file:" + CAMINHO_REAL_SAIDA + TipoContato.TRABALHO.name())
			.when()
				.xpath("/person/tipoContato[text() = '" + TipoContato.OUTROS.name() + "']")
				.to("file:" + CAMINHO_REAL_SAIDA + TipoContato.OUTROS.name())
		.end();
		
	}

}
