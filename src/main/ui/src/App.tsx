import React from 'react';

import 'normalize.css'
import './styles/globals.css';

import {
  BrowserRouter as Router,
  Switch,
  Route
} from 'react-router-dom'

import {I18nextProvider} from 'react-i18next';
import i18n from './i18n'

import Home from './pages/home'
import Signin from './pages/signin'
import SignUpClient from './pages/signup/client'
import SignUpWorker from './pages/signup/worker'
import AdminPanel from './pages/panels/adminPanel'
import ModeratorPanel from "./pages/panels/moderatorPanel";


import ClientPanel from './pages/panels/clientPanel'
import WorkerPanel from './pages/panels/workerPanel'

function App() {
  return (
      <I18nextProvider i18n={i18n}>
        <Router basename="/cruisehub">
          <Switch>
            <Route exact path="/">
              <Home/>
            </Route>

            <Route path="/signin">
              <Signin/>
            </Route>


            <Route path="/signup/client">
              <SignUpClient/>
            </Route>

            <Route path="/signup/worker">
              <SignUpWorker/>
            </Route>

            <Route path="/panels/workerPanel">
              <WorkerPanel/>
            </Route>

            <Route path="/panels/clientPanel">
              <ClientPanel/>
            </Route>

            <Route path="/panels/adminPanel">
              <AdminPanel/>
            </Route>
            <Route path="/panels/moderatorPanel">
              <ModeratorPanel/>
            </Route>
           

            <Route path="*">
              <div>404 not found</div>
            </Route>

          </Switch>
        </Router>
      </I18nextProvider>
  );
}

export default App;
