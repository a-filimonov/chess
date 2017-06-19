import 'babel-polyfill';

import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, IndexRoute, browserHistory } from 'react-router';
import { Provider } from 'react-redux';

import FrontPage from 'pages/frontpage/FrontPageContainer';

import store from 'store';
import Root from 'root/RootContainer';

const ReduxProvider = (props) => {
    return (
        <Provider store={store}>
            <Root {...props}/>
        </Provider>
    );
};

ReactDOM.render((
    <Router history={browserHistory}>
        <Route path={'/'} component={ReduxProvider}>
            <IndexRoute component={FrontPage}/>
        </Route>
    </Router>
), document.getElementById('app'));