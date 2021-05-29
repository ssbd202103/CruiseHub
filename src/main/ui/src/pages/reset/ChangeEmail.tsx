import {useHistory, useLocation} from 'react-router-dom';

import React, {useState} from 'react';
import axios from "../../Services/URL";
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
import useHandleError from "../../errorHandler";


export default function ChangeEmail(props: any) {
    const location = useLocation();
    const {t} = useTranslation();
    const history = useHistory();
    const handleError = useHandleError()



    const submitPasswordReset = async (event: any) => {
        event.preventDefault()

        const json = {
            "token": location.pathname.toString().substring('/reset/changeEmail/'.length)
        }

        axios.put('account/change-email', json, {
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "Authorization": `Bearer ${location.pathname.toString().substring('/reset/changeEmail/'.length)}`
            }
        }).then(res => {
            history.push('/')
        },error => {
            const message = error.response.data
            handleError(message)
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

