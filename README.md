# skos_categories_taxonomy_miner
Arrange [SKOS Wikipedia Categories Dataset](http://wiki.dbpedia.org/services-resources/documentation/datasets#skoscategories) into plain text files.

##Requirements

* JDK 8 (Java 1.8)
* Project Lombok: https://projectlombok.org/
* javatuples: http://www.javatuples.org/

##Input

* `skos_categories_en.ttl`: DBpedia SKOS Categories dataset in `.ttl` format: http://wiki.dbpedia.org/downloads-2016-04

##Output

* `wiki_cat.txt`: List of category ids with texts (tab-splitted).
* `wiki_relation.txt`: List of parent-child relations between categories (tab-splitted). **(Note: The relations are graphs, not trees!)**

##Sample Output

* `wiki_cat.txt`:
```
1	Futurama
2	World War II
3	Programming languages
4	Professional wrestling
5	Algebra
6	Anime
7	Abstract algebra
8	Mathematics
9	Linear algebra
10	Calculus
11	British monarchs
12	Monarchs
13	Star Trek
14	People
15	Desserts
...	...
```

* `wiki_relation.txt`:

```
1	4044
1	25036
1	250051
1	806081
2	2659
2	28202
2	40342
2	91655
2	126070
2	142632
2	215635
2	292083
2	293028
2	293214
2	298882
...	...
```

## License

Licnese of the source codes in this repository can be found in file `LICENSE.md`.

The SKOS Wikipedia Categories Dataset is licensed under [Creative Commons Attribution-ShareAlike 3.0 License](https://en.wikipedia.org/wiki/Wikipedia:Text_of_Creative_Commons_Attribution-ShareAlike_3.0_Unported_License) and the [GNU Free Documentation License](https://en.wikipedia.org/wiki/Wikipedia:Text_of_the_GNU_Free_Documentation_License). See http://wiki.dbpedia.org/about.

The copyright notices of Wikipedia can be found at https://en.wikipedia.org/wiki/Wikipedia:Copyrights.
