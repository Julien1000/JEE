document.addEventListener("DOMContentLoaded", function () {
  $(document).on("click", ".category_choose", function () {
    console.log("DOM loaded");
    // Sélectionner tous les boutons radio
    var radios = document.querySelectorAll('input[type="radio"]');
    // Ajouter un gestionnaire d'événements 'change' à chaque bouton radio
    radios.forEach(function (radio) {
      radio.addEventListener("change", function () {
        console.log("change");
        var price = this.dataset.prix;

        // Afficher le prix dans le p
        var pElement = document.getElementById("prix_affiche");
        pElement.innerText = price + " €";
      });
    });

    var categories = document.querySelectorAll(".category_choose");

    // Ajouter un gestionnaire d'événements 'click' à chaque élément de catégorie
    categories.forEach(function (categorie) {
      categorie.addEventListener("click", function () {
        // Lorsqu'un élément de catégorie est cliqué, parcourir tous les éléments de catégorie
        categories.forEach(function (otherCategorie) {
          // Réinitialiser le style de tous les autres éléments de catégorie
          otherCategorie.classList.remove(
            "bg-indigo-600",
            "text-white",
            "hover:bg-indigo-500"
          );
          otherCategorie.classList.add(
            "bg-white",
            "text-gray-900",
            "hover:bg-gray-50"
          );
        });

        // Changer le style de l'élément de catégorie cliqué
        this.classList.add(
          "bg-indigo-600",
          "text-white",
          "hover:bg-indigo-500"
        );
        this.classList.remove("bg-white", "text-gray-900", "hover:bg-gray-50");
      });
    });
  });

  $("#detailProduit").on("change", function () {
    var detailProduitId = $(this).val();
    if (detailProduitId) {
      // Effectuer une requête AJAX pour obtenir les détailsProduit en fonction du produit sélectionné
      $.get("/getCategoryByDetail/" + detailProduitId, function (data) {
        $(".categories-date").empty();
        $(".image_lieu").empty();
        $.each(data, function (index, categoriePlace) {
          $(".categories-date").append(
            $(
              '<label class=" category_choose group relative flex cursor-pointer items-center justify-center rounded-md border bg-white px-4 py-3 text-sm font-medium uppercase text-gray-900 shadow-sm hover:bg-gray-50 focus:outline-none sm:flex-1 sm:py-6"><input type="radio" name="idCategoriePlace" value="' +
                categoriePlace.id +
                '" data-prix="' +
                categoriePlace.prix +
                '"  class="sr-only" aria-labelledby="size-choice-label" /><span id="size-choice-label" text="' +
                categoriePlace.nomCategoriePlace +
                '">' +
                categoriePlace.nomCategoriePlace +
                '</span><span class="pointer-events-none absolute -inset-px rounded-md" aria-hidden="true"></span><span class="' +
                categoriePlace.nomCategoriePlace +
                '" id="' +
                categoriePlace.prix +
                '"></span></label'
            )
          );
        });
        $(".image_lieu").append(
          $(
            '<img src="/displayImageLieu/' +
              detailProduitId +
              '" class="h-full w-full object-cover object-center"/>'
          )
        );
      });
    } else {
      $(".categories-date").empty();
      $(".image_lieu").empty();
    }
  });

  function changeQuantity(element, operation) {
    var productId = element.getAttribute("data-product-id");
    var stock = element.getAttribute("data-stock");
    var quantityInput = document.getElementById("quantity" + productId);
    var currentQuantity = parseInt(quantityInput.value);

    if (operation === "plus" && currentQuantity < stock) {
      quantityInput.value = currentQuantity + 1;
    } else if (operation === "minus" && currentQuantity > 0) {
      quantityInput.value = currentQuantity - 1;
    }
  }

  function addToCart(element) {
    var productId = element.getAttribute("data-product-id");
    var productPrix = element.getAttribute("data-product-prix");
    var quantity = document.getElementById("quantity" + productId).value;
    console.log(
      "Produit ID:",
      productId,
      "Quantité:",
      quantity,
      "Prix",
      productPrix
    );
  }
});
