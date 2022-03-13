import { putJson } from "../helpers/fetchJson";

function Task({ task, onTglStatus }) {
  const onCompleteTask = async () => {
    //TODO: do something to prevent double clicking
    //TODO: in general control timeout in be responsed and give feedback to user

    let result = await putJson(`/tasks/${task.id}`,
      { description: task.description, due_date: task.due_date, completed: !task.completed });

    if (result.data) {
      onTglStatus(task);
    } else {
      console.log("failed to update task");
      console.log(result.error);
    }

  };

  return (
    <div className="card text-left" key={task.id}>
      <div className="row">
        <div className="col-10">
          <h4>{task.description}</h4>
          <div className="task-meta">
            <img
              src="https://icongr.am/feather/calendar.svg?size=12&color=b5b5b5"
              alt="calendar"
            />
            {task.due_date}
          </div>
        </div>

        <div className="col-2 is-center">
          <button
            className="button icon-only clear"
            onClick={onCompleteTask}>
            {task.completed && "✅"}
            {!task.completed && "⬜"}
          </button>
        </div>
      </div>
    </div>
  );
}

export default Task;
