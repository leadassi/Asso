# Livraison/views.py
from django.shortcuts import render
from rest_framework import viewsets
from .models import Livreur, Livraison, Prime, LivraisonEffectuee
from .serializers import LivreurSerializer, LivraisonSerializer, PrimeSerializer, LivraisonEffectueeSerializer
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from django.db.models import Count

# pour react 
from rest_framework import generics
from .models import Livreur, MotDePasseHache, Admin
from .serializers import LivreurSerializer, MotDePasseHacheSerializer, AdminSerializer

class LivreurListCreateView(generics.ListCreateAPIView):
    queryset = Livreur.objects.all()
    serializer_class = LivreurSerializer

class LivreurDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Livreur.objects.all()
    serializer_class = LivreurSerializer

class MotDePasseHacheListView(generics.ListAPIView):
    queryset = MotDePasseHache.objects.all()
    serializer_class = MotDePasseHacheSerializer

class AdminListCreateView(generics.ListCreateAPIView):
    queryset = Admin.objects.all()
    serializer_class = AdminSerializer

# API REST pour chaque modèle
# views.py
from rest_framework import viewsets, status
from rest_framework.response import Response
from rest_framework.decorators import action
from .models import Livreur
from .serializers import LivreurSerializer
from django.contrib.auth.hashers import check_password

class LivreurViewSet(viewsets.ModelViewSet):
    queryset = Livreur.objects.all()
    serializer_class = LivreurSerializer

    def authenticate_user(self, matricule, password):
        """
        Authentifie un utilisateur en fonction de son matricule et mot de passe.
        """
        try:
            # Recherche du livreur avec le matricule
            livreur = Livreur.objects.get(matricule=matricule)
            # Vérification du mot de passe
            if check_password(password, livreur.mpd):
                return livreur
            return None
        except Livreur.DoesNotExist:
            return None

    def get_queryset(self):
        """
        Filtre les livreurs en fonction de l'utilisateur authentifié (matricule + password).
        """
        matricule = self.request.headers.get("matricule")
        password = self.request.headers.get("password")

        # Authentification utilisateur
        user = self.authenticate_user(matricule, password)
        if user:
            return Livreur.objects.filter(matricule=user.matricule)
        return Livreur.objects.none()

    @action(detail=False, methods=['get'])
    def profile(self, request):
        """
        Récupère les informations du profil de l'utilisateur.
        """
        matricule = request.headers.get("matricule")
        password = request.headers.get("password")

        # Authentification utilisateur
        user = self.authenticate_user(matricule, password)
        if not user:
            return Response({"detail": "Matricule ou mot de passe incorrect"}, status=status.HTTP_401_UNAUTHORIZED)

        # Récupération des données du profil
        try:
            livreur = self.get_queryset().first()
            if livreur:
                serializer = self.get_serializer(livreur)
                return Response(serializer.data, status=status.HTTP_200_OK)
            return Response({"detail": "Utilisateur non trouvé"}, status=status.HTTP_404_NOT_FOUND)
        except Exception as e:
            return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)

    @action(detail=False, methods=['put'])
    def update_profile(self, request):
        """
        Met à jour les informations du profil, y compris l'image.
        """
        matricule = request.headers.get("matricule")
        password = request.headers.get("password")

        # Authentification utilisateur
        user = self.authenticate_user(matricule, password)
        if not user:
            return Response({"detail": "Matricule ou mot de passe incorrect"}, status=status.HTTP_401_UNAUTHORIZED)

        # Mise à jour du profil
        try:
            livreur = self.get_queryset().first()
            if livreur:
                serializer = self.get_serializer(livreur, data=request.data, partial=True)
                if serializer.is_valid():
                    serializer.save()
                    return Response(serializer.data, status=status.HTTP_200_OK)
                return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
            return Response({"detail": "Utilisateur non trouvé"}, status=status.HTTP_404_NOT_FOUND)
        except Exception as e:
            return Response({"detail": str(e)}, status=status.HTTP_400_BAD_REQUEST)



class LivraisonViewSet(viewsets.ModelViewSet):
    queryset = Livraison.objects.all()
    serializer_class = LivraisonSerializer


class PrimeViewSet(viewsets.ModelViewSet):
    queryset = Prime.objects.all()
    serializer_class = PrimeSerializer


class LivraisonEffectueeViewSet(viewsets.ModelViewSet):
    queryset = LivraisonEffectuee.objects.all()
    serializer_class = LivraisonEffectueeSerializer


# Vues pour les templates


def livraison_list(request):
    return render(request, 'Livraison/livraison_list.html', {'livraisons': Livraison.objects.all()})


def prime_list(request):
    return render(request, 'Livraison/prime_list.html', {'primes': Prime.objects.all()})

# Livraison/views.py

from django.shortcuts import render
from .models import Livreur

def livreur_list(request):
    livreurs = Livreur.objects.all()
    return render(request, 'Livraison/livreur_list.html', {'livreurs': livreurs})



"""****************************************Partie Service de Paiement ***********************************************"""



from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
import requests
from requests.exceptions import RequestException

class PaymentFetchView(APIView):
    def get(self, request, *args, **kwargs):
        paiement_service_url = "http://192.168.88.245:9090/payments/1"
        try:
            response = requests.get(paiement_service_url)
            response.raise_for_status()  # Lève une erreur pour les codes HTTP >= 400
            return Response(response.json())
        except RequestException as e:
            return Response(
                {"error": "Unable to connect to payment service", "details": str(e)},
                status=500
            )

    def post(self, request, *args, **kwargs):
        data = request.data
        # Affiche les données interprétées comme JSON
        print("Données JSON interprétées :", request.data)
        # Vérification des champs obligatoires
        required_fields = ["orderId", "customer_id", "payment_method", "payment_state","amount"]
        missing_fields = [field for field in required_fields if field not in data]
        
        if missing_fields:
            return Response(
                {"error": f"Champs manquants : {', '.join(missing_fields)}"},
                status=status.HTTP_400_BAD_REQUEST
            )

        # Validation des données spécifiques
        if not isinstance(data["orderId"], int):
            return Response(
                {"error": "Le champ 'orderId' doit être un entier."},
                status=status.HTTP_400_BAD_REQUEST
            )

        if not isinstance(data["customer_id"], int):
            return Response(
                {"error": "Le champ 'customer_id' doit être un entier."},
                status=status.HTTP_400_BAD_REQUEST
            )

        if not isinstance(data["payment_method"], str) or len(data["payment_method"].strip()) == 0:
            return Response(
                {"error": "Le champ 'payment_method' doit être une chaîne de caractères non vide."},
                status=status.HTTP_400_BAD_REQUEST
            )

        if data["payment_state"] not in ["IN_PROGRESS", "ACCEPTED", "REFUSED","NULL"]:
            return Response(
                {"error": "Le champ 'payment_state' doit être 'IN_PROGRESS', 'ACCEPTED', 'REFUSED','NULL'."},
                status=status.HTTP_400_BAD_REQUEST
            )

        # Logique simulée : traitement du paiement
        if data["payment_state"] == "ACCEPTED":
            return Response(
                {
                    "message": "Paiement validé avec succès.",
                    "details": {
                        "orderId": data["orderId"],
                        "customer_id": data["customer_id"],
                        "payment_method": data["payment_method"],
                        "payment_state": data["payment_state"],
                    },
                },
                status=status.HTTP_200_OK,
            )
        else:
            # Traitement des autres états de paiement
            return Response(
                {
                    "message": f"Paiement avec l'état '{data['payment_state']}'.",
                    "details": {
                        "orderId": data["orderId"],
                        "customer_id": data["customer_id"],
                        "payment_method": data["payment_method"],
                        "payment_state": data["payment_state"],
                    },
                },
                status=status.HTTP_400_BAD_REQUEST,
            )



from rest_framework import viewsets, status
from rest_framework.response import Response
from .models import LivEffectuer
from .serializers import LivEffectuerSerializer
import requests
from requests.exceptions import ReadTimeout, ConnectionError

class LivEffectuerViewSet(viewsets.ModelViewSet):
    queryset = LivEffectuer.objects.all()
    serializer_class = LivEffectuerSerializer

    def perform_create(self, serializer):
        # Enregistrer les données localement
        liv_effectuer = serializer.save()
        
        # Préparer les données à envoyer, sans la date et l'heure
        payload = {
            "customer_id":liv_effectuer.customer_id,
            "orderId": liv_effectuer.orderId,
            "amount": str(liv_effectuer.amount),
            "payment_state": liv_effectuer.payment_state,
            "payment_method": liv_effectuer.payment_method,
        }

        # Envoyer les données via POST au microservice externe
        try:
            response = requests.post(
                url="http://192.168.88.245:9090/payments/savepayment",
                json=payload,
                timeout=100
            )
            response.raise_for_status()  # Lève une exception pour les statuts HTTP 4xx/5xx
        except ReadTimeout:
            print("Erreur : Le microservice a mis trop de temps à répondre.")
            raise Exception("Timeout atteint lors de l'envoi au microservice.")
        except ConnectionError:
            print("Erreur : Impossible de se connecter au microservice.")
            raise Exception("Connexion échouée au microservice.")
        except Exception as e:
            print(f"Erreur inattendue : {e}")
            raise Exception("Erreur lors de la communication avec le microservice.")
        
        
        

"""*************************************************Partie Imelda***********************************"""
from .serializers import CommandeSerializer

class CommandesAPIView(APIView):
    def get(self, request):
        base_url = "http:"
        commandes_data = []
        current_id = 0

        while True:
            url = f"{base_url}{current_id}/"
            try:
                response = requests.get(url)

                if response.status_code == 200:
                    # Valider et formater les données avec le sérializer
                    commande_data = response.json()
                    serializer = CommandeSerializer(data=commande_data)
                    if serializer.is_valid():
                        commandes_data.append(serializer.data)
                    else:
                        return Response(
                            {"error": f"Erreur de validation pour l'ID {current_id}"},
                            status=status.HTTP_400_BAD_REQUEST
                        )
                elif response.status_code == 404:
                    break
                else:
                    return Response(
                        {"error": f"Erreur avec l'ID {current_id}: {response.reason}"},
                        status=response.status_code
                    )
            except requests.exceptions.RequestException as e:
                return Response(
                    {"error": f"Erreur de connexion au microservice : {str(e)}"},
                    status=status.HTTP_500_INTERNAL_SERVER_ERROR
                )

            current_id += 1

        return Response(commandes_data, status=status.HTTP_200_OK)
    
    def post(self, request):
        # Valider les données entrantes avec le sérializer
        serializer = CommandeSerializer(data=request.data)
        if serializer.is_valid():
            try:
                base_url = "http://127.0.0.1:8000/commandes"
                response = requests.post(base_url, json=serializer.data)

                if response.status_code == 201:
                    return Response(response.json(), status=status.HTTP_201_CREATED)
                else:
                    return Response(
                        {"error": "Erreur lors de l'envoi des données."},
                        status=response.status_code
                    )
            except requests.exceptions.RequestException as e:
                return Response(
                    {"error": f"Erreur de connexion au microservice : {str(e)}"},
                    status=status.HTTP_500_INTERNAL_SERVER_ERROR
                )
        else:
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)



from .models import Utilisateur
from .serializers import UtilisateurSerializer, CommandeSerializer


from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Utilisateur
from .serializers import UtilisateurSerializer
import requests

class UtilisateurAPIView(APIView):
    """
    Vue pour gérer la table Utilisateur :
    - GET : Récupérer les données locales de la table Utilisateur.
    - POST : Envoyer toutes les données locales à un autre microservice.
    """

    def get(self, request):
        """
        Récupérer les données locales de la table Utilisateur.
        """
        utilisateurs = Utilisateur.objects.all()  # Récupérer toutes les données locales
        serializer = UtilisateurSerializer(utilisateurs, many=True)  # Sérialiser les données
        return Response(serializer.data, status=status.HTTP_200_OK)

    def post(self, request):
        """
        Envoyer les données locales de la table Utilisateur à un autre microservice.
        """
        base_url = "http://localhost:9091/utilisateurs"  # URL du microservice cible
        utilisateurs = Utilisateur.objects.all()  # Récupérer toutes les données locales
        serializer = UtilisateurSerializer(utilisateurs, many=True)  # Sérialiser les données

        try:
            # Envoyer les données sérialisées au microservice cible
            response = requests.post(base_url, json=serializer.data)
            if response.status_code == 201:
                return Response(
                    {"message": "Données envoyées avec succès.", "data": response.json()},
                    status=status.HTTP_201_CREATED
                )
            else:
                return Response(
                    {"error": "Erreur lors de l'envoi des données."},
                    status=response.status_code
                )
        except requests.exceptions.RequestException as e:
            return Response(
                {"error": f"Erreur de connexion au microservice : {str(e)}"},
                status=status.HTTP_500_INTERNAL_SERVER_ERROR
            )

"""**************************Partie frontend **************************************"""
#partie connexion Livreurs
from django.views.decorators.csrf import csrf_exempt
from django.http import JsonResponse
from django.contrib.auth.hashers import check_password  # Pour vérifier les mots de passe
from .models import Livreur  # Importer votre modèle Livreur
import json
import logging

logger = logging.getLogger(__name__)

@csrf_exempt  # Permet d'ignorer temporairement les vérifications CSRF si nécessaire
def login_livreur(request):
    if request.method == "POST":
        try:
            # Récupérer les données envoyées par le client (matricule et mot de passe)
            data = json.loads(request.body)
            matricule = data.get("matricule")
            password = data.get("password")

            logger.info(f"Tentative de connexion - Matricule : {matricule}")
            logger.info(f"Mot de passe tenté pour {matricule}: {password}")  # Affiche le mot de passe tenté (en clair)

            # Recherche du livreur dans la base de données en utilisant le matricule
            try:
                livreur = Livreur.objects.get(matricule=matricule)
            except Livreur.DoesNotExist:
                logger.warning(f"Matricule non trouvé : {matricule}")
                logger.info(f"Mot de passe correct pour {matricule}: {livreur.mpd}")  # Affiche le mot de passe correct si l'utilisateur existe
                return JsonResponse({"error": "Matricule ou mot de passe incorrect"}, status=401)

            # Vérification du mot de passe
            if check_password(password, livreur.mpd):  # Vérifie le mot de passe haché
                logger.info(f"Connexion réussie pour {matricule}")
                return JsonResponse({"success": "Connexion réussie"}, status=200)
            else:
                logger.warning(f"Mot de passe incorrect pour {matricule}")
                return JsonResponse({"error": "Matricule ou mot de passe incorrect"}, status=401)
        
        except Exception as e:
            logger.error(f"Erreur serveur : {str(e)}")
            return JsonResponse({"error": "Erreur serveur"}, status=500)
    
    # Si la méthode HTTP n'est pas POST
    return JsonResponse({"error": "Méthode non autorisée"}, status=405)


from django.http import JsonResponse
from django.middleware.csrf import get_token

def csrf(request):
    token = get_token(request)
    return JsonResponse({'csrfToken': token})

#Paritie connection Admin

import json
from django.http import JsonResponse
from django.middleware.csrf import get_token
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.hashers import check_password
import logging
from .models import Admin 

logger = logging.getLogger(__name__)

@csrf_exempt  # Pour éviter les erreurs CSRF lors des tests locaux
def admin_login(request):
    if request.method == "POST":
        try:
            # Récupérer les données envoyées par le client (matricule et mot de passe)
            data = json.loads(request.body)
            matricule = data.get("matricule")
            password = data.get("password")

            logger.info(f"Tentative de connexion - Matricule : {matricule}")

            # Recherche de l'administrateur dans la base de données en utilisant le matricule
            try:
                admin = Admin.objects.get(matricule=matricule)
            except Admin.DoesNotExist:
                logger.warning(f"Matricule non trouvé : {matricule}")
                return JsonResponse({"error": "Matricule ou mot de passe incorrect"}, status=401)

            # Vérification du mot de passe
            if check_password(password, admin.mot_de_passe):  # Vérifie le mot de passe haché
                logger.info(f"Connexion réussie pour {matricule}")
                return JsonResponse({"success": "Connexion réussie"}, status=200)
            else:
                logger.warning(f"Mot de passe incorrect pour {matricule}")
                return JsonResponse({"error": "Matricule ou mot de passe incorrect"}, status=401)
        
        except Exception as e:
            logger.error(f"Erreur serveur : {str(e)}")
            return JsonResponse({"error": "Erreur serveur"}, status=500)
    
    # Si la méthode HTTP n'est pas POST
    return JsonResponse({"error": "Méthode non autorisée"}, status=405)


def csrf_token(request):
    token = get_token(request)
    return JsonResponse({'csrfToken': token})

#pour le front Admin 
from rest_framework.viewsets import ModelViewSet
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from .models import Livreur, Admin
from .serializers import LivreurSerializer
from django.contrib.auth.hashers import check_password

class LivreurViewSet(ModelViewSet):
    queryset = Livreur.objects.all()
    serializer_class = LivreurSerializer
    permission_classes = [IsAuthenticated]

    def authenticate_admin(self, matricule, password):
        """
        Authentifie un administrateur via matricule et mot de passe.
        """
        try:
            admin = Admin.objects.get(matricule=matricule)
            if check_password(password, admin.mot_de_passe):
                return admin
            return None
        except Admin.DoesNotExist:
            return None

    def list(self, request, *args, **kwargs):
        """
        Liste tous les livreurs enregistrés, accessible seulement à un administrateur.
        """
        matricule = request.headers.get("matricule")
        password = request.headers.get("password")

        # Authentification administrateur
        admin = self.authenticate_admin(matricule, password)
        if not admin:
            return Response({"detail": "Non autorisé"}, status=401)

        queryset = self.get_queryset()
        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)

    def create(self, request, *args, **kwargs):
        """
        Crée un livreur, accessible uniquement par un administrateur authentifié.
        """
        matricule = request.headers.get("matricule")
        password = request.headers.get("password")

        # Authentification administrateur
        admin = self.authenticate_admin(matricule, password)
        if not admin:
            return Response({"detail": "Non autorisé"}, status=401)

        return super().create(request, *args, **kwargs)

    def update(self, request, *args, **kwargs):
        """
        Met à jour un livreur, accessible uniquement par un administrateur authentifié.
        """
        matricule = request.headers.get("matricule")
        password = request.headers.get("password")

        # Authentification administrateur
        admin = self.authenticate_admin(matricule, password)
        if not admin:
            return Response({"detail": "Non autorisé"}, status=401)

        return super().update(request, *args, **kwargs)

    def destroy(self, request, *args, **kwargs):
        """
        Supprime un livreur, accessible uniquement par un administrateur authentifié.
        """
        matricule = request.headers.get("matricule")
        password = request.headers.get("password")

        # Authentification administrateur
        admin = self.authenticate_admin(matricule, password)
        if not admin:
            return Response({"detail": "Non autorisé"}, status=401)

        return super().destroy(request, *args, **kwargs)
    
#livraisons
import json
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from .models import Livraison, Livreur
from django.db.models import Count
from django.utils.crypto import get_random_string
import requests
from datetime import datetime

@csrf_exempt
def gestion_livraisons(request):
    if request.method == "POST":
        try:
            # Lire et décoder les données JSON depuis le corps de la requête
            data = json.loads(request.body)

            # Récupérer les informations nécessaires
            liste_produit = data.get("liste_produit")
            destination = data.get("destination")
            cout = data.get("cout")
            id_client = data.get("id_client")
            date = data.get("date")
            qr_code_url = data.get("qr_code")  # URL du QR code reçu
            id_commande = data.get("id_commande", get_random_string(10))  # Générer un ID unique si non fourni

            # Vérifier si les champs obligatoires sont présents
            if not liste_produit:
                return JsonResponse({"error": "Le champ 'liste_produit' est obligatoire."}, status=400)
            if not destination:
                return JsonResponse({"error": "Le champ 'destination' est obligatoire."}, status=400)
            if not cout:
                return JsonResponse({"error": "Le champ 'cout' est obligatoire."}, status=400)
            if not id_client:
                return JsonResponse({"error": "Le champ 'id_client' est obligatoire."}, status=400)
            if not date:
                return JsonResponse({"error": "Le champ 'date' est obligatoire."}, status=400)

            # Conversion de la date en datetime si c'est une chaîne
            if isinstance(date, str):
                try:
                    date = datetime.strptime(date, "%Y-%m-%d")
                except ValueError:
                    return JsonResponse({"error": "Format de date invalide, attendu 'YYYY-MM-DD'"}, status=400)

            # Trouver un livreur disponible
            livreur = (
                Livreur.objects.annotate(livraison_count=Count("livraisons"))
                .order_by("livraison_count")
                .first()
            )

            if not livreur:
                return JsonResponse({"error": "Aucun livreur disponible"}, status=400)

            # Créer l'objet Livraison
            livraison = Livraison.objects.create(
                livreur=livreur,
                liste_produit=liste_produit,
                destination=destination,
                cout=cout,
                id_client=id_client,
                date=date,  # Date déjà convertie en datetime
                id_commande=id_commande,
            )

            # Poster les données vers un autre service
            url_autre_service = "http://autreservice.example.com/api/livraisons"
            payload = {
                "livreur": {
                    "nom": livraison.livreur.nom,
                    "prenom": livraison.livreur.prenom,
                    "image_url": livraison.livreur.image.url if livraison.livreur.image else None,
                },
                "liste_produit": livraison.liste_produit,
                "destination": livraison.destination,
                "cout": livraison.cout,
                "id_client": livraison.id_client,
                "date": livraison.date.strftime("%Y-%m-%d"),  # Formater la date
                "id_commande": livraison.id_commande,
                "qr_code_url": qr_code_url,  # Inclure l'URL du QR code reçu
            }

            try:
                response = requests.post(url_autre_service, json=payload)
                response.raise_for_status()
            except requests.exceptions.RequestException as e:
                return JsonResponse({"error": f"Erreur lors de l'envoi au service : {e}"}, status=500)

            return JsonResponse({"message": "Livraison créée et envoyée avec succès", "livraison_id": livraison.id})

        except json.JSONDecodeError:
            return JsonResponse({"error": "Erreur de parsing du JSON"}, status=400)

    elif request.method == "GET":
        # Récupérer toutes les livraisons
        livraisons = Livraison.objects.all()
        livraisons_data = [
            {
                "id_commande": livraison.id_commande,
                "livreur": {
                    "nom": livraison.livreur.nom,
                    "prenom": livraison.livreur.prenom,
                    "image_url": livraison.livreur.image.url if livraison.livreur.image else None,
                },
                "liste_produit": livraison.liste_produit,
                "destination": livraison.destination,
                "cout": livraison.cout,
                "id_client": livraison.id_client,
                # Conversion de la date en string au format souhaité
                "date": livraison.date.strftime("%Y-%m-%d") if isinstance(livraison.date, datetime) else livraison.date,
                "qr_code_url": livraison.qr_code_url if hasattr(livraison, "qr_code_url") else None,
            }
            for livraison in livraisons
        ]

        return JsonResponse(livraisons_data, safe=False)

    return JsonResponse({"error": "Méthode non autorisée"}, status=405)




#systeme de notifiacation
# notifications/views.py

from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from channels.layers import get_channel_layer

class NotificationView(APIView):
    def post(self, request, *args, **kwargs):
        message = request.data.get("message", None)

        if message:
            # Envoyer le message à tous les clients WebSocket connectés
            channel_layer = get_channel_layer()
            channel_layer.group_send(
                "notifications",  # Le groupe WebSocket
                {
                    "type": "notification_message",
                    "message": message,
                }
            )
            return Response({"status": "Notification envoyée"}, status=status.HTTP_200_OK)
        else:
            return Response({"error": "Message manquant"}, status=status.HTTP_400_BAD_REQUEST)
