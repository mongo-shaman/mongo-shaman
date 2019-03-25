package io.github.mongoshaman.core.providers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mongoshaman.core.domain.MigrationFile;
import io.github.mongoshaman.core.domain.Statement;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;

public class MigrationFilesToStatementsConverter {

  private static final Logger log = LoggerFactory.getLogger(MigrationFilesToStatementsConverter.class);

  public List<Statement> convert(final MigrationFile file) {
    final Options options = new Options("nashorn");
    options.set("parse.only", true);
    options.set("scripting", true);

    final ErrorManager errors = new ErrorManager();
    final Context context = new Context(options, errors, Thread.currentThread().getContextClassLoader());
    final Parser parser = new Parser(context.getEnv(), Source.sourceFor("test", file.getContent()), errors);

    return parser.parse().getBody().getStatements().stream().map(this::toStatement).collect(Collectors.toList());
  }

  private Statement toStatement(final jdk.nashorn.internal.ir.Statement stmt) {
    final Statement statement = new Statement(stmt.getLineNumber(), stmt.toString(false));
    if (log.isTraceEnabled()) {
      log.trace(statement.toString());
    }
    return statement;
  }


}
