package com.maersk.booking.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "com.maersk.booking.model")
public class CassandraConfig extends AbstractCassandraConfiguration {

    private
    SessionFactoryFactoryBean sessionFactoryFactoryBean = new ExecutableSessionFactoryFactoryBean();


    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;

    @Value("${spring.data.cassandra.schema-action}")
    private String appSchemaAction;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keyspace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return List.of(specification);
    }

    //@Override
    //protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
    //    return List.of(DropKeyspaceSpecification.dropKeyspace(keyspace));
    //}

    @Override
    @Bean
    @Primary
    public SessionFactoryFactoryBean cassandraSessionFactory(CqlSession cqlSession) {
        sessionFactoryFactoryBean = new ExecutableSessionFactoryFactoryBean();

        // Initialize the CqlSession reference first since it is required, or must not be null!
        sessionFactoryFactoryBean.setSession(cqlSession);

        sessionFactoryFactoryBean.setConverter(requireBeanOfType(CassandraConverter.class));
        sessionFactoryFactoryBean.setKeyspaceCleaner(keyspaceCleaner());
        sessionFactoryFactoryBean.setKeyspacePopulator(keyspacePopulator());
        sessionFactoryFactoryBean.setSchemaAction(getSchemaAction());

        return sessionFactoryFactoryBean;
    }

    @Override
    public SchemaAction getSchemaAction() {

        switch (this.appSchemaAction) {
            case "RECREATE_DROP_UNUSED":
                return SchemaAction.RECREATE_DROP_UNUSED;
            case "RECREATE":
                return SchemaAction.RECREATE;
            case "CREATE_IF_NOT_EXISTS":
                return SchemaAction.CREATE_IF_NOT_EXISTS;
            case "CREATE":
                return SchemaAction.CREATE;
            default:
                return SchemaAction.NONE;
        }
    }
}