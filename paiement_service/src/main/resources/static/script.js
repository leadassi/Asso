document.addEventListener("DOMContentLoaded", function() {
            //const userId = sessionStorage.getItem('utilisateurId');
            //const orderId = sessionStorage.getItem('idCommande');
            const paymentServiceURL = "http://192.168.17.101:9090/";
            const orderServiceURL = "http://192.168.17.234:8081/";
            const userId = 1;
            const orderId = 8;

            if (!userId) {
               console.error("No user ID found in local storage.");
               return; // Stop execution if userId is not found
            }

            if (!orderId) {
                console.error("No order ID found in local storage.");
                return; // Stop execution if orderId is not found
            }

            function getPaymentData(userId) {
                return fetch(paymentServiceURL+`cinetpaypayments/${userId}/${orderId}`)
                    .then(response => response.json())
                    .catch(error => console.error('Error fetching user data:', error));
            }

            function getDeliveryPaymentData(userId) {
                return fetch(paymentServiceURL+`cinetpaydeliverypayments/${userId}/${orderId}`)
                    .then(response => response.json())
                    .catch(error => console.error('Error fetching user data:', error));
            }

            // Function to reduce product quantity
            async function reduceQuantity(orderId) {
                try {
                    const response = await fetch(paymentServiceURL+`payments/orders/contenancepanier`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Access-Control-Allow-Origin':'*'
                        },
                        body: JSON.stringify({
                            orderId:orderId
                        })
                    });

                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }

                    const result = await response.json();
                    console.log("Notification processed on server:", result);
                } catch (error) {
                    console.error("Error updating payment status on server:", error.message);
                    throw error;
                }
            }

            // Function to update the payment status in order
            async function updateOrderStatus(orderId, status) {
                try {
                    const response = await fetch(orderServiceURL+`commande/${orderId}`, {
                        method: 'PATCH',
                        headers: {
                            'Content-Type': 'application/json',
                            'Access-Control-Allow-Origin':'*'
                        },
                        body: JSON.stringify({
                            Statut:status
                        })
                    });

                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }

                    const result = await response.json();
                    console.log("Notification processed on server:", result);
                } catch (error) {
                    console.error("Error updating payment status on server:", error.message);
                    throw error;
                }
            }

            //Function to send the informations about transaction to the delivery service

            async function sendPaymentRequest(orderId, status, payment_method, amount, userId) {
               try {
                    const response = await fetch(paymentServiceURL+`payments/sendpayment`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Access-Control-Allow-Origin':'*'
                        },
                        body: JSON.stringify({
                           orderId: orderId,
                           customer_id: userId,
                           amount: amount,
                           payment_method: payment_method,
                           payment_state: status
                        })
                    });

                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    const result = await response.json();
                    console.log("Response of delivery service:", result);
               } catch (error) {
                   console.error("Error sending the payment to delivery_service:", error.message);
                   throw error;
               }
            }

            //Function to save transaction
            async function saveTransaction(orderId, status, payment_method, amount, userId) {
                            try {
                                const response = await fetch(paymentServiceURL+`payments/savepayment`, {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/json',
                                        'Access-Control-Allow-Origin':'*'
                                    },
                                    body: JSON.stringify({
                                        orderId: orderId,
                                        customer_id: userId,
                                        amount: amount,
                                        payment_method: payment_method,
                                        payment_state: status
                                    })
                                });

                                if (!response.ok) {
                                    throw new Error(`HTTP error! Status: ${response.status}`);
                                }

                                const result = await response.json();
                                console.log("Transaction saved in database:", result);

                            } catch (error) {
                                console.error("Error saving payment in database:", error.message);
                                throw error;
                            }
            }

            // Function to make a payment with cinetpay
            function checkout() {
                getPaymentData(userId).then(userData => {
                    CinetPay.setConfig({
                        apikey: userData.apikey,
                        site_id: userData.siteId,
                        notify_url: userData.notifyUrl,
                        return_url: userData.returnUrl,
                        mode: userData.mode
                    });

                    CinetPay.getCheckout({
                        transaction_id: userData.transactionId,
                        amount: userData.amount,
                        currency: userData.currency,
                        channels: userData.channels,
                        description: userData.description,
                        //data for credit card payment
                        customer_name: userData.customerName,
                        customer_surname: userData.customerSurname,
                        customer_email: userData.customerMail,
                        customer_phone_number: userData.customerPhoneNumber,
                        customer_address: userData.customerAddress,
                        customer_city: userData.customerCity,
                        customer_country: userData.customerCountry,
                        customer_state: userData.customerState,
                        customer_zip_code: userData.customerZipCode,
                    });

                    CinetPay.waitResponse(async function(data) {
                        console.log(data);

                        if (data.status == "ACCEPTED") {
                            //reduceQuantity(orderId);
                            sendPaymentRequest(orderId, status, payment_method, amount, userId);
                        }
                        updateOrderStatus(userData.transactionId, data.status, data.payment_method);
                        saveTransaction(userData.transactionId, data.status, data.payment_method, data.amount, userId);
                    });

                    CinetPay.onError(function(data) {
                        console.log(data);
                    });
                });
            }

            function deliveryCheckout() {
               getDeliveryPaymentData(userId).then(userData => {
                  CinetPay.setConfig({
                     apikey: userData.apikey,
                     site_id: userData.siteId,
                     notify_url: userData.notifyUrl,
                     return_url: userData.returnUrl,
                     mode: userData.mode
                  });

                  CinetPay.getCheckout({
                     transaction_id: userData.transactionId,
                     amount: userData.amount,
                     currency: userData.currency,
                     channels: userData.channels,
                     description: userData.description,
                     //data for credit card payment
                     customer_name: userData.customerName,
                     customer_surname: userData.customerSurname,
                     customer_email: userData.customerMail,
                     customer_phone_number: userData.customerPhoneNumber,
                     customer_address: userData.customerAddress,
                     customer_city: userData.customerCity,
                     customer_country: userData.customerCountry,
                     customer_state: userData.customerState,
                     customer_zip_code: userData.customerZipCode,
                  });

                  CinetPay.waitResponse(async function(data) {
                     console.log(data);

                     if (data.status == "ACCEPTED") {
                        //reduceQuantity(orderId);
                        sendPaymentRequest(orderId, status, payment_method, amount, userId);
                     }
                     updateOrderStatus(userData.transactionId, "IN_PROGRESS");
                     saveTransaction(userData.transactionId, data.status, data.payment_method, data.amount, userId);
                  });

                  CinetPay.onError(function(data) {
                     console.log(data);
                  });
               });
            }

            function testCheckout() {
               getDeliveryPaymentData(userId).then(userData => {
                    //updateOrderStatus(orderId, "ACCEPTED");
                    //reduceQuantity(8);
                    sendPaymentRequest(orderId, "ACCEPTED", "OMCM", 100, userId);
                    //saveTransaction(8, 'ACCEPTED', 'OMCM', 100, userData.transactionId);
               });
            }

            document.getElementById("checkoutBtn1").addEventListener("click", checkout);
            document.getElementById("checkoutBtn2").addEventListener("click", deliveryCheckout);
            document.getElementById("checkoutBtn3").addEventListener("click", testCheckout);
});
