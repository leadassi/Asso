# Livraisons/utils/api_client.py
import requests

def fetch_payment_details(payment_id):
    url = f"http://url_du_microservice_de_paiement/api/payments/{payment_id}"  # Remplacez avec l'URL de votre microservice
    try:
        response = requests.get(url)
        response.raise_for_status()  # Vérifie si la requête s'est bien déroulée
        return response.json()  # Retourne les données au format JSON
    except requests.RequestException as e:
        print(f"Erreur lors de l'appel à l'API de paiement : {e}")
        return None
