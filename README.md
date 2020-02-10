### Run database

```shell script
docker run -p 5432:5432 -e POSTGRES_DB=places -e POSTGRES_USER=coya -e POSTGRES_PASSWORD=password -d postgres:9.6
```

### Run migrations
```sbt flywayMigrate```

### Run application
```sbt run```
