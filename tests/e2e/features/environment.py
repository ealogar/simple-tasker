import requests
from behave import fixture, use_fixture
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.options import Options
from os import getenv

TASKER_HOME_URL = getenv("TASKER_HOME_URL", "http://localhost:3000")
TASKER_BE_BASEPATH = getenv("TASKER_BE_BASEPATH", "http://localhost:8080")
TASKER_HEADLESS_MODE = getenv("TASKER_HEADLESS_MODE", "false")

@fixture
def selenium_browser_chrome(context):
    options = Options()
    options.add_argument("--no-sandbox")
    options.add_argument("--window-size=1280,1000")

    if TASKER_HEADLESS_MODE != "false":
        options.add_argument("--headless")
    context.browser = webdriver.Chrome(ChromeDriverManager().install(), options=options)

    yield context.browser
    context.browser.quit()

def before_scenario(context, scenario):
    if 'browser' in scenario.tags:
        use_fixture(selenium_browser_chrome, context)

def after_scenario(context, scenario):
    if "new_task" in context:
        requests.delete(f'{TASKER_BE_BASEPATH}/tasks/{context.new_task["id"]}')
