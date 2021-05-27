import {useLocation} from 'react-router-dom';

import React, {useState} from 'react';
import axios from "../../Services/URL";
import AuthLayout from "../../layouts/AuthLayout";
import DarkedTextField from "../../components/DarkedTextField";
import {useTranslation} from "react-i18next";
import PasswordIcon from "@material-ui/icons/VpnKeyRounded";
import styles from '../../styles/auth.global.module.css'
import RoundedButton from "../../components/RoundedButton";
import {useSnackbarQueue} from "../snackbar";
import {refreshToken} from "../../Services/userService";


function PasswordReset(props: any) {
    const location = useLocation();
    const {t} = useTranslation()
    const showError = useSnackbarQueue('error')

    const [login, setLogin] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const showSuccess = useSnackbarQueue('success')

    const submitPasswordReset = async (event: any) => {
        event.preventDefault()

        const json = {
            "token": location.pathname.toString().substring('/reset/passwordReset/'.length),
            login,
            password
        }

        axios.put('account/reset-password', json, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        }).then(res => {
            //refreshToken()
            showSuccess(t('successful action'))
        });
    }


    return (
        <AuthLayout>
            <div>
                <form onSubmit={submitPasswordReset}>
                    <DarkedTextField
                        label={t("login") + ' *'}
                        placeholder="login"
                        className={styles.input}
                        value={login}
                        onChange={event => {
                            setLogin(event.target.value)
                        }}
                    />

                    <DarkedTextField
                        type="password"
                        label={t("password") + ' *'}
                        placeholder="1234567890"
                        className={styles.input}
                        style={{marginRight: 20}}
                        icon={(<PasswordIcon/>)}
                        value={password}
                        onChange={event => {
                            setPassword(event.target.value)
                        }}

                    />

                    <DarkedTextField
                        type="password"
                        label={t("confirm password") + ' *'}
                        placeholder="1234567890"
                        className={styles.input}
                        icon={(<PasswordIcon/>)}
                        value={confirmPassword}
                        onChange={event => {
                            setConfirmPassword(event.target.value)
                        }}
                    />

                    <RoundedButton
                        onClick={submitPasswordReset}
                        style={{width: '100%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                        color="pink"
                    >Reset </RoundedButton>

                </form>
            </div>
        </AuthLayout>
    );
}

export default PasswordReset;
