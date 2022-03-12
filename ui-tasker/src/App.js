import "./App.css";
import "./assets/styles.css";

import { useState } from "react";

import Header from "./components/Header";
import Tasks from "./components/Tasks";
import TaskEdit from "./components/TaskEdit";

//inspired in 
//use chota CSS https://jenil.github.io/chota


function App() {
  const [tasks, setTasks] = useState([
    {
      desc: "Learn React",
      id: 1,
      date: "Fri Mar 03 2022",
      complete: false,
    },
    {
      desc: "Profit",
      id: 2,
      date: "Fri May 03 2022",
      complete: false,
    },
  ]);

  const onTglStatus = (task) => {
    console.log("completing task");
    setTasks(
      tasks.map((chkTask) => {
        chkTask.complete =
          task.id === chkTask.id ? !chkTask.complete : chkTask.complete;
        return chkTask;
      })
    );
  };

  const [showTaskEdit, setShowTaskEdit] = useState(false);

  const onSaveTask = ({ desc, date }) => {
    console.log("saving tasks");
    setTasks([
      { desc: desc, date: date, id: Date.now(), complete: false },
      ...tasks,
    ]);
  };

  const onClickTaskEdit = () => {
    setShowTaskEdit(!showTaskEdit);
  };

  return (
    <div className="App">
      <Header />

      <div className="container">
        <div className="col-12 text-right">
          <button
            className="button outline"
            onClick={onClickTaskEdit}>
            {!showTaskEdit && "New"}
            {showTaskEdit && "➖"}
          </button>
        </div>
        {showTaskEdit && <TaskEdit task={{}} onSaveTask={onSaveTask} />}
        <Tasks tasks={tasks} onTglStatus={onTglStatus}></Tasks>
      </div>
    </div>
  );
}

export default App;
