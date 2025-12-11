# my-base-saas-project
Next.js + Java/Spring

🔐 OAuth2 + JWT 
Logowanie się przez Google, Facebook, GitHub (OAuth2) oraz przez e-mail i hasło z bazy danych.

➡️ Użytkownik loguje się przez Google → backend generuje własny JWT token → frontend trzyma go w localStorage → każde żądanie API ma Authorization: Bearer <token>
✅ Idealne do SPA/React + mikroserwisów
✅ Łatwo weryfikować token bez trzymania sesji
✅ Skalowalne

🔧 Dependencies:
✅ Spring Web
✅ Spring Security
✅ Spring Data JPA
✅ Spring Boot DevTools
✅ PostgreSQL Driver
✅ Validation
✅ OAuth2 Client
✅ Lombok
