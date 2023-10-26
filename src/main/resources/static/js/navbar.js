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

document.addEventListener('DOMContentLoaded', (event) => {
  const searchBar = document.getElementById('searchbar');
  const produitsList = document.getElementById('list');
  const produitsItems = produitsList.getElementsByClassName('produits');

  const MAX_VISIBLE_ITEMS = 5;

  const positionProduitsList = () => {
    const rect = searchBar.getBoundingClientRect();
    produitsList.style.top = (rect.bottom + window.scrollY) + 'px';
    produitsList.style.left = (rect.left + window.scrollX) + 'px';
    produitsList.style.width = rect.width + 'px';
  };

 searchBar.addEventListener('focus', function() {
   positionProduitsList();
 });

  searchBar.addEventListener('keyup', function() {
    const searchText = searchBar.value.toLowerCase();

    let visibleCount = 0;
    let produitFound = false;
    for (let produit of produitsItems) {
      const produitName = produit.textContent.toLowerCase();
      const isMatch = produitName.includes(searchText);

      if (isMatch && visibleCount < MAX_VISIBLE_ITEMS) {
        produit.style.display = '';
        visibleCount++;
        produitFound = true;
      } else {
        produit.style.display = 'none';
      }
    }

    produitsList.style.display = produitFound ? 'block' : 'none';
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
  for (let produit of produitsItems) {
    produit.addEventListener('click', function() {
      const produitId = produit.getAttribute('data-id');
      window.location.href = '/produit/perso/' + produitId;
    });
  }

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