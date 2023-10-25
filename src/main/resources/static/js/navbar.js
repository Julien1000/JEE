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

  document.addEventListener('DOMContentLoaded', (event) => {
    const searchBar = document.getElementById('searchbar');
    const produitsList = document.getElementById('list');
    const produitsItems = produitsList.getElementsByClassName('produits');
  
    const MAX_VISIBLE_ITEMS = 5;
  
    const positionProduitsList = () => {
      const rect = searchBar.getBoundingClientRect();
      produitsList.style.top = (rect.bottom + window.scrollY) + 'px';
      produitsList.style.left = (rect.left + window.scrollX) + 'px';
      produitsList.style.width = rect.width + 'px';
    };
  
   searchBar.addEventListener('focus', function() {
     positionProduitsList();
   });
  
    searchBar.addEventListener('keyup', function() {
      const searchText = searchBar.value.toLowerCase();
  
      let visibleCount = 0;
      let produitFound = false;
      for (let produit of produitsItems) {
        const produitName = produit.textContent.toLowerCase();
        const isMatch = produitName.includes(searchText);
        
        if (isMatch && visibleCount < MAX_VISIBLE_ITEMS) {
          produit.style.display = '';
          visibleCount++;
          produitFound = true;
        } else {
          produit.style.display = 'none';
        }
      }
  
      produitsList.style.display = produitFound ? 'block' : 'none';
    });
  
    document.addEventListener('click', function(event) {
      if (!searchBar.contains(event.target) && !produitsList.contains(event.target)) {
        produitsList.style.display = 'none';
      }
    });
  
    window.addEventListener('resize', function() {
      if (produitsList.style.display === 'block') {
        positionProduitsList();
      }
    });
    for (let produit of produitsItems) {
      produit.addEventListener('click', function() {
        const produitId = produit.getAttribute('data-id');
        window.location.href = '/produit/perso/' + produitId;
      });
    }
    
  });