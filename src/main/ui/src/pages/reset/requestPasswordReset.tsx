import React, {useState} from 'react';
import axios from "../../Services/URL";
import AuthLayout from "../../layouts/AuthLayout";
import DarkedTextField from "../../components/DarkedTextField";
import styles from "../../styles/auth.global.module.css";
import {useTranslation} from "react-i18next";
import RoundedButton from "../../components/RoundedButton";
import {useSnackbarQueue} from "../snackbar";
import useHandleError from "../../errorHandler";
import PopupAcceptAction from "../../PopupAcceptAction";
import {useHistory} from "react-router-dom";

const RequestPasswordReset = () => {
    const {t} = useTranslation()
    const showSuccess = useSnackbarQueue('success')
    const handleError = useHandleError()
    const history = useHistory()

    const [buttonPopupAcceptAction, setButtonPopupAcceptAction] = useState(false);

    const [login, setLogin] = useState('')
    const [loginEmptyError, setLoginEmptyError] = useState(false)

    const resetPassword = () => {
        setButtonPopupAcceptAction(true)
    }


    const onFormSubmit = () => {
        if (login === "") {
            setLoginEmptyError(true)
            handleError("fill.login.password.fields")
        } else {
            axios.post(`account/request-password-reset/${login}`, {})
                .then(res => {
                    console.log("hello")
                    showSuccess(t('successful action'))
                    history.push('/')
                })
                .catch(error => {
                    const message = error.response.data
                    handleError(message, error.response.status)
                });
        }
    }

    return (
        <AuthLayout>
            <DarkedTextField
                label={t("login") + ' *'}
                placeholder={t("login")}
                className={styles.input}
                value={login}
                onChange={event => {
                    setLogin(event.target.value)
                    event.target.value ? setLoginEmptyError(false) : setLoginEmptyError(true)
                }}
                regexError={loginEmptyError}
            />
            <PopupAcceptAction
                open={buttonPopupAcceptAction}
                onConfirm={onFormSubmit}
                onCancel={() => {
                    setButtonPopupAcceptAction(false)
                }}
            />
            <RoundedButton
                onClick={resetPassword}
                style={{width: '100%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                color="pink"
            >{t("sendEmail")}</RoundedButton>

        </AuthLayout>

    );
};

export default RequestPasswordReset;