# Webflix

## Introduction

Webflix est un projet réalisé dans le cadre de la formation du Master MIAGE pour l'UE d'Applications Web.
Ce projet consiste à réaliser une application de vidéo à la demande (comme [Netflix][]).
Le contenu de l'application se base sur une liste de films "open-source" : [List of open-source films][].

## Equipe

* Albasser Sylvain : [Pandraghon][]
* Neghouche Akim : [Akim0992][]
* Raji Ines : [InesMiage][]

## Installation

### Prérequis

[Git][] et [JDK 8 update 20 ou plus récent][JDK8 build]  
Assurez-vous que la variable d'environnement `JAVA_HOME` pointe vers le dossier `jdk1.8.0`.

### Téléchargement des sources

Clonez le projet  
`git clone git@github.com:Pandraghon/projet-netflix.git`  
ou [télechargez le ZIP][ZIP]

### Importation des sources dans l'IDE

#### Eclipse

`File > Import > Git > Projects from Git`

#### NetBeans

`Team > Git > Clone`

### Compilation

`Run As... > Maven Build` Goals : `spring-boot:run`


[Netflix]: https://www.netflix.com
[List of open-source films]: https://en.wikipedia.org/wiki/List_of_open-source_films
[Pandraghon]: https://github.com/Pandraghon
[Akim0992]: https://github.com/Akim0992
[InesMiage]: https://github.com/InesMiage
[Git]: http://help.github.com/set-up-git-redirect
[JDK8 build]: http://www.oracle.com/technetwork/java/javase/downloads
[ZIP]: https://github.com/Pandraghon/projet-netflix/archive/master.zip