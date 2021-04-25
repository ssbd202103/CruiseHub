import i18n from 'i18next'
import {initReactI18next} from 'react-i18next'

const resources = {
    pl: {
        translation: {
            "go back": "Wróć",
            "email": "Email",
            "password": "Hasło",
            "confirm password": "Potwierdź hasło",
            "name": "Imię",
            "surname": "Nazwisko",
            "company": "Firma",
            "the best cruises": "Najlepsze wycieczki",
            "ad": "Reklama",
            "confirm": "Potwierdz",
            "logout": "Wyloguj",
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
            //adminPanel
            "adminPanel": "Panel administracyjny",
            "login": "Login",
            "active": "Aktywny",
            "access level": "Poziom dostępu",
            "moderator Panel": "Panel moderatora",
            "list accounts": "Lista kont",
            "manage account": "Zarządzaj swoim kontem",
            "edit": "Edytuj dane",
            "change password" : "Zmień hasło",
            "reset password": "Resetuj hasło",
            "block": "Zablokuj konto",
            "grand access level": "Przydziel poziom dostępu",

            //panels.clientPanel
            "clientPanelH2": "Panel klienta",
            "passwordChange": "Zmień hasło",
            "phoneNumber": "Numer telefonu",
            "personalDataChange": "Zmień dane personalne",
            "personalData": "Dane personalne",
            "address": "Adres",
            "addressChange": "Zmień adres",
            "street": "Ulica",
            "houseNumber": "Numer domu",
            "postalCode": "Kod pocztowy",
            "city": "Miasto",
            "country": "Kraj",
            "emailChange": "Zmień Email",
            "newEmail": "Podaj nowy Emial",
            "newEmailConfirm": "Potwierdź nowy Email",
            "oldPassword": "Podaj stare hasło",
            "newPassword": "Podaj nowe hasło",
            "newPasswordConfirm": "Porwierdź nowe hasło",
        }
    },
    en: {
        translation: {
            "go back": "Go back",
            "email": "Email",
            "password": "Password",
            "confirm password": "Confirm password",
            "name": "Name",
            "surname": "Surname",
            "company": "Company",
            "the best cruises": "The best cruises",
            "ad": "Ad",
            "confirm": "Confrim",
            "logout": "Logout",
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
            //adminPanel
            "adminPanel": "Administrator panel",
            "login": "Login",
            "active": "Active",
            "access level": "Access level",
            "list accounts": "Accounts list",
            "manage account": "Account manage",
            "moderator Panel": "Moderator Panel",
            "edit": "Edit data",
            "change password" : "Change password",
            "reset password": "Reset password",
            "block": "Block account",
            "grand access level": "Grand access level",

            //panels.clientPanel
            "clientPanelH2": "Client panel",
            "passwordChange": "Change password",
            "phoneNumber": "Phone number",
            "personalDataChange":"Change personal data",
            "personalData": "Personal data",
            "address": "Address",
            "addressChange": "Change address",
            "street": "Street",
            "houseNumber": "House number",
            "postalCode": "Postal code",
            "city": "City",
            "country": "Country",
            "emailChange": "Change email",
            "newEmail": "New Email",
            "newEmailConfirm": "Confirm Email",
            "oldPassword": "Enter the old password",
            "newPassword": "Enter a new password",
            "newPasswordConfirm": "Confirm new password",
        }
    }
}

i18n
    .use(initReactI18next)
    .init({
        lng: 'en',
        fallbackLng: 'pl',

        resources
    })

export default i18n