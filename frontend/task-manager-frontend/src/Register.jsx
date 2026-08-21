import {useState, useEffect} from 'react'
import './App.css';

function Register({onRegisterSuccess, switchToLogin}) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [error, setError] = useState('');

    function handleRegister(e) {
        e.preventDefault();
        setError('');
        fetch('http://localhost:8080/users', {
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
                switchToLogin();
            })
            .catch(err => setError(err.message));
    }

    return (
        <div className="container">
            <div className="auth-card">
                <h1 className="auth-title">Sign up</h1>
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
                    <button className="authenticationButton" type="submit">Sign up</button>
                </form>
                <p>Already registered? Sign in <button className="authenticationButton" onClick ={switchToLogin}>Sign in</button></p>
            </div>
        </div>
    );
}

export default Register;