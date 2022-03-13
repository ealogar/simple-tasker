// import react-testing methods
import { render, screen } from '@testing-library/react';

import App from './App';
// use the react testing library
// https://testing-library.com/docs/react-testing-library/example-intro
// https://github.com/testing-library/jest-dom
// roles: https://www.w3.org/TR/html-aria/#docconformance

test('renders Tasker link', () => {
  render(<App />);
  const linkElement = screen.getByText(/Tasker/i);
  expect(linkElement).toBeInTheDocument();
});

test('renders loading text', () => {
  render(<App />);
  const loadingText = screen.getByText(/Loading/i);
  expect(loadingText).toBeInTheDocument();
});
