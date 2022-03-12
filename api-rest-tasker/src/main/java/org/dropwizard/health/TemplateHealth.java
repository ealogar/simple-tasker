package org.dropwizard.health;

import com.codahale.metrics.health.HealthCheck;

public class TemplateHealth extends HealthCheck {

    private final String template;

    public TemplateHealth(String template){
        this.template = template;
    } 

    @Override
    protected Result check() throws Exception {
        // IllegalArgument in template will make the check to fail implicitely
        String templateFormatted = String.format(template, "check");
        if (!templateFormatted.contains("check")) {
            return Result.unhealthy("Template config is not correct");
        }
        return Result.healthy();
    }
    
}
