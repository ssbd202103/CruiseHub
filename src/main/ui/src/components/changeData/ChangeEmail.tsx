import Grid from "@material-ui/core/Grid";
import styles from "../../styles/ManageAccount.module.css";
import RoundedButton from "../RoundedButton";
import DarkedTextField from "../DarkedTextField";
import React, {createRef, useEffect, useReducer, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {changeEmail as changeEmailAction, selectEmail} from "../../redux/slices/userSlice";
import {changeEmail as changeEmailService} from "../../Services/changeEmailService";
import {useTranslation} from "react-i18next";
import {ConfirmCancelButtonGroup} from "../ConfirmCancelButtonGroup";
import {ChangeDataComponentProps} from '../interfaces'
import Recaptcha from "react-recaptcha";
import Popup from "../../PopupRecaptcha";
import {useSnackbarQueue} from "../../pages/snackbar";
import useHandleError from "../../errorHandler";
import PopupAcceptAction from "../../PopupAcceptAction";

export default function ChangeEmail({open, onOpen, onConfirm, onCancel}: ChangeDataComponentProps) {
    // i18n
    const {t} = useTranslation()
    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')
    // redux
    const dispatch = useDispatch()
    const email = useSelector(selectEmail)
    const currentSelfMTD = JSON.parse(sessionStorage.getItem("changeSelfAccountDataMta") as string)
    const [emailValue, setEmailValue] = useState('')
    const [confirmEmailValue, setConfirmEmailValue] = useState('')

    const [alterType, setAlterType] = useState('')
    const [alteredBy, setAlteredBy] = useState('')
    const [createdBy, setCreatedBy] = useState('')
    const [creationDateTime, setCreationDateTime] = useState('')
    const [lastAlterDateTime, setLastAlterDateTime] = useState('')
    const [lastCorrectAuthenticationDateTime, setLastCorrectAuthenticationDateTime] = useState('')
    const [lastCorrectAuthenticationLogicalAddress, setLastCorrectAuthenticationLogicalAddress] = useState('')
    const [lastIncorrectAuthenticationDateTime, setLastIncorrectAuthenticationDateTime] = useState('')
    const [lastIncorrectAuthenticationLogicalAddress, setLastIncorrectAuthenticationLogicalAddress] = useState('')
    const [numberOfAuthenticationFailures, setNumberOfAuthenticationFailures] = useState('')
    const [version, setVersion] = useState('')

    const handleCancel = () => {
        setEmailValue('')
        setConfirmEmailValue('')
        onCancel()
    }

    // internal state and behavior

    const [buttonPopup, setButtonPopup] = useState(false);

    const [buttonPopupAcceptAction, setButtonPopupAcceptAction] = useState(false);

    function verifyCallback(){
        setButtonPopup(false)
        if (!emailValue || !confirmEmailValue) {
            setButtonPopupAcceptAction(false)
            handleError('error.fields')
            return;
        }

        if (emailValue !== confirmEmailValue) {
            setButtonPopupAcceptAction(false)
            handleError('emails are not equal')
            return;
        }

        changeEmailService(emailValue).then(res => {
            onConfirm()
            showSuccess(t('successful action'))
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
            onCancel()
        }).finally(() => {
            setEmailValue('')
            setConfirmEmailValue('')
        });
    }



    const changeEmail = () => {
        setButtonPopup(true)
        setButtonPopupAcceptAction(false)

    }

    useEffect(() => {
        setAlterType(currentSelfMTD.alterType);
        setAlteredBy(currentSelfMTD.alteredBy);
        setCreatedBy(currentSelfMTD.createdBy);
        if(currentSelfMTD.creationDateTime !=null)
            setCreationDateTime(currentSelfMTD.creationDateTime.dayOfMonth +"/"+ currentSelfMTD.creationDateTime.month +" / "+ currentSelfMTD.creationDateTime.year +"    "+ currentSelfMTD.creationDateTime.hour +":"+ currentSelfMTD.creationDateTime.minute )
        if(currentSelfMTD.lastAlterDateTime !=null)
            setLastAlterDateTime(currentSelfMTD.lastAlterDateTime.dayOfMonth +"/"+ currentSelfMTD.lastAlterDateTime.month +" / "+ currentSelfMTD.lastAlterDateTime.year +"    "+ currentSelfMTD.lastAlterDateTime.hour +":"+ currentSelfMTD.lastAlterDateTime.minute);
        if(currentSelfMTD.lastCorrectAuthenticationDateTime !=null)
            setLastCorrectAuthenticationDateTime(currentSelfMTD.lastCorrectAuthenticationDateTime.dayOfMonth +"/"+ currentSelfMTD.lastCorrectAuthenticationDateTime.month +" / "+ currentSelfMTD.lastCorrectAuthenticationDateTime.year +"    "+ currentSelfMTD.lastCorrectAuthenticationDateTime.hour +":"+ currentSelfMTD.creationDateTime.minute);
        setLastCorrectAuthenticationLogicalAddress(currentSelfMTD.lastCorrectAuthenticationLogicalAddress)
        if(currentSelfMTD.lastIncorrectAuthenticationDateTime !=null)
            setLastIncorrectAuthenticationDateTime(currentSelfMTD.lastIncorrectAuthenticationDateTime.dayOfMonth +"/"+ currentSelfMTD.lastIncorrectAuthenticationDateTime.month +" / "+ currentSelfMTD.lastIncorrectAuthenticationDateTime.year +"    "+ currentSelfMTD.lastIncorrectAuthenticationDateTime.hour +":"+ currentSelfMTD.lastIncorrectAuthenticationDateTime.minute);
        setLastIncorrectAuthenticationLogicalAddress(currentSelfMTD.lastIncorrectAuthenticationLogicalAddress);
        setNumberOfAuthenticationFailures(currentSelfMTD.numberOfAuthenticationFailures)
        setVersion(currentSelfMTD.version);

    }, [])

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
                        type="email"
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
                <Popup trigger={buttonPopup} setTrigger={setButtonPopup}>
                    <div>
                        <Recaptcha
                            sitekey={process.env.REACT_APP_SECRET_NAME}
                            size="normal"
                            verifyCallback={verifyCallback}


                        />
                    </div>
                </Popup>
                <PopupAcceptAction
                    open={buttonPopupAcceptAction}
                    onConfirm={changeEmail}
                    onCancel={() => {setButtonPopupAcceptAction(false)
                    }}
                />
                <ConfirmCancelButtonGroup
                    onConfirm={()=>setButtonPopupAcceptAction(true)}
                    onCancel={handleCancel} />
                <tr>
                    <td><h4>{t("alterType")}</h4></td> <td><h4>{t("alteredBy")}</h4></td> <td><h4>{t("createdBy")}</h4></td>
                    <td><h4>{t("creationDateTime")}</h4></td> <td><h4>{t("lastAlterDateTime")}</h4></td> <td><h4>{t("lastCorrectAuthenticationDateTime")}</h4></td>
                    <td><h4>{t("lastCorrectAuthenticationLogicalAddress")}</h4></td> <td><h4>{t("lastIncorrectAuthenticationDateTime")}</h4></td> <td><h4>{t("lastIncorrectAuthenticationLogicalAddress")}</h4></td>
                    <td><h4>{t("numberOfAuthenticationFailures")}</h4></td> <td><h4>{t("version")}</h4></td>
                </tr>
                <tr>
                    <td><h4>{alterType}</h4></td><td><h4>{alteredBy}</h4></td><td><h4>{createdBy}</h4></td>
                    <td><h4>{creationDateTime}</h4></td><td><h4>{lastAlterDateTime}</h4></td><td><h4>{lastCorrectAuthenticationDateTime}</h4></td>
                    <td><h4>{lastCorrectAuthenticationLogicalAddress}</h4></td><td><h4>{lastIncorrectAuthenticationDateTime}</h4></td><td><h4>{lastIncorrectAuthenticationLogicalAddress}</h4></td>
                    <td><h4>{numberOfAuthenticationFailures}</h4></td><td><h4>{version}</h4></td>
                </tr>
            </Grid>
        </>
    )
}