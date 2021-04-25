import i18n from 'i18next'
import { initReactI18next } from 'react-i18next'

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
            "Logout": "Wyloguj",
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
            "Manage account": "Zarządzaj kontem",
            "personalData": "Dane presonalne",
            "phoneNumber": "Numer telefonu",
            "personalDataChangeBtn": "Edytuj dane personalne",
            "personalDataChange": "Edycja danych personalnych",
            "address": "Adres",
            "street": "Ulica",
            "houseNumber": "Numer domu",
            "postalCode": "Kod pocztowy",
            "city": "Miasto",
            "country": "Kraj",
            "addressChangeBtn": "Edytuj dane adresowe",
            "addressChange": "Edycja danych adresowych",
            "emailChangeBtn": "Edytuj e-mail",
            "emailChange": "Edycja e-maila",
            "newEmail": "Podaj nowy e-mail",
            "newEmailConfirm": "Potwierdź nowy e-mail",
            "passwordChangeBtn": "Zmień hasło",
            "passwordChange": "Zmiana hasła",
            //workerPanel
            "WorkerPanel": "Panel pracownika",
            "buyAdvertisement": "Kup reklamę",
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
            "confirm": "Confirm",
            "Logout": "Logout",
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
            "Manage account": "Manage account",
            "personalData": "Personal Data",
            "phoneNumber": "Phone Number",
            "personalDataChangeBtn": "Edit personal data",
            "personalDataChange": "Editing personal data",
            "address": "Address",
            "street": "Street",
            "houseNumber": "House number",
            "postalCode": "Postal code",
            "city": "City",
            "country": "Country",
            "addressChangeBtn": "Edit address",
            "addressChange": "Editing address",
            "emailChangeBtn": "Change e-mail",
            "emailChange": "Editing e-mail",
            "newEmail": "Insert new e-mail",
            "newEmailConfirm": "Confirm new e-mail",
            "passwordChangeBtn": "Change password",
            "passwordChange": " password",
            //workerPanel
            "WorkerPanel": "Worker panel"
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