import {Link} from 'react-router-dom'

import Box from '@material-ui/core/Box'
import EmailIcon from '@material-ui/icons/AlternateEmail'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'

import AuthLayout from '../../layouts/AuthLayout'
import DarkedTextField from '../../components/DarkedTextField'
import RoundedButton from '../../components/RoundedButton'
import styles from '../../styles/auth.global.module.css'

import {useTranslation} from 'react-i18next'
import axios from "axios";
import {useState} from "react";


export default function ClientSignUp() {
    const {t} = useTranslation()

    const [firstName, setFirstName] = useState('')
    const [secondName, setSecondName] = useState('')
    const [login, setLogin] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [languageType, setLanguageType] = useState('')

    const [houseNumber, setHouseNumber] = useState('')
    const [street, setStreet] = useState('')
    const [postalCode, setPostalCode] = useState('')
    const [city, setCity] = useState('')
    const [country, setCountry] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')

    const clientSignUpFun = async () => {

        const json = JSON.stringify({
                firstName,
                secondName,
                login,
                email,
                password,
                languageType,
                addressDto: {
                    houseNumber,
                    street,
                    postalCode,
                    city,
                    country
                },
                phoneNumber
            }
        );

        await axios.post('http://localhost:8080/api/account/client/registration', json, {
            headers: {
                'Content-Type': 'application/json'
            }
        });


    }


    return (
        <AuthLayout>
            <h1 className={styles.h1}>{t("signup.welcome")}</h1>
            <h2 className={styles.h2}>{t("signup.client.subtitle")}</h2>

            <Box
                style={{
                    display: "flex",
                    width: '100%',
                    padding: 0
                }}
            >
                <DarkedTextField
                    label={t("name") + ' *'}
                    placeholder="John"
                    className={styles.input}
                    style={{
                        marginRight: 20
                    }}
                    value={firstName}
                    onChange={event => {setFirstName(event.target.value)}}
                />


                <DarkedTextField
                    label={t("surname") + ' *'}
                    placeholder="Doe"
                    className={styles.input}
                    value={secondName}
                    onChange={event => {setSecondName(event.target.value)}}
                />
            </Box>

            <DarkedTextField
                type="email"
                label={t("email") + ' *'}
                placeholder="example@Email(message = REGEX_INVALID_EMAIL).com"
                className={styles.input}
                icon={(<EmailIcon/>)}
                value={email}
                onChange={event => {setEmail(event.target.value)}}
            />

            <DarkedTextField
                label={t("login") + ' *'}
                placeholder="examplelogin"
                className={styles.input}
                value={login}
                onChange={event => {setLogin(event.target.value)}}
            />


            <DarkedTextField
                label={t("languageType") + ' *'}
                placeholder="language type"
                className={styles.input}
                value={languageType}
                onChange={event => {setLanguageType(event.target.value)}}
            />


            <DarkedTextField
                label={t("houseNumber") + ' *'}
                placeholder="house number"
                className={styles.input}
                value={houseNumber}
                onChange={event => {setHouseNumber(event.target.value)}}
            />

            <DarkedTextField
                label={t("street") + ' *'}
                placeholder="street"
                className={styles.input}
                value={street}
                onChange={event => {setStreet(event.target.value)}}
            />
            <DarkedTextField
                label={t("postalCode") + ' *'}
                placeholder="postal code"
                className={styles.input}
                value={postalCode}
                onChange={event => {setPostalCode(event.target.value)}}
            />
            <DarkedTextField
                label={t("city") + ' *'}
                placeholder="city"
                className={styles.input}
                value={city}
                onChange={event => {setCity(event.target.value)}}
            />
            <DarkedTextField
                label={t("country") + ' *'}
                placeholder="country"
                className={styles.input}
                value={country}
                onChange={event => {setCountry(event.target.value)}}
            />

            <DarkedTextField
                label={t("phoneNumber") + ' *'}
                placeholder="phone number"
                className={styles.input}
                value={phoneNumber}
                onChange={event => {setPhoneNumber(event.target.value)}}
            />

            <Box
                style={{
                    display: "flex",
                    width: '100%',
                    padding: 0
                }}
            >
                <DarkedTextField
                    type="password"
                    label={t("password") + ' *'}
                    placeholder="1234567890"
                    className={styles.input}
                    style={{marginRight: 20}}
                    icon={(<PasswordIcon/>)}
                    value={password}
                    onChange={event => {setPassword(event.target.value)}}
                />

                <DarkedTextField
                    type="password"
                    label={t("confirm password") + ' *'}
                    placeholder="1234567890"
                    className={styles.input}
                    icon={(<PasswordIcon/>)}
                    value={confirmPassword}
                    onChange={event => {setConfirmPassword(event.target.value)}}
                />
            </Box>


            <Box
                style={{
                    width: '100%',
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "flex-end",
                    justifyContent: "space-between"
                }}
            >
                <RoundedButton
                    onClick={clientSignUpFun}
                    style={{width: '50%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                    color="pink"
                >{t("signup")} </RoundedButton>

                <Link to="worker">
                    <a className={styles.link}> {t("i am a business worker")} </a>
                </Link>
                <Link to="../signin">
                    <a className={styles.link}> {t("i have an account")} </a>
                </Link>
            </Box>
        </AuthLayout>
    )
}