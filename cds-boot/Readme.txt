This app is an example of using spring-boot and stardog to do reasoning on SNOMED terminalogy service.

Prerequisites:

The SNOMED ontology is loaded in the Stardog triple store (http://stardog.com/)
To create data base use command line utility, as in the example below.
stardog-admin db create -n snomed -s (manualy create, all defaults, search is on)
-s : search enabled

the qwl files are too big for stardog to load in one scoop.
Run scripts to create chunks up to 64K triples.
Add data manually
1) from SNOMED-ontology/SNOMED_module4fma
2) from SNOMED-ontology/from SNOMED_module4nci
Latest ontology can be downloaded from http://bioportal.bioontology.org/. Registration may be required.

 
RxNorm MySql Database. (http://www.mysql.com/)
Latest rxNorm database scripts can be downloaded from http://bioportal.bioontology.org/.

The scripts come with instructions how to load the data into MySql.
PTSD Web Service

The web service for CDS is implemented using RestController functionality available in Spring 4.

The Controller receives REST get message with list of ndcs and rxNorms. Example:
http://localhost:7007/riskyMeds?ndcList=54868077800;35356033500;50458051570&=rxnormList=312134.
The CDSController uses RxnormServce to resolve the ndc and rxNorm codes to identify the main drug ingredient.

The next step is to run the text search (full index provided) to identify the uri of the drug main ingredient in the SNOMED ontology.
And finally to run the reasoning binary query to check if the drug belongs to the risky meds category defined by medical subject mater experts.
When the information for all the drugs in request collected, the clinical advice is provided based on set of rules and the result is sent back as a JSON

Examples.
Request:
http://localhost:7007/classesAll?drug=Oxycodone

Response:
  {
    "success": true,
    "lexMeds": [
    {
    "ingredient": "Oxycodone",
    "classifications": [
	    " Oxycodone",
	    " Opiate agonist",
	    " Central depressant",
	    " Psychoactive substance",
	    " Substance categorized functionally",
	    " Substance",
	    " Unapproved attribute",
	    " Attribute",
	    " Linkage concept",
	    " SNOMED CT Concept",
	    " Thing",
	    " Central nervous system agent",
	    " Drug or medicament",
	    " Drug pseudoallergen by function",
	    " Drug pseudoallergen",
	    " Drug allergen or pseudoallergen",
	    " Allergen or pseudoallergen",
	    " Allergen class",
	    " Analgesic",
	    " Pharmaceutical / biologic product",
	    " Morphine derivative",
	    " Heterocyclic compound",
	    " Organic compound",
	    " Chemical compound",
	    " Chemical categorized structurally",
	    " Substance categorized structurally",
	    " Opiate",
	    " CNS drug"
	   ]
    }
    ]
    }

Request:http://localhost:7007/riskyMeds?ndcList=54868077800;35356033500

Response:
{
  "success": true,
  "isRiskyMeds": true,
  "lexMeds": [
    {
      "ingredient": "Temazepam",
      "ndc": "54868077800",
      "rxnorm": "208464",
      "classifications": [
        "Anxiolytic",
        "Hypnotic And Or Sedative"
      ]
    },
    {
      "ingredient": "Morphine",
      "ndc": "35356033500",
      "rxnorm": "892574",
      "classifications": [
        "Analgesic"
      ]
    }
  ],
  "errors": null
}



I am not checking-in all the jars form stardog, there is a bunch of extras.
Here is my classpath dump.That worked for me.
        <classpathentry kind="lib" path="lib/stardog-icv-api-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-icv-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-icv-snarl-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-icv-snarl-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-reasoning-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-reasoning-api-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-reasoning-protocol-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-reasoning-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-reasoning-snarl-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-reasoning-snarl-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-search-api-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-search-snarl-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-snarl-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-snarl-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-watchdog-protocol-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-watchdog-snarl-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-watchdog-snarl-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/bigpacket-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/bigpacket-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/cp-common-protobuf-1.2.jar"/>
        <classpathentry kind="lib" path="lib/netty-all-4.0.17.Final.jar"/>
        <classpathentry kind="lib" path="lib/pellet-3.0.1.jar"/>
        <classpathentry kind="lib" path="lib/protobuf-java-2.5.0.jar"/>
        <classpathentry kind="lib" path="lib/aopalliance-1.0.jar"/>
        <classpathentry kind="lib" path="lib/asm-3.1.jar"/>
        <classpathentry kind="lib" path="lib/blackout-1.1.5.jar"/>
        <classpathentry kind="lib" path="lib/cglib-2.2.1-v20090111.jar"/>
        <classpathentry kind="lib" path="lib/commons-codec-1.4.jar"/>
        <classpathentry kind="lib" path="lib/commons-compress-1.6.jar"/>
        <classpathentry kind="lib" path="lib/commons-httpclient-3.1.jar"/>
        <classpathentry kind="lib" path="lib/commons-io-2.4.jar"/>
        <classpathentry kind="lib" path="lib/commons-lang-2.6.jar"/>
        <classpathentry kind="lib" path="lib/commons-pool-1.6.jar"/>
        <classpathentry kind="lib" path="lib/cp-common-openrdf-2.0.2.jar"/>
        <classpathentry kind="lib" path="lib/data-exporter-1.0.0.jar"/>
        <classpathentry kind="lib" path="lib/guava-15.0.jar"/>
        <classpathentry kind="lib" path="lib/guice-3.0.jar"/>
        <classpathentry kind="lib" path="lib/guice-assistedinject-3.0.jar"/>
        <classpathentry kind="lib" path="lib/guice-multibindings-3.0.jar"/>
        <classpathentry kind="lib" path="lib/guice-throwingproviders-3.0.jar"/>
        <classpathentry kind="lib" path="lib/hppc-0.5.2.jar"/>
        <classpathentry kind="lib" path="lib/javax.inject-1.jar"/>
        <classpathentry kind="lib" path="lib/jsonld-java-1.0.1-SNAPSHOT.jar"/>
        <classpathentry kind="lib" path="lib/jsonld-java-sesame-1.0.1-SNAPSHOT.jar"/>
        <classpathentry kind="lib" path="lib/jsr305-1.3.9.jar"/>
        <classpathentry kind="lib" path="lib/opencsv-2.0.jar"/>
        <classpathentry kind="lib" path="lib/openrdf-sesame-2.7.10.jar"/>
        <classpathentry kind="lib" path="lib/protocols-api-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/protocols-api-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-common-rdf-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-icv-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-icv-core-api-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-protocols-spec-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-protocols-spec-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-snarl-api-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/xz-1.4.jar"/>
        <classpathentry kind="lib" path="lib/stardog-http-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-http-shared-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-icv-http-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-reasoning-http-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-search-http-client-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/activation-1.1.jar"/>
        <classpathentry kind="lib" path="lib/commons-logging-1.1.1.jar"/>
        <classpathentry kind="lib" path="lib/gson-2.2.1.jar"/>
        <classpathentry kind="lib" path="lib/httpclient-4.2.2.jar"/>
        <classpathentry kind="lib" path="lib/httpcore-4.2.2.jar"/>
        <classpathentry kind="lib" path="lib/httpmime-4.2.2.jar"/>
        <classpathentry kind="lib" path="lib/json-20090211.jar"/>
        <classpathentry kind="lib" path="lib/mailapi-1.4.3.jar"/>
        <classpathentry kind="lib" path="lib/cp-common-utils-3.1.1.jar"/>
        <classpathentry kind="lib" path="lib/stardog-common-utils-2.1.3.jar"/>
        <classpathentry kind="lib" path="lib/stardog-openrdf-utils-2.1.3.jar"/>
  
