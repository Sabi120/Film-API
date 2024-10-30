import { createContext } from 'react';

/**
 * FormatContext is a React context for sharing format state and updater function throughout the application.
 *
 * @type {React.Context}
 * @property {string} format - The current data format (set as 'json' by default).
 * @property {function} setFormat - A function to update the format state.
 */
const FormatContext = createContext({
  format: 'json',
  setFormat: () => {},
});

export default FormatContext;