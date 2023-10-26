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
});
