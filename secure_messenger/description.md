# Task 1 : Secure Messenger (Protocol V1)
## Scenario
You are developing the core security module for a messaging application. Your specific task is to implement the packageMessage method, which prepares a raw text message for secure transmission over an untrusted network.

## Objective
Transform a plaintext message into a SecurePackage object that ensures confidentiality (encryption) and authenticity (digital signature).

## Requirements

- Session Key: Generate a fresh AES key.

- Encryption: Encrypt the data using AES in CBC mode with the appropriate padding scheme.

- Protocol Rule: The payload must start with a constant MAGIC_HEADER followed by the actual message text.

- Signature: Generate a digital signature of the resulting ciphertext (not the plaintext) to prove the sender's identity.

- Algorithm: Use SHA256withRSA.