// import react-testing methods
import { render, screen } from '@testing-library/react';

import TaskEdit from './TaskEdit';

test('renders description, date and button for task edit', () => {
  render(<TaskEdit />);
  const labelDescription = screen.getByLabelText(/desc/i);
  expect(labelDescription).toBeInTheDocument();
  expect(labelDescription).not.toHaveValue();
  const labelDate = screen.getByLabelText(/date/i);
  expect(labelDate).toBeInTheDocument();
  expect(labelDate).not.toHaveValue();
  const inputSubmit = screen.getByRole("button");
  expect(inputSubmit).toBeInTheDocument();
  
});
