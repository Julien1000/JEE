// On attend que le document ait chargé avec DOMContentLoaded
document.addEventListener("DOMContentLoaded", function () {
  // // Sélectionnez l'input de type date
  var dateInput = document.querySelector('input[type="date"]');

  // Obtenez la date d'aujourd'hui
  var today = new Date();

  // Formatez la date au format yyyy-mm-dd
  var formattedDate =
    today.getFullYear() +
    "-" +
    String(today.getMonth() + 1).padStart(2, "0") +
    "-" +
    String(today.getDate()).padStart(2, "0");

    console.log(formattedDate);

  // Définissez l'attribut min de l'input sur la date d'aujourd'hui
  dateInput.min = formattedDate;
  dateInput.value = formattedDate;
});
