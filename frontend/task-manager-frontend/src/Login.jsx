import {useState, useEffect} from 'react'
import './App.css';

function Login({onLoginSuccess, switchToRegister}) {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    function handleLogin(e) {
        e.preventDefault();
        fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({username, password})
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error('Wrong username or password');
                }
                return res.json();
            })
            .then(data => {
                localStorage.setItem('token', data.token);
                onLoginSuccess(data.token);
            })
            .catch(err => setError(err.message));
    }

    return (
        <div className="container">
            <h1>Authentication</h1>
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
                    <button type="submit">Login</button>
            </form>
            <p>New there? Join the website <button onClick ={switchToRegister}>Sign up</button></p>
        </div>
);
}

export default Login;