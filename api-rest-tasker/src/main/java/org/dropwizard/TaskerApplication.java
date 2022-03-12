package org.dropwizard;

import org.dropwizard.core.JerseryValidationsExceptionMapper;
import org.dropwizard.core.JacksonExceptionMapper;
import org.dropwizard.db.TaskDAO;
import org.dropwizard.health.TemplateHealth;
import org.dropwizard.resources.TasksResource;
import org.jdbi.v3.core.Jdbi;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TaskerApplication extends Application<TaskerConfiguration> {


    public static void main(final String[] args) throws Exception {
        new TaskerApplication().run(args);
    }

    @Override
    public String getName() {
        return "tasker";
    }

    @Override
    public void initialize(final Bootstrap<TaskerConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(), 
                new EnvironmentVariableSubstitutor(false)));
    }

    @Override
    public void run(final TaskerConfiguration configuration,
            final Environment environment) {
        // database and DAOs
        final JdbiFactory factory = new JdbiFactory();
        // This will register automatically a healthcheck for database
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        final TaskDAO taskDAO = jdbi.onDemand(TaskDAO.class);
        // resources
        final TasksResource helloWorld = new TasksResource(taskDAO);
        environment.jersey().register(helloWorld);

        final TemplateHealth templateHealth = new TemplateHealth(configuration.getTemplate());
        environment.healthChecks().register("template", templateHealth);

        // exception mappers
        // TODO: consider configuring server.registerDefaultExceptionMappers to false
        // and build all exception mappers in our preferred way
        environment.jersey().register(new JacksonExceptionMapper());
        environment.jersey().register(new JerseryValidationsExceptionMapper());
    }

}
