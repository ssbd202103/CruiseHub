import {Link} from 'react-router-dom'

import Box from '@material-ui/core/Box'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'

import { useHistory } from 'react-router-dom'

import AuthLayout from '../layouts/AuthLayout'
import DarkedTextField from '../components/DarkedTextField'
import RoundedButton from '../components/RoundedButton'

import {useTranslation} from 'react-i18next'

import styles from '../styles/auth.global.module.css'
import axios from "axios"
import React, {useState} from "react"

import {getUser} from "../Services/userService";

import {useSnackbarQueue} from "./snackbar";

export default function SignIn() {
    const {t} = useTranslation();

    const showError = useSnackbarQueue('error')

    const history = useHistory();

    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')

    const auth = async () => {
        const json = JSON.stringify({
            login: login,
            password: password
        })

        axios.post('/api/auth/sign-in', json, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            getUser(res.data)
            history.push('/')
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        })
    }


    return (
        <AuthLayout>
            <h1 className={styles.h1}>{t("signin.welcome")}</h1>
            <h2 className={styles.h2}>{t("signin.subtitle")}</h2>

            <DarkedTextField
                type="text"
                label={t("login") + ' *'}
                style={{
                    width: '70%',
                    margin: '20px 0'
                }}
                placeholder="login"
                value={login}
                onChange={event => {setLogin(event.target.value)}}
                colorIgnored
            />

            <DarkedTextField
                type="password"
                label={t("password") + ' *'}
                style={{
                    width: '70%',
                    margin: '20px 0'
                }}
                icon={(<PasswordIcon />)}
                placeholder="1234567890"
                value={password}
                onChange={event => {setPassword(event.target.value)}}
                colorIgnored
            />

            <Box style={{
                width: '70%',
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between"
            }}>
                <RoundedButton
                    style={{
                        width: '50%',
                        fontSize: '1.2rem',
                        padding: '10px 0'
                    }}
                    color="pink"
                    onClick={auth}
                >{t("signin")}</RoundedButton>
                <Link to="signup/client">
                    <a className={styles.link}>{t("i don't have an account")}</a>
                </Link>

                <Link to="/reset/requestPassword">
                    <a className={styles.link}>Zapomniałem hasło</a>
                </Link>

            </Box>


        </AuthLayout>
    )
}