import {useState, useEffect} from 'react'
import './App.css';

function App() {
    const [tasks, taskSet] = useState([])

    useEffect(() =>{
        fetch('http://localhost:8080/tasks')
            .then(response => response.json())
            .then(data => taskSet(data))
    }, [])

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
        </div>
    )
}

export default App
