document.addEventListener('DOMContentLoaded', function() {
  console.log('index.js');
  const navbar = document.getElementById('navbar');
  navbar.classList.add('hidden');
  const closeButton = document.getElementById('close-nav');

  closeButton.addEventListener('click', function() {
    navbar.classList.add('hidden');
  });

  const openButton = document.getElementById('open-nav');

  openButton.addEventListener('click', function() {
    navbar.classList.remove('hidden');
  });



});