import {useState, useEffect} from 'react'
import './App.css';

function Register({onRegisterSuccess, switchToLogin}) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [error, setError] = useState('');

    function handleLogin(e) {
        e.preventDefault();
        setError('');
        fetch('http://localhost:8080/register', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({username, password, email})
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error('Registration failed, check the data');
                }
                return res.json();
            })
            .then(data => {
                localStorage.setItem('token', data.token);
                onRegisterSuccess(data.token);
            })
            .catch(err => setError(err.message));
    }

    return (
        <div className="container">
            <h1>Registration</h1>
            <form onSubmit={handleRegister}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
                <button type="submit">Login</button>
            </form>
            <p>Already registered? Sign in <button onClick ={switchToLogin}>Sign in</button></p>
        </div>
    );
}

export default Register;