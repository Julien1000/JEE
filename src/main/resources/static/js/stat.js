document.addEventListener('DOMContentLoaded', function () {
	var status1 = parseInt(document.getElementById('status1').textContent, 10);
    var status2 = parseInt(document.getElementById('status2').textContent, 10);
    var status3 = parseInt(document.getElementById('status3').textContent, 10);

    var ctx = document.getElementById('myChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Commandes en cours de validation', 'Commande en préparation', 'Commande en livraison'],
            datasets: [{
                data: [status1, status2, status3],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            animation: false,
            plugins: {
                legend: {
                    display: true
                },
                tooltip: {
                    enabled: false
                }
            },
            scales: {
                x: {
                    display: false
                },
                y: {
                    display: false
                }
            },
            elements: {
                arc: {
                    borderWidth: 0
                }
            }
        }
    });
    var ctx1 = document.getElementById('barChart').getContext('2d');
    var myBarChart1 = new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: categories,
            datasets: [{
                label: 'Produits Vendus',
                data: valeurs,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
    var ctx3 = document.getElementById('barChart1').getContext('2d');
	var myBarChart1 = new Chart(ctx3, {
	    type: 'bar',
	    data: {
	        labels: top5ProduitsNoms,
	        datasets: [{
	            label: 'Quantité Vendue',
	            data: top5ProduitsQuantites,
	            backgroundColor: 'rgba(255, 99, 132, 0.2)',
	            borderColor: 'rgba(255, 99, 132, 1)',
	            borderWidth: 1
	        }]
	    },
	    options: {
	        scales: {
	            y: {
	                beginAtZero: true
	            }
	        }
	    }
	});

});
