import React from 'react';
import ReactDOM from 'react-dom/client';
import './css/style.css';
import {FormatRoutes} from './components/FormatDropdown';
import 'bootstrap/dist/css/bootstrap.min.css';

/**
 * Creates a root node for the application.
 */
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <FormatRoutes />
  </React.StrictMode>
);
