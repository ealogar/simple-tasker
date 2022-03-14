from behave import *
import requests
import random
import string
from environment import TASKER_BE_BASEPATH

@given('I create a new task for "{feature_name}"')
def step_impl(context, feature_name):
    random_str = ''.join(random.choices(string.ascii_uppercase + string.digits, k = 20))
    description = f"{feature_name}_{random_str}"

    body = { "description": description, "due_date": "01-01-2025 00:01"}
    r = requests.post(f'{TASKER_BE_BASEPATH}/tasks', json=body)
    if r.status_code != 200:
        assert False, f'a new task could not be created {r.text}'
    
    context.new_task_description = description
    context.new_task = r.json()

@when('I do a "{method}" request to the "{url}" url')
def step_impl(context, method, url):
    r = getattr(requests, method.lower())(f"{TASKER_BE_BASEPATH}{url}")
    context.status_code = r.status_code
    context.json_response = r.json()

@then('I get the tasks as json')
def step_impl(context):
    assert context.status_code == 200
    new_task_created = next((task for task in context.json_response if task["description"] == context.new_task_description), None)
    if not new_task_created:
        assert False, f'The new task {context.new_task_description} was not found'
        