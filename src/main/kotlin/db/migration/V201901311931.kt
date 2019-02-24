package db.migration

import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import java.lang.StringBuilder


class V201901311931: BaseJavaMigration() {

    private val CREATE_PESSOA: StringBuilder = StringBuilder(
            "CREATE TABLE PESSOAS" +
            "(" +
            "    CODIGO SERIAL NOT NULL," +
            "    NOME VARCHAR(140) NOT NULL," +
            "    EMAIL VARCHAR(140) NOT NULL," +
            "    SENHA VARCHAR(140) NOT NULL," +
            "    PRIMARY KEY (CODIGO)" +
            ");")

    private val CREATE_TAG: StringBuilder = StringBuilder(
            "CREATE TABLE TAGS" +
            "(" +
            "    CODIGO SERIAL NOT NULL," +
            "    DESCRICAO VARCHAR(140) NOT NULL," +
            "    PRIMARY KEY (CODIGO)" +
            ");")

    private val CREATE_POST : StringBuilder = StringBuilder(
            "CREATE TABLE POSTS" +
            "(" +
            "    CODIGO SERIAL NOT NULL," +
            "    TITULO VARCHAR(140) NOT NULL," +
            "    DESCRICAO TEXT NOT NULL," +
            "    DATACRIACAO TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "    CODIGO_PESSOA BIGINT NOT NULL," +
            "    DATAEDICAO TIMESTAMP," +
            "    PRIMARY KEY (CODIGO)," +
            "    FOREIGN KEY (CODIGO_PESSOA) REFERENCES PESSOAS (CODIGO)" +
            ");")

    private val CREATE_POST_TAG : StringBuilder = StringBuilder(
            "CREATE TABLE POSTS_TAGS" +
            "(" +
            "    CODIGO_POST BIGINT NOT NULL," +
            "    CODIGO_TAG BIGINT NOT NULL," +
            "    PRIMARY KEY (CODIGO_POSTS, CODIGO_TAGS)," +
            "    FOREIGN KEY (CODIGO_POSTS) REFERENCES POSTS (CODIGO)" +
            "    FOREIGN KEY (CODIGO_TAGS) REFERENCES TAGS (CODIGO)" +
            ");")

    override fun migrate(context: Context?) {
        val jdbcTemplate = JdbcTemplate(SingleConnectionDataSource(context?.connection!!, true))
        jdbcTemplate.execute(CREATE_PESSOA.toString())
        jdbcTemplate.execute(CREATE_TAG.toString())
        jdbcTemplate.execute(CREATE_POST.toString())
        jdbcTemplate.execute(CREATE_POST_TAG.toString())
    }

}