Feature: API rest tasker - Get tasks
  As a dev user,
  I want to get a json list with all the tasks.

  Background: I have  newly created task for the tests
    Given I create a new task for "api_rest_tasker"

  Scenario: Verify all tasks are returned
     Given I have at least one task
      When I do a "GET" request to the "/tasks" url
      Then I get the tasks as json

