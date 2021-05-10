import React, {createRef, useState} from 'react';
import axios from "axios";
import AuthLayout from "../../layouts/AuthLayout";
import DarkedTextField from "../../components/DarkedTextField";
import styles from "../../styles/auth.global.module.css";
import {useTranslation} from "react-i18next";
import RoundedButton from "../../components/RoundedButton";
import {Link} from "react-router-dom";
import TableCell from "@material-ui/core/TableCell";

const RequestSomeonePasswordReset = () => {
    const {t} = useTranslation()

    const currentAccount = JSON.parse(sessionStorage.getItem("resetPasswordAccount") as string)

    const emailRef = createRef() as React.RefObject<HTMLDivElement>
    let login = currentAccount.login;
    const onFormSubmit = () => {
        // event.preventDefault()
        // console.log("hello world")
        axios.post(`http://localhost:8080/api/account/request-someones-password-reset/${login}/${emailRef?.current?.querySelector('input')?.value}/`, {});
    }
    return (
        <AuthLayout>
            <h1 className={styles.h1}>{login}</h1>
            <DarkedTextField
                label={t("email") + ' *'}
                placeholder="email"
                className={styles.input}
                ref={emailRef}
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