# Author ashsepra@gmail.com
# This load test for example
# Scope on load test at wikipedia

import time
from locust import HttpUser, TaskSet, task, between

class SubClassTest(TaskSet):

    @task(2)
    def get_all_leagues(self):
        self.client.get("/league/all")

    @task(1)
    def add_league(self):
        self.client.post('/league/add', json={"leagueName": "B League",
                                      "teams": ["Ingenieria","Medicina"],
                                      "startDate": "2023-05-17",
                                      "finishDate": "2023-05-23"})

    @task(3)
    def get_league(self):
        id = 1
        self.client.get("/league?id={}".format(id))


class MainClassTest(HttpUser):
    tasks = [SubClassTest]
    wait_time = between(2, 5)