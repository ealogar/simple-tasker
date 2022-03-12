// import react-testing methods
import { render, screen } from '@testing-library/react';

import App from './App';
// use the react testing library
// https://testing-library.com/docs/react-testing-library/example-intro
// https://github.com/testing-library/jest-dom
// roles: https://www.w3.org/TR/html-aria/#docconformance

test('renders learn react link', () => {
  render(<App />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});

test('renders new button to add task', () => {
  render(<App />);
  const buttonNew = screen.getByRole("button", {"name": /New/i});
  expect(buttonNew).toBeInTheDocument();
  expect(buttonNew).toBeEnabled();
});
