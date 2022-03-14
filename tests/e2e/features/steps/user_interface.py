from time import sleep
from behave import *
from selenium.webdriver.common.by import By
import requests
import random
import string

from environment import TASKER_BE_BASEPATH

@given('I have at least one task')
def step_impl(context):
    r = requests.get(f"{TASKER_BE_BASEPATH}/tasks")
    assert r.status_code == 200
    assert len(r.json()) > 0

@given('I access to the tasker application home with a browser')
@when('I access to the tasker application home with a browser')
def step_impl(context):
    context.browser.implicitly_wait(10)
    context.browser.get("http://localhost:3000/")
    context.browser.set_window_size(1130, 694)

@then('I can see my current tasks')
def step_impl(context):
    assert context.browser.find_element(By.XPATH, f"//h4[contains(.,\'{context.new_task_description}\')]").text == context.new_task_description

@then('I can create a new task')
def step_impl(context):
    context.browser.find_element(By.CSS_SELECTOR, ".outline").click()

@when('I click on the new task button')
def step_impl(context):
    context.browser.find_element(By.CSS_SELECTOR, ".outline").click()

@when('I fill in the task form with "{description}" and some random text, any date and submit')
def step_impl(context, description):
    random_str = ''.join(random.choices(string.ascii_uppercase + string.digits, k = 20))
    random_description = f"{description}_{random_str}"
    context.new_task_ui_description = random_description
    context.browser.find_element(By.ID, "desc").click()
    context.browser.find_element(By.ID, "desc").send_keys(random_description)
    context.browser.find_element(By.ID, "date").click()
    context.browser.find_element(By.CSS_SELECTOR, ".react-datepicker__day--016").click()
    context.browser.find_element(By.CSS_SELECTOR, ".react-datepicker__time-list-item:nth-child(3)").click()
    context.browser.find_element(By.XPATH, "//input[@type='submit']").click()

@then('I can see my newly created task')
def step_impl(context):
    assert context.browser.find_element(By.XPATH, f"//h4[contains(.,\'{context.new_task_ui_description}\')]").text == context.new_task_ui_description
