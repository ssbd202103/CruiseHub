import {useReducer, useState} from 'react'

import Grid from '@material-ui/core/Grid'

import {Link} from 'react-router-dom'

import {useTranslation} from 'react-i18next';

import styles from '../../../styles/ManageAccount.module.css'
import RoundedButton from '../../../components/RoundedButton';
import DarkedTextField from '../../../components/DarkedTextField';
import axios from "axios";
import {useSnackbarQueue} from "../../snackbar";
import store from "../../../redux/store";

export default function ChangeAccountData() {
    const {t} = useTranslation()
    const showError = useSnackbarQueue('error')

    const [, forceUpdate] = useReducer(x => x + 1, 0); // used to force component refresh on forceUpdate call
    const [ChangePerData, setPerData] = useState(false)
    const [ChangAddress, setChangChangAddress] = useState(false)
    const [ChangePhone, setChangePhone] = useState(false)

    const [firstName, setFirstName] = useState('')
    const [secondName, setSecondName] = useState('')
    const [email, setEmail] = useState('')

    const [houseNumber, setHouseNumber] = useState('')
    const [street, setStreet] = useState('')
    const [postalCode, setPostalCode] = useState('')
    const [city, setCity] = useState('')
    const [country, setCountry] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')

    const [businessPhoneNumber, setBusinessPhoneNumber] = useState('')
    const {token} = store.getState()

    //Functions for personal data change
    const handleChangePerData = () => {
        setPerData(state => !state)
        setChangChangAddress(false)
        setChangePhone(false)
    }
    //Functions for address data change
    const handleChangAddress = () => {
        setChangChangAddress(state => !state)
        setPerData(false)
        setChangePhone(false)
    }
    const handleChangePhone = () => {
        setChangChangAddress(false)
        setPerData(false)
        setChangePhone(state => !state)
    }


    const changePersonalData = async () => {
        const json = JSON.stringify({
            login: currentAccount.login,
            newFirstName: firstName,
            newSecondName: secondName,
            newEmail: email,
            version: currentAccount.version

        })
        fetch("/api/account/change-account-data", {
            method: "PUT",
            mode: "same-origin",
            body: json,
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag
            }
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        });
        const result = await axios.get(`/api/account/details/${currentAccount.login}`);
        sessionStorage.setItem("changeAccountData", JSON.stringify(result.data));
        forceUpdate()
        handleChangePerData()
    }


    const changeAddress = async () => {
        const json = JSON.stringify({
            login: currentAccount.login,
            version: currentAccount.version,

            newPhoneNumber: phoneNumber,
            newAddress: {
                newHouseNumber: houseNumber,
                newStreet: street,
                newPostalCode: postalCode,
                newCity: city,
                newCountry: country,
            },
            accVersion: clientAddr.accVersion
        })

        fetch("/api/account/change-client-data", {
            method: "PUT",
            mode: "same-origin",
            body: json,
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag
            }
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        });

        const result = axios.get(`/api/account/details/${currentAccount.login}`).then(res => {
            sessionStorage.setItem("changeAccountData", JSON.stringify(res.data));
            forceUpdate()
            handleChangAddress()
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        });
    }
    const changeBusinessPhone = async () => {
        const json = JSON.stringify({
            login: currentAccount.login,
            version: currentAccount.version,
            newPhoneNumber: businessPhoneNumber,
            accVersion: businnesPhone.accVersion

        })
        fetch("/api/account/change-business-worker-data", {
            method: "PUT",
            mode: "same-origin",
            body: json,
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag
            }
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        });

        axios.get(`/api/account/details/${currentAccount.login}`).then(res => {
            sessionStorage.setItem("changeAccountData", JSON.stringify(res.data));
            forceUpdate()
            handleChangePhone()
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        });

    }
    const currentAccount = JSON.parse(sessionStorage.getItem("changeAccountData") as string)
    const clientAccount = JSON.parse(sessionStorage.getItem("changeAccountData") as string)
    const businessAccount = JSON.parse(sessionStorage.getItem("changeAccountData") as string)
    const acLevel = currentAccount.accessLevels.map((accessLevel: any) => accessLevel.accessLevelType)
    const clientAddr = clientAccount.accessLevels.find((data: any) => (data.accessLevelType.includes("CLIENT")))
    const businnesPhone = businessAccount.accessLevels.find((data: any) => (data.accessLevelType.includes("BUSINESS_WORKER")))

    return (
        <Grid container className={styles.wrapper}>
            <Grid item style={{display: ChangePerData ? "none" : "block"}} className={styles.item}>
                <h3>{t("personal data")}</h3>
                <div>
                    <div>
                        <h4>{t("name")}</h4>
                        <p>{currentAccount.firstName}</p>
                    </div>
                    <div>
                        <h4>{t("surname")}</h4>
                        <p>{currentAccount.secondName}</p>
                    </div>
                    <div>
                        <h4>{t("Login")}</h4>
                        <p>{currentAccount.login}</p>
                    </div>
                    <div>
                        <h4>{t("email")}</h4>
                        <p>{currentAccount.email}</p>

                    </div>
                    <RoundedButton
                        color="blue"
                        onClick={handleChangePerData}
                    >{t("personal data change btn")}</RoundedButton>
                </div>
            </Grid>

            <Grid item style={{display: ChangePerData ? "block" : "none"}} className={styles['change-item']}>
                <h3>{t("personal data change")}</h3>
                <div>
                    <DarkedTextField
                        type="text"
                        label={t("name")}
                        placeholder={currentAccount.firstName}
                        value={firstName}
                        onChange={event => {
                            setFirstName(event.target.value)
                        }}/>
                    <DarkedTextField
                        type="text"
                        label={t("surname")}
                        placeholder={currentAccount.secondName}
                        value={secondName}
                        onChange={event => {
                            setSecondName(event.target.value)
                        }}/>

                    <DarkedTextField
                        type="text"
                        label={t("new email")}
                        placeholder={t(currentAccount.email)}
                        value={email}
                        onChange={event => {
                            setEmail(event.target.value)
                        }}/>

                </div>
                <RoundedButton color="blue"
                               onClick={changePersonalData}
                >{t("confirm")}</RoundedButton>
                <RoundedButton color="pink"
                               onClick={handleChangePerData}
                >{t("cancel")}</RoundedButton>
            </Grid>
            <Grid item style={{display: acLevel.includes('CLIENT') ? "block" : "none"}} className={styles.item}>
                <Grid item style={{display: ChangAddress ? "none" : "block"}} className={styles.item}>
                    <h3>{t("address")}</h3>

                    <div>
                        <div>
                            <h4>{t("street")}</h4>
                            <p>{clientAddr ? clientAddr.address.street : ""}</p>
                        </div>
                        <div>
                            <h4>{t("house number")}</h4>
                            <p>{clientAddr ? clientAddr.address.houseNumber : ""}</p>
                        </div>
                        <div>
                            <h4>{t("postal code")}</h4>
                            <p>{clientAddr ? clientAddr.address.postalCode : ""}</p>
                        </div>
                        <div>
                            <h4>{t("city")}</h4>
                            <p>{clientAddr ? clientAddr.address.city : ""}</p>
                        </div>
                        <div>
                            <h4>{t("country")}</h4>
                            <p>{clientAddr ? clientAddr.address.country : ""}</p>
                        </div>
                        <div>
                            <h4>{t("phone number")}</h4>
                            <p>{clientAddr ? clientAddr.phoneNumber : ""}</p>
                        </div>
                        <RoundedButton color="blue"
                                       onClick={handleChangAddress}
                        >{t("address change btn")}</RoundedButton>
                    </div>

                </Grid>
                <Grid item style={{display: ChangAddress ? "block" : "none"}} className={styles['change-item']}>
                    <h3>{t("address change")}</h3>
                    <div>
                        <DarkedTextField
                            type="text"
                            label={t("street")}
                            placeholder={clientAddr ? clientAddr.address.street : ""}
                            value={street}
                            onChange={event => {
                                setStreet(event.target.value)
                            }}/>
                        <DarkedTextField
                            type="text"
                            label={t("house number")}
                            placeholder={clientAddr ? clientAddr.address.houseNumber : ""}
                            value={houseNumber}
                            onChange={event => {
                                setHouseNumber(event.target.value)
                            }}/>
                        <DarkedTextField
                            type="text"
                            label={t("postal code")}
                            placeholder={clientAddr ? clientAddr.address.postalCode : ""}
                            value={postalCode}
                            onChange={event => {
                                setPostalCode(event.target.value)
                            }}/>
                        <DarkedTextField
                            type="text"
                            label={t("city")}
                            placeholder={clientAddr ? clientAddr.address.city : ""}
                            value={city}
                            onChange={event => {
                                setCity(event.target.value)
                            }}/>
                        <DarkedTextField
                            type="text"
                            label={t("country")}
                            placeholder={clientAddr ? clientAddr.address.country : ""}
                            value={country}
                            onChange={event => {
                                setCountry(event.target.value)
                            }}/>
                        <DarkedTextField
                            type="text"
                            label={t("phone number")}
                            placeholder={clientAddr ? clientAddr.phoneNumber : ""}
                            value={phoneNumber}
                            onChange={event => {
                                setPhoneNumber(event.target.value)
                            }}/>
                    </div>
                    <RoundedButton
                        color="blue"
                        onClick={changeAddress}
                    >{t("confirm")}</RoundedButton><RoundedButton
                    color="pink"
                    onClick={handleChangAddress}
                >{t("cancel")}</RoundedButton>

                </Grid>
            </Grid>
            <Grid item style={{display: acLevel.includes('BUSINESS_WORKER') ? "block" : "none"}}
                  className={styles.item}>
                <Grid item style={{display: ChangePhone ? "none" : "block"}} className={styles.item}>
                    <div>
                        <div>
                            <h4>{t("phone number")}</h4>
                            <p>{businnesPhone ? businnesPhone.phoneNumber : ""}</p>
                        </div>
                        <RoundedButton color="blue"
                                       onClick={handleChangePhone}
                        >{t("phone change btn")}</RoundedButton>
                    </div>

                </Grid>
                <Grid item style={{display: ChangePhone ? "block" : "none"}} className={styles['change-item']}>
                    <h4>{t("phone number")}</h4>
                    <div>
                        <DarkedTextField
                            type="text"
                            label={t("phone number")}
                            placeholder={businnesPhone ? businnesPhone.phoneNumber : ""}
                            value={businessPhoneNumber}
                            onChange={event => {
                                setBusinessPhoneNumber(event.target.value)
                            }}/>
                    </div>
                    <RoundedButton
                        color="blue"
                        onClick={changeBusinessPhone}
                    >{t("confirm")}</RoundedButton>
                    <RoundedButton
                        color="pink"
                        onClick={handleChangePhone}
                    >{t("cancel")}</RoundedButton>
                </Grid>

            </Grid>
            <Grid item>
                <Link to="/panels/adminPanel/accounts">
                    <RoundedButton color="pink">
                        {t("go back")}
                    </RoundedButton>
                </Link>
            </Grid>
        </Grid>
    )
}