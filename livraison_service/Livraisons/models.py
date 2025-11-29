# Livraison/models.py
from django.db import models

from django.db import models

from django.contrib.auth.hashers import make_password
from django.db import models
from django.contrib.auth.hashers import make_password
from django.utils import timezone

class Livreur(models.Model):
    SEXE_CHOICES = [
        ('M', 'Homme'),
        ('F', 'Femme'),
    ]

    matricule = models.CharField(max_length=10, unique=True)
    nom = models.CharField(max_length=100)
    prenom = models.CharField(max_length=100)
    sexe = models.CharField(max_length=1, choices=SEXE_CHOICES)
    salaire = models.DecimalField(max_digits=10, decimal_places=2, default=150000.00)
    mpd = models.CharField(max_length=100)
    image = models.ImageField(upload_to='livreurs/', blank=True, null=True)

    def save(self, *args, **kwargs):
        if not self.mpd.startswith('pbkdf2_'):  # Hachage si nécessaire
            self.mpd = make_password(self.mpd)
        super().save(*args, **kwargs)

    def __str__(self):
        return f"{self.nom} {self.prenom} ({self.matricule})"

class MotDePasseHache(models.Model):
    matricule = models.CharField(max_length=10, unique=True)
    nom = models.CharField(max_length=100)
    mot_de_passe = models.CharField(max_length=100)

    def __str__(self):
        return f"{self.nom} ({self.matricule})"

class Admin(models.Model):
    id_admin = models.AutoField(primary_key=True)
    mot_de_passe = models.CharField(max_length=100)
    matricule = models.CharField(max_length=10, unique=True)

    def save(self, *args, **kwargs):
        if not self.mot_de_passe.startswith('pbkdf2_'):  # Hachage si nécessaire
            self.mot_de_passe = make_password(self.mot_de_passe)
        super().save(*args, **kwargs)

    def __str__(self):
        return f"Admin ID: {self.id_admin}"



from django.db import models
from django.utils import timezone

class Livraison(models.Model):
    livreur = models.ForeignKey("Livreur", on_delete=models.CASCADE, related_name="livraisons")
    liste_produit = models.TextField()
    destination = models.CharField(max_length=255)
    cout = models.DecimalField(max_digits=10, decimal_places=2)
    id_client = models.CharField(max_length=50)
    date = models.DateField(default=timezone.now)
    id_commande = models.CharField(max_length=50, unique=True)
    qr_code = models.ImageField(upload_to="qrcodes/", blank=True, null=True)

    def __str__(self):
        return f"Livraison pour {self.livreur.nom} {self.livreur.prenom} - Client {self.id_client}"



class Prime(models.Model):
    livreur = models.ForeignKey(Livreur, on_delete=models.CASCADE, related_name="primes")
    mois = models.DateField()
    montant = models.DecimalField(max_digits=10, decimal_places=2, default=50000.00)

    def __str__(self):
        return f"Prime de {self.montant} pour {self.livreur.nom} {self.livreur.prenom} - {self.mois}"


class LivraisonEffectuee(models.Model):
    livreur = models.ForeignKey(Livreur, on_delete=models.CASCADE, related_name="livraisons_effectuees")
    mois = models.DateField()
    nbre_livraisons = models.PositiveIntegerField()

    def __str__(self):
        return f"{self.nbre_livraisons} livraisons pour {self.livreur.nom} {self.livreur.prenom} - {self.mois}"



class Payment(models.Model):
    orderId = models.IntegerField()
    customer_id = models.IntegerField()
    payment_method = models.CharField(max_length=50)
    payment_state = models.CharField(max_length=50)
    
"""************************************Partie service Paiement ******************************************************************"""

from django.db import models

class LivEffectuer(models.Model):
    STATUT_CHOICES = [
        ('ACCEPTED', 'ACCEPTED'),
    ]
    PAYMENT_METHOD_CHOICES = [
        ('ON_DELIVERY', 'ON_DELIVERY'),
    ]

    customer_id =models.IntegerField() #id clients
    order_id = models.IntegerField()   # ID unique pour la commande
    amount = models.IntegerField()  # Montant de la commande
    payment_state = models.CharField(max_length=10, choices=STATUT_CHOICES, default='ACCEPTED')  # Statut par défaut
    payment_method = models.CharField(max_length=20, choices=PAYMENT_METHOD_CHOICES, default='ON_DELIVERY')
    date_paiement = models.DateField()  # Date de paiement
    heure_livraison = models.TimeField()  # Heure de livraison

    def __str__(self):
        return f"Commande {self.commande_id} - {self.statut}"



"""Partie d'envoie du montant de la livraisons"""


class Commande(models.Model):
    idcommande = models.IntegerField(primary_key=True)
    date = models.DateTimeField()
    prixTotal = models.FloatField()
    montant_livraison = models.IntegerField()
    idutilisateur = models.IntegerField()
    panier = models.IntegerField(null=True, blank=True)

    def __str__(self):
        return f"Commande {self.idcommande} - Prix: {self.prixTotal}"
    
    
from django.db import models

class Utilisateur(models.Model):
    idcommande = models.IntegerField()
    idutilisateur = models.IntegerField()
    PrixLiv = models.FloatField()

    def __str__(self):
        return f"Utilisateur {self.idutilisateur} - Commande {self.idcommande} - PrixLiv: {self.PrixLiv}"


