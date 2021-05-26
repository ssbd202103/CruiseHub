import React, {useState} from 'react';
import axios from "../../Services/URL";
import AuthLayout from "../../layouts/AuthLayout";
import DarkedTextField from "../../components/DarkedTextField";
import styles from "../../styles/auth.global.module.css";
import {useTranslation} from "react-i18next";
import RoundedButton from "../../components/RoundedButton";
import {useSnackbarQueue} from "../snackbar";

const RequestPasswordReset = () => {
    const {t} = useTranslation()
    const showError = useSnackbarQueue('error')
    const showSuccess = useSnackbarQueue('success')

    const [login, setLogin] = useState('')

    const onFormSubmit = () => {
        // event.preventDefault()
        // console.log("hello world")
        axios.post(`account/request-password-reset/${login}`, {})
            .catch(error => {
                const message = error.response.data
                showError(t(message))
            });
        showSuccess(t('successful action'))
    }

    return (
        <AuthLayout>
            <DarkedTextField
                label={t("login") + ' *'}
                placeholder="login"
                className={styles.input}
                value={login}
                onChange={event => {setLogin(event.target.value)}}
            />
            <RoundedButton
                onClick={onFormSubmit}
                style={{width: '100%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                color="pink"
            >Send email </RoundedButton>

        </AuthLayout>

    );
};

export default RequestPasswordReset;