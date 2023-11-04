document.addEventListener("DOMContentLoaded", function () {
  // Sélectionnez tous les boutons radio
  var radios = document.querySelectorAll('input[type="radio"]');
  console.log("On est dans le js");

  // Ajoutez un gestionnaire d'événements 'change' à chaque bouton radio
  radios.forEach(function (radio) {
    radio.addEventListener("change", function () {
      console.log("change");
      var price = this.dataset.prix;

      // Affichez le prix dans le p
      var pElement = document.getElementById("prix_affiche");
      pElement.innerText = price + " €";
    });
  });
});
