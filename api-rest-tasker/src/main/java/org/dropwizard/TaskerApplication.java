package org.dropwizard;

import org.dropwizard.core.JerseryValidationsExceptionMapper;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.dropwizard.core.JacksonExceptionMapper;
import org.dropwizard.db.TaskDAO;
import org.dropwizard.health.TemplateHealth;
import org.dropwizard.resources.TasksResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;
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

        // filters
        configureCorsFilter(environment);

    }

    private void configureCorsFilter(final Environment environment) {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
                "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        cors.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }

}
