import {
    BrowserRouter as Router,
    Switch,
    Route,
    Redirect,
    Link
} from "react-router-dom";
import React from "react";
import QueryBuilder from "./component/querybuilder/QueryBuilder";
import Dashboard from "./pages/Dashboard";

function App() {
    return (
        <div className="App">
            <header className="App-header">
            </header>
            <div>
                <Router>
                    <Switch>

                        <Route exact path="/make-magic" render={() => <QueryBuilder/>}/>

                        <Route path="/spells" component={Dashboard}/>
                        <a>
                            <Link to="/make-magic">Make Magic</Link>
                            <p>Para otro lado</p>
                            <Link to="/spells">Spells</Link>
                        </a>

                        <Redirect to="/"/>
                    </Switch>
                </Router>

            </div>

        </div>
    );
}

export default App;
