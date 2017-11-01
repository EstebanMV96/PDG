import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';


import registerServiceWorker from './registerServiceWorker';

import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import promise from 'redux-promise';
import reducers from './reducers';

import {BrowserRouter, Route, Switch} from 'react-router-dom';

//--------COMPONENTS----------------//

// import CodeForm from './components/code_form';
import Activate from './components/activate_auth';
import Administration from './components/administration';
import Steps from './components/steps';
import Login from './components/login';
import Loading from './components/loading';
//----------------------------------//

const createStoreWithMiddleware = applyMiddleware(promise)(createStore);

ReactDOM.render(
    <Provider store={createStoreWithMiddleware(reducers)}>
        <BrowserRouter>
            <div>
                <Switch>
                    <Route exact path="/administration/:id" component={Administration} />
                    <Route exact path="/steps" component={Steps} />
                    <Route path="/login" component={Login} />
                    <Route path="/landing/:id/:url" component={Activate} />
                    <Route path="/:id/:url" component={Loading} />
                </Switch>
            </div>
        </BrowserRouter>
    </Provider>
, document.getElementById('root'));
registerServiceWorker();
