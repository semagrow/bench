# KoBE Benchmark Engine #

KoBE Benchmark Engine helps setting up ang manage experimental federated topologies
of triple stores and evaluate federated SPARQL query processors. In particular,
it uses Docker to distribute containerized stores over a Docker swarm cluster.

## Query sets ##

## Datasets ###

In order to run the experiments you will need the following datasets.
For each dataset you could use either the public endpoints, or you can set up
your own (by using the pre populated virtuoso databases)

| Dataset   | Endpoint                          |  
|-----------|-----------------------------------|
| ChEBI     | http://143.233.226.25:8890/sparql |
| Dbpedia   | http://143.233.226.25:8891/sparql |
| DrugBank  | http://143.233.226.25:8892/sparql |
| GeoNames  | http://143.233.226.25:8893/sparql |
| jamendo   | http://143.233.226.25:8894/sparql |
| KEGG      | http://143.233.226.25:8895/sparql |
| LinkedMDB | http://143.233.226.25:8896/sparql |
| NYT       | http://143.233.226.25:8897/sparql |
| SP2Bench  | http://143.233.226.25:8898/sparql |
| SWDF      | http://143.233.226.25:8899/sparql |

### Configuration ###

Our current configuration uses the live endpoints, so if you want to use our
public datasets skip this part.

If you want to use your own endpoints, you should rename the endpoints inside
the configuration/metadata files, i.e.

* for __SemaGrow__:
    * ./suites/semagrow-reactive/lifeScience/lifeScience.void.n3
    * ./suites/semagrow-reactive/crossDomain/crossDomain.void.n3
* for __SemaGrow-HiBISCuS__:
    * ./suites/semagrow-hibiscus/lifeScience/lifeScience.void.n3
    * ./suites/semagrow-hibiscus/crossDomain/crossDomain.void.n3
    * ./suites/semagrow-hibiscus/lifeScience/summaries/lifeScience.void.n3
    * ./suites/semagrow-hibiscus/crossDomain/summaries/crossDomain.void.n3
* for __SemaGrow-TBSS__:
    * ./suites/semagrow-tbss/lifeScience/lifeScience.void.n3
    * ./suites/semagrow-tbss/crossDomain/crossDomain.void.n3
    * ./suites/semagrow-tbss/lifeScience/summaries/lifeScience.void.n3
    * ./suites/semagrow-tbss/crossDomain/summaries/crossDomain.void.n3
* for __SPLENDID__:
    * ./suites/SPLENDID/lifeScience-config_SPLENDID.n3
    * ./suites/SPLENDID/crossDomain-config_SPLENDID.n3
* for __FedX__:
    * ./doc/fedx/LifeScience-FedX-SPARQL.ttl
    * ./doc/fedx/CrossDomain-FedX-SPARQL.ttl
* for __FedX-HiBiSCuS__:
    * ./doc/fedx-hibiscus/summaries/crossDomain.n3
    * ./doc/fedx-hibiscus/summaries/lifeScience.n3

### Running the Experiment ###

In order to run one experiment you should execute:

```
sh runEval.sh ./suites/semagrow-reactive/lifeScience/lifeScience-config.prop
```

The ".prop" files are the entry points for the configuration for each experiment.

* ./suites/semagrow-reactive/crossDomain/crossDomain-config.prop
* ./suites/semagrow-reactive/lifeScience/lifeScience-config.prop
* ./suites/semagrow-hibiscus/crossDomain/crossDomain-config.prop
* ./suites/semagrow-hibiscus/lifeScience/lifeScience-config.prop
* ./suites/semagrow-tbss/crossDomain/crossDomain-config.prop
* ./suites/semagrow-tbss/lifeScience/lifeScience-config.prop
* ./suites/SPLENDID/crossDomain-config.prop
* ./suites/SPLENDID/lifeScience-config.prop
* ./doc/fedx/CrossDomain-FedX-SPARQL.prop
* ./doc/fedx/LifeScience-FedX-SPARQL.prop
* ./doc/fedx-hibiscus/CrossDomain-FedX-SPARQL.prop
* ./doc/fedx-hibiscus/LifeScience-FedX-SPARQL.prop

In order to switch between the three versions of SemaGrow you have to point
the semagrow symbolic link to the corresponding semagrow directory.

* For Semagrow with default source selection, the semagrow symlink should
  point to semagrow-reactive directory.
* For Semagrow with HiBiSCUS source selection, the semagrow symlink should point
  to semagrow-hibiscus directory.
* For Semagrow with TBSS source selection, the semagrow symlink should point
  to semagrow-tbss directory.

### Viewing the results ###

In order to view the results (total query processing time and number of results
for each query), you should open the result/result.csv file in the directory
of the .prop file. In order to view the optimization time you should check
the .log files or the prints during the evaluation of the experiment.
