# Task 2 : Secure Note Saver (Vault V1)
## Scenario
You are developing a "Digital Vault" feature for a personal organizer application. Your specific task is to implement the saveNote method, which securely stores a user's sensitive note protected by a password.

## Objective
Transform a plaintext note (title and content) into a VaultEntry object that ensures confidentiality (encryption) and integrity (checksum verification).

## Requirements

- Derive Key (PBKDF2): You cannot use a random key. You must derive a 256-bit AES key from the user's password.

- Algorithm: Use PBKDF2WithHmacSHA256.

- Security Rule: You must generate a random 32-byte Salt and use at least 65,536 iterations to prevent brute-force attacks.

- Encryption (GCM): Encrypt the data using AES in GCM mode with NoPadding.

- Security Rule: You must explicitly generate a random 12-byte IV (Nonce). (Note: This size is specific to GCM).

- Protocol Rule: The encrypted payload must consist of the Note Title followed by the Note Content. You must not create a new byte array to merge these manually; instead, you must feed the title to the cipher using update() and the content using doFinal().

- Integrity (Hash): Calculate a SHA-256 hash of the resulting Ciphertext (not the plaintext) to allow for integrity verification upon retrieval.