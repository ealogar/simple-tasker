import { useForm, Controller } from "react-hook-form";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import { useState } from "react";
import { postJson } from "../helpers/fetchJson";


// use https://react-hook-form.com/ for validations
// use https://www.npmjs.com/package/react-datepicker for entering g a date
// with https://thewebdev.info/2021/12/05/how-to-use-react-datepicker-with-react-hooks-forms/ we 
// use datepicker easily inside react-hook-form

function TaskEdit({ task, onSaveTask }) {

    const [errorSave, setErrorSave] = useState(false);

    const { register, handleSubmit, formState: { errors }, reset, control } = useForm();

    const formatDate = (d) => {
        let month = (d.getMonth() + 1).toString().padStart(2, '0');
        let day = d.getDate().toString().padStart(2, '0');
        let year = d.getFullYear();
        let hours = d.getHours();
        let minutes = d.getMinutes();
        return [day, month, year].join('-') + " " + [hours, minutes].join(':');
    }

    const saveTask = async (formData) => {
        let result = await postJson('/tasks', { description: formData.desc, due_date: formatDate(formData.date) });
        if (result.data && result.data.id) {
            onSaveTask(result.data);
            setErrorSave(false);
            reset();
        } else {
            console.log("failed to add task");
            console.log(result.error);
            setErrorSave(true);
        }
    };

    return (
        <div className="card">
            <h3>Add Task</h3>
            <form onSubmit={handleSubmit(saveTask)} autoComplete="off">

                <div className="row">
                    <div className="col">
                        <label htmlFor="desc">Description</label>
                        <input id="desc" {...register("desc", { required: true })} placeholder="Description" />
                        {errors.desc?.type === 'required' && <span className="text-error">Provide a description</span>}
                    </div>
                </div>

                <div className="row">
                    <div className="col">
                        <label htmlFor="date">Date</label>
                        <Controller
                            name="date"
                            control={control}
                            rules={{ required: true }}
                            render={({ field }) => (
                                <DatePicker
                                    id="date"
                                    onChange={(date) => field.onChange(date)}
                                    selected={field.value}
                                    placeholderText="Due date"
                                    showTimeSelect
                                />
                            )}
                        />

                        {errors.date?.type === 'required' && <span className="text-error">Provide a Due date</span>}
                    </div>
                </div>
                <div className="row">
                    <div className="col">
                        <input type="submit" className="button dark" />
                    </div>
                </div>
                {errorSave && <span className="text-error">Error adding task. Try again.</span>}
            </form>
        </div>
    );
}

export default TaskEdit;
