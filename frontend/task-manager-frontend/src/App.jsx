import {useState, useEffect} from 'react'
import './App.css';


function App() {
    const [tasks, taskSet] = useState([])
    const [newTitle, setNewTitle] = useState('')
    const [newDescription, setNewDescription] = useState('')
    const [newPriority, setNewPriority] = useState('LOW')
    const [newCompletion, setNewCompletion] = useState(false)

    const [isOpen, setOpen] = useState(false);

    useEffect(() =>{
        fetch('http://localhost:8080/tasks')
            .then(response => response.json())
            .then(data => taskSet(data))
    }, [])


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
            .then(response => response.json())
            .then(data => {
                taskSet([...tasks, data]);
                setNewTitle('');
                setNewDescription('');
                setNewPriority('');
                setOpen(false);
            })
        .catch(error => {
            console.log(error);

        });
    }

    return (
        <div className="container">
            <h1 className="main-title">Task manager</h1>
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
                    <input
                        type="text"
                        value={newDescription}
                        onChange={(e) => setNewDescription(e.target.value)}
                        placeholder="Task description"
                    />
                    <select value={newPriority} onChange={(e) => setNewPriority(e.target.value)}>
                        <option value="LOW">LOW</option>
                        <option value="MEDIUM">MEDIUM</option>
                        <option value="HIGH">HIGH</option>
                    </select>
                    <button onClick={addTask}>Add</button>
                    <button onClick={() => setOpen(false)}>Close</button>
                </div>
            )}
        </div>
    )
}

export default App
