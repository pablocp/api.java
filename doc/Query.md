# Query

A `Query` can be build with a fluent interface as described in this document.


### Filter

`filter` can be used to add a filter to the query:

```javascript
// filter by field value
Query.builder().filter("name", "john")

// custom filter operator
Query.builder().filter("name", "=", "john")

// using filter builder
Query.builder().filter(Filter.equal("name", "john"))
```

If more than one filter is added to a query, the default operator to combine 
the filters is an `AND`.

```javascript
// The query
Query.builder().filter("title", "1984")
	           .filter("author", "like", "G.* Orwell")
	           .filter("year", ">", 1900)

// is the same as
Query.builder().filter(
		Filter.equal("title", "1984")
			  .and("author", "like", "G.* Orwell")
			  .and("year", ">", 1900)
	)
```


### Search

`search` can be used to define a search object for the query:

```javascript
// search on all field with search "match" filter
Query.builder().search("match phrase")

// match filter
Query.builder().search("title", "movie title")

// custom search filter
Query.builder().search("title", "fuzzy", "matrix")
Query.builder().search(SearchFilter.prefix("pre"))

// using search builder
Query.builder().search(
        Search.builder()
              .query("match phrase")
              .highlight("body")
    )
```

Note that the default filter operator for `Query#search` is `match`.

### Type

The query type can be set as:

```javascript
// types = "fetch", "count", "scan"
Query.builder().type("fetch")
Query.builder().fetch()
Query.builder().count()
Query.builder().scan()
```


### Sort

```javascript
// ascending sort by field value
Query.builder().sort("name")

// custom sort direction ("asc", "desc")
Query.builder().sort("name", "asc")
```


### Limit

```javascript
Query.builder().limit(10)
```


### Offset

```javascript
Query.builder().from(1)
```


# Filter

A simple `Filter` (SQL filter predicate) can be build as follows:

```javascript
// The filter
Filter.of("name", "john")

// is the same as
Filter.of("name", "=", "john")
```

A filter can be upgraded to a composite filter as follows:

```javascript
// The filter
Filter.of("name", "john").and("age", 25)

// is the same as
Filter.of("name", "john").and("age", "=", 25)

// and also the same as
Filter.of("name", "john").and(Filter.of("age", 25))
```

Once upgraded, this field can only use the same composition operator.
For disjunction we support:

```javascript
// Simple "or" filter
Filter.of("name", "john").or("name", "michael").or("age", ">", 25)

// Search dis_max operator (uses maximum sub-filter score as query score)
Filter.of("name", "john").disMax("name", "michael")
```

The `not` composition operator can be used as follows:

```javascript
Filter.notOf("name", "john")            // not named John 
      .not("age", ">", 25)              // and not older than 25 years
      .not(Filter.gt("height", 1.5))    // and not taller than 1.5m
```

Example of complex composite filter:

```javascript
// this filter
SearchFilter.match("star wars")
	.and(
		SearchFilter.range("year", 1980, 1990)
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
	    SearchFilter.range("year", 1980, 1990),
	    Filter.of("title", "fuzzy", "clone")
	),
	Filter.of("rating", ">", 8.0),
	Filter.notOf(
	    Filter.in("category", "comedy", "horror")
	)
)
```

The class `SearchFilter` can be used to build filters with search operators:

```javascript
SearchFilter.match("matrix")
SearchFilter.fuzzy("description", "neo the one")
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

### Search filters

```javascript
SearchFilter.exists("title")
SearchFilter.missing("name")
```

```javascript
SearchFilter.range("age", 20, 30)
```


#### Text match

A simple text match filter can be specified as:

```javascript
// default field is "*" (all indexed fields)
SearchFilter.match("search query")

SearchFilter.match("title", "The matrix")
```

To configure a advanced match type you can set the match type:

```javascript
// types = PHRASE, PHRASE_PREFIX
SearchFilter.match("search phrase")
            .type(MatchFilter.MatchType.PHRASE)
```

Or use one of the methods:

```javascript
// The filter
SearchFilter.phrase("match this phrase")

// is the same as
SearchFilter.match("match this phrase", MatchFilter.MatchType.PHRASE)

// and the filter
SearchFilter.phrasePrefix("match an incomplete phra")

// is the same as
SearchFilter.match("match this phrase", MatchType.PHRASE_PREFIX)
```

To filter by term prefix (prefix filter) use:

```javascript
SearchFilter.prefix("scar")
SearchFilter.prefix("title", "scar") // matches "Scarface" and "Scary movie"
Filter.of("title", "prefix", "scar")
```


#### Fuzzy

To add fuzziness to the text match, use:

```javascript
SearchFilter.fuzzy("content")
SearchFilter.fuzzy("name", "Lais") // also matches Louis
```

To specify the edit distance (0, 1 or 2) as fuzziness:
 
```javascript
SearchFilter.fuzzy("name", "Lais").fuzziness(1) // matches Luis, but not Louis
SearchFilter.fuzzy("name", "Lais", 2)           // matches Luis, Luiz and Louis 
```

You can also specify the fuzziness as the percentage [0..1) of the word that 
needs to match (note that the maximum edit distance is always 2):

```javascript
SearchFilter.fuzzy("document text").fuzziness(0.5) // matches "docmnt tx"
SearchFilter.fuzzy("body", "document", 0.75)       // matches documents but not docume
```


#### Common terms

To dynamically define word relevance without a static definition of stop-words,
use the common terms filter as follows:

```javascript
SearchFilter.common("a phrase with common words")     // [a,with] are high frequency terms
                                                      // [phrase,common,words] are low frequency

SearchFilter.common("title", "p.s. i love you")       // [i,you] are high frequency terms
                                                      // [p,s,love] are low frequency

Filter.of("title", "common", "p.s. i love you")
```

The common terms behavior is:

```javascript
// this filter
SearchFilter.common("body", "search with this phrase")

// is dynamically converted to
Filter.andOf(
        Filter.of("body", "search").or("body", "phrase"),
        Filter.of("body, "with").or("body", "this")
    )
```

To specify the word frequency threshold:
 
```javascript
SearchFilter.common("video may be a high frequency word")
            .threshold(0.1)		// if "video" appears in at least 10% of the base

Filter.common("title", "the matrix", 0.001)
```


#### More like this

To search by example use the more like this filter:

```javascript
SearchFilter.moreLikeThis(document.body())
SearchFilter.moreLikeThis("body", document.body())
```

You can add a list of stop-words to be used against the query text:

```javascript
SearchFilter.moreLikeThis(document.body())
            .stopWords("this", "is", "a")
```

You can also specify values to control term and document frequency for extracted terms:

```javascript
//	min term frequency in the query (removes non-representative query terms)
//	min document frequency in index (removes non-representative terms)
//	max document frequency in index (removes common terms)
SearchFilter.moreLikeThis("this content is just an example. it is just an example")
			.minTf(2)       // will select "is", "an", "just" and "example" 
			.minDf(100)     // may exclude "example" as non-representative for the base
			.maxDf(1000)    // may exclude "is" and "an" as too common
```

To combine the more like this query construction with a fuzzy match, use fuzzy like this:

```javascript
SearchFilter.fuzzyLikeThis(document.body())
SearchFilter.fuzzyLikeThis("body", document.body())
```

In this case it is only possible to set the fuzziness, as we do with the fuzzy filter:

```javascript
SearchFilter.fuzzyLikeThis(document.body()).fuzzyness(0.5)
SearchFilter.fuzzyLikeThis("body", document.body(), 2)
```

#### Geolocation

A `GeoPoint` can be specified as one of the following:

```javascript
// [lon,lat] (GeoJson format)
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
Geo.polygon([0,0], "0,10", Geo.point(10,0), "0,0")
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

The `Search` class is used to build complext search filters and predicates.

### Query, Pre and Post filters

`query`, `preFilter` and `postFilter` can be used to add filters to the search.
The supported methods follow the pattern of `Query#filter`, but here the default
operator is `match` and the default field is `"*"` (all indexed fields).

```javascript
Search.builder()
      .query("star wars")
	  .preFilter("year", ">", 2000)
	  .postFilter(Filter.in("category", "action"))
```

The default composition operator is `AND`.


### Highlights

Search highlight on fields can be added as follows:

```javascript
Search.builder().highlight("body")
Search.builder().highlight("body", 100) 		// 100 characters fragments
Search.builder().highlight("body", 100, 2)		// 2 fragments of 100 characters
```


### Aggregations

Aggregation results can be defined as:

```javascript
Search.builder().aggregate("youngest", Aggregation.min("age"))

// operators = min, max, sum, avg, stats, extended_stats, count, terms, missing
Search.builder().aggregate("average_age", "age", "avg")
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
Aggregation.distance("geo", "location", "0,0", [0,1], [1,2], [2,3])
           .unit("km")
```

### Cursor

A search cursor for page navigation can be specified:

```javascript
Search.builder().cursor(result.nextPage())
```
