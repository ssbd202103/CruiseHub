import React, {useState} from 'react';
import axios from "axios";
import AuthLayout from "../../layouts/AuthLayout";
import DarkedTextField from "../../components/DarkedTextField";
import styles from "../../styles/auth.global.module.css";
import {useTranslation} from "react-i18next";
import RoundedButton from "../../components/RoundedButton";
import {Link} from "react-router-dom";
import {useSnackbarQueue} from "../snackbar";
import store from "../../redux/store";
import {getUser} from "../../Services/userService";

const RequestSomeonePasswordReset = () => {
    const {t} = useTranslation()
    const showError = useSnackbarQueue('error')
    const showSuccess = useSnackbarQueue('success')

    const currentAccount = JSON.parse(sessionStorage.getItem("resetPasswordAccount") as string)

    const [email, setEmail] = useState('')
    const {token} = store.getState()
    let login = currentAccount.login;
    const onFormSubmit = () => {
        axios.post(`/api/account/request-someones-password-reset/${login}/${email}/`, null, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .catch(error => {
                const message = error.response.data
                showError(t(message))
            });
        showSuccess(t('successful action'))
    }
    return (
        <AuthLayout>
            <h1 className={styles.h1}>{login}</h1>
            <DarkedTextField
                label={t("email") + ' *'}
                placeholder="email"
                className={styles.input}
                value={email}
                onChange={event => {setEmail(event.target.value)}}
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
