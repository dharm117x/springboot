curl -H "Content-Type: application/json" -d {"username":"admin","password":"admin"}  http://127.0.0.1:8083/auth

RSA Key gen
------------
1. keypair gerate:
openssl genrsa -out jwt.pem 2048

2. private key
openssl rsa -in jwt.pem

3. Public key
openssl rsa -in jwt.pem -pubout
   
   
--google clent id and secret--
https://support.google.com/cloud/answer/6158849?hl=en

https://github.com/settings/applications/new
   