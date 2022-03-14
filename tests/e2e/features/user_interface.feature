Feature: UI tasker - Handle tasks
  As a user,
  I want to get my tasks when I open the browser.

  Background:
    Given I create a new task for "user_interface"
  
  @browser
  Scenario: Verify all tasks are shown to the user
     Given I have at least one task
      When I access to the tasker application home with a browser
      Then I can see my current tasks
      And I can create a new task

  @browser
  Scenario: I can add a new task
     Given I access to the tasker application home with a browser
      When I click on the new task button
      And I fill in the task form with "description-test-add" and some random text, any date and submit
      Then I can see my newly created task
