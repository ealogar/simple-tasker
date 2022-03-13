import "./App.css";
import "./assets/styles.css";

import { useState, useEffect } from "react";

import Header from "./components/Header";
import Tasks from "./components/Tasks";
import TaskEdit from "./components/TaskEdit";
import { getJson } from "./helpers/fetchJson";

//inspired in 
//use chota CSS https://jenil.github.io/chota


function App() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [tasks, setTasks] = useState([]);
  //TODO: extract all code that make requests and put in a common library
  //      from here we just look ad result and wrap everything inside
  useEffect( () => {

    const getAllTasks = async () => {
      let res = await getJson("/tasks");
      if (res.data) {
        setTasks(res.data);
        setIsLoaded(true);
      } else {
        setIsLoaded(false);
        console.log("Error when recovering tasks");
        console.log(res.error);
      }
    };
    getAllTasks();

  }, []);


  const onTglStatus = (task) => {
    console.log("completing task");

    setTasks(
      tasks.map((chkTask) => {

        chkTask.completed =
          task.id === chkTask.id ? !chkTask.completed : chkTask.completed;
        return chkTask;
      })
    );
  };

  const [showTaskEdit, setShowTaskEdit] = useState(false);

  const onSaveTask = async (savedTask) => {
    setTasks([
      savedTask,
      ...tasks,
    ]);
  };

  const onClickTaskEdit = () => {
    setShowTaskEdit(!showTaskEdit);
  };
  if (!isLoaded) {
    return (
      <div className="App">
        <Header />
        <div className="container">
          <span className="text-light ">Loading ...</span>
        </div>
      </div>
    );
  } else {
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
}

export default App;
