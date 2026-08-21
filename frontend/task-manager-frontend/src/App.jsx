import {useState, useEffect} from 'react'
import './App.css';
import Login from './Login.jsx'
import Register from './Register.jsx'


function App() {
    const [tasks, taskSet] = useState([]);
    const [newTitle, setNewTitle] = useState('');
    const [newDescription, setNewDescription] = useState('');
    const [newPriority, setNewPriority] = useState('LOW');
    const [newCompletion, setNewCompletion] = useState(false);
    const [editingTask, setEditingTask] = useState(null);

    const [isOpen, setOpen] = useState(false);

    const [filter, setFilter] = useState('all');

    const[errors, setErrors] = useState({});

    const [deleteError, setDeleteError] = useState('');

    const [token, setToken] = useState(localStorage.getItem('token'));
    const [view, setView] = useState('login');

    const [userId, setUserId] = useState(localStorage.getItem('userId'));

    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if(token) {
            fetchTasks(filter);
        }
    }, [filter, token]);

    const authHeaders = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    };

    function addTask(){
        fetch('http://localhost:8080/tasks', {
            method: 'POST',
            headers: authHeaders,
            body: JSON.stringify({
                title: newTitle,
                description: newDescription,
                priority: newPriority,
                userId: parseInt(userId)
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
            headers: authHeaders,
        })
            .then(response =>{
                if(!response.ok){
                    return response.json().then(errorData => {
                        throw errorData;
                    })
                }
                taskSet(tasks.filter(task => task.id !== id));
                setDeleteError('');
            })
        .catch(error => {
            console.log(error);
            setDeleteError(error.message);
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
            headers: authHeaders,
            body: JSON.stringify({
                title: newTitle,
                description: newDescription,
                priority: newPriority,
                userId: parseInt(userId),
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
            headers: authHeaders,
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
        fetch(url, { headers: authHeaders })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to load tasks');
                }
                return response.json();
            })
            .then(data => taskSet(data))
            .catch(error => console.log(error))
            .finally(()=>setLoading(false));
    }

    function handleLogout(){
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
        setToken(null);
        setUserId(null);
    }

    if(!token){
        if(view === 'register'){
            return(
                <Register
                    onRegisterSuccess={(newToken) => setToken(newToken)}
                    switchToLogin={() => setView('login')}
                />
            );
        }
        return (
            <Login
                onLoginSuccess={(newToken, newUserId) => {
                    setToken(newToken)
                    setUserId(newUserId);
                }}
                switchToRegister={() => setView('register')}
            />
        );
    }

    return (
        <div className="container">
            <title>Task Manager</title>
            <div className="header-bar">
                <h1 className="main-title">Task manager</h1>
                <button className="logoutButton" onClick={handleLogout}>Logout</button>
            </div>
            <div className="filterButtons">
                <button className={`filterButton ${filter === 'all' ? 'active' : ''}`} onClick={() => setFilter('all')}>All</button>
                <button className={`filterButton ${filter === 'pending' ? 'active' : ''}`} onClick={() => setFilter('pending')}>Pending</button>
                <button className={`filterButton ${filter === 'completed' ? 'active' : ''}`} onClick={() => setFilter('completed')}>Completed</button>
            </div>
            {deleteError && <div className="error-banner">
                <span>{deleteError}</span>
                <button className="closeBanner" onClick={() => setDeleteError('')}>×</button>
            </div>}
            {loading ? (
                <div className="empty-message">Loading tasks...</div>
            ) : tasks.length === 0 ? (
                <div className="empty-message">
                    <p>No tasks available!</p>
                    <p>Click <strong>"Add task"</strong> below to create your first task.</p>
                </div>
            ) : (
                <ul className="task-list">
                    {tasks.map(task => (
                        <li key={task.id} className="task-card">
                            <div className="task-header">
                                <div className="task-title">{task.title}</div>
                                <div className="task-actions">
                                    <button className="editButton" onClick={() => openEditTask(task)}>Edit</button>
                                    <button className="deleteButton" onClick={() => deleteTask(task.id)}>Delete</button>
                                </div>
                            </div>

                            <div className="task-description">{task.description}</div>

                            <div className="task-details">
                                <span>Priority: <span className={`priority-${task.priority}`}>{task.priority}</span></span>
                                <span>Status: <span className={task.completed ? 'status-finished' : 'status-progress'}>{task.completed ? 'Task finished' : 'In progress'}</span></span>
                                <label className="switch">
                                    <input type="checkbox" checked={task.completed} onChange={() => toggleCompleted(task)} />
                                    <span className="slider round"></span>
                                </label>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
            <button className="addButton" onClick={() => setOpen(true)}>Add task</button>
            {isOpen && (
                <div className ="modal-overlay">
                    <div className="popupContent">
                        <h2>{editingTask ? 'Edit task' : 'Add a new task'}</h2>
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
                </div>
            )}
        </div>
    );
}

export default App
