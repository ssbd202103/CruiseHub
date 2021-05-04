import i18n from 'i18next'
import {initReactI18next} from 'react-i18next'

const resources = {
    pl: {
        translation: {
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
            //signin
            "signin": "Zaloguj się",
            "signin.welcome": "Witamy ponownie",
            "signin.subtitle": "Bardzo się cieszymy, że znów jesteś z nami",
            "i don't have an account": "Nie mam konta",
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
            "address": "Adres",
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
            //workerPanel
            "worker panel": "Panel pracownika",
            //adminPanel
            "admin panel": "Panel administracyjny",
            "login": "Login",
            "active": "Aktywny",
            "access level": "Poziom dostępu",
            "moderator panel": "Panel moderatora",
            "list accounts": "Lista kont",
            "edit": "Edytuj dane",
            "change password": "Zmień hasło",
            "reset password": "Resetuj hasło",
            "block": "Zablokuj konto",
            "unblock": "Odblokuj konto",
            "grand access level": "Przydziel poziom dostępu",
            //panels.clientPanel
            "client panel": "Panel klienta",
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
            "error.parsing.serialization": "Błąd serializacji danych"
            "error.security.etag.invalid": "Nagłówek ETAG żądania nie przechodzi walidacji",
            "error.password.reset.wrongIdentity": "Żądanie zawiera złą tożsamość",
            "error.email.incorrect": "Address email jest niepoprawny",
            "error.emailService.inaccessible": "Serwis pocztowy jest niedostępny",
            "error.password.reset.contentError": "Błędna zawartość tokenu do resetowania hasła"

        }
    },
    en: {
        translation: {
            "go back": "Go back",
            "email": "E-mail",
            "password": "Password",
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
            //signin
            "signin": "Sign in",
            "signin.welcome": "Welcome back",
            "signin.subtitle": "We are very glad to see you again",
            "i don't have an account": "I don't have an account",
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
            "address": "Address",
            "street": "Street",
            "house number": "House number",
            "postal code": "Postal code",
            "city": "City",
            "country": "Country",
            "address change btn": "Edit address",
            "address change": "Editing address",
            "email change btn": "Change e-mail",
            "email change": "Editing e-mail",
            "new email": "Insert a new e-mail",
            "new email confirm": "Confirm the new e-mail",
            "password change btn": "Change password",
            "password change": "Changing password",
            "old password": "Insert the old password",
            "new password": "Insert a new password",
            "new password confirm": "Confirm the new password",
            //workerPanel
            "worker panel": "Worker panel",
            //adminPanel
            "admin panel": "Administrator panel",
            "login": "Login",
            "active": "Active",
            "access level": "Access level",
            "list accounts": "Accounts list",
            "moderator panel": "Moderator Panel",
            "edit": "Edit data",
            "change password": "Change password",
            "reset password": "Reset password",
            "block": "Block account",
            "unblock": "Unblock account",
            "grand access level": "Grand access level",
            //panels.clientPanel
            "clientPanelH2": "Client panel",
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
            "error.parsing.serialization": "Received data could not be serialized properly"
            "error.security.etag.invalid": "Request's ETag header does not pass validation",
            "error.password.reset.wrongIdentity": "Request contains wrong identity",
            "error.email.incorrect": "Email address is incorrect",
            "error.password.reset.contentError": "Błędna zawartość tokenu do resetowania hasła"

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