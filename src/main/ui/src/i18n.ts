import i18n from 'i18next'
import {initReactI18next} from 'react-i18next'

const resources = {
    pl: {
        translation: {
            "404": "Niestety, nie udało nam się znaleść potrzebnej Ci strony :(",
            "CLIENT": "Klient",
            "BUSINESS_WORKER": "Pracownik firmy",
            "MODERATOR": "moderator",
            "ADMINISTRATOR": "administrator",
            "invalid.form": "Niepoprawnie wypełniony formularz",
            "dismiss": "Odrzuć",
            "go back": "Wróć",
            "email": "E-mail",
            "password": "Hasło",
            "confirm password": "Potwierdź hasło",
            "name": "Imię",
            "surname": "Nazwisko",
            "company": "Firma",
            "the best cruises": "Najlepsze wycieczki",
            "ad": "Reklama",
            "confirm": "Potwierdź",
            "logout": "Wyloguj",
            "settings": "Ustawienia",
            "cruises": "Wycieczki",
            "my cruises": "Moje wycieczki",
            "phone": "Telefon",
            "cancel": "Anuluj",
            //signin
            "signin": "Zaloguj się",
            "signin.welcome": "Witamy ponownie",
            "signin.subtitle": "Bardzo się cieszymy, że znów jesteś z nami",
            "i don't have an account": "Nie mam konta",
            "forgot password": "Zapomniałem hasła",
            "refresh": "Odśwież",
            //signup
            "signup": "Zarejestruj",
            "signup.welcome": "Witamy",
            "i have an account": "Już mam konto",
            //signup.client
            "signup.client.subtitle": "Zostań członkiem największej przygody w Twoim życiu",
            "i am a business worker": "Jestem pracownikiem firmy",
            //signup.worker
            "signup.worker.subtitle": "Tylko kilka kliknięć dzieli Cię od dostępu do jednej z największych sieci klientów",
            "i am a client": "Jestem clientem",
            //ManageAccount
            "manage account": "Zarządzaj kontem",
            "personal data": "Dane presonalne",
            "phone number": "Numer telefonu",
            "personal data change btn": "Edytuj dane personalne",
            "personal data change": "Edycja danych personalnych",
            "address": "Dane adresowe",
            "street": "Ulica",
            "house number": "Numer domu",
            "postal code": "Kod pocztowy",
            "city": "Miasto",
            "country": "Kraj",
            "address change btn": "Edytuj dane adresowe",
            "address change": "Edycja danych adresowych",
            "email change btn": "Edytuj e-mail",
            "email change": "Edycja e-maila",
            "new email": "Podaj nowy e-mail",
            "new email confirm": "Potwierdź nowy e-mail",
            "password change btn": "Zmień hasło",
            "password change": "Zmiana hasła",
            "old password": "Podaj stare hasło",
            "new password": "Wprowadź nowe hasło",
            "new password confirm": "Potwierdź nowe hasło",
            "confrim email change": "Potwierdz zmiane maila",
            //workerPanel
            "workerPanel": "Panel pracownika",
            //adminPanel
            "search account": "Wyszukać konto",
            "adminPanel": "Panel administracyjny",
            "login": "Login",
            "first name": "Imię",
            "last name": "Nazwisko",
            "active": "Aktywny",
            "access level": "Poziom dostępu",
            "moderatorPanel": "Panel moderatora",
            "list accounts": "Lista kont",
            "edit": "Edytuj dane",
            "change password": "Zmień hasło",
            "reset password": "Resetuj hasło",
            "block": "Zablokuj konto",
            "unblock": "Odblokuj konto",
            "grand access level": "Przydziel poziom dostępu",
            "enable": "Aktywuj",
            "disable": "Dezaktywuj",
            "grant access level": "Przydziel poziom dostępu",
            "change access level state": "Zmień stan poziomu dostępu",
            //panels.clientPanel
            "clientPanel": "Panel klienta",
            //panels.ModeratorPanel
            "search business workers": "Szukaj niezatwierdzonych pracowników",
            "company name": "Nazwa firmy",
            "company phone number": " Numer telefonu firmy",
            "Manage business workers": "Zarządzaj pracownikami frim",
            //verify
            "verifyAccount": "Zweryfikuj konto",
            //color
            "color": "Kolor",
            "color.light": "Jasny",
            "color.dark": "Ciemny",
            //errors
            "error.facade.noSuchElement": "Nie znaleziono elementu w bazie danych",
            "error.account.accessLevels.alreadyAssigned": "Poziom dostępu jest już przydzielony do konta",
            "error.account.accessLevels.notAssigned": "Poziom dostępu nie jest przydzielony do konta",
            "error.account.accessLevels.notAssignable": "Nie można przydzielić podanego poziomu dostępu",
            "error.account.accessLevels.alreadyDisabled": "Poziom dostępu jest już zablokowany",
            "error.account.accessLevels.alreadyEnabled": "Poziom dostępu nie jest zablokowany",
            "error.security.etag.empty": "Żądanie nie zawiera wymaganego nagłówka ETag",
            "error.security.etag.invalid": "Nagłówek ETag żądania nie przechodzi walidacji",
            "error.security.etag.integrity": "Nagłówek ETag nie jest spójny z przekazanymi danymi",
            "error.parsing.serialization": "Błąd serializacji danych",
            "error.password.reset.wrongIdentity": "Żądanie zawiera złą tożsamość",
            "error.email.incorrect": "Address email jest niepoprawny",
            "error.emailService.inaccessible": "Serwis pocztowy jest niedostępny",
            "error.password.reset.contentError": "Błędna zawartość tokenu do resetowania hasła",
            "error.password.change.oldPasswordError": "Podane stare hasło jest niepoprawne",
            "error.security.etag.creation": "Błąd podczas tworzenia nagłówka ETag",
            "error.account.accessLevels.doesNotExist": "Dany poziom dostępu nie istnieje!",
            "error.password.reset.tokenUsed": "Podany link aktywacyjny został już wykorzystany!",
            "error.account.verification.contentError": "Błędna zawartość tokenu do zatwierdzenia konta",
            "error.token.expired": "Token wygasł",
            "error.token.refresh": "Token nie mógł być odświeżony",
            "error.account.verification.alreadyVerifiedError": "Dane konto zostało już zatwierdzone",
            "error.token.decode": "Błąd podczas odczytu tokenu",
            "error.token.invalidate": "Niepoprawny token",
            "error.database.loginReserved": "Podany login jest juz użyty",
            "error.database.emailReserved": "Podany email jest już użyty",
            "error.constraintViolation": "Podane dane są niepoprawne",
            "error.constraint.positive": "Podana liczba musi być dodatnia",
            "error.constraint.positiveOrZero": "Podana wartość musi być większa lub równa 0",
            "error.constraint.notEmpty": "Podane pole nie może być puste",
            "error.constraint.notNull": "Podana wartość nie może być zerem",
            "error.constraint.rating": "Ocena powinna być liczbą z zakresu 1-5",
            "error.forbidden": "Operacja zabroniona",
            "error.internal.server": "Błąd wewnętrzny serwera",
            "error.integration.optimistic": "Nieaktualny stan przesłanych danych",
            "error.account.notVerified": "Konto niezweryfikowne",
            "error.database.operation": "Błąd po stronie bazy danych",

            //auth errors
            "auth.incorrect.login": "Podany login jest niepoprawny",
            "auth.incorrect.password": "Podane hasło jest niepoprawne",

            ////regex errors
            "error.regex.postCode": "Niepoprawny format kodu pocztowego",
            "error.regex.city": "Niepoprawny format miasta",
            "error.regex.country": "Niepoprawny format państwa",
            "error.regex.street": "Niepoprawny format ulicy",
            "error.regex.phoneNumber": "Niepoprawny format numeru telefonu",
            "error.regex.login": "Niepoprawny format loginu",
            "error.regex.password": "Niepoprawny format hasła",
            "error.regex.name": "Niepoprawny format imienia",
            "error.regex.companyName": "Niepoprawny format nazwy firmy",
            "phone change btn": "Zmień numer telefonu",
            "error.regex.email": "Niepoprawny adres email",

            //else
            'error.emptypanellist': 'Nie masz żadnych paneli do sterowania',

            'error.fields': 'Zostały wprowadzone niepoprawne dane',
            'passwords are not equal': 'Hasła nie są identyczne',
            'emails are not equal': 'Emaile nie są identyczne',
            'error.houseNumber.NaN': 'Podany numer domu nie jest liczbą',
            'no options': 'Brak opcji',
            "error.size.phoneNumber": "Numer telefonu powinien mieć maksymalnie 15 znaków",
            "error.size.name": "Nazwa powinna mieć maksymalnie 64 znaków",
            "error.size.country": "Kraj powinna mieć maksymalnie 64 znaków",
            "error.size.city": "Miasto powinno mieć maksymalnie 64 znaków",
            "error.size.street": "Ulica powinna mieć maksymalnie 64 znaki",
            "error.size.companyName": "Nazwa firmy powinna mieć maksymalnie 64 znaki",
            "error.size.postCode": "Kod pocztowy powinien mieć maksymalnie 20 znaków",

            //successes
            "success.accessLevelStateChanged": "Stan poziomu dostępu został zmieniony",
            "success.accessLevelAssigned": "Poziom dostępu przydzielony",
            'successful action': 'Akcja udała się',

            //paths
            "/": "Strona główna",
            "/profile": "Profil",
            "/cruises": "Wycieczki",
            "/settings": "Ustawienia",
            "/profile/cruises": "Wycieczki",
            "/profile/settings": "Ustawienia",
            "/accounts": "Konta",
            "/ChangeAccountData": "Zmiana danych konta",
            "/GrantAccessLevel": "Przydzielenie dostępu kontowi",
            "/reset/passwordReset": "Resetowanie hasła",
            "/reset/changeEmail": "Zmiana maila konta",
            "/reset/resetSomebodyPassword": "Resetowanie hasła konta",
            "/verify/accountVerification": "Weryfikacja",
            "/ChangeAccessLevelState": "Zmień stan poziomu dostępu"
        }
    },
    en: {
        translation: {
            "404": "Unfortunately, we cannot find the desired page :(",
            "CLIENT": "Client",
            "BUSINESS_WORKER": "Business worker",
            "MODERATOR": "Moderator",
            "ADMINISTRATOR": "Administrator",
            "invalid.form": "Invalid form",
            "dismiss": "Dismiss",
            "go back": "Go back",
            "email": "E-mail",
            "password": "Password",
            "bad_password": "Bad Password",
            "confirm password": "Confirm password",
            "name": "Name",
            "surname": "Surname",
            "company": "Company",
            "the best cruises": "The best cruises",
            "ad": "Ad",
            "confirm": "Confirm",
            "logout": "Logout",
            "settings": "Settings",
            "cruises": "Cruises",
            "my cruises": "My cruises",
            "phone": "Phone",
            "cancel": "Cancel",
            //signin
            "signin": "Sign in",
            "signin.welcome": "Welcome back",
            "signin.subtitle": "We are very glad to see you again",
            "forgot password": "Forgot password",
            "refresh": "Refresh",
            //signup
            "signup": "Sign up",
            "signup.welcome": "Welcome",
            "i have an account": "I have an account",
            //signup.client
            "signup.client.subtitle": "Participate in the biggest adventure in your life",
            "i am a business worker": "I'm a business worker",
            //signup.worker
            "signup.worker.subtitle": "You'll have an access to the largest clients network after a couple of clicks",
            "i am a client": "I'm a client",
            //ManageAccount
            "manage account": "Manage account",
            "personal data": "Personal Data",
            "phone number": "Phone Number",
            "personal data change btn": "Edit personal data",
            "personal data change": "Editing personal data",
            "address": "Address data",
            "street": "Street",
            "house number": "House number",
            "postal code": "Postal code",
            "city": "City",
            "country": "Country",
            "address change btn": "Edit address",
            "address change": "Editing address data",
            "email change btn": "Change e-mail",
            "email change": "Editing e-mail",
            "new email": "Insert a new e-mail",
            "new email confirm": "Confirm the new e-mail",
            "password change btn": "Change password",
            "password change": "Changing password",
            "old password": "Insert the old password",
            "new password": "Insert a new password",
            "new password confirm": "Confirm the new password",
            "confrim email change": "Confirm change email",
            //workerPanel
            "workerPanel": "Worker panel",
            //adminPanel
            "adminPanel": "Administrator panel",
            "search account": "Search account",
            "login": "Login",
            "bad_login": "Bad Login",
            "first name": "First name",
            "last name": "Last name",
            "active": "Active",
            "access level": "Access level",
            "list accounts": "Accounts list",
            "moderator panel": "Moderator Panel",
            "edit": "Edit data",
            "change password": "Change password",
            "reset password": "Reset password",
            "block": "Block account",
            "enable": "Enable",
            "disable": "Disable",
            "grant access level": "Grant access level",
            "change access level state": "Change access level state",
            "unblock": "Unblock account",
            "grand access level": "Grand access level",
            //panels.clientPanel
            "clientPanel": "Client panel",
            //panels.ModeratorPanel
            "search business workers": "Search business workers",
            "company name": "Company name",
            "company phone number": "Company phone number",
            "Manage business workers": "Manage business workers",
            //verify
            "verifyAccount": "Verify account",
            //color
            "color": "Color",
            "color.light": "Light",
            "color.dark": "Dark",
            //errors
            "error.facade.noSuchElement": "No such element found in the database",
            "error.account.accessLevels.alreadyAssigned": "Access level already assigned to the account",
            "error.account.accessLevels.notAssigned": "Access level not assigned to the account",
            "error.account.accessLevels.notAssignable": "Cannot assign this access level",
            "error.account.accessLevels.alreadyDisabled": "Access level is already disabled",
            "error.account.accessLevels.alreadyEnabled": "Access level is already enabled",
            "error.security.etag.empty": "Request does not contain necessary ETag header",
            "error.security.etag.invalid": "Request's ETag header does not pass validation",
            "error.security.etag.integrity": "Request's ETag header does not match provided data",
            "error.parsing.serialization": "Received data could not be serialized properly",
            "error.password.reset.wrongIdentity": "Request contains wrong identity",
            "error.email.incorrect": "Email address is incorrect",
            "error.emailService.inaccessible": "Email service not available at the moment",
            "error.password.reset.contentError": "Invalid password reset token content",
            "error.password.change.oldPasswordError": "Provided old password is invalid",
            "error.security.etag.creation": "Error during creating ETag",
            "error.account.accessLevels.doesNotExist": "Given access level does not exists",
            "error.password.reset.tokenUsed": "Given activation link has already been used",
            "error.account.verification.contentError": "Invalid token to confirm account",
            "error.token.expired": "Token has expired",
            "error.token.refresh": "Token could not be refreshed",
            "error.account.verification.alreadyVerifiedError": "Given account has already been confirmed",
            "error.token.decode": "Error during decoding token",
            "error.token.invalidate": "Invalid token",
            "error.database.loginReserved": "Given login is already in use",
            "error.database.emailReserved": "Given email is already in use",
            "error.constraintViolation": "Incorrect data",
            "error.constraint.positive": "Given number must be positive",
            "error.constraint.positiveOrZero": "Given value must be greater or equal to zero",
            "error.constraint.notEmpty": "Given field can not be empty",
            "error.constraint.notNull": "Given value can not be null",
            "error.constraint.rating": "Rating should be a number in range 1-5",
            "error.forbidden": "Operation forbidden",
            "error.internal.server": "Internal server error",
            "error.integration.optimistic": "Outdated data provided",
            "error.account.notVerified": "Account not verified",
            "error.database.operation": "Database operation error",

            //auth errors
            "auth.incorrect.login": "Given login is incorrect",
            "auth.incorrect.password": "Given password is incorrect",

            ////regex errors
            "error.regex.postCode": "Invalid post code format",
            "error.regex.city": "Invalid city format",
            "error.regex.country": "Invalid country format",
            "error.regex.street": "Invalid street format",
            "error.regex.phoneNumber": "Invalid phone number format",
            "error.regex.login": "Invalid login format",
            "error.regex.password": "Invalid password format",
            "error.regex.name": "Invalid name format",
            "error.regex.companyName": "Invalid company name format",
            "phone change btn": "Change phone number",
            "error.regex.email": "Invalid email address",

            'error.emptypanellist': "You don't have any panel to manage",

            'error.fields': 'Given invalid data',
            'passwords are not equal': 'Passwords are not equal',
            'emails are not equal': "Emails are not equal",
            'error.houseNumber.NaN': 'Given house number is not a number',
            'no options': 'No options',
            "error.size.phoneNumber": "Phone number should be no longer than 15 characters",
            "error.size.name": "Name should be no longer than 64 characters",
            "error.size.country": "Country should be no longer than 64 characters",
            "error.size.city": "City should be no longer than 64 characters",
            "error.size.street": "Street should be no longer than 64 characters",
            "error.size.companyName": "Company name should be no longer than 64 characters",
            "error.size.postCode": "Post code should be no longer than 20 characters",
            'successful action': 'The action was successful',

            //successes
            "success.accessLevelStateChanged": "Access level state changed",
            "success.accessLevelAssigned": "Access level assigned",

            //paths
            "/": "Home",
            "/profile": "Profile",
            "/cruises": "Cruises",
            "/settings": "Settings",
            "/profile/cruises": "Cruises",
            "/profile/settings": "Settings",
            "/accounts": "Accounts",
            "/ChangeAccountData": "Change account data",
            "/GrantAccessLevel": "Grant access level",
            "/reset/passwordReset": "Password request",
            "/reset/changeEmail": "Change email",
            "/reset/resetSomebodyPassword": "Reset user password",
            "/verify/accountVerification": "Account verification",
            "/ChangeAccessLevelState": "Change access level state"
        }
    }
}

i18n
    .use(initReactI18next)
    .init({
        lng: 'pl',
        fallbackLng: 'en',

        resources
    })

export default i18n