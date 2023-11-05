document.addEventListener("DOMContentLoaded", function () {
  const listbox = document.querySelector(".list-category");
  const button = document.querySelector(".button_select_category");

  button.addEventListener("click", function () {
    if (listbox.style.display === "block") {
      listbox.style.display = "none";
    } else {
      listbox.style.display = "block";
    }
  });

  const list = document.querySelectorAll(".list-category li");
  list.forEach(function (li) {
    li.addEventListener("click", function () {
      list.forEach(function (li) {
        li.querySelector("svg").classList.remove("svg_selected");
        li.querySelector("svg").classList.add("svg_select");
      });

      const category = document.querySelector(".category_choosen");
      li_text = li.querySelector(".category_value").textContent;
      li_value = li.querySelector(".category_value").getAttribute("value");
      category.textContent = li_text;
      listbox.style.display = "none";
      const hidden_input = document.querySelector(".hidden_input_category");
      hidden_input.setAttribute("value", li_value);

      const svg = li.querySelector("svg");
      svg.classList.remove("svg_select");
      svg.classList.add("svg_selected");
    });
  });

  const fileInput = document.querySelector(".input_file");
  const fileUploadDiv = document.querySelector(".input_file_area");
  const file_text = document.querySelector(".text_image");
  const imageDisplay = document.getElementById('imageDisplay');
  const imageDisplay2 = document.getElementById('imageDisplay2');
  imageDisplay.style.display = "none";

  fileUploadDiv.addEventListener("click", function () {
    fileInput.click();
  });

  fileInput.addEventListener("change", function () {
    const file = fileInput.files[0];
    const fileName = file.name;

    handleFile(file, fileName);
  });


  fileUploadDiv.addEventListener("dragover", function (e) {
    e.preventDefault();
    fileUploadDiv.classList.add("border-indigo-600");
    fileUploadDiv.classList.remove("border-gray-900");
  });

  fileUploadDiv.addEventListener("dragleave", function (e) {
    e.preventDefault();
    fileUploadDiv.classList.add("border-gray-900");
    fileUploadDiv.classList.remove("border-indigo-600");
  });

  fileUploadDiv.addEventListener("drop", function (e) {
    e.preventDefault();
    fileUploadDiv.classList.add("border-gray-900");
    fileUploadDiv.classList.remove("border-indigo-600");
    const file = e.dataTransfer.files[0];
    const fileName = file.name;

    handleFile(file, fileName);
  });


  function handleFile(file, fileName) {
    imageDisplay.style.display = "block";
    if (imageDisplay2) {
      imageDisplay2.style.display = "none";
    }
    file_text.textContent = fileName;
    file_text.classList.add("relative", "cursor-pointer", "rounded-md", "bg-white", "font-semibold", "text-indigo-600", "focus-within:outline-none", "focus-within:ring-2", "focus-within:ring-indigo-600", "focus-within:ring-offset-2", "hover:text-indigo-500");

    const reader = new FileReader();

    // Définissez la fonction à exécuter lorsque le fichier a été lu
    reader.onload = function (e) {
      // Mettez à jour l'élément d'affichage de l'image avec l'image lue
      imageDisplay.src = e.target.result;
    };

    // Lisez le fichier sélectionné
    reader.readAsDataURL(file);
  }

});

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


document.addEventListener("DOMContentLoaded", function () {
  const imageDisplay2 = document.getElementById('imageDisplay2');
  const fileInput = document.querySelector(".input_file");

  imageDisplay2.style.display = "none";

  // Vérifiez l'URL actuelle
  const path = window.location.pathname;
  console.log(path);

  if (path.startsWith('/editProduct')) {
    console.log("path.startsWith('/editProduit')");
    imageDisplay2.style.display = "block";
    fileInput.removeAttribute('required');
  } else if (path.startsWith('/addProduit')) {
    // Si l'URL commence par '/addProduit', faites quelque chose d'autre
    console.log("path.startsWith('/addProduit')");
  }
});