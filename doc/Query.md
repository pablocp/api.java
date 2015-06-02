# Query

A `Query` can be build with a fluent interface as described in this document.

### Filter

`filter` can be used to add a filter to the query:

```javascript
// Query.filter(Filter)
Query.build().filter(Filter.equal("name", "john"))

// Query.filter(field, value)
Query.build().filter("name", "john")

// Query.filter(field, operator, value)
Query.build().filter("name", "=", "john")
```

If more than one filter is added to a query, the default operator to combine 
the filters is an `AND`.

```javascript
// The query
Query.build().filter("title", "1984")
	         .filter("author", "like", "G.* Orwell")
	         .filter("year", ">", 1900)

// is the same as
Query.build().filter(
		Filter.equal("title", "1984")
			  .and("author", "like", "G.* Orwell")
			  .and("year", ">", 1900)
	)
```

### Search

`search` can be used to define a search object for the query:

```javascript
// Query.search(query)
// same as Query.search(Search.query(query))
Query.build().search("match phrase")

// Query.search(field, query)
// same as Query.search(Search.query(field, query))
Query.build().search("title", "movie title")

// Query.search(field, operator, value)
// same as Query.search(Search.query(field, operator, value))
Query.build().search("title", "fuzzy", "matrix")

// Query.search(Filter)
// same as Query.search(Search.query(Filter))
Query.build().search(Filter.prefix("pre"))

// Query.search(Search)
Query.build().search(
        Search.build()
              .query("match phrase")
              .highlight("body")
    )
```

Note that the default filter operator for `Query#search` is `match`.

### Sort

```javascript
// Query.sort(field) (default direction is ascending)
Query.build().sort("name")

// Query.sort(field, direction)
// direction = "asc", "desc"
Query.build().sort("name", "asc")
```

### Limit

```javascript
// Query.limit(value)
Query.build().limit(10)
```

### Offset

```javascript
// Query.from(pos)
Query.build().from(1)
```


# Filter

A simple `Filter` (SQL filter predicate) can be build as follows:

```javascript
// Filter.of(field, value) (default is "=")
Filter.of("name", "john")

// Filter.of(field, operator, value)
Filter.of("year", "=", "1990")
```

A filter can be upgraded to a composite filter as follows:

```javascript
// filter.and(Filter)
Filter.of("name", "john").and(Filter.of("age", 25))

// filter.and(field, value)
Filter.of("name", "john").and("age", 25)

// filter.and(field, operator, value)
Filter.of("name", "john").and("age", "=", 25)
```

Once upgraded, this field can only use the same composition operator.
The supported operators are:

```javascript
Filter.of("name", "john").or("name", "michael").or("age", ">", 25)
Filter.of("name", "john").disMax("name", "michael")    	// uses maximum sub-filter score to calculate the score
```

The `not` composition operator can be used as follows:

```javascript
// Filter.notOf(filter)
// Filter.notOf(field, value)
// Filter.notOf(field, operator, value)
Filter.notOf("name", "john").not("age", ">", 25)		// not named John and not older than 25 years
```

Example of complex composite filter:

```javascript
// this filter
Filter.match("star wars")
	.and(
		Filter.range("year", 1980, 1990)
			.or("title", "fuzzy", "clone")
	)
	.and(
		"rating", ">", 8.0
	)
	.and(
		Filter.notOf(Filter.in("category", "comedy", "horror"))
	)

// is the same as
Filter.andOf(
	Filter.orOf(
	    Filter.range("year", 1980, 1990),
	    Filter.of("title", "fuzzy", "clone")
	),
	Filter.of("rating", ">", 8.0),
	Filter.notOf(
	    Filter.in("category", "comedy", "horror")
	)
)
```

### SQL filters

There are also methods for each supported SQL filter:

```javascript
// operator = "lt", "less_than", "<"
Filter.lt("age", 25)

// operator = "lte", "less_than_or_equal", "<="
Filter.lte("age", 25)

// operator = "gt", "greater_than", ">"
Filter.gt("age", 25)

// operator = "gte", "greater_than_or_equal", ">=" 
Filter.gte("age", 25)

// operator = "eq", "equal", "="
Filter.equal("age", 25)

// operator = "neq", "not_equal", "!=" 
Filter.notEqual("age", 25)

// operator = "in"
Filter.in("age", 24, 25, 26)

// operator = "nin", "not_in" 
Filter.notIn("age", 24, 25, 26)

// operator = "like"
Filter.like("name", "J.* Smith")
```

The search filters are:

### Field filters

```javascript
// Filter.exists(field)
Filter.exists("title")

// Filter.missing(field)
Filter.missing("name")
```

### Range filter

```javascript
// Filter.range(field, min, max)
Filter.range("age", 20, 30)
```

### Text filters

A simple text match filter can be specified as:

```javascript
// Filter.match(text) (default is all fields "*")
Filter.match("search query")

// Filter.match(field, text)
Filter.match("title", "The matrix")
```

To configure a advanced match type you can set the match type:

```javascript
// Filter.match(...).type(MatchType)
// types = PHRASE, PHRASE_PREFIX
Filter.match("search phrase")
      .type(MatchFilter.MatchType.PHRASE)
```

Or use one of the methods:

```javascript
// Filter.phrase(text)
// Filter.phrase(field, text)
Filter.phrase("match this phrase")
Filter.match("match this phrase", MatchFilter.MatchType.PHRASE)

// Filter.phrasePrefix(text)
// Filter.phrasePrefix(field, text)
Filter.phrasePrefix("match an incomplete phra")
Filter.match("match this phrase", MatchType.PHRASE_PREFIX)
```

To filter by term prefix (prefix filter) use:

```javascript
// Filter.prefix(prefix) (default is all fields "*")
Filter.prefix("pre")

// Filter.prefix(field, prefix)
Filter.prefix("title", "scar") // matches "Scarface" and "Scary movie"

// Filter.of(field, operator, value
Filter.of("body", "prefix", "word")
```

#### Fuzzy

To add fuzziness to the text match, use:

```javascript
// Filter.fuzzy(text) (default is all fields "*")
Filter.fuzzy("content")

// Filter.fuzzy(field, text)
Filter.fuzzy("name", "Lais") // matches Louis
```

To specify the edit distance (0, 1 or 2) as fuzziness:
 
```javascript
// Filter.fuzzy(...).fuzziness(distance)
Filter.fuzzy("Lais").fuzziness(1) // matches Luis, but not Louis

// Filter.fuzzy(..., distance)
Filter.fuzzy("name", "Lais", 2) // matches Luis, Luiz and Louis 
```

You can also specify the fuzziness as the percentage [0..1) of the word that 
needs to match (note that the maximum edit distance is 2):

```javascript
// Filter.fuzzy(...).fuzziness(percentage)
Filter.fuzzy("document text").fuzziness(0.5) // matches "docmnt tx"

// Filter.fuzzy(..., percentage)
Filter.fuzzy("body", "document", 0.75) // matches documents but not docume
```

#### Common terms

To dynamically define word relevance without a static definition of stop-words,
use the common terms filter as follows:

```javascript
// Filter.common(text) (default is all fields "*")
Filter.common("a phrase with common words")     // [a,with] are high frequency terms
                                                // [phrase,common,words] are low frequency

// Filter.common(field, text)
Filter.common("title", "p.s. i love you")       // [i,you] are high frequency terms
                                                // [p,s,love] are low frequency

// Filter.of(field, operator, text)
Filter.of("title", "common", "p.s. i love you")
```

The common terms behavior is:

```javascript
// this filter
Filter.common("body", "search with this phrase")

// is dynamically converted to
Filter.andOf(
        Filter.of("body", "search").or("body", "phrase"),
        Filter.of("body, "with").or("body", "this")
    )
```

To specify the word frequency threshold:
 
```javascript
// Filter.common(...).threshold(value) (default is all fields "*")
Filter.common("video may be a high frequency word").threshold(0.1) // if "video" appears in at least 10% of the base

// Filter.common(..., threshold)
Filter.common("title", "the matrix", 0.001)
```

#### More like this filters

To use a example of the kind of content you are searching without constructing a
proper query, use the more like this filter:

```javascript
// Filter.moreLikeThis(text) (default is all fields "*")
Filter.moreLikeThis(document.body())

// Filter.moreLikeThis(field, text)
Filter.moreLikeThis("body", document.body())
```

You can add a list of stop-words to be used against the query text:

```javascript
// Filter.moreLikeThis(...).stopWords(words)
Filter.moreLikeThis(document.body()).stopWords("this", "is")
```

You can also specify values to control term and document frequency for extracted terms:

```javascript
// Filter.moreLikeThis(...)
//			.minTf(value) - in the query (removes non-representative query terms)
//			.minDf(value) - in documents (removes non-representative terms)
//			.maxDf(value) - in documents (removes common terms)
Filter.moreLikeThis("this content is just an example. it is just an example")
	  .minTf(2)		        // will select "is", "an", "just" and "example" 
	  .minDf(100)	        // may exclude "example" as non-representative for the base
	  .maxDf(1000)	        // may exclude "is" and "an" as too common
```

To combine the more like this query construction with a fuzzy match, use fuzzy like this:

```javascript
// Filter.fuzzyLikeThis(text) (default is all fields "*")
Filter.fuzzyLikeThis(document.body())

// Filter.fuzzyLikeThis(field, text)
Filter.fuzzyLikeThis("body", document.body())
```

In this case it is only possible to set the fuzziness, as we do with the fuzzy filter:

```javascript
// Filter.fuzzyLikeThis(...).fuzziness(value)
Filter.fuzzyLikeThis(document.body()).fuzzyness(0.5)

// Filter.fuzzyLikeThis(..., fuzziness)
Filter.fuzzyLikeThis("body", document.body(), 2)
```

### Geo

A `GeoPoint` can be specified as one of the following:

```javascript
// [lon,lat] (GeoJson)
[2,1]

// "lat, lon"
"1,2"

// Geo.point(lat, lon)
Geo.point(1, 2)
```

All other shapes can be constructed with these points:

```javascript
// Geo.line(points)
Geo.line("0,0", [1,1], Geo.point(2,2))

// Geo.bbox(upperLeft, lowerRight)
Geo.bbox("0,1", "1,0")

// Geo.polygon(points).hole(points)
Geo.polygon([0,0], "0,1", Geo.point(1,0), "0,0")
Geo.polygon("0,0", "0,10", "10,0", "0,0")
	.hole("0,0", "0,1", "1,0", "0,0")
	.hole("1,1", "1,2", "2,1", "1,1")

// Geo.circle(center, radius)
Geo.circle(Geo.point(0, 0), "100m")
```

A polygon filter can be one of the following:

```javascript
// Filter.bbox(field, upperLeft, lowerRight)
Filter.bbox("location", "0,1", "1,0")

// Filter.polygon(field, points)
Filter.polygon("location", [0,0], "0,1", Geo.point(1,0), "0,0")

// Filter.polygon(field, bbox)
Filter.polygon("location", Geo.bbox("0,1", "1,0"))

// Filter.polygon(field, polygon)
Filter.polygon("location", Geo.polygon("0,0", "0,1", "1,0", "0,0"))
```

A distance filter can be defined as:

```javascript
// Filter.distance(field, point, max)
Filter.distance("location", [1,1], "2km")

// Filter.distance(field, point, min, max)
Filter.distance("location", [1,1], "50m", "2km")

// Filter.distance(field, circle)
Filter.distance("location", Geo.circle([1,1], "2km"))
```

A geo shape filter can match regions that intersects any of the filter shapes:

```javascript
// Filter.shape(field, shapes)
// shapes = Geo.point, Geo.line, Geo.bbox, Geo.polygon, Geo.circle
Filter.shape("area", Geo.line("0,0","10,10"), "40,40")
```

# Search

The search type can be set as:

```javascript
// Search.type(SearchType)
// types = FETCH, COUNT, SCAN
Search.type(Search.SearchType.COUNT)
```

Also a search cursor for page navigation can be specified:

```javascript
// Search.cursor(cursor)
Search.build().cursor(result.nextPage())
```

### Query, Pre and Post filters

`query`, `preFilter` and `postFilter` can be used to add filters to the search

```javascript
// Search.query(text)
// Search.query(field, text)
// Search.query(field, operator, value)
// Search.query(Filter)

Search.build()
    .query("star wars")
	.preFilter("year", ">", 2000)
	.postFilter(Filter.in("category", "action"))
```

The default composition operator is `AND`.

### Highlights

Highlight fields can be added to a search as follows:

```javascript
// Search.highlight(field)
Search.build().highlight("body")

// Search.highlight(field, size)
Search.build().highlight("body", 100) 		// 100 characters fragments

// Search.highlight(field, size, count)
Search.build().highlight("body", 100, 2)	// 2 fragments of 100 characters
```

### Aggregations

Aggregation results can be defined as:

```javascript
// Search.aggregate(name, Aggregation)
Search.build().aggregate("youngest", Aggregation.min("age"))

// Search.aggregate(name, field, operator)
// operators = min, max, sum, avg, stats, extended_stats, count, terms, missing
Search.build().aggregate("average_age", "age", "avg")
```

Simple aggregations can be defined as follows:

```javascript
Aggregation.min("age")
Aggregation.max("age")
Aggregation.sum("age")
Aggregation.avg("age")
Aggregation.stats("age")
Aggregation.extendedStats("age")
Aggregation.count("age")
Aggregation.terms("category")	// aggregates all generated terms for category 
Aggregation.missing("age")		// counts documents missing field age
```

Other aggregations are:

```javascript
// Aggregation.range(field, ranges)
Aggregation.range("years", "year", [1980, 1990], [1990, 2000], [2000, 2010])

// Aggregation.histogram(field, interval)
Aggregation.histogram("years", "year", 10)

// Aggregation.distance(field, point, ranges).unit(distance_unit)
Aggregation.distance("geo", "location", "0,0", [0,1], [1,2], [2,3]).unit("km")
```
