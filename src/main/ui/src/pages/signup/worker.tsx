import {Link} from 'react-router-dom'

import Box from '@material-ui/core/Box'
import EmailIcon from '@material-ui/icons/AlternateEmail'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'

import AuthLayout from '../../layouts/AuthLayout'
import DarkedTextField from '../../components/DarkedTextField'
import DarkedSelect from '../../components/DarkedSelect'
import RoundedButton from '../../components/RoundedButton'
import styles from '../../styles/auth.global.module.css'

import {useTranslation} from 'react-i18next'
import {createRef, useEffect, useState} from "react";
import axios from "axios";

export default function WorkerSignUp() {
    const {t} = useTranslation()

    const firstNameRef = createRef() as React.RefObject<HTMLDivElement>
    const secondNameRef = createRef() as React.RefObject<HTMLDivElement>
    const loginRef = createRef() as React.RefObject<HTMLDivElement>
    const emailRef = createRef() as React.RefObject<HTMLDivElement>
    const passwordRef = createRef() as React.RefObject<HTMLDivElement>
    const confirmPasswordRef = createRef() as React.RefObject<HTMLDivElement>

    const languageTypeRef = createRef() as React.RefObject<HTMLDivElement>
    const phoneNumberRef = createRef() as React.RefObject<HTMLDivElement>

    const [company, setCompany] = useState("");

    const [companiesList, setCompaniesList] = useState([]);

    useEffect(() => {
        const getCompaniesList = async () => {
            const {data} = await axios.get('http://localhost:8080/cruisehub/api/company/companiesinfo', {});
            setCompaniesList(data.map((comp: { name: string }) => comp.name))
        }
        getCompaniesList()
    }, [company]);
    console.log(companiesList)

    const workerSignUpFun = async () => {

        const json = JSON.stringify({
            firstName: firstNameRef?.current?.querySelector('input')?.value,
            secondName: secondNameRef?.current?.querySelector('input')?.value,
            login: loginRef?.current?.querySelector('input')?.value,
            email: emailRef?.current?.querySelector('input')?.value,
            password: passwordRef?.current?.querySelector('input')?.value,
            languageType: languageTypeRef?.current?.querySelector('input')?.value,
                phoneNumber: phoneNumberRef?.current?.querySelector('input')?.value,
                companyName: company
            }
        );

        await axios.post('http://localhost:8080/cruisehub/api/account/businessworker/registration', json, {
            headers: {
                'Content-Type': 'application/json'
            }
        });

    }


    return (
        <AuthLayout>
            <h1 className={styles.h1}>{t("signup.welcome")}</h1>
            <h2 className={styles.h2}>{t("signup.worker.subtitle")}</h2>

            <Box
                style={{
                    display: "flex",
                    width: '100%',
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

            <Box
                style={{
                    display: "flex",
                    width: '100%',
                    padding: 0
                }}
            >
                <DarkedSelect
                    label={t("company") + ' *'}
                    // options={["Firma 1", "Firma 2", "FirmaJez", "Firma 4", "Firma 5"]}
                    options={companiesList}
                    className={styles.input}
                    style={{
                        marginRight: 20
                    }}
                    onSelectedChange={setCompany}
                />

                <DarkedTextField
                    type="email"
                    label={t("email") + ' *'}
                    placeholder="example@email.com"
                    className={styles.input}
                    icon={(<EmailIcon/>)}
                    ref={emailRef}
                />


            </Box>


            <Box
                style={{
                    display: "flex",
                    width: '100%',
                    padding: 0
                }}
            >

                <DarkedTextField
                    label={t("login") + ' *'}
                    placeholder="examplelogin"
                    className={styles.input}
                    style={{
                        marginRight: 20
                    }}
                    ref={loginRef}
                />

                <DarkedTextField
                    label={t("phoneNumber") + ' *'}
                    placeholder="examplenumber"
                    className={styles.input}
                    ref={phoneNumberRef}
                />

            </Box>

            <Box
                style={{
                    display: "flex",
                    width: '100%',
                    padding: 0
                }}
            >
                <DarkedTextField
                    label={t("languageType") + ' *'}
                    placeholder="language type"
                    className={styles.input}
                    ref={languageTypeRef}

                />
            </Box>


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
                    onClickListenerFun={workerSignUpFun}
                    style={{width: '50%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                    color="pink"
                >{t("signup")}</RoundedButton>
                <Link to="client">
                    <a className={styles.link}>{t("i am a client")}</a>
                </Link>
                <Link to="../signin">
                    <a className={styles.link}>{t("i have an account")}</a>
                </Link>
            </Box>
        </AuthLayout>
    )
}