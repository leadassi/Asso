import os
from django.core.asgi import get_asgi_application
from channels.routing import ProtocolTypeRouter, URLRouter
from channels.auth import AuthMiddlewareStack
from django.urls import path
from Livraisons import consumers  # Remplacez "your_app_name" par le nom de votre app

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'Gestion_Livraison.settings')

application = ProtocolTypeRouter({
    "http": get_asgi_application(),
    "websocket": AuthMiddlewareStack(
        URLRouter([
            path("ws/Gestion_Livraison.asgi/notifications/", consumers.NotificationConsumer.as_asgi()),
        ])
    ),
})
