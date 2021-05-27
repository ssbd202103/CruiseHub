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
import {useSelector} from "react-redux";
import {selectLogin} from "../redux/slices/userSlice";

export default function CodeSignIn() {
    const {t} = useTranslation();

    const showError = useSnackbarQueue('error')

    const history = useHistory();
    const login = useSelector(selectLogin);
    const [code, setCode] = useState('')

    const codeAuth = async () => {
        const json = JSON.stringify({
            login: login,
            code: code
        })

        axios.post('/api/auth/code-sign-in', json, {
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
            <h2 className={styles.h2}>{login}</h2>
            <DarkedTextField
                type="text"
                label={t("code") + ' *'}
                style={{
                    width: '70%',
                    margin: '20px 0'
                }}
                placeholder="code"
                value={code}
                onChange={event => {setCode(event.target.value)}}
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
                    onClick={codeAuth}
                >{t("signin")}</RoundedButton>
            </Box>
        </AuthLayout>
    )
}