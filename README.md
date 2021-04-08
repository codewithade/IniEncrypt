# IniEncrypt
The goal of IniEncrypt is to test the efficacy and the applicability of both symmetric and asymmetric cryptographic algorithms when encrypting/decrypting text and images.

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
Symmetric Algorithm are cryptographic algorithms that use a single key to either encrypt or decrypt data. This means both parties (Bob and Alice) share a single secret key. AES has a key size length of 16-bytes while TripleDES can support both 16-bytes and 24-bytes key sizes.

### Asymmetric Algorithm
Asymmetric Algorithms are cryptographic algorithms that require the use of a key pair i.e. two keys (PrivateKey and PublicKey) are required. When two parties (Bob and Alice) want to communicate, The following processes happens: 
1. The sender(Alice) generates a *Public and Private Key* pair. 
2. Alice then encodes her *Public Key* and sends it to the receiver(Bob). 
3. Bob generates his own *Public and Private Key* pair and sends his encoded *Public Key* over to Alice. 
4. This marks the end of the key sharing process. 
5. To transmit data between both parties, Alice, for example, encrypts her message (data) with *Bob's Public Key* and sends it to Bob. 
6. Bob receives the encrypted message from Alice and decrypts it with his *Private Key*.

* RSA accepts key sizes between 512 and 65536 bytes (in multiples of 64 bytes). RSA can only encrypt data with a maximum size of ((keySize/8)-11) i.e. for example, if a keySize of 1024 is used, it can only encrypt data with a maximum size of ((1024/8)-11) = 117 bytes

* Diffie Hellman is a Key Exchange algorithm and accepts key sizes between 512 and 2048 bytes (in multiples of 64 bytes). The DH Key Exchange Process:
1. The sender (Alice) starts the DH Key exchange process by generating a *Private and Public Key* pair with a specified Key size.
2. Alice encodes her _*Public Key*_ and sends it to the receiver (Bob).
3. Bob receives Alice's encoded Public Key and generates his own _*Public and Private Key*_ pair using the parameters encoded in Alice's Public Key.
4. Bob encodes his _*Public Key*_ and sends it over to Alice.
5. Alice uses Bob's encoded _*Public Key*_ to initiate her own first phase of the DH protocol.
6. Bob also uses Alice's _*Public Key*_ to initiate his own first phase of the DH protocol.
7. Both Bob and Alice generates a _*Shared Secret Key*_ (The keys generated at both ends must tally).
8. The size of the Shared Secret Key is dependent on the size of the cryptographic Key used. If a key size of 512 is used, a Shared Secret Key of 64 bytes would be generated.
9. The Shared Secret Key is used to encrypt/decrypt data being sent or received to/from both parties (Bob and Alice).

## Screenshots
![launch](https://user-images.githubusercontent.com/65837990/113030360-68cee700-9185-11eb-8b12-51f99070d3cd.png)
![default](https://user-images.githubusercontent.com/65837990/113030913-ff030d00-9185-11eb-8700-d9f54151e0c4.png)
![portrait](https://user-images.githubusercontent.com/65837990/111162720-b69bfa80-859c-11eb-8c00-d289e071daa0.png)

![RSA1](https://user-images.githubusercontent.com/65837990/113267793-a55a2a00-92ce-11eb-8e31-a1462ea4a2ee.png)
![RSA 2](https://user-images.githubusercontent.com/65837990/113267875-b73bcd00-92ce-11eb-917b-e7733f10f8cb.png)
![RSA3](https://user-images.githubusercontent.com/65837990/113267914-c15dcb80-92ce-11eb-9361-50366b8617d7.png)

![land](https://user-images.githubusercontent.com/65837990/111162712-b4d23700-859c-11eb-83d5-b2256595be58.png)


## References
1. https://docs.oracle.com/javase/9/security/java-cryptography-architecture-jca-reference-guide.htm
2. https://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html 



