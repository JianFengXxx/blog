# syntax=docker/dockerfile:1

FROM mozilla/sbt:11.0.13_1.6.2

WORKDIR /app

CMD sbt run