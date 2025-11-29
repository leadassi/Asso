from django.contrib import admin
from django.urls import path, re_path, include
from rest_framework import permissions
from drf_yasg.views import get_schema_view
from drf_yasg import openapi
from django.conf import settings
from django.conf.urls.static import static
from rest_framework.routers import DefaultRouter
from Livraisons import views
from Livraisons.views import LivreurViewSet
from Livraisons.views import admin_login, csrf_token
from Livraisons.views import NotificationView
from Livraisons.views import gestion_livraisons


# pour react
from Livraisons.views import (
    LivreurListCreateView,
    LivreurDetailView,
    MotDePasseHacheListView,
    AdminListCreateView,
)

# Configuration du schéma Swagger
schema_view = get_schema_view(
    openapi.Info(
        title="API Documentation",
        default_version='v1',
        description="Documentation de l'API avec Swagger pour le projet Django REST",
        terms_of_service="https://www.example.com/terms/",
        contact=openapi.Contact(email="support@example.com"),
        license=openapi.License(name="Licence MIT"),
    ),
    public=True,
    permission_classes=(permissions.AllowAny,),
)

# Configuration du routeur pour les ViewSets
router = DefaultRouter()
router.register(r'livreurs', views.LivreurViewSet)
router.register(r'livraisons', views.LivraisonViewSet)
router.register(r'primes', views.PrimeViewSet)
router.register(r'livraisons_effectuees', views.LivraisonEffectueeViewSet)
router.register(r'liv-effectuer', views.LivEffectuerViewSet, basename='liv-effectuer')


urlpatterns = [
    path('admin/', admin.site.urls),
    path('', include(router.urls)),
    path('livreurs/', views.livreur_list, name='livreur_list'),
    path('livraisons/', views.livraison_list, name='livraison_list'),
    path('primes/', views.prime_list, name='prime_list'),
    path('fetch-payments/', views.PaymentFetchView.as_view(), name='fetch-payments'),
    path('commandes/', views.CommandesAPIView.as_view(), name='commandes'),
    path('utilisateurs/', views.UtilisateurAPIView.as_view(), name='utilisateurs'),
    path('api/csrf/', views.csrf, name='get_csrf_token'),
    path('api/login/', views.login_livreur, name='login_livreur'),
    re_path(r'^swagger(?P<format>\.json|\.yaml)$', schema_view.without_ui(cache_timeout=0), name='schema-json'),
    path('swagger/', schema_view.with_ui('swagger', cache_timeout=0), name='schema-swagger-ui'),
    path('redoc/', schema_view.with_ui('redoc', cache_timeout=0), name='schema-redoc'),
    path('mots-de-passe-haches/', MotDePasseHacheListView.as_view(), name='motdepassehache-list'),
    path('admins/', AdminListCreateView.as_view(), name='admin-list-create'),
    path('livreurs/', LivreurListCreateView.as_view(), name='livreur-list-create'),
    path('livreurs/<int:pk>/', LivreurDetailView.as_view(), name='livreur-detail'),
    path('api/', include(router.urls)),
    path('api/livreurs/profile/', LivreurViewSet.as_view({'get': 'profile', 'put': 'update_profile'})),  # Profil
    path('api/admin-login/', admin_login, name='admin-login'),
    path('api/csrf-token/', csrf_token, name='csrf-token'),
    path('send-notification/', NotificationView.as_view(), name='send-notification'),
    path("Livraisonservicesapi/livraisons", gestion_livraisons, name="gestion_livraisons"),
]

if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)

# **Ajout d'un préfixe global** pour toutes les routes existantes
urlpatterns = [
    path('Livraisonservices/', include(urlpatterns)),  
]
