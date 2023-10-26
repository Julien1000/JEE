document.addEventListener('DOMContentLoaded', function() {
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
      li_value = li.querySelector(".category_value").textContent;
      category.textContent = li_value;
      listbox.style.display = "none";

      const svg = li.querySelector("svg");
      svg.classList.remove("svg_select");
      svg.classList.add("svg_selected");
    });
  });
});
