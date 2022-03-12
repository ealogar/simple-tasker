import { useForm, Controller } from "react-hook-form";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";


// use https://react-hook-form.com/ for validations
// use https://www.npmjs.com/package/react-datepicker for entering g a date
// with https://thewebdev.info/2021/12/05/how-to-use-react-datepicker-with-react-hooks-forms/ we 
// use datepicker easily inside react-hook-form

function TaskEdit({ task, onSaveTask }) {


    const { register, handleSubmit, formState: { errors }, reset, control } = useForm();

    const saveTask = (formData) => {
        console.log(formData);
        onSaveTask({ desc: formData.desc, date: formData.date.toDateString() });
        reset();
    };

    return (
        <div className="card">
            <h3>Add Task</h3>
            <form onSubmit={handleSubmit(saveTask)}>

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
                            rules={{ required: true}}
                            render={({ field }) => (
                                <DatePicker
                                id="date"
                                onChange={(date) => field.onChange(date)}
                                selected={field.value}
                                placeholderText="Due date"
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
            </form>
        </div>
    );
}

export default TaskEdit;
