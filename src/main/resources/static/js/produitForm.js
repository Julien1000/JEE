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
  imageDisplay.style.display = "none";

  fileUploadDiv.addEventListener("click", function () {
    fileInput.click();
  });

  fileInput.addEventListener("change", function () {
    imageDisplay.style.display = "block";
    const file = fileInput.files[0];
    const fileName = file.name;
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
  });
});
