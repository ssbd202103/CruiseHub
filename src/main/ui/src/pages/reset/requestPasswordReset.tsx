import React, {useState} from 'react';
import axios from "axios";
import AuthLayout from "../../layouts/AuthLayout";
import DarkedTextField from "../../components/DarkedTextField";
import styles from "../../styles/auth.global.module.css";
import {useTranslation} from "react-i18next";
import RoundedButton from "../../components/RoundedButton";
import {useErrorSnackbar} from "../snackbar";

const RequestPasswordReset = () => {
    const {t} = useTranslation()
    const showError = useErrorSnackbar()

    const [login, setLogin] = useState('')

    const onFormSubmit = () => {
        // event.preventDefault()
        // console.log("hello world")
        axios.post(`http://localhost:8080/api/account/request-password-reset/${login}`, {})
            .catch(error => {
                const message = error.response.data
                showError(t(message))
            });
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