window.onload = function() {
    const profile = document.getElementById('user-menu-button');
    const profileMenu = document.getElementById('user-menu');
  
    profile.addEventListener('click', function() {
      profileMenu.classList.toggle('hidden');
    });

    const mobileMenuButton = document.getElementById('mobile-menu-button');
    const mobileMenu = document.getElementById('mobile-menu');

    mobileMenuButton.addEventListener('click', function() {
      mobileMenu.classList.toggle('hidden');
    });



  };