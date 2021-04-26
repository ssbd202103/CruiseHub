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
import {createRef} from "react";


export default function ClientSignUp() {
    const {t} = useTranslation()

    const firstNameRef = createRef() as React.RefObject<HTMLDivElement>
    const secondNameRef = createRef() as React.RefObject<HTMLDivElement>
    const loginRef = createRef() as React.RefObject<HTMLDivElement>
    const emailRef = createRef() as React.RefObject<HTMLDivElement>
    const passwordRef = createRef() as React.RefObject<HTMLDivElement>
    const confirmPasswordRef = createRef() as React.RefObject<HTMLDivElement>
    const languageTypeRef = createRef() as React.RefObject<HTMLDivElement>

    const houseNumberRef = createRef() as React.RefObject<HTMLDivElement>
    const streetRef = createRef() as React.RefObject<HTMLDivElement>
    const postalCodeRef = createRef() as React.RefObject<HTMLDivElement>
    const cityRef = createRef() as React.RefObject<HTMLDivElement>
    const countryRef = createRef() as React.RefObject<HTMLDivElement>
    const phoneNumberRef = createRef() as React.RefObject<HTMLDivElement>

    const clientSignUpFun = async () => {


        const json = JSON.stringify({
                firstName: firstNameRef?.current?.querySelector('input')?.value,
                secondName: secondNameRef?.current?.querySelector('input')?.value,
                login: loginRef?.current?.querySelector('input')?.value,
                email: emailRef?.current?.querySelector('input')?.value,
                password: passwordRef?.current?.querySelector('input')?.value,
                languageType: languageTypeRef?.current?.querySelector('input')?.value,
                addressDto: {
                    houseNumber: houseNumberRef?.current?.querySelector('input')?.value,
                    street: streetRef?.current?.querySelector('input')?.value,
                    postalCode: postalCodeRef?.current?.querySelector('input')?.value,
                    city: cityRef?.current?.querySelector('input')?.value,
                    country: countryRef?.current?.querySelector('input')?.value
                },
                phoneNumber: phoneNumberRef?.current?.querySelector('input')?.value
            }
        );

        await axios.post('http://localhost:8080/cruisehub/api/account/client/registration', json, {
            headers: {
                'Content-Type': 'application/json'
            }
        });


    }


    return (
        <AuthLayout>
            <h1 className={styles.h1}>{t("signup.welcome")}</h1>
            <h2 className={styles.h2}>{t("signup.client.subtitle")}</h2>

            <Box
                style={{
                    display: "flex",
                    width: '100%',
                    padding: 0
                }}
            >
                <DarkedTextField
                    label={t("name") + ' *'}
                    placeholder="John"
                    className={styles.input}
                    style={{
                        marginRight: 20
                    }}
                    ref={firstNameRef}

                />


                <DarkedTextField
                    label={t("surname") + ' *'}
                    placeholder="Doe"
                    className={styles.input}
                    ref={secondNameRef}


                />
            </Box>

            <DarkedTextField
                type="email"
                label={t("email") + ' *'}
                placeholder="example@email.com"
                className={styles.input}
                icon={(<EmailIcon/>)}

                ref={emailRef}


            />

            <DarkedTextField
                label={t("login") + ' *'}
                placeholder="examplelogin"
                className={styles.input}
                ref={loginRef}
            />


            <DarkedTextField
                label={t("languageType") + ' *'}
                placeholder="language type"
                className={styles.input}
                ref={languageTypeRef}

            />


            <DarkedTextField
                label={t("houseNumber") + ' *'}
                placeholder="house number"
                className={styles.input}
                ref={houseNumberRef}

            />
            <DarkedTextField
                label={t("street") + ' *'}
                placeholder="street"
                className={styles.input}
                ref={streetRef}

            />
            <DarkedTextField
                label={t("postalCode") + ' *'}
                placeholder="postal code"
                className={styles.input}
                ref={postalCodeRef}

            />
            <DarkedTextField
                label={t("city") + ' *'}
                placeholder="city"
                className={styles.input}
                ref={cityRef}

            />
            <DarkedTextField
                label={t("country") + ' *'}
                placeholder="country"
                className={styles.input}
                ref={countryRef}

            />

            <DarkedTextField
                label={t("phoneNumber") + ' *'}
                placeholder="phone number"
                className={styles.input}
                ref={phoneNumberRef}

            />


            <Box
                style={{
                    display: "flex",
                    width: '100%',
                    padding: 0
                }}
            >
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
            </Box>


            <Box
                style={{
                    width: '100%',
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "flex-end",
                    justifyContent: "space-between"
                }}
            >
                <RoundedButton
                    onClickListenerFun={clientSignUpFun}
                    style={{width: '50%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                    color="pink"
                >{t("signup")} </RoundedButton>
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