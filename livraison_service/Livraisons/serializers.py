# Livraison/serializers.py
from rest_framework import serializers
from .models import Livreur, Livraison, Prime, LivraisonEffectuee
from rest_framework import serializers
from .models import Livreur, MotDePasseHache, Admin
from django.contrib.auth.hashers import make_password


class LivreurSerializer(serializers.ModelSerializer):
    class Meta:
        model = Livreur
        fields = ['id', 'matricule', 'nom', 'prenom', 'sexe', 'salaire', 'mpd', 'image']
    def get_image_url(self, obj):
        if obj.image:
            return obj.image.url  # Retourne l'URL complète de l'image
        return None

    def create(self, validated_data):
        validated_data['mpd'] = make_password(validated_data['mpd'])
        return super().create(validated_data)

class MotDePasseHacheSerializer(serializers.ModelSerializer):
    class Meta:
        model = MotDePasseHache
        fields = ['id', 'matricule', 'nom', 'mot_de_passe']

class AdminSerializer(serializers.ModelSerializer):
    class Meta:
        model = Admin
        fields = ['id_admin', 'mot_de_passe','matricule']

    def create(self, validated_data):
        validated_data['mot_de_passe'] = make_password(validated_data['mot_de_passe'])
        return super().create(validated_data)



class LivraisonSerializer(serializers.ModelSerializer):
    class Meta:
        model = Livraison
        fields = '__all__'


class PrimeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Prime
        fields = '__all__'


class LivraisonEffectueeSerializer(serializers.ModelSerializer):
    class Meta:
        model = LivraisonEffectuee
        fields = '__all__'
        
        
""""***********partie service paiement ************************************************** """

from rest_framework import serializers
from .models import LivEffectuer

class LivEffectuerSerializer(serializers.ModelSerializer):
    class Meta:
        model = LivEffectuer
        fields = '__all__'


"""********************************partie service coammandes***********************************"""

from .models import Commande

class CommandeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Commande
        fields = [
            'idcommande',
            'date',
            'prixTotal',
            'montant_livraison',
            'idutilisateur',
            'panier'
        ]


from .models import Utilisateur

class UtilisateurSerializer(serializers.ModelSerializer):
    class Meta:
        model = Utilisateur
        fields = ['idcommande', 'idutilisateur', 'PrixLiv']
        
