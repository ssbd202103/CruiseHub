import i18n from 'i18next'
import {initReactI18next} from 'react-i18next'

const resources = {
    PL: {
        translation: {
            "mainPage.headers": ["Parostatkiem w piękny rejs", "Titanici, tylko bezpieczniejsze",
                "Ahoj przygodo!",
                "Gdzie dzisiaj płyniemy?",
                "Która wyspa tym razem?",
                "Płyńmy z prądem!"],
            "404": "Niestety, nie znaleźliśmy szukanej strony :(",
            "CLIENT": "Klient",
            "BUSINESS_WORKER": "Pracownik firmy",
            "MODERATOR": "Moderator",
            "ADMINISTRATOR": "Administrator",
            "invalid.form": "Niepoprawnie wypełniony formularz",
            "dismiss": "Odrzuć",
            "go back": "Wróć",
            "email": "E-mail",
            "password": "Hasło",
            "confirm password": "Potwierdź hasło",
            "name": "Imię",
            "nameExample": "Jan",
            "surname": "Nazwisko",
            "surnameExample": "Kowalski",
            "company": "Firma",
            "the best cruises": "Najlepsze wycieczki",
            "ad": "Reklama",
            "confirm": "Potwierdź",
            "logout": "Wyloguj",
            "settings": "Ustawienia",
            "cruises": "Wycieczki",
            "expand cruises": "Rozwiń wycieczki",
            "my cruises": "Moje wycieczki",
            "phone": "Telefon",
            "cancel": "Anuluj",
            'reserve': "Zarezerwuj",
            "reservations": "Rezerwacje",
            "publish": "Opublikuj",
            'choose another date': 'Wybierz inną datę',
            'no dates': 'Nie ma innych dat',
            //signin
            "signin": "Zaloguj się",
            "signin.welcome": "Witamy ponownie",
            "signin.subtitle": "Bardzo się cieszymy, że znów jesteś z nami",
            "i don't have an account": "Nie mam konta",
            "forgot password": "Zapomniałem hasła",
            "refresh": "Odśwież",
            "code": "Kod weryfikacyjny",
            //signup
            "signup": "Zarejestruj",
            "signup.welcome": "Witamy",
            "i have an account": "Już mam konto",
            "houseNumber": "Numer domu",
            "postalCode": "Kod pocztowy",
            "emailExample": "przykład@email.com",
            "streetExample": "Tuwima",
            "houseNumberExample": "15",
            "cityExample": "Warszawa",
            "postalCodeExample": "05-800",
            "countryExample": "Polska",
            "phoneNumber": "Numer telefonu",
            "phoneNumberExample": "+48 507 422 550",
            //signup.client
            "signup.client.subtitle": "Zostań członkiem największej przygody w Twoim życiu",
            "i am a business worker": "Jestem pracownikiem firmy",
            //signup.worker
            "signup.worker.subtitle": "Tylko kilka kliknięć dzieli Cię od dostępu do jednej z największych sieci klientów",
            "i am a client": "Jestem klientem",
            //reset password
            "sendEmail": "Wyślij e-mail",
            "reset": "Resetowanie hasła użytkownika: ",
            "itsPassword": "",
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
            "confrim email change": "Potwierdź zmianę maila",
            //workerPanel
            "workerPanel": "Panel pracownika",
            "deactivateCruiseGroup": "Dezaktywuj grupę wycieczek",
            "deactivate": "Dezaktywuj",
            //adminPanel
            "search account": "Wyszukać konto",
            "search company": "Wyszukać firme",
            "adminPanel": "Panel administracyjny",
            "login": "Login",
            "first name": "Imię",
            "last name": "Nazwisko",
            "active": "Aktywny",
            "access level": "Poziom dostępu",
            "moderatorPanel": "Panel moderatora",
            "list accounts": "Lista kont",
            "list companies": "Lista firm",
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
            "business workers": "Pracownicy firmy",
            "search business workers": "Szukaj niezatwierdzonych pracowników",
            "company name": "Nazwa firmy",
            "company phone number": " Numer telefonu firmy",
            "Manage business workers": "Zarządzaj pracownikami firm",
            "show business workers": "Pokaż pracowników",
            "add company": "Dodaj firmę",
            "company name example": "FirmaJez",
            //verify
            "verifyAccount": "Zweryfikuj konto",
            //color
            "color": "Kolor",
            "color.light": "Jasny",
            "color.dark": "Ciemny",
            //Metadate
            "alterType": "Typ modyfikacji:",
            "alteredBy": "Modyfikowany przez:",
            "createdBy": "Stworzony przez:",
            "creationDateTime": "Czas stworzenia:",
            "lastAlterDateTime": "Czas ostatniej modyfikacji:",
            "lastCorrectAuthenticationDateTime": "Czas ostatniej poprawnej autoryzacji:",
            "lastCorrectAuthenticationLogicalAddress": "Adres IP ostatniej poprawnej autoryzacji:",
            "lastIncorrectAuthenticationDateTime": "Czas ostatniej niepoprawnej autoryzacji:",
            "lastIncorrectAuthenticationLogicalAddress": "Adres IP ostatniej niepoprawnej autoryzacji:",
            "numberOfAuthenticationFailures": "Liczba niepoprawnych autoryzacji:",
            "version": "Wersja:",
            //cruisecard
            "cruiseExample": "Rejs po Morzu Śródziemnym",
            "createCruiseGroup": "Stworz nową grupę wycieczek",
            "createCruise": "Stworz nową wycieczkę",
            //change language
            "changeLanguage": "Zmień język",
            //errors
            "error.facade.noSuchElement": "Nie znaleziono elementu w bazie danych",
            "error.security.notAuthorized": "Brak uprawnień do wykonania operacji",
            "error.account.accessLevels.alreadyAssigned": "Poziom dostępu jest już przydzielony do konta",
            "error.account.accessLevels.notAssigned": "Poziom dostępu nie jest przydzielony do konta",
            "error.account.accessLevels.notAssignable": "Nie można przydzielić podanego poziomu dostępu",
            "error.account.accessLevels.alreadyDisabled": "Poziom dostępu jest już zablokowany",
            "error.account.accessLevels.alreadyEnabled": "Poziom dostępu nie jest zablokowany",
            "error.account.accessLevels.parse": "Podany poziom dostępu nie może być poprawnie odczytany",
            "error.security.etag.empty": "Żądanie nie zawiera wymaganego nagłówka ETag",
            "error.security.etag.invalid": "Nagłówek ETag żądania nie przechodzi walidacji",
            "error.security.etag.integrity": "Nagłówek ETag nie jest spójny z przekazanymi danymi",
            "error.parsing.serialization": "Błąd serializacji danych",
            "error.password.reset.wrongIdentity": "Żądanie zawiera złą tożsamość",
            "error.email.incorrect": "Address email jest niepoprawny",
            "error.emailService.inaccessible": "Serwis pocztowy jest niedostępny",
            "error.password.reset.contentError": "Błędna zawartość tokenu do resetowania hasła",
            "error.password.change.oldPasswordError": "Podane stare hasło jest niepoprawne",
            "error.password.change.newAndOldPasswordAreTheSameErorr": "Stare hasło jest takie samo jak nowe",
            "error.code.dontMatchError": "Podany kod nie jest poprawny",
            "error.code.expireError": "Podany kod wygasł",
            "error.code.alreadyUsedError": "Podany kod został już użyty",
            "error.code.incorrectCodeError": "Podany kod jest niepoprawny",
            "error.security.etag.creation": "Błąd podczas tworzenia nagłówka ETag",
            "error.account.accessLevels.doesNotExist": "Dany poziom dostępu nie istnieje!",
            "error.password.reset.tokenUsed": "Podany link aktywacyjny został już wykorzystany!",
            "error.account.verification.contentError": "Błędna zawartość tokenu do zatwierdzenia konta",
            "error.token.expired": "Token wygasł",
            "error.token.refresh": "Token nie mógł być odświeżony",
            "error.account.verification.alreadyVerifiedError": "Dane konto zostało już zatwierdzone",
            "error.token.decode": "Błąd podczas odczytu tokenu",
            "error.token.invalidate": "Niepoprawny token",
            "error.token.alreadyUsedError": "Ten link został już wcześniej użyty",
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
            "error.integration.optimistic": "Nieaktualny stan danych, odśwież stronę",
            "error.account.notVerified": "Konto niezweryfikowne",
            "error.account.notActive": "Konto jest zablokowane",
            "error.database.operation": "Błąd po stronie bazy danych",
            "error.business.worker.confirmed": "Pracownik jest już zatwierdzony",
            "companies_nip_unique_constraint": "Firma o podanym numerze NIP znajduje się już w bazie danych",
            "companies_name_unique_constraint": "Firma o podanej nazwie znajduje się już w bazie danych",

            //auth errors
            "auth.incorrect.login": "Błąd logowania",
            "auth.incorrect.password": "Błąd logowania",

            ////regex errors
            "error.regex.postCode": "Niepoprawny format kodu pocztowego",
            "error.regex.city": "Niepoprawny format miasta",
            "error.regex.country": "Niepoprawny format państwa",
            "error.regex.street": "Niepoprawny format ulicy",
            "error.regex.houseNumber": "Niepoprawny format numeru domu",
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
            'no options': 'Brak opcji',
            "error.size.phoneNumber": "Numer telefonu powinien mieć maksymalnie od 7 do 15 znaków",
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
            "/companies": "Firmy",
            "/accounts/change_account_data": "Zmiana danych konta",
            "/accounts/grant_access_level": "Przydzielenie dostępu kontowi",
            "/reset/passwordReset": "Resetowanie hasła",
            "/reset/changeEmail": "Zmiana maila konta",
            "/reset/resetSomebodyPassword": "Resetowanie hasła konta",
            "/verify/accountVerification": "Weryfikacja",
            "/accounts/change_access_level_state": "Zmień stan poziomu dostępu",
            'accept.action': 'Czy chcesz wykonać tę akcję',
            '/accounts/resetSomebodyPassword': 'Resetowanie hasła',
            "/addCruiseGroup": "Dodaj grupę wycieczek",
            "/addCruise": "Dodaj wycieczkę",
            "/listCruiseGroup": "Wyświetl grupę wycieczek",
            "/add-company": "Dodaj firmę",

            'yes': 'Tak',
            'no': 'Nie',

            "update": "Odśwież",
            "fill.login.password.fields": "Pola login oraz haslo muszą być wypełnione",
            "error.unauthorized": "Token sesyjny prawdopodobnie wygasł, zaloguj się ponownie",
            'token.missing': 'Brak tokenu',
            'redirect in': 'Przekierowanie na główną stronę przez',
            'data.load.success': 'Dane zostały załadowane',
            // alter type
            'UPDATE': 'Edycja',
            'INSERT': 'Wstawienie',
            'DELETE': 'Wycofanie',

            // mięsiące
            'JANUARY': 'Styczeń',
            'FEBRUARY': 'Luty',
            'MARCH': 'Marzec',
            'APRIL': 'Kwiecień',
            'MAJ': 'Maj',
            'JUNE': 'Czerwiec',
            'JULY': 'Lipiec',
            'AUGUST': 'Sierpień',
            'SEPTEMBER': 'Wrzesień',
            'OCTOBER': 'Październik',
            'NOVEMBER': 'Listopad',
            'DECEMBER': 'Grudzień',

            //cruises
            "cruiseName": "Nazwa grupy wycieczek",
            "cruisneNamePlaceHolder": "Najlepsza przygoda",
            "description": "Opis",
            "descriptionPlaceHolder": "Tygodniowy rejs",
            "numberOfSeats": "Liczba miejsc",
            "seatsPlaceHolder":"30",
            "price":"Cena",
            "pricePlaceHolder":"100",
            "streetPlaceHolder":"Wolności",
            "streetNumber":"Numer ulicy",
            "numberPlaceHolder":"55",
            "harborName": "Nazwa portu",
            "harborPlaceHolder":"port Srilanka",
            "cityPlaceHolder":" Aleksandrów",
            "countryPlaceHolder":"Polska",
            "addPicture":"Dodaj zdjęcie",
            "removePicture": "Usuń zdjęcie",
            "addCruise": "Dodaj grupę",
            "listCruiseGroup": "Wyświetl grupę wycieczek",
            "cruiseReserved": "Zarezersowano wycieczkę",

            'no description': 'Nie ma żadnego opisu',
            'attractions': 'Atrakcje',
            'no attractions': 'Nie ma żadnych atrakcji',
            'take': 'Weżmij',
            'opinions': 'Opinie',
            "search cruiseGroup": "Wyszukaj grupę",
            "seats": "Liczba miejsc",
            "start_date": "Data początkowa",
            "end_date": "Data końcowa",

            "newCruise": "Dodaj wycieczkę",
            "startDate":"Data rozpoczęcia rejsu",
            "endDate":"Data zakończenia rejsu",
            "startTime":"Czas rozpoczęcia rejsu",
            "endTime":"Czas zakończenia rejsu",
            "cruiseGroup":"Grupa wycieczek",
            "error.field.end.date.before.start.date":"Data zakończenia jest przed datą rozpoczęcia",
            "error.field.start.date":"Data rozpoczęcia nie może być wcześniejsza niż dzisiejsza data",
            "error.cruiseGroup.no.active":"Grupa wycieczek nie jest aktywna",
            "error.startDate.before.currentDate":"Data rozpoczęcia nie może być przed obecną datą",
            "error.startDate.after.endDate":"Data rozpoczęcia nie może być po dacie zakończenia",

            "error.cruise.mapper.data.parse":"Nieprawidłowy format daty",
            "error.cruise.mapper.uuid.parse":"Nieprawidłowy format uuid",

            "max.date.message":"Data nie powinna być póżniejsza niż maksymalna data",
            "min.date.message":"Data nie powinna być wcześniejsza niż minimalna data",

            "invalid.date.message":"Nieprawidłowy format daty",
            "invalid.label":"nieznana",
            "cancel.label":"Anuluj",
            "clear.label":"Wyczyść",
            "ok.label":"Akceptuj",
            "today.label":"Dzisiaj",
            "not.all.fields.filled":"Nie wszystkie pola zostały wypełnione",
            "Client name": "Nazwa Klienta",
            "numberOfSeatsReserved": "Liczba zarezerwowanych miejsc",
            "CruiseName": "Nazwa wycieczki",
            "/reservations" :"Rezerwacje wycieczki",
            "search reservation": "Wyszukaj rezerwacje",
        }
    },
    EN: {
        translation: {
            "mainPage.headers": ["Titinics, but safer",
                "Keep floating!",
                "Boats are waiting!",
                "Which island will you choose today?"],
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
            "nameExample": "John",
            "surname": "Surname",
            "surnameExample": "Doe",
            "company": "Company",
            "the best cruises": "The best cruises",
            "ad": "Ad",
            "confirm": "Confirm",
            "logout": "Logout",
            "settings": "Settings",
            "cruises": "Cruises",
            "expand cruises": "Expand cruises",
            "my cruises": "My cruises",
            "phone": "Phone",
            "cancel": "Cancel",
            'reserve': "Reserve",
            "reservations": "Reservations",
            "publish": "Publish",
            'choose another date': 'Choose another date',
            'no dates': 'There are no another dates',
            //signin
            "signin": "Sign in",
            "signin.welcome": "Welcome back",
            "signin.subtitle": "We are very glad to see you again",
            "forgot password": "Forgot password",
            "refresh": "Refresh",
            "code": "Authentication Code",
            "i don't have an account": "I don't have an account",
            //signup
            "signup": "Sign up",
            "signup.welcome": "Welcome",
            "i have an account": "I have an account",
            "houseNumber": "House number",
            "postalCode": "Postal code",
            "emailExample": "example@email.com",
            "streetExample": "Baker Street",
            "houseNumberExample": "221b",
            "cityExample": "London",
            "postalCodeExample": "NW1 6XE",
            "countryExample": "England",
            "phoneNumber": "Phone number",
            "phoneNumberExample": "+44 507 422 550",
            //signup.client
            "signup.client.subtitle": "Participate in the biggest adventure in your life",
            "i am a business worker": "I'm a business worker",
            //signup.worker
            "signup.worker.subtitle": "You'll have an access to the largest clients network after a couple of clicks",
            "i am a client": "I'm a client",
            //reset password
            "sendEmail": "Send e-mail",
            "reset": "Reset ",
            "itsPassword": "'s password",
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
            "deactivateCruiseGroup": "Deactivate Cruise Group",
            "deactivate": "Deactivate",
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
            "business workers": "Business workers",
            "search business workers": "Search business workers",
            "company name": "Company name",
            "company phone number": "Company phone number",
            "Manage business workers": "Manage business workers",
            "show business workers": "Show business workers",
            "add company": "Add a company",
            "company name example": "Hedgehog Company",
            //verify
            "verifyAccount": "Verify account",
            //color
            "color": "Color",
            "color.light": "Light",
            "color.dark": "Dark",
            //Metadate
            "alterType": "Alter type:",
            "alteredBy": "Altered by:",
            "createdBy": "Created by:",
            "creationDateTime": "Creation time:",
            "lastAlterDateTime": "Last alter Time:",
            "lastCorrectAuthenticationDateTime": "Last correct authentication time:",
            "lastCorrectAuthenticationLogicalAddress": "Last correct authentication IP Address:",
            "lastIncorrectAuthenticationDateTime": "Last incorrect authentication time:",
            "lastIncorrectAuthenticationLogicalAddress": "Last incorrect authentication IP Address:",
            "numberOfAuthenticationFailures": "Number of authentication failures:",
            "version": "Version:",
            //cruisecard
            "cruiseExample": "Cruise on the Mediterranean Sea",
            //change language
            "changeLanguage": "Change language",
            //errors
            "error.facade.noSuchElement": "No such element found in the database",
            "error.security.notAuthorized": "Operation not authorized",
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
            "error.password.change.newAndOldPasswordAreTheSameErorr": "Old password is the same as the new one",
            "error.code.dontMatchError": "Inserted code is not correct",
            "error.code.expireError": "Inserted code expire",
            "error.code.alreadyUsedError": "Inserted code is already used",
            "error.code.incorrectCodeError": "Inserted code is incorrect",
            "error.security.etag.creation": "Error during creating ETag",
            "error.account.accessLevels.doesNotExist": "Given access level does not exists",
            "error.password.reset.tokenUsed": "Given activation link has already been used",
            "error.account.verification.contentError": "Invalid token to confirm account",
            "error.token.expired": "Token has expired",
            "error.token.refresh": "Token could not be refreshed",
            "error.account.verification.alreadyVerifiedError": "Given account has already been confirmed",
            "error.token.decode": "Error during decoding token",
            "error.token.invalidate": "Invalid token",
            "error.token.alreadyUsedError": "This link has already been used before",
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
            "error.integration.optimistic": "Outdated data provided, please reload the site",
            "error.account.notVerified": "Account not verified",
            "error.account.notActive": "Account is blocked",
            "error.database.operation": "Database operation error",
            "error.business.worker.confirmed": "Worker is already confirmed",
            "companies_nip_unique_constraint": "Company with said NIP already exists in the database",
            "companies_name_unique_constraint": "Company with said name already exists in the database",

            //auth errors
            "auth.incorrect.login": "Login error",
            "auth.incorrect.password": "Login error",

            ////regex errors
            "error.regex.postCode": "Invalid post code format",
            "error.regex.city": "Invalid city format",
            "error.regex.country": "Invalid country format",
            "error.regex.street": "Invalid street format",
            "error.regex.houseNumber": "Invalid house number format",
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
            'no options': 'No options',
            "error.size.phoneNumber": "Phone number should consist of 7 to 15 characters",
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
            "/accounts/change_account_data": "Change account data",
            "/accounts/grant_access_level": "Grant access level",
            "/reset/passwordReset": "Password request",
            "/reset/changeEmail": "Change email",
            "/reset/resetSomebodyPassword": "Reset user password",
            "/verify/accountVerification": "Account verification",
            "/accounts/change_access_level_state": "Change access level state",
            "/accounts/resetSomebodyPassword": "Password reseting",
            "/addCruiseGroup": "Add cruise group",
            "/addCruise": "Add cruise",
            "/listCruiseGroup": "List cruise groups",
            "/add-company": "Add a company",

            'accept.action': 'Do you want to do this action?',

            'yes': 'Yes',
            'no': 'No',
            "update": "Refresh",
            "fill.login.password.fields": "Fill in login and password fields",
            "error.unauthorized": "Session token is probably expired, please login one more time",
            'token.missing': 'Token is missing',
            'redirect in': 'Redirect to the main page in',
            'data.load.success': 'Data has been downloaded',

            // alter type
            'UPDATE': 'Update',
            'INSERT': 'Insert',
            'DELETE': 'Delete',

            // mięsiące
            'JANUARY': 'January',
            'FEBRUARY': 'February',
            'MARCH': 'March',
            'APRIL': 'April',
            'MAJ': 'Maj',
            'JUNE': 'June',
            'JULY': 'July',
            'AUGUST': 'August',
            'SEPTEMBER': 'September',
            'OCTOBER': 'October',
            'NOVEMBER': 'November',
            'DECEMBER': 'December',

            // cruises
            "cruiseName": "Cruise name",
            "cruisneNamePlaceHolder": "Best adventure",
            "description": "Description",
            "descriptionPlaceHolder": "A week tour ",
            "numberOfSeats": "Number of seats",
            "seatsPlaceHolder":"30",
            "price":"Price",
            "pricePlaceHolder":"100",
            "streetPlaceHolder":"Goergs streat",
            "streetNumber":"Street number",
            "numberPlaceHolder":"55",
            "harborName": "Harbor name",
            "harborPlaceHolder":" Srilanka port",
            "cityPlaceHolder":" London",
            "countryPlaceHolder":"Poland",
            "addPicture":"Add picture",
            "removePicture": "Remove Picture",
            "addCruise": "Add group",
            "listCruiseGroup": "List cruises groups",
            "createCruiseGroup": "Create new cruiseGroup",
            "cruiseReserved": "Cruise booked",
            'no description': 'There are no description',
            'attractions': 'Attractions',
            'no attractions': 'There are no attractions',
            'take': 'Take',
            'opinions': 'Opinions',
            "search cruiseGroup": "Search group",
            "seats": "Number of seats",
            "start_date": "Start date",
            "end_date": "End date",
            "newCruise": "Add cruise",
            "createCruise": "Create new cruise",
            "startDate":"Cruise start date",
            "endDate":"Cruise end date",
            "startTime":"Cruise start time",
            "endTime":"Cruise end time",
            "cruiseGroup":"Cruise group",
            "error.field.end.date.before.start.date":"End date is before start date",
            "error.field.start.date":"The start date cannot be earlier than today's date",
            "error.cruiseGroup.no.active":"Cruise group is not active",
            "error.startDate.before.currentDate":"The start date cannot be before current date",
            "error.startDate.after.endDate":"The start date cannot be after end date",
            "error.cruise.mapper.data.parse":"Invalid date format",
            "error.cruise.mapper.uuid.parse":"Invalid uuid format",

            "max.date.message":"Date should not be after maximal date",
            "min.date.message":"Date should not be before minimal date",

            "invalid.date.message":"Invalid Date Form",
            "invalid.label":"unknown",
            "cancel.label":"Cancel",
            "clear.label":"Clear",
            "ok.label":"OK",
            "today.label":"Today",
            "not.all.fields.filled":"Not all fields have been completed",
            "Client name": "Client name",
            "numberOfSeatsReserved": "Number of seats reserved",
            "CruiseName": "Name of the cruise",
            "/reservations" :"Cruise reservations",
            "search reservation": "Search reservations",
        }
    }
}

i18n
    .use(initReactI18next)
    .init({
        lng: 'EN',
        fallbackLng: 'PL',

        resources
    })

export default i18n