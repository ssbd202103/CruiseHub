import {Link} from 'react-router-dom'

import Box from '@material-ui/core/Box'
import EmailIcon from '@material-ui/icons/AlternateEmail'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'

import AuthLayout from '../../layouts/AuthLayout'
import DarkedTextField from '../../components/DarkedTextField'
import RoundedButton from '../../components/RoundedButton'
import styles from '../../styles/auth.global.module.css'

import {useTranslation} from 'react-i18next'
import axios from "axios";


export default function ClientSignUp() {
    const {t} = useTranslation()

    const clientSignUpFun = async () => {
        // const response = await axios.get('http://localhost:8080/cruisehub/api/account/greeting', {})
        // console.log(response.data)

        // const json = JSON.stringify({ answer: 42 });

        console.log("before post")

        const json = JSON.stringify({
                firstName: "Artur",
                secondName: "Radiuk",
                login: "aradiuk",
                email: "aradiuk@gmail.com",
                password: "123456789",
                languageType: "PL",
                addressDto: {
                    houseNumber: 1,
                    street: "Bortnyka",
                    postalCode: "30-302",
                    city: "Pluzhne",
                    country: "Ukraine"
                },
                phoneNumber: "123456789"
            }
        );

        const res = await axios.post('http://localhost:8080/cruisehub/api/account/client/registration', json, {
            headers: {
                'Content-Type': 'application/json'
            }
        });

        console.log("res = ", res)


    }

    return (
        <AuthLayout>
            <h1 className={styles.h1}>{t("signup.welcome")}</h1>
            <h2 className={styles.h2}>{t("signup.client.subtitle")}</h2>

            <Box style={{
                display: "flex",
                width: '100%',
                padding: 0
            }}>
                <DarkedTextField
                    label={t("name") + ' *'}
                    placeholder="John"
                    className={styles.input}
                    style={{
                        marginRight: 20
                    }}
                />


                <DarkedTextField
                    label={t("surname") + ' *'}
                    placeholder="Doe"
                    className={styles.input}
                />
            </Box>

            <DarkedTextField
                type="email"
                label={t("email") + ' *'}
                placeholder="example@email.com"
                className={styles.input}
                icon={(<EmailIcon/>)}
            />

            <Box style={{
                display: "flex",
                width: '100%',
                padding: 0
            }}>
                <DarkedTextField
                    type="password"
                    label={t("password") + ' *'}
                    placeholder="1234567890"
                    className={styles.input}
                    style={{marginRight: 20}}
                    icon={(<PasswordIcon/>)}
                />

                <DarkedTextField
                    type="password"
                    label={t("confirm password") + ' *'}
                    placeholder="1234567890"
                    className={styles.input}
                    icon={(<PasswordIcon/>)}
                />
            </Box>


            <Box style={{
                width: '100%',
                display: "flex",
                flexDirection: "column",
                alignItems: "flex-end",
                justifyContent: "space-between"
            }}>
                <RoundedButton onClickListenerFun={clientSignUpFun}
                               style={{width: '50%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                               color="pink">{t("signup")} </RoundedButton>
                <Link to="worker">
                    <a className={styles.link}> {t("i am a business worker")} </a>
                </Link>
                <Link to="../signin">
                    <a className={styles.link}> {t("i have an account")} </a>
                </Link>
            </Box>
        </AuthLayout>
    )
}