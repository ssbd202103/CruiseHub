import {Link, useHistory} from 'react-router-dom'

import Box from '@material-ui/core/Box'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'

import AuthLayout from '../layouts/AuthLayout'
import DarkedTextField from '../components/DarkedTextField'
import RoundedButton from '../components/RoundedButton'

import {useTranslation} from 'react-i18next'

import styles from '../styles/auth.global.module.css'
import axios from "../Services/URL"
import React, {useEffect, useState} from "react"
import {setLogin as setLoginAction} from '../redux/slices/userSlice'

import {useSnackbarQueue} from "./snackbar";
import useHandleError from "../errorHandler";
import store from "../redux/store";
import {useDispatch} from "react-redux";
import PopupAcceptAction from "../PopupAcceptAction";

export default function SignIn() {
    const {t} = useTranslation();

    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')

    const history = useHistory();
    const dispatch = useDispatch();
    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')

    const [loginEmptyError, setLoginEmptyError] = useState(false)
    const [passwordEmptyError, setPasswordEmptyError] = useState(false)


    const auth = () => {
        if (login === "" || password === "") {
            login ? setPasswordEmptyError(true) : setLoginEmptyError(true)
            handleError("fill.login.password.fields")
        } else {
            const json = JSON.stringify({
                login: login,
                password: password
            })
            axios.post('/auth/sign-in', json, {
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(res => {
                dispatch(setLoginAction(login))
                history.push('/signin-code')
                showSuccess(t('successful action'))
            }).catch(error => {
                const message = error.response.data
                handleError(message, error.response.status)
            })
        }


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
                onChange={event => {
                    setLogin(event.target.value)
                    event.target.value ? setLoginEmptyError(false) : setLoginEmptyError(true)
                }}
                colorIgnored
                regexError={loginEmptyError}
            />

            <DarkedTextField
                type="password"
                label={t("password") + ' *'}
                style={{
                    width: '70%',
                    margin: '20px 0'
                }}
                icon={(<PasswordIcon/>)}
                placeholder="1234567890"
                value={password}
                onChange={event => {
                    setPassword(event.target.value)
                    event.target.value ? setPasswordEmptyError(false) : setPasswordEmptyError(true)
                }}
                regexError={passwordEmptyError}
                colorIgnored
            />

            <Box className={styles['button-wrap']}>
                <RoundedButton
                    style={{
                        width: '50%',
                        fontSize: '1.2rem',
                        padding: '10px 0',
                        marginBottom: 16,
                    }}
                    color="pink"
                    onClick={auth}
                >{t("signin")}</RoundedButton>
                <Link to="signup/client" className={styles.link}>
                    {t("i don't have an account")}
                </Link>

                <Link to="/reset/requestPassword" className={styles.link}>
                    {t("forgot password")}
                </Link>

            </Box>


        </AuthLayout>
    )
}