import {useHistory, useLocation} from 'react-router-dom';

import React, {useState} from 'react';
import axios from "axios";
import AuthLayout from "../../layouts/AuthLayout";
import DarkedTextField from "../../components/DarkedTextField";
import {useTranslation} from "react-i18next";
import PasswordIcon from "@material-ui/icons/VpnKeyRounded";
import styles from '../../styles/auth.global.module.css'
import RoundedButton from "../../components/RoundedButton";
import {useSnackbarQueue} from "../snackbar";
import Grid from "@material-ui/core/Grid";
import {Link} from "@material-ui/core";
import {getUser} from "../../Services/userService";


export default function PasswordReset(props: any) {
    const location = useLocation();
    const {t} = useTranslation();
    const history = useHistory();
    const showError = useSnackbarQueue('error')



    const submitPasswordReset = async (event: any) => {
        event.preventDefault()

        const json = {
            "token": location.pathname.toString().substring('/reset/changeOtherEmail/'.length)
        }

        axios.put('/api/account/change-other-email', json, {
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "Authorization": `Bearer ${location.pathname.toString().substring('/reset/changeOtherEmail/'.length)}`
            }
        }).then(res => {
            history.push('/')
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        });
    }


    return (
        <AuthLayout>
            <div>
                <RoundedButton
                    onClick={submitPasswordReset}
                    style={{width: '100%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                    color="pink"
                >{t("confrim email change")}</RoundedButton>
            </div>
        </AuthLayout>
    );
}

