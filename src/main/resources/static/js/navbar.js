document.addEventListener('DOMContentLoaded', function() {
  console.log("navbar.js");
  const profile = document.getElementById("user-menu-button");
  const profileMenu = document.getElementById("user-menu");

  profile.addEventListener("click", function () {
    profileMenu.classList.toggle("hidden");
    console.log("click");
  });

  const mobileMenuButton = document.getElementById("mobile-menu-button");
  const mobileMenu = document.getElementById("mobile-menu");

  mobileMenuButton.addEventListener("click", function () {
    mobileMenu.classList.toggle("hidden");
  });

  // Sélectionnez tous les boutons de la navbar
const buttons = document.querySelectorAll('.navbar-button');

// Gestionnaire d'événement clic pour chaque bouton
buttons.forEach(button => {
  button.addEventListener('click', function() {
    // Retirez la classe "button-active" de tous les boutons
    buttons.forEach(btn => {
      btn.classList.remove('button-active');
      btn.classList.add('button-inactive');
    });

    // Ajoutez la classe "button-active" au bouton actuel
    button.classList.add('button-active');
  });
});


});

document.addEventListener('DOMContentLoaded', function() {
  const searchBar = document.getElementById('searchbar');
  const produitsList = document.getElementById('results-list');
  const MAX_VISIBLE_ITEMS = 5;

  const positionProduitsList = () => {
    const rect = searchBar.getBoundingClientRect();
    produitsList.style.top = (rect.bottom + window.scrollY) + 'px';
    produitsList.style.left = (rect.left + window.scrollX) + 'px';
    produitsList.style.width = rect.width + 'px';
  };

  searchBar.addEventListener('focus', positionProduitsList);

  searchBar.addEventListener('keyup', function() {
    const searchText = searchBar.value.toLowerCase();

    if (searchText.length >= 3) {
      fetch(`/api/produits/search?query=${searchText}`)
        .then(response => response.json())
        .then(produits => {
          produitsList.innerHTML = '';
          produits.slice(0, MAX_VISIBLE_ITEMS).forEach(produit => {
            const li = document.createElement('li');
            li.classList.add('produits');
            li.textContent = produit.name;
            li.setAttribute('data-id', produit.id);
            produitsList.appendChild(li);
          });

          produitsList.style.display = 'block';
          positionProduitsList();
          console.log("Produits chargés", produits);
        })
        .catch(error => console.error('Erreur lors de la récupération des produits:', error));
    } else {
      produitsList.style.display = 'none';
    }
  });

  document.addEventListener('click', function(event) {
    if (!searchBar.contains(event.target) && !produitsList.contains(event.target)) {
      produitsList.style.display = 'none';
    }
  });

  window.addEventListener('resize', function() {
    if (produitsList.style.display === 'block') {
      positionProduitsList();
    }
  });

  produitsList.addEventListener('click', function(event) {
    if (event.target.classList.contains('produits')) {
      const produitId = event.target.getAttribute('data-id');
      window.location.href = '/produit/perso/' + produitId;
    }
  });
});




document.addEventListener("DOMContentLoaded", function () {

  const adminMenuButton = document.querySelector(".admin-menu-button");
  const dropdown = document.querySelector(".dropdown-menu");

  adminMenuButton.addEventListener("click", function () {
    if (dropdown.style.display === "block") {
      dropdown.style.display = "none";
    } else {
      dropdown.style.display = "block";
    }
  });

  // Gérer la fermeture du dropdown lorsqu'on clique en dehors
  document.addEventListener("click", function (e) {
    if (!adminMenuButton.contains(e.target) && !dropdown.contains(e.target)) {
      dropdown.style.display = "none";
    }
  });

});