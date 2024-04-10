# Auth Firebase remote data

### Handled register responses
- register success
- register with invalid email
  - FirebaseAuthInvalidCredentialsException: `ERROR_INVALID_EMAIL`
  - The email address is badly formatted.
- register with short password
  - FirebaseAuthWeakPasswordException: `ERROR_WEAK_PASSWORD`
  - The given password is invalid. [ Password should be at least 6 characters ]
- register with existing email
  - FirebaseAuthUserCollisionException: `ERROR_EMAIL_ALREADY_IN_USE`
  - The email address is already in use by another account.

### Handled login responses
- login success
- login with not registered email
  - FirebaseAuthInvalidCredentialsException: `ERROR_INVALID_CREDENTIAL`
  - The supplied auth credential is incorrect, malformed or has expired.
- login with disabled account
  - FirebaseAuthInvalidUserException: `ERROR_USER_DISABLED`
  - The user account has been disabled by an administrator.
- login with invalid password
  - FirebaseAuthInvalidCredentialsException: `ERROR_INVALID_CREDENTIAL`
  - The supplied auth credential is incorrect, malformed or has expired.
- login with invalid password until account locked
  - FirebaseTooManyRequestsException: N/A
  - We have blocked all requests from this device due to unusual activity. Try again later. [ Access to this account has been temporarily disabled due to many failed login attempts. You can immediately restore it by resetting your password or you can try again later. ]
