# JEE
Projet JEE 2023/2024  
Groupe : Les Éclairs Cosmiques.  
BERNARD Adrien, DUPONT Hippolyte, PAPINI Julien, HERAS ESPINOSA Alejandro

# Thème : Application web de vente de billet d'évènement

# Fonctionalitées
Administrateur :
 - Ajouter/modifier/supprimer une catégorie d'évènement, exemple : concert, spectacle, etc...
 - Ajouter/modifier/supprimer un produit, exemple : concert de The Weeknd
 - Ajouter un detail de produit, exemple : le 15/08/2023 au Stade de France  
   Chaque produit peut avoir plusieurs détails, permettant à l'utilisateur de choisir parmi les dates et les lieux
 - Ajouter une catégorie de place : exemple fosse à 50 euros, 60 places disponibles  
   Chaque detail de produit peut avoir plusieurs catégories de place permettant à l'utilisateur de chosisir où se placer grâce à la carte du satde
 - Suivre les commandes et changer leurs états
 - Afficher des statsitiques sur les commandes: catégorie la plus populaire, produit phare
 - Aperçu de tous les formulaires de contact qui ont été envoyés et possibilité de les supprimer
 - Aperçu de tous les produits et de toutes les catégories
   
Utilisateur :
 - Ajouter/supprimer un élément à son panier
 - Valider son panier et suivre l'état de sa commande
 - Rechercher un produit via une barre de recherche ergonomique
 - Contacter l'adminsitrateur via un formulaire
 - Créer un compte et se connecter

# Technologies utilisées
- [Spring] est un framework JEE utilisé pour la structure du projet
- [HTML] est utilisé pour structurer le contenu d'une page web
- [tailwind/CSS] est un framework css utilisé pour styliser et mettre en forme les pages
- [Javascript] est utilisé pour rendre dynamique les pages
- [SpringSecurity] est utilisé pour la sécurité du site
- [mysql] est utilisé pour stocker les différentes données 
- [Chart.js] est une bibliothèque utilisée pour créer des graphes et des visualisations de données
- [Hibernate] est utilisé pour la communication avec la base de donnée
- [Thymeleaf] est utilisé pour la communication avec la base de donées et les vues

# Arborescence
src/main/java/ProjetJee/ProjetJee : 
- [Config] contient les controllers responsables de la configuration avant lancement du projet
- [Controller] contient tous les controllers du projet
- [Entity] contient tous les fichiers des entitées de la base de données
- [Repository] contient tous les repository du projet
- [SecurityConfig] contient tous les fichiers nécéssaires à la configuration de la sécurité du projet
- [Service] contient tous les services du projet
- [UserDetail] contient le service pour le chargement des utilisateurs
  
src/main/ressources :  
/static :
 - [css] contient tous les fichiers css
 - [js] contient tous les fichiers javascript
 - [img] contient les images du projet
   
/templates/ contient les différents templates des pages du projet

# Améliorations depuis la soutenance
- Remaniement de la base de données pour qu'elle soit plus adaptée à notre thème de vente de billet
- Ajout d'images représentant les différents stades pour choisir sa catégorie de place
- Choix parmi différentes dates, différents lieux et différentes catégories de place pour un seul et même évènement
- Ajout du formulaire de contact et de la liste des demandes pour l'administrateur
- Panier fonctionnel
- Correction et optimisation de la barre de recherche

# Récupération de l'archive 
Télécharger l'archive via le lien puis la dézipper :
- unzip JEE
- cd JEE

# Initialisation de la base de données
Dans mysql :  
Création de la base de donnée
- CREATE DATABASE IF NOT EXISTS JEE;
  
Cette étape permet d'insérer des objets dans la base de données pour une démonstration.  
Le projet peut-être utilisé sans cette étape mais il est préférable de le faire pour une utilisation plus réaliste.  
Utilisation de la base de donnée
- use JEE;
Chargement des données
- source JEE.sql;

# Lancement du projet
Avant de lancer le projet il faut se rendre dans le fichier src/main/ressources/application.properties et mettre l'identifiant et le mot de passe de votre mysql.  
Dans eclipse, cliquez sur run une fois dans le fichier src/main/java/ProjetJEE/ProjetJEE/ProjetJeeApplication.java.

# Utilisation
Peu importe si les données ont été initalisée avec le fichier sql ou non, il est possible d'utiliser l'application :  

Compte administrateur :
- username : admin
- email : admin@gmail.com
- password : admin

Compte utilisateur :
- username : test
- email : test@gmail.com
- password : test

Il est aussi possible de se créer un compte utilisateur via le formulaire d'inscription.  
Pour se connecter, vous pouvez utiliser l'email ou le nom d'utilisateur d'un compte ainsi que son mot de passe dans le formulaire de connexion.  

Pour pouvoir être ajouté au panier, un produit doit avoir une catégorie de place.  
Il faut donc d'abord créer une catégorie, puis créer un produit, puis créer un detail de produit pour ce produit et enfin une catégorie de place pour le detail du produit du produit en question.  

Pour le moment, il est nécessaire d'être connecté pour avoir accès aux différentes parties du site. Pour vous connecter cliquer sur l'image de profil en haut à droite.

# Nous contacter

Adrien : bernardadr@cy-tech.fr  
Hippolyte : duponthipp@cy-tech.fr  
Julien : papinijuli@cy-tech.fr  
Alejandro : herasespin@cy-tech.fr  


