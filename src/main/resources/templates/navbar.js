window.onload = function() {
    const profile = document.getElementById('user-menu-button');
    const profileMenu = document.getElementById('user-menu');
  
    profile.addEventListener('click', function() {
      profileMenu.classList.toggle('hidden');
    });

    
  };