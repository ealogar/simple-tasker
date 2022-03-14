package org.dropwizard.db;

import java.util.List;

import org.dropwizard.api.Task;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


public interface TaskDAO {

    @SqlQuery("select id, description, dueDate, completed from tasks where id = :id")
    @RegisterBeanMapper(Task.class)
    Task getTaskById(@Bind("id") Long id);

    @SqlQuery("select id, description, dueDate, completed FROM tasks ORDER BY dueDate")
    @RegisterBeanMapper(Task.class)
    List<Task> getAllTasks();

    @SqlUpdate("insert into tasks (description, dueDate) values (:description, :dueDate)")
    @GetGeneratedKeys
    @RegisterBeanMapper(Task.class)
    Task insert(@BindBean Task task);

    @SqlUpdate("update tasks set description = :description, dueDate = :dueDate, completed = :completed where id = :id")
    @GetGeneratedKeys
    @RegisterBeanMapper(Task.class)
    Task update(@BindBean Task task);

    @SqlUpdate("delete from tasks where id = :id")
    void delete(@Bind("id") Long id);

}
