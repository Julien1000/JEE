# JEE
Projet JEE 2023/2024 Groupe : Les Éclairs Cosmiques

# Thème : Application web de vente de billet d'évènement

# Fonctionnalitées
Adminsitrateur :
 - Ajouter/modifier/supprimer une catégorie d'évènement
 - Ajouter/modifier/supprimer un produit : exemple concert de The Weeknd
 - Ajouter un detail de produit : exemple le 15/08/2023 au Stade de France
   Chaque produit peut avoir plusieurs détails, permettant à l'utilisateur de choisir parmis les dates et les lieux
 - Ajouter une catégorie de place : exemple fosse à 50 euros, 60 places disponibles
   Chaque detail de produit peut avoir plusieurs catégories de place permettant à l'utilisateur de chosisir où se placer grâce à la carte du satde
 - Suivre les commandes et changer leurs états
 - Afficher des statsitiques sur les commandes: catégorie la plus populaire, produit phare
 - Aperçu de tous les formulaires de contact qui ont été envoyés et possibilitées de les supprimer
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
- [mysql] est utilisé pour sotcker les différentes données 
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
- [UserDetail] contient
  
src/main/ressources
- /static :
 - [css] contient tous les fichiers css
 - [js] contient tous les fichiers javascript
 - [img] contient les images du projet
- /templates contient les différents templates des pages du projet

