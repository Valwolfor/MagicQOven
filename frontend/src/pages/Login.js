import React, {useState} from 'react';

const Login = () => {
    const [isAdminLogin, setIsAdminLogin] = useState(false);
    const [username, setUsername] = useState();
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();

    const usernameApp = 'witcher.valwolfor';
    const passwordApp = 'abrakadabra777';

    const basicAuth = 'Basic ' + btoa(usernameApp + ':' + passwordApp);
    const handleLogin = () => {
        let url = isAdminLogin ? 'http://localhost:8085/login-admin' : 'http://localhost:8085/login';
        let body = isAdminLogin ?
            JSON.stringify({username: username.toString(), email: email.toString(), password,role: "ADMIN"}) :
            JSON.stringify({username: username.toString(), email: email.toString(), role : "ANALYST"});
console.log(body);

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': basicAuth,
                mode: 'no-cors'
            },
            body: body
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Guardar usuario en el local storage
                    const user = {
                        username: data.username,
                        email: data.email,
                        isAdmin: data.isAdmin,
                    };
                    localStorage.setItem('user', JSON.stringify(user));
                    window.location.href = '/';

                } else {
                    alert('Error in the data procession, please check the values.');
                }
            })

            .catch(error => {
                console.error('Error en el login:', error);
            });
    };

    return (
        <div>
            <h2>Login</h2>
            <div>
                <label>Username:</label>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
            </div>
            <div>
                <label>Email:</label>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>
            <div>
                <label>Admin:</label>
                <input
                    type="checkbox"
                    checked={isAdminLogin}
                    onChange={(e) => setIsAdminLogin(e.target.checked)}
                />
            </div>
            {isAdminLogin && (
                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
            )}
            <button onClick={handleLogin}>Login</button>
        </div>
    );
};

export default Login;
