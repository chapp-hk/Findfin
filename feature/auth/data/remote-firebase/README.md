# Auth Firebase remote data

### Handled responses
- register success
- register with invalid email
  - FirebaseAuthInvalidCredentialsException: ERROR_INVALID_EMAIL - The email address is badly formatted.
- register with short password
  - FirebaseAuthWeakPasswordException: ERROR_WEAK_PASSWORD - The given password is invalid. [ Password should be at least 6 characters ]
- register with existing email
  - FirebaseAuthUserCollisionException: ERROR_EMAIL_ALREADY_IN_USE - The email address is already in use by another account.
