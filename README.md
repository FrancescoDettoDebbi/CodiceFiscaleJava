# CodiceFiscaleJava
This repository contains a simple Java class which can generate the "codice fiscale" of a person.


All the files in this repository are meant to be stored in a package called "Fiscalo".

This repository cointains a csv file containing the data scraped from these two url:
    1. https://dait.interno.gov.it/territorio-e-autonomie-locali/sut/elenco_codici_comuni.php // Comuni Italiani
    2. http://www.globallaboratory.it/pit/TB_STATIESTERI1.htm // Stati Esteri
the data is provided in the following shape:
"Comune";"CodiceCatastale"
all the keys "Comune" are in capital letters
all the values "CodiceCatastale" consist of 4 alphanumeric characters: the first one is a letter and all the others are digits.

3 Java classes + 1 Enum are provided:

- CodiceFiscale
- NuovoCodiceFiscale
- Main
- Sesso

CodiceFiscale contains the first implementation of the class.
In this class several attributes and methods have been implemented for utility reasons.
The critical flaw of this class is the implementation of the database HashMap attribute.
This database loads to itself all the data present in the .csv file, which is a pain in the ass from a memory point of view.
Without any doubt this emplementation is daughter of a very stupid software design decision:
keep in mind that java classes should not do the job of the databases.

NuovoCodiceFiscale contains a lighter implementation of the class.
All the only-functional attributes have been deleted and most of the methods have been too.
The vanished attributes are now declared in their specific methods as local variables.
The lack of readability has been compensated by lots of comments which i hope would be helpful in understanding what the code actually does.

The Main class is used for testing

Sesso in an Enum, which contains the possible boilogical sex values
