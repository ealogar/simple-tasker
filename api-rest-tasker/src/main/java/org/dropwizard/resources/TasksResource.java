package org.dropwizard.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;

import org.dropwizard.api.Task;
import org.dropwizard.db.TaskDAO;


@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TasksResource {

    final TaskDAO taskDAO;

    public TasksResource(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    
    @GET
    @Timed
    @Metered(name = "get-all-tasks")
    public List<Task> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    @POST
    @Timed
    @Metered(name = "create-task")
    public Task createTask(@NotNull @Valid Task task) {
        return taskDAO.insert(task);
    }

    @GET
    @Path("/{id}")
    @Timed
    @Metered(name = "get-task")
    public Task getTask(@PathParam("id") Long id ) {
        return taskDAO.getTaskById(id);
    }  

    @DELETE
    @Path("/{id}")
    @Timed
    @Metered(name = "delete-task")
    public void deleteTask(@PathParam("id") Long id ) {
        taskDAO.delete(id);
    }

    @PUT
    @Path("/{id}")
    @Timed
    @Metered(name = "update-task")
    public Task updateTask(@PathParam("id") Long id, @NotNull @Valid Task task) {
        task.setId(id);
        return taskDAO.update(task);
    }    

}
