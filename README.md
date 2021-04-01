# IniEncrypt
The goal of IniEncrypt is to test the efficiency and the applicability of both symmetric and asymmetric cryptographic algorithms when encrypting/decrypting text and images.

Algorithms being considered include:
1. Diffie-Hellman
2. RSA
3. 3DES
4. AES

## How it Works?
Cryptographic Algorithms can be:
1. Symmetric Algorithm (e.g AES and TripleDES)
2. Asymmetric Algorithm (e.g. RSA and DiffieHellman)

### Symmetric Algorithm 
Symmetric ALgorithm are cryptographic algorithms that use a single key to either encrypt or decrypt data. This means both parties (Bob and Alice) share a single secret key. AES has a key size length of 16bytes while TripleDES cabn support both 16-bytes and 24-bytes key sizes.

### Asymmetric Algorithm
Asymmetric Algorithms are cryptographic algorithms that require the use of a key pair i.e. two keys (PrivateKey and PublicKey) are required. When two parties (Bob and Alice) want to communicate, The followwing processes happens: 
1. The sender(Alice) generates a *Public and Private Key* pair. 
2. Alice then encodes her *Public Key* and sends it to the receiver(Bob). 
3. Bob generates his own *Public and Private Key* pair and sends his encoded *Public Key* it over to Alice. 
4. This marks the end of the key sharing process. 
5. To transmit data between both parties, Alice, for example, encrypts her message (data) with *Bob's Public Key* and sends it to Bob. 
6. Bob receives the encrypted message from Alice and decrypts it with his *Private Key*.

* RSA accepts key sizes between 512 and 65536 bytes (in multiples of 64 bytes). RSA can only encrypt data with a maximum size of ((keySize/8)-11) i.e. for example, if a keySize of 1024 is used, it can only encrypt data with a maximum size of ((1024/8)-11) = 117 bytes

* Diffie Hellman is a Key Exchange algorithm and accepts key sizes between 512 and 2048 bytes (in multiples of 64 bytes). A Shared Secret Key is generated at the end of the Key Exchange process between the communicating parties (Bob and Alice). The size of the Shared Secret Key is dependent on the size of the cryptographic Key used. If a key size of 512 is used, a Shared Secret Key of 64 bytes would be generated. The Shared Secret Key is used to encrypt/decrypt data being sent or received to/from both parties (Bob and Alice).

## Screenshots
![launch](https://user-images.githubusercontent.com/65837990/113030360-68cee700-9185-11eb-8b12-51f99070d3cd.png)
![default](https://user-images.githubusercontent.com/65837990/113030913-ff030d00-9185-11eb-8700-d9f54151e0c4.png)
![portrait](https://user-images.githubusercontent.com/65837990/111162720-b69bfa80-859c-11eb-8c00-d289e071daa0.png)

![land](https://user-images.githubusercontent.com/65837990/111162712-b4d23700-859c-11eb-83d5-b2256595be58.png)


