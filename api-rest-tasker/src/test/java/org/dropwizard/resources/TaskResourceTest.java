package org.dropwizard.resources;

import org.dropwizard.db.TaskDAO;
import org.junit.Test;
import org.dropwizard.api.Task;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

public class TaskResourceTest {
    private final TaskDAO taskDAO = mock(TaskDAO.class);
    private final TasksResource resource = new TasksResource(taskDAO);

    @Test
    public void getsReturnNotifications() {
        final Task task = new Task(1L, "test", new Date());
        final List<Task> tasks = List.of(task);
        when(resource.getAllTasks()).thenReturn(tasks);

        final List<Task> list = resource.getAllTasks();

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId().longValue());
    }
}
