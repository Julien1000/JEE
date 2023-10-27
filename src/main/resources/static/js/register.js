document.addEventListener("DOMContentLoaded", function () {
  const registerButton = document.querySelector(".register_button");
  const registerForm = document.querySelector(".register_form");

  registerButton.addEventListener("click", function () {
    registerForm.submit();
  });
});
