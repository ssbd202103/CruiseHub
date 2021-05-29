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

const RequestPasswordReset = () => {
    const {t} = useTranslation()
    const showSuccess = useSnackbarQueue('success')
    const handleError = useHandleError()

    const [buttonPopupAcceptAction, setButtonPopupAcceptAction] = useState(false);

    const [login, setLogin] = useState('')
    let x = null

    const onFormSubmit = () => {
        // event.preventDefault()
        // console.log("hello world")
        axios.post(`account/request-password-reset/${login}`, {})
            .then(res => {
                setButtonPopupAcceptAction(false)
                showSuccess(t('successful action'))

            })
            .catch(error => {
                setButtonPopupAcceptAction(false)
                const message = error.response.data
                handleError(message)
            });
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
                }}
            />
            <PopupAcceptAction
                open={buttonPopupAcceptAction}
                onConfirm={onFormSubmit}
                onCancel={() => {setButtonPopupAcceptAction(false)
                }}
            />
            <RoundedButton
                onClick={()=>setButtonPopupAcceptAction(true)}
                style={{width: '100%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                color="pink"
            >{t("sendEmail")}</RoundedButton>

        </AuthLayout>

    );
};

export default RequestPasswordReset;