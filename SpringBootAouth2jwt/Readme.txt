curl -H "Content-Type: application/json" -d {"username":"admin","password":"admin"}  http://127.0.0.1:8083/auth

curl trusted_client:secret@localhost:9001/oauth/token -d grant_type=client_credentials

curl trusted_client:secret@localhost:9001/oauth/token -d grant_type=password -d username=admin -d password=admin

curl http://localhost:9001/oauth/token -d grant_type=password -d username=user -d password=password -d client_id=client -d client_secret=secret


RSA Key gen
------------
1. keypair gerate:
openssl genrsa -out jwt.pem 2048

2. private key
openssl rsa -in jwt.pem

3. Public key
openssl rsa -in jwt.pem -pubout
   