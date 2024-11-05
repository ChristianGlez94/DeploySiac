# backend-siac
Porbando que funcionan bien lo git push
Documentacion

Toda la documentacion esta disponible en 
Es necesario realizar el login para que se puedan cragar todos los controlladores
http://localhost:8080/swagger-ui/index.html




http://localhost:8080/api/usuarios/register

Registrar Usuario JSON POST

{
  "nombre": "Juan",
  "apellidoPaterno": "Perez",
  "apellidoMaterno": "Gomez",
  "correo": "christian.perez@example.com",
  "contrasena": "123456",
  "telefono": "555-1234"
}


http://localhost:8080/api/usuarios/login

Se envia por JSON
{
"correo": "christiantronix@gmail.com",
"contrasena": "12345678"
}

y se recibe

{
    "id": 13,
    "nombre": "Christian",
    "apellidoPaterno": "Gonzalez",
    "apellidoMaterno": "Garcia",
    "correo": "christiantronix@gmail.com",
    "telefono": null,
    "catUsuario": {
        "id": 1,
        "nombre": "General"
    },
    "catEstatusUsuario": {
        "id": 1,
        "estatus": "Activo"
    },
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaHJpc3RpYW50cm9uaXhAZ21haWwuY29tIiwiaWF0IjoxNzI5MTg4Mjk1LCJleHAiOjE3MjkxOTE4OTV9.w0MurlCV0moy8oUPI-Uy-iXbbzKjU5qdAP2g0npwbUE",
    "message": "Login exitoso"
}

El login con github se hace haciendo un Get
http://localhost:8080/oauth2/authorization/github

Y el login por Google se hace haciendo un Get
http://localhost:8080/oauth2/authorization/google

Cuando se obtiene un login valido se obtiene como respuesta, EL TOKEN NO ES NECESARIO ENVIARLO 

{"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaHJpc3RpYW50cm9uaXhAZ21haWwuY29tIiwiaWF0IjoxNzI5MTg4MDYyLCJleHAiOjE3MjkxOTE2NjJ9.-9d2f5uTndUoR5eEBrAWOuqKOfBHVPexorGwjRAJ1zM","message":"Login exitoso con Google","picture":"https://lh3.googleusercontent.com/a/ACg8ocKnMJyKLa7SFyuq3KHnW6HH_1TItrM8bV4fECPg5nrgyTo1eEbV=s96-c","email":"christiantronix@gmail.com","name":"Christian Gonzalez"}



Por default se asigna categoría 1 estatus 1

# se cambio el nombre de una rama features/back-2 a features/back_2

