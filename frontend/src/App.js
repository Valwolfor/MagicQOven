import React, { useState, useEffect } from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Redirect,
    Link
} from "react-router-dom";

import Login from "./pages/Login";
import QueryBuilder from "./component/querybuilder/QueryBuilder";

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const userEmail = localStorage.getItem('userEmail');

        if (userEmail) {
            fetch(`http://localhost:8085/check-session?email=${userEmail}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text();
                })
                .then(data => {
                    setIsLoggedIn(data === 'SessionActive');
                })
                .catch(error => {
                    console.error('Error al verificar la sesión:', error);
                });
        } else {
            setIsLoggedIn(false);
        }
    }, []);

    return (
        <div className="App">
            <Router>
                <Switch>
                    <Route exact path="/login">
                        {isLoggedIn ? <Link to="/make-magic" /> : <Login />}
                    </Route>
                    <Route path="/make-magic" component={isLoggedIn ? QueryBuilder : Login} />
                    <Redirect to="/login" />
                </Switch>
            </Router>
        </div>
    );
}

export default App;
