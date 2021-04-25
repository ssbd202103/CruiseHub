import {Link} from 'react-router-dom'

import Box from '@material-ui/core/Box'
import EmailIcon from '@material-ui/icons/AlternateEmail'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'

import AuthLayout from '../layouts/AuthLayout'
import DarkedTextField from '../components/DarkedTextField'
import RoundedButton from '../components/RoundedButton'

import {useTranslation} from 'react-i18next'

import styles from '../styles/auth.global.module.css'
import AuthService from "../services/AuthService";

export default function SignIn() {
    const {t} = useTranslation();

    function auth() {
        AuthService('', '')
    }


    return (
            <AuthLayout>
                    <h1 className={styles.h1}>{t("signin.welcome")}</h1>
                    <h2 className={styles.h2}>{t("signin.subtitle")}</h2>

                    <DarkedTextField 
                        type="email"
                        label={t("email") + ' *'}
                        style={{
                            width: '70%',
                            margin: '20px 0'
                        }}
                        icon={(<EmailIcon />)}
                        placeholder="example@email.com"
                    />

                    <DarkedTextField 
                        type="password"
                        label={t("password") + ' *'}
                        style={{
                            width: '70%',
                            margin: '20px 0'
                        }} 
                        icon={(<PasswordIcon />)}
                        placeholder="1234567890"
                    />

                    <Box style={{
                        width: '70%',
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "space-between"
                    }}>
                        <RoundedButton
                            style={{
                                width: '50%',
                                fontSize: '1.2rem',
                                padding: '10px 0'
                            }}
                            color="pink"
                            onClick={auth}
                        >{t("signin")}</RoundedButton>
                        <Link to="signup/client">
                            <a className={styles.link}>{t("i don't have an account")}</a>
                        </Link>
                    </Box>

                    
            </AuthLayout>
    )
}