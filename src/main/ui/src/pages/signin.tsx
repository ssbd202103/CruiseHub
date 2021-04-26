import {Link} from 'react-router-dom'

import Box from '@material-ui/core/Box'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'

import AuthLayout from '../layouts/AuthLayout'
import DarkedTextField from '../components/DarkedTextField'
import RoundedButton from '../components/RoundedButton'

import {useTranslation} from 'react-i18next'

import styles from '../styles/auth.global.module.css'
import axios from "axios"
import React, {createRef} from "react"

export default function SignIn() {
    const {t} = useTranslation();

    const loginRef = createRef() as React.RefObject<HTMLDivElement>
    const passwordRef = createRef() as React.RefObject<HTMLDivElement>

    const auth = async () => {
        const json = JSON.stringify({
            login: loginRef?.current?.querySelector('input')?.value,
            password: passwordRef?.current?.querySelector('input')?.value
        })

        let response = await axios.post('http://localhost:8080/cruisehub/api/signin', json, {
            headers: {
                'Content-Type': 'application/json'
            }
        })

        window.localStorage.setItem('token', response.data)
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
                        ref={loginRef}
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
                        ref={passwordRef}
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
                    </Box>

                    
            </AuthLayout>
    )
}