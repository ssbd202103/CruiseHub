import Grid from "@material-ui/core/Grid";
import styles from "../../styles/ManageAccount.module.css";
import RoundedButton from "../RoundedButton";
import DarkedTextField from "../DarkedTextField";
import React, {createRef, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {changeEmail as changeEmailAction, selectEmail} from "../../redux/slices/userSlice";
import {changeEmail as changeEmailService} from "../../Services/changeEmailService";
import {useTranslation} from "react-i18next";
import {ConfirmCancelButtonGroup} from "../ConfirmCancelButtonGroup";
import {ChangeDataComponentProps} from '../interfaces'

export default function ChangeEmail({open, onOpen, onConfirm, onCancel}: ChangeDataComponentProps) {
    // i18n
    const {t} = useTranslation()

    // redux
    const dispatch = useDispatch()
    const email = useSelector(selectEmail)

    // internal state and behavior

    const [emailValue, setEmailValue] = useState('')
    const [confirmEmailValue, setConfirmEmailValue] = useState('')

    const changeEmail = () => {
        if (!emailValue || !confirmEmailValue) {
            return alert("Fill up required fields")
        }

        if (emailValue !== confirmEmailValue) {
            //TODO
            return alert("FATAL: emails are not equal")
        }

        changeEmailService(emailValue).then(res => {
            setEmailValue('')
            setConfirmEmailValue('')
        })
            .catch(error => {
            //TODO
            alert('ERROR. Go to the console')
            console.log(error)
        })

        onConfirm()
    }

    return (
        <>
            <Grid item  style={{display: open ? "none" : "block"}} className={styles.item}>
                <h3>{t("email")}</h3>
                <div>
                    <p>{email}</p>
                    <RoundedButton
                        color="blue"
                        onClick={onOpen}
                    >{t("email change btn")}</RoundedButton>
                </div>
            </Grid>
            <Grid item  style={{display: open ? "block" : "none"}} className={styles['change-item']}>
                <h3>{t("email change")}</h3>
                <div>
                    <DarkedTextField
                        type="text"
                        label={t("new email")}
                        placeholder={t("new email")}
                        value={emailValue}
                        onChange={event => {setEmailValue(event.target.value)}}/>
                    <DarkedTextField
                        type="text"
                        label={t("new email confirm")}
                        placeholder={t("new email confirm")}
                        value={confirmEmailValue}
                        onChange={event => {setConfirmEmailValue(event.target.value)}}/>
                </div>

                <ConfirmCancelButtonGroup
                    onConfirm={changeEmail}
                    onCancel={onCancel} />
            </Grid>
        </>
    )
}