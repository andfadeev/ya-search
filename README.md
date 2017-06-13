# ya-search

Run with:
```
=> lein run
```

Or build uberjar and run:
````
=> lein uberjar
=> java -jar target/ya-search-0.1.0-SNAPSHOT-standalone.jar
````

Get json from endpoint (content negotiation is enabled with yada):
````
=> curl -i -H "Accept: application/json" "http://localhost:3000/search?query=clojure"
````