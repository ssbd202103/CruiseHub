import i18n from 'i18next'
import { initReactI18next } from 'react-i18next'

const resources = {
    pl: {
        translation: {
            "go back": "Wróć",
            "email": "Email",
            "password": "Hasło",
            "submit password": "Potwierdzenie hasła",
            "name": "Imię",
            "surname": "Nazwisko",
            "company": "Firma",
            "the best cruises": "Najlepsze wycieczki",
            "ad": "Reklama",
            //signin
            "signin": "Zaloguj się",
            "signin.welcome": "Witamy ponownie",
            "signin.subtitle": "Bardzo się cieszymy, że znów jesteś z nami",
            "i don't have an account": "Nie mam konta",
            //signup
            "signup": "Zarejestruj",
            "signup.welcome": "Witamy",
            //signup.client
            "signup.client.subtitle": "Zostań członkiem największej przygody w Twoim życiu",
            "i am a business worker": "Jestem pracownikiem firmy",
            //signup.worker
            "signup.worker.subtitle": "Tylko kilka kliknięć dzieli Cię od dostępu do jednej z największych sieci klientów",
            "i am a client": "Jestem clientem"
        }
    },
    en: {
        translation: {
            "go back": "Go back",
            "email": "Email",
            "password": "Password",
            "submit password": "Submit password",
            "name": "Name",
            "surname": "Surname",
            "company": "Company",
            "the best cruises": "The best cruises",
            "ad": "Ad",
            //signin
            "signin": "Sign in",
            "signin.welcome": "Welcome back",
            "signin.subtitle": "We are very glad to see you again",
            "i don't have an account": "I don't have an account",
            //signup
            "signup": "Sign up",
            "signup.welcome": "Welcome",
            //signup.client
            "signup.client.subtitle": "Participate in the biggest adventure in your life",
            "i am a business worker": "I'm a business worker",
            //signup.worker
            "signup.worker.subtitle": "You'll have an access to the largest clients network after a couple of clicks",
            "i am a client": "I'm a client"
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