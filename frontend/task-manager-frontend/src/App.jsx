import {useState, useEffect} from 'react'
import './App.css';


function App() {
    const [tasks, taskSet] = useState([])
    const [newTitle, setNewTitle] = useState('')
    const [newDescription, setNewDescription] = useState('')
    const [newPriority, setNewPriority] = useState('LOW')
    const [newCompletion, setNewCompletion] = useState(false)
    const [editingTask, setEditingTask] = useState(null)

    const [isOpen, setOpen] = useState(false);

    const [filter, setFilter] = useState('all')

    const[errors, setErrors] = useState({})

    useEffect(() => {
        fetchTasks(filter);
    }, [filter]);


    function addTask(){
        fetch('http://localhost:8080/tasks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: newTitle,
                description: newDescription,
                priority: newPriority
            })
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw errorData;
                    });
                }
                return response.json();
            })
            .then(data => {
                taskSet([...tasks, data]);
                setNewTitle('');
                setNewDescription('');
                setNewPriority('LOW');
                setOpen(false);
            })
        .catch(error => {
            console.log(error);
            setErrors(error);
        });
    }

    function deleteTask(id){
        fetch(`http://localhost:8080/tasks/${id}`, {
            method: 'DELETE',
        })
            .then(response =>{
                if(!response.ok){
                    return response.json().then(errorData => {
                        throw errorData;
                    })
                }
                taskSet(tasks.filter(task => task.id !== id));
            })
        .catch(error => {
            console.log(error);
            setErrors(error);
        })
    }

    function openEditTask(task){
        setNewTitle(task.title);
        setNewDescription(task.description);
        setNewPriority(task.priority);
        setEditingTask(task.id);
        setNewCompletion(task.completed);
        setOpen(true);
    }

    function updateTask(id){
        fetch(`http://localhost:8080/tasks/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: newTitle,
                description: newDescription,
                priority: newPriority
            })
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw errorData;
                })
            }
            return response.json();
        })
            .then(data => {
                taskSet(tasks.map(task => task.id === id ? data : task));
                setNewTitle('');
                setNewDescription('');
                setNewPriority('LOW');
                setEditingTask(null);
                setOpen(false);
            })
            .catch(error => {
                console.log(error);
                setErrors(error);
            })
    }

    function toggleCompleted(task){
        fetch(`http://localhost:8080/tasks/${task.id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                completed: !task.completed
            })
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => { throw errorData;});
                }
                return response.json();
            })
            .then(data => {
                taskSet(tasks.map(t => t.id === task.id ? data : t));
            })
            .catch(error => console.log(error));
    }

    function fetchTasks(filter){
        let url = `http://localhost:8080/tasks`;
        if(filter === 'completed'){
            url = 'http://localhost:8080/tasks/search?completed=true';
        }
        else if(filter === 'pending'){
            url = 'http://localhost:8080/tasks/search?completed=false';
        }
        fetch(url)
        .then(response => response.json())
        .then(data => taskSet(data));    
    }

    return (
        <div className="container">
            <h1 className="main-title">Task manager</h1>
            <div className="filterButtons">
                <button className="filterButton" onClick={() => setFilter('all')}>All</button>
                <button className="filterButton" onClick={() => setFilter('pending')}>Pending</button>
                <button className="filterButton" onClick={() => setFilter('completed')}>Completed</button>
            </div>
            <ul className="task-list">
                {tasks.map(task => (
                    <li key={task.id} className="task-card">
                        <div className="task-title">{task.title}</div>
                        <div className="task-description">{task.description}</div>

                        <div className="task-details">
                            <span>
                                Priority: <span className={`priority-${task.priority}`}>{task.priority}</span>
                            </span>
                            <span>
                                Status: <span className={task.completed ? 'status-finished' : 'status-progress'}>
                                    {task.completed ? 'Task finished' : 'In progress'}</span>
                            </span>
                        </div>
                        <button className="deleteButton" onClick={() => deleteTask(task.id)}>Delete</button>
                        <button className="editButton" onClick={() => openEditTask(task)}>Edit</button>
                        <label className="switch">
                            <input type="checkbox" checked={task.completed} onChange={() => toggleCompleted(task)} />
                            <span className="slider round"></span>
                        </label>
                    </li>
                ))}
            </ul>
            <button className="addButton" onClick={() => setOpen(true)}>Add task</button>
            {isOpen && (
                <div className="popupContent">
                    <h2>Add a new task</h2>
                    <input
                        type="text"
                        value={newTitle}
                        onChange={(e) => setNewTitle(e.target.value)}
                        placeholder="Task title"

                    />
                    {errors.title && <span className="error-text">{errors.title}</span>}
                    <input
                        type="text"
                        value={newDescription}
                        onChange={(e) => setNewDescription(e.target.value)}
                        placeholder="Task description"

                    />
                    {errors.description && <span className="error-description">{errors.description}</span>}
                    <select value={newPriority} onChange={(e) => setNewPriority(e.target.value)}>
                        <option value="LOW">LOW</option>
                        <option value="MEDIUM">MEDIUM</option>
                        <option value="HIGH">HIGH</option>
                    </select>
                    <button onClick={() => editingTask ? updateTask(editingTask) : addTask()}>
                        {editingTask ? 'Save' : 'Add'}
                    </button>
                    <button onClick={() => { setOpen(false); setEditingTask(null); }}>Close</button>
                </div>
            )}
        </div>
    )
}

export default App
