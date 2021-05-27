import React, {useState} from 'react';
import axios from "../../Services/URL";
import AuthLayout from "../../layouts/AuthLayout";
import DarkedTextField from "../../components/DarkedTextField";
import styles from "../../styles/auth.global.module.css";
import {useTranslation} from "react-i18next";
import RoundedButton from "../../components/RoundedButton";
import {Link} from "react-router-dom";
import {useSnackbarQueue} from "../snackbar";
import store from "../../redux/store";
import {getUser, refreshToken} from "../../Services/userService";
import useHandleError from "../../errorHandler";

const RequestSomeonePasswordReset = () => {
    const {t} = useTranslation()
    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')

    const currentAccount = JSON.parse(sessionStorage.getItem("resetPasswordAccount") as string)

    const [email, setEmail] = useState('')
    const {token} = store.getState()
    let login = currentAccount.login;

    const onFormSubmit = () => {
        axios.post(`account/request-someones-password-reset/${login}/${email}/`, null, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).catch(error => {
            const message = error.response.data
            handleError(message)
        }).then(res => {
            refreshToken()
            showSuccess(t('successful action'))
        });


    }
    return (
        <AuthLayout>
            <h1 className={styles.h1}>{login}</h1>
            <DarkedTextField
                label={t("email") + ' *'}
                style={{
                    width: '70%',
                    margin: '20px 0'
                }}
                placeholder="email"
                className={styles.input}
                value={email}
                onChange={event => {
                    setEmail(event.target.value)
                }}
                colorIgnored
            />

            <Link to="/panels/adminPanel/accounts">
                <RoundedButton
                    onClick={onFormSubmit}
                    style={{width: '100%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                    color="pink"
                >Send email </RoundedButton>
            </Link>
        </AuthLayout>

    );
};

export default RequestSomeonePasswordReset;
