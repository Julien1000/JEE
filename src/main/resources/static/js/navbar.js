document.addEventListener("DOMContentLoaded", function () {
  console.log("navbar.js");
  const profile = document.getElementById("user-menu-button");
  const profileMenu = document.getElementById("user-menu");

  profile.addEventListener("click", function () {
    profileMenu.classList.toggle("hidden");
  });

  // Quand on clique en dehors du menu, on le ferme
  document.addEventListener("click", function (event) {
    if (!profile.contains(event.target) && !profileMenu.contains(event.target)) {
      profileMenu.classList.add("hidden");
    }
  });


  const mobileMenuButton = document.getElementById("mobile-menu-button");
  const mobileMenu = document.getElementById("mobile-menu");

  mobileMenuButton.addEventListener("click", function () {
    mobileMenu.classList.toggle("hidden");
  });

  // Sélectionner tous les boutons de la navbar
  const buttons = document.querySelectorAll(".navbar-button");

  // Gestionnaire d'événement clic pour chaque bouton
  buttons.forEach((button) => {
    button.addEventListener("click", function () {
      // Retirer la classe "button-active" de tous les boutons
      buttons.forEach((btn) => {
        btn.classList.remove("button-active");
        btn.classList.add("button-inactive");
      });

      // Ajouter la classe "button-active" au bouton actuel
      button.classList.add("button-active");
    });
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const searchBar = document.getElementById("searchbar");
  const produitsList = document.getElementById("results-list");
  const MAX_VISIBLE_ITEMS = 5;

  const positionProduitsList = () => {
    const rect = searchBar.getBoundingClientRect();
    produitsList.classList.add("absolute", "w-full", "top-full", "z-9999", "block");
  };

  searchBar.addEventListener("focus", positionProduitsList);

  searchBar.addEventListener("keyup", function () {
    const searchText = searchBar.value.toLowerCase();

    if (searchText.length >= 3) {
      fetch(`/api/produits/search?query=${searchText}`)
        .then((response) => response.json())
        .then((produits) => {
          produitsList.innerHTML = "";
          produits.slice(0, MAX_VISIBLE_ITEMS).forEach((produit) => {
            const li = document.createElement("li");
            produitsList.classList.remove("hidden")
            li.classList.add("produits", "bg-white", "hover:bg-gray-100", "cursor-pointer", "rounded-lg");
            li.textContent = produit.name;
            li.setAttribute("data-id", produit.id);
            produitsList.appendChild(li);
          });

          positionProduitsList();
          console.log("Produits chargés", produits);
        })
        .catch((error) =>
          console.error("Erreur lors de la récupération des produits:", error)
        );
    } else {
      produitsList.classList.remove("absolute", "w-full", "top-full", "z-9999", "block");
      produitsList.classList.add("hidden")
    }
  });

  document.addEventListener("click", function (event) {
    if (
      !searchBar.contains(event.target) &&
      !produitsList.contains(event.target)
    ) {
      produitsList.classList.remove("absolute", "w-full", "top-full", "z-9999", "block");
      produitsList.classList.add("hidden")
    }
  });

  window.addEventListener("resize", function () {
    if (produitsList.style.display === "block") {
      positionProduitsList();
    }
  });

  produitsList.addEventListener("click", function (event) {
    if (event.target.classList.contains("produits")) {
      const produitId = event.target.getAttribute("data-id");
      window.location.href = "/produit/perso/" + produitId;
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

// Changer le fond de la searchbar lorsqu'on clique dessus
document.addEventListener("DOMContentLoaded", function () {
  const searchBarDiv = document.querySelector(".searchbar_container");
  const searchBarDiv2 = document.querySelector(".searchbar_under_container");
  const searchInput = document.querySelector(".searchbar_input");

  searchBarDiv.addEventListener("click", function () {
    searchBarDiv2.classList.add("bg-white");
    searchBarDiv2.classList.remove("bg-slate-700");
    searchInput.classList.add("bg-white");
    searchInput.classList.remove("bg-slate-700");
    searchInput.focus();
  });

  document.addEventListener("click", function (e) {
    if (!searchBarDiv.contains(e.target) && !searchInput.contains(e.target)) {
      searchInput.value = "";
      searchInput.placeholder = "Rechercher un produit";
      searchBarDiv2.classList.remove("bg-white");
      searchBarDiv2.classList.add("bg-slate-700");
      searchInput.classList.remove("bg-white");
      searchInput.classList.add("bg-slate-700");
    }
  });
});
