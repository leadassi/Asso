function checkout() {
            CinetPay.setConfig({
                apikey: '5337111116358eef42b6448.37599996',
                site_id: '301005',
                notify_url: 'http://127.0.0.1:9090',
                return_url: 'http://127.0.0.1:9090',
                mode: 'TEST'
            });
            CinetPay.getCheckout({
                transaction_id:  Math.floor(Math.random() * 100000000).toString(),
                amount: 100,
                currency: 'XAF',
                channels: 'ALL',
                description: 'Test de paiement',
                 //Fournir ces variables pour le paiements par carte bancaire
                customer_name:"Jane",//Le nom du client
                customer_surname:"Down",//Le prenom du client
                customer_email: "down@test.com",//l'email du client
                customer_phone_number: "088767611",//l'email du client
                customer_address : "BP 0024",//addresse du client
                customer_city: "Antananarivo",// La ville du client
                customer_country : "CM",// le code ISO du pays
                customer_state : "CM",// le code ISO l'état
                customer_zip_code : "06510", // code postal

            });
            CinetPay.waitResponse(function(data) {
                console.log(data);

                if (data.status == "REFUSED") {
                    if (alert("Votre paiement a échoué")) {
                        window.location.reload();
                    }
                } else if (data.status == "ACCEPTED") {
                    if (alert("Votre paiement a été effectué avec succès")) {
                        window.location.reload();
                    }
                }
            });
            CinetPay.onError(function(data) {
                console.log(data);
            });
}

function printS() {
            window.location.href = 'index1.html';
}
