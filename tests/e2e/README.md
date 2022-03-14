# Overview

We use selenium and behave as our base frameworks for testing the user interface and the api rest.

# Setup

You should have python 3.8 installed and virtualenv.
for the initial setup, create a virtualenv and install required dependencies:

```
virtualenv venv -p python3.8
````

Activate python virtualenv ```source venv/bin/activate``` and install required dependencies, you simply run ```pip install -r requirements.txt```



# Run the tests

Launch the environment you want to test, to test local dev deployment:

```
COMPOSE_FILE=../../docker-compose.yml docker-compose up -d app-fe
```

Wait until the services have started, when ```docker-compose logs -f app-fe``` writes that the process is listening


To run the tests, run ```./scripts/run-e2e-tests.sh```.


To run the tests agains the production Oracle cloud platform, just provide the base url.

# Configuration

You can set the following environment variables to manage the environment to tests:

- **TASKER_HOME_URL**: Home url of the user interface. By default http://localhost:3000
- **TASKER_BE_BASEPATH**: Basepath of the api rest backend. By default http://localhost:8080
- **TASKER_HEADLESS_MODE**: Run the selenium tests in headless mode (without the browser at first sight), useful for CI environments. By default 'false' (set to true for CI).
```