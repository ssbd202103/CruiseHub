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
import {useEffect, useState} from "react";
import axios from "axios";
import Recaptcha from "react-recaptcha";
import Popup from "../../PopupRecaptcha";
import {useSelector} from "react-redux";

export default function WorkerSignUp() {
    const {t} = useTranslation()

    const [firstName, setFirstName] = useState('')
    const [secondName, setSecondName] = useState('')
    const [login, setLogin] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const [languageType, setLanguageType] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')

    const [company, setCompany] = useState("");

    const [companiesList, setCompaniesList] = useState([]);

    const [buttonPopup, setButtonPopup] = useState(false);

    async function verifyCallback() {
        const json = JSON.stringify({
            firstName,
            secondName,
            login,
            email,
            password,
            languageType,
            phoneNumber,
            companyName: company
        });
        setButtonPopup(false)
        await axios.post('http://localhost:8080/api/account/business-worker/registration', json, {
            headers: {
                'Content-Type': 'application/json'
            }
        });

    }

    useEffect(() => {
        const getCompaniesList = async () => {
            const {data} = await axios.get('http://localhost:8080/api/company/companiesinfo', {});
            setCompaniesList(data.map((comp: { name: string }) => comp.name))
        }
        getCompaniesList()
    }, [company]);
    console.log(companiesList)

    const workerSignUpFun = async () => {
        setButtonPopup(true)
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
                    value={firstName}
                    onChange={event => {setFirstName(event.target.value)}}
                    colorIgnored
                />


                <DarkedTextField
                    label={t("surname") + ' *'}
                    placeholder="Doe"
                    className={styles.input}
                    value={secondName}
                    onChange={event => {setSecondName(event.target.value)}}
                    colorIgnored
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
                    colorIgnored
                />

                <DarkedTextField
                    type="email"
                    label={t("email") + ' *'}
                    placeholder="example@Email(message = REGEX_INVALID_EMAIL).com"
                    className={styles.input}
                    icon={(<EmailIcon/>)}
                    value={email}
                    onChange={event => {setEmail(event.target.value)}}
                    colorIgnored
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
                    value={login}
                    onChange={event => {setLogin(event.target.value)}}
                    colorIgnored
                />

                <DarkedTextField
                    label={t("phoneNumber") + ' *'}
                    placeholder="examplenumber"
                    className={styles.input}
                    value={phoneNumber}
                    onChange={event => {setPhoneNumber(event.target.value)}}
                    colorIgnored
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
                    value={languageType}
                    onChange={event => {setLanguageType(event.target.value)}}
                    colorIgnored
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
                    value={password}
                    onChange={event => {setPassword(event.target.value)}}
                    colorIgnored
                />

                <DarkedTextField
                    type="password"
                    label={t("confirm password") + ' *'}
                    placeholder="1234567890"
                    className={styles.input}
                    icon={(<PasswordIcon/>)}
                    value={confirmPassword}
                    onChange={event => {setConfirmPassword(event.target.value)}}
                    colorIgnored
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
                    onClick={workerSignUpFun}
                    style={{width: '50%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                    color="pink"
                >{t("signup")}</RoundedButton>
                <Popup trigger={buttonPopup} setTrigger={setButtonPopup}>
                    <div>
                        <Recaptcha
                            sitekey={process.env.REACT_APP_SECRET_NAME}
                            size="normal"
                            verifyCallback={verifyCallback}
                        />
                    </div>
                </Popup>
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