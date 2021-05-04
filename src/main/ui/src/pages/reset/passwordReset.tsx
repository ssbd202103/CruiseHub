import {useHistory, useLocation} from 'react-router-dom';

import React, {createRef, useState} from 'react';
import axios from "axios";
import AuthLayout from "../../layouts/AuthLayout";
import DarkedTextField from "../../components/DarkedTextField";
import {useTranslation} from "react-i18next";
import PasswordIcon from "@material-ui/icons/VpnKeyRounded";
import styles from '../../styles/auth.global.module.css'
import RoundedButton from "../../components/RoundedButton";


function PasswordReset(props: any) {
    const location = useLocation();
    const {t} = useTranslation()

    const loginRef = createRef() as React.RefObject<HTMLDivElement>
    const passwordRef = createRef() as React.RefObject<HTMLDivElement>
    const confirmPasswordRef = createRef() as React.RefObject<HTMLDivElement>


    const submitPasswordReset = async (event: any) => {
        event.preventDefault()

        const json = {
            "token": location.pathname.toString().substring('/reset/passwordReset/'.length),
            "login": loginRef?.current?.querySelector('input')?.value,
            "password": passwordRef?.current?.querySelector('input')?.value
        }

        await axios.put('http://localhost:8080/api/account/reset-password', json, {
            headers: {
                'Content-Type': 'application/json'
            }
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
                        ref={loginRef}
                    />

                    <DarkedTextField
                        type="password"
                        label={t("password") + ' *'}
                        placeholder="1234567890"
                        className={styles.input}
                        style={{marginRight: 20}}
                        icon={(<PasswordIcon/>)}

                        ref={passwordRef}

                    />

                    <DarkedTextField
                        type="password"
                        label={t("confirm password") + ' *'}
                        placeholder="1234567890"
                        className={styles.input}
                        icon={(<PasswordIcon/>)}

                        ref={confirmPasswordRef}
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
