import React, {createRef, useState} from 'react'

import Grid from '@material-ui/core/Grid'

import {useTranslation} from 'react-i18next';

import styles from '../../../styles/ManageAccount.module.css'
import RoundedButton from '../../../components/RoundedButton';
import DarkedTextField from '../../../components/DarkedTextField';
import {useDispatch, useSelector} from "react-redux";
import {selectColor} from "../../../redux/slices/colorSlice";
import {
    changeEmail as changeEmailAction,
    selectEmail,
} from "../../../redux/slices/userSlice";
import { changeEmail as changeEmailService } from '../../../Services/changeEmailService';
import { changeOwnPassword as changeOwnPasswordService } from "../../../Services/changePasswordService";

export default function ManageAccount() {
    const {t} = useTranslation()

    const dispatch = useDispatch()

    const email = useSelector(selectEmail)

    const emailRef = createRef() as React.RefObject<HTMLDivElement>
    const confirmEmailRef = createRef() as React.RefObject<HTMLDivElement>
    const oldPasswordRef = createRef() as React.RefObject<HTMLDivElement>
    const newPasswordRef = createRef() as React.RefObject<HTMLDivElement>
    const newPasswordConfirmRef = createRef() as React.RefObject<HTMLDivElement>

    const color = useSelector(selectColor)

    const [ChangePerData, setPerData] = useState(false)
    const [ChangAddress, setChangChangAddress] = useState(false)
    const [ChangEmail, setChangEmail] = useState(false)
    const [ChangPasswd, setChangPasswd] = useState(false)

    //Functions for personal data change
    const handleChangePerData = () => {
        setPerData(state => !state)
        setChangChangAddress( false)
        setChangEmail( false)
        setChangPasswd(false)
    }
    const changePersonalData = () => {
        //Place for transfer function (change personal data in database)
        handleChangePerData()
    }

    //Functions for address data change
    const handleChangAddress = () => {
        setChangChangAddress(state => !state)
        setPerData(false)
        setChangEmail(false)
        setChangPasswd( false)
    }
    const changeAddress = () => {
        //Place for transfer function (change address in database)
        handleChangAddress()
    }

    //Functions for email data change
    const handleChangEmail = () => {
        setChangEmail(state => !state)
        setPerData(false)
        setChangChangAddress(false)
        setChangPasswd(false)
    }
    const changeEmail = () => {
        const emailValue = emailRef?.current?.querySelector('input')?.value
        const confirmEmailValue = confirmEmailRef?.current?.querySelector('input')?.value

        if (!emailValue || !confirmEmailValue) {
            //TODO
            return alert("FATAL: fields or values are missing")
        }

        if (emailValue !== confirmEmailValue) {
            //TODO
            return alert("FATAL: emails are not equal")
        }

        changeEmailService(emailValue).then(res => {
            dispatch(changeEmailAction(emailValue))
        }).catch(error => {
            //TODO
            alert('ERROR. Go to the console')
            console.log(error)
        })

        setChangEmail(state => !state)
    }

    //Functions for password
    const handleChangPasswd = () => {
        setChangPasswd(state => !state)
        setPerData( false)
        setChangChangAddress(false)
        setChangEmail( false)
    }
    const changePassword = async () => {
        const oldPasswordValue = oldPasswordRef?.current?.querySelector('input')?.value
        const newPasswordValue = newPasswordRef?.current?.querySelector('input')?.value
        const newPasswordConfirmValue = newPasswordConfirmRef?.current?.querySelector('input')?.value

        if (!oldPasswordValue || !newPasswordValue || !newPasswordConfirmValue) {
            //TODO
            return alert("FATAL: fields or values are missing")
        }

        if (newPasswordValue != newPasswordConfirmValue) {
            //TODO
            return alert("FATAL: new passwords are not equal")
        }

        await changeOwnPasswordService(oldPasswordValue, newPasswordValue)

        handleChangPasswd()
    }

    return (
        <Grid container className={styles.wrapper + ' ' + styles[`text-${color ? 'white' : 'dark'}`]} >
                <Grid item style={{display: ChangePerData ? "none" : "block"}} className={styles.item}>
                    <h3>{t("personal data")}</h3>
                    <div>
                        <div>
                            <h4>{t("name")}</h4>
                            <p>Michał</p>
                        </div>
                        <div>                        
                            <h4>{t("surname")}</h4>
                            <p>Blazymur</p>
                        </div>
                        <div>                        
                            <h4>{t("Login")}</h4>
                            <p>BigMike</p>
                        </div>
                        <div>                                            
                            <h4>{t("phone number")}</h4>
                            <p>+48-767-765-456</p>
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
                            placeholder="Michał" />
                        <DarkedTextField 
                            type="text" 
                            label={t("surname")}
                            placeholder="Blazymur"/>
                        <DarkedTextField 
                            type="text" 
                            label="Login"
                            placeholder="BigMike"/>
                        <DarkedTextField 
                            type="text" 
                            label={t("phone")}
                            placeholder="+48-767-765-456"/>
                    </div>
                    <RoundedButton color="blue"
                                onClick={changePersonalData}
                        >{t("confirm")}</RoundedButton>
                </Grid>

                <Grid item  style={{display: ChangAddress ? "none" : "block"}} className={styles.item}>
                    <h3>{t("address")}</h3>
                    <div>
                        <div>
                            <h4>{t("street")}</h4>
                            <p>11-Listopada 28</p>
                        </div>
                        <div>
                            <h4>{t("house number")}</h4>
                            <p>23</p>
                        </div>
                        <div>
                            <h4>{t("postal code")}</h4>
                            <p>91-345</p>
                        </div>
                        <div>
                            <h4>{t("city")}</h4>
                            <p>Zakopianka</p>
                        </div>
                        <div>
                            <h4>{t("country")}</h4>
                            <p>Zanzibar</p>
                        </div>
                    <RoundedButton color="blue"
                            onClick={handleChangAddress}
                    >{t("address change btn")}</RoundedButton>
                    </div>
                </Grid>
                <Grid item  style={{display:ChangAddress  ? "block" : "none"}} className={styles['change-item']}>
                    <h3>{t("address change")}</h3>
                    <div>
                    <DarkedTextField 
                        type="text" 
                        label={t("street")}
                        placeholder="11-Listopada 28" />
                    <DarkedTextField 
                        type="text" 
                        label={t("house number")}
                        placeholder="23" />
                    <DarkedTextField 
                        type="text" 
                        label={t("postal code")}
                        placeholder="91-345" />
                    <DarkedTextField 
                        type="text" 
                        label={t("city")}
                        placeholder="Zakopianka" />
                    <DarkedTextField 
                        type="text"
                        label={t("country")} 
                        placeholder="Zanzibar" />
                    </div>

                    <RoundedButton 
                        color="blue"
                        onClick={changeAddress}
                    >{t("confirm")}</RoundedButton>
                </Grid>

                <Grid item  style={{display: ChangEmail ? "none" : "block"}} className={styles.item}>
                    <h3>{t("email")}</h3>
                    <div>
                        <p>{email}</p>
                        <RoundedButton 
                            color="blue"
                            onClick={handleChangEmail}
                        >{t("email change btn")}</RoundedButton>
                    </div>
                </Grid>
                <Grid item  style={{display: ChangEmail ? "block" : "none"}} className={styles['change-item']}>
                    <h3>{t("email change")}</h3>
                    <div>
                        <DarkedTextField 
                            type="text"
                            label={t("new email")} 
                            placeholder={t("new email")}
                            ref={emailRef} />
                        <DarkedTextField 
                            type="text" 
                            label={t("new email confirm")}
                            placeholder={t("new email confirm")}
                            ref={confirmEmailRef} />
                    </div>
                    <RoundedButton 
                        color="blue"
                        onClick={changeEmail}
                    >{t("confirm")}</RoundedButton>
                </Grid>

                <Grid item  style={{display: ChangPasswd ? "none" : "block"}} className={styles.item}>
                    <h3>{t("password")}</h3>
                    <RoundedButton color="blue"
                            onClick={handleChangPasswd}
                    >{t("password change btn")}</RoundedButton>
                </Grid>
                <Grid item  style={{display: ChangPasswd ? "block" : "none"}} className={styles['change-item']}>
                    <h3>{t("password change")}</h3>
                    <div>
                        <DarkedTextField 
                            type="password"
                            label={t("old password")}
                            placeholder={t("old password")}
                            ref={oldPasswordRef} />

                        <DarkedTextField
                            type="password" 
                            label={t("new password")}
                            placeholder={t("new password")}
                            ref={newPasswordRef} />

                        <DarkedTextField 
                            type="password" 
                            label={t("new password confirm")}
                            placeholder={t("new password confirm")}
                            ref={newPasswordConfirmRef} />
                    </div>
                    <RoundedButton 
                        color="blue"
                        onClick={changePassword}
                    >{t("confirm")}</RoundedButton>
                    
                </Grid>
        </Grid>
    )
}