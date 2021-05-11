import React, {createRef, useReducer, useState} from 'react'

import Grid from '@material-ui/core/Grid'

import {Link} from 'react-router-dom'

import {useTranslation} from 'react-i18next';

import styles from '../../../styles/ManageAccount.module.css'
import RoundedButton from '../../../components/RoundedButton';
import DarkedTextField from '../../../components/DarkedTextField';
import axios from "axios";

export default function ChangeAccountData() {
    const {t} = useTranslation()
    const [, forceUpdate] = useReducer(x => x + 1, 0); // used to force component refresh on forceUpdate call
    const [ChangePerData, setPerData] = useState(false)
    const [ChangAddress, setChangChangAddress] = useState(false)
    const [ChangePhone, setChangePhone] = useState(false)

    const firstNameRef = createRef() as React.RefObject<HTMLDivElement>
    const secondNameRef = createRef() as React.RefObject<HTMLDivElement>
    const emailRef = createRef() as React.RefObject<HTMLDivElement>
    const phoneNumberRef = createRef() as React.RefObject<HTMLDivElement>
    const businnesPhoneNumberRef = createRef() as React.RefObject<HTMLDivElement>
    const streetRef = createRef() as React.RefObject<HTMLDivElement>
    const houseNumberRef = createRef() as React.RefObject<HTMLDivElement>
    const postalCodeRef = createRef() as React.RefObject<HTMLDivElement>
    const cityRef = createRef() as React.RefObject<HTMLDivElement>
    const countryRef = createRef() as React.RefObject<HTMLDivElement>


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
            newFirstName: firstNameRef?.current?.querySelector('input')?.value,
            newSecondName: secondNameRef?.current?.querySelector('input')?.value,
            newEmail: emailRef?.current?.querySelector('input')?.value,
            version: currentAccount.version,
            alteredBy: currentAccount.login //temporary until roles are implemented.

        })
        await fetch("http://localhost:8080/api/account/changeOtherData", {
            method: "PUT",
            mode: "same-origin",
            body: json,
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag
            }
        })
        const result = await axios.get(`http://localhost:8080/api/account/details-view/${currentAccount.login}`);
        sessionStorage.setItem("changeAccountData", JSON.stringify(result.data));
        forceUpdate()
        handleChangePerData()
    }


    const changeAddress = async () => {
        const json = JSON.stringify({
            login: currentAccount.login,
            version: currentAccount.version,
            newFirstName: currentAccount.firstName,
            newSecondName: currentAccount.secondName,
            newEmail: currentAccount.email,
            newPhoneNumber: phoneNumberRef?.current?.querySelector('input')?.value,
            newAddress: {
                newHouseNumber: houseNumberRef?.current?.querySelector('input')?.value,
                newStreet: streetRef?.current?.querySelector('input')?.value,
                newPostalCode: postalCodeRef?.current?.querySelector('input')?.value,
                newCity: cityRef?.current?.querySelector('input')?.value,
                newCountry: countryRef?.current?.querySelector('input')?.value,
                alteredBy: currentAccount.login //temporary until roles are implemented.
            },
            alteredBy: currentAccount.login //temporary until roles are implemented.

        })
        await fetch("http://localhost:8080/api/account/changeOtherData/client", {
            method: "PUT",
            mode: "same-origin",
            body: json,
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag
            }
        })
        const result = await axios.get(`http://localhost:8080/api/account/details-view/${currentAccount.login}`);
        sessionStorage.setItem("changeAccountData", JSON.stringify(result.data));
        forceUpdate()
        handleChangAddress()
    }
    const changeBusinessPhone = async () => {
        const json = JSON.stringify({
            login: currentAccount.login,
            version: currentAccount.version,
            newFirstName: currentAccount.firstName,
            newSecondName: currentAccount.secondName,
            newEmail: currentAccount.email,
            newPhoneNumber: businnesPhoneNumberRef?.current?.querySelector('input')?.value,
            alteredBy: currentAccount.login //temporary until roles are implemented.

        })
        await fetch("http://localhost:8080/api/account/changeOtherData/businessworker", {
            method: "PUT",
            mode: "same-origin",
            body: json,
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag
            }
        })
        const result = await axios.get(`http://localhost:8080/api/account/details-view/${currentAccount.login}`);
        sessionStorage.setItem("changeAccountData", JSON.stringify(result.data));
        forceUpdate()
        handleChangePhone()
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
                        ref={firstNameRef}/>
                    <DarkedTextField
                        type="text"
                        label={t("surname")}
                        placeholder={currentAccount.secondName}
                        ref={secondNameRef}/>

                    <DarkedTextField
                        type="text"
                        label={t("new email")}
                        placeholder={t(currentAccount.email)}
                        ref={emailRef}/>

                </div>
                <RoundedButton color="blue"
                               onClick={changePersonalData}
                >{t("confirm")}</RoundedButton>
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
                            ref={streetRef}/>
                        <DarkedTextField
                            type="text"
                            label={t("house number")}
                            placeholder={clientAddr ? clientAddr.address.houseNumber : ""}
                            ref={houseNumberRef}/>
                        <DarkedTextField
                            type="text"
                            label={t("postal code")}
                            placeholder={clientAddr ? clientAddr.address.postalCode : ""}
                            ref={postalCodeRef}/>
                        <DarkedTextField
                            type="text"
                            label={t("city")}
                            placeholder={clientAddr ? clientAddr.address.city : ""}
                            ref={cityRef}/>
                        <DarkedTextField
                            type="text"
                            label={t("country")}
                            placeholder={clientAddr ? clientAddr.address.country : ""}
                            ref={countryRef}/>
                        <DarkedTextField
                            type="text"
                            label={t("phone number")}
                            placeholder={clientAddr ? clientAddr.phoneNumber : ""}
                            ref={phoneNumberRef}/>
                    </div>
                    <RoundedButton
                        color="blue"
                        onClick={changeAddress}
                    >{t("confirm")}</RoundedButton>
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
                            ref={businnesPhoneNumberRef}/>
                    </div>
                    <RoundedButton
                        color="blue"
                        onClick={changeBusinessPhone}
                    >{t("confirm")}</RoundedButton>
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