import {ChangeDataComponentProps} from "../interfaces";
import Grid from "@material-ui/core/Grid";
import styles from "../../styles/ManageAccount.module.css";
import RoundedButton from "../RoundedButton";
import DarkedTextField from "../DarkedTextField";
import React, {useEffect, useState} from "react";
import {changeOwnPassword as changeOwnPasswordService} from "../../Services/changePasswordService";
import {useTranslation} from "react-i18next";
import {ConfirmCancelButtonGroup} from "../ConfirmCancelButtonGroup";
import Recaptcha from "react-recaptcha";
import Popup from "../../PopupRecaptcha";
import {useSnackbarQueue} from "../../pages/snackbar";
import PopupAcceptAction from "../../PopupAcceptAction";
import useHandleError from "../../errorHandler";

export default function ChangePassword({open, onOpen, onConfirm, onCancel}: ChangeDataComponentProps) {
    const {t} = useTranslation()

    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')

    const currentSelfMTD = JSON.parse(sessionStorage.getItem("changeSelfAccountDataMta") as string)
    const [oldPassword, setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const [confirmNewPassword, setConfirmNewPassword] = useState('')

    const [buttonPopup, setButtonPopup] = useState(false);

    const [buttonPopupAcceptAction, setButtonPopupAcceptAction] = useState(false);

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
        setOldPassword('')
        setNewPassword('')
        setConfirmNewPassword('')
        onCancel()
    }

    async function verifyCallback() {
        setButtonPopup(false)
        if (!oldPassword || !newPassword || !confirmNewPassword) {
            handleError('error.fields')
            return;
        }

        if (newPassword != confirmNewPassword) {
            handleError('passwords are not equal');
            return;
        }


        changeOwnPasswordService(oldPassword, newPassword).then(res => {
            setConfirmNewPassword('')
            onConfirm()
            showSuccess(t('successful action'))
        }).catch(error => {
            const message = error.response.data
            try {
                handleError(JSON.parse(message),error.response.status);
            } catch (e) {
                handleError(message, error.response.status)
            }
            onCancel()
        }).finally(() => {
            setOldPassword('')
            setNewPassword('')
        });

    }


    const changePassword = async () => {
        setButtonPopup(true)
        setButtonPopupAcceptAction(false)
    }

    useEffect(() => {
        setAlterType(currentSelfMTD.alterType);
        setAlteredBy(currentSelfMTD.alteredBy);
        setCreatedBy(currentSelfMTD.createdBy);
        if(currentSelfMTD.creationDateTime !=null)
            setCreationDateTime(currentSelfMTD.creationDateTime.dayOfMonth +"/"+ t(currentSelfMTD.creationDateTime.month) +" / "+ currentSelfMTD.creationDateTime.year +"    "+ currentSelfMTD.creationDateTime.hour +":"+ currentSelfMTD.creationDateTime.minute )
        if(currentSelfMTD.lastAlterDateTime !=null)
            setLastAlterDateTime(currentSelfMTD.lastAlterDateTime.dayOfMonth +"/"+ t(currentSelfMTD.lastAlterDateTime.month) +" / "+ currentSelfMTD.lastAlterDateTime.year +"    "+ currentSelfMTD.lastAlterDateTime.hour +":"+ currentSelfMTD.lastAlterDateTime.minute);
        if(currentSelfMTD.lastCorrectAuthenticationDateTime !=null)
            setLastCorrectAuthenticationDateTime(currentSelfMTD.lastCorrectAuthenticationDateTime.dayOfMonth +"/"+ t(currentSelfMTD.lastCorrectAuthenticationDateTime.month) +" / "+ currentSelfMTD.lastCorrectAuthenticationDateTime.year +"    "+ currentSelfMTD.lastCorrectAuthenticationDateTime.hour +":"+ currentSelfMTD.creationDateTime.minute);
        setLastCorrectAuthenticationLogicalAddress(currentSelfMTD.lastCorrectAuthenticationLogicalAddress)
        if(currentSelfMTD.lastIncorrectAuthenticationDateTime !=null)
            setLastIncorrectAuthenticationDateTime(currentSelfMTD.lastIncorrectAuthenticationDateTime.dayOfMonth +"/"+ t(currentSelfMTD.lastIncorrectAuthenticationDateTime.month) +" / "+ currentSelfMTD.lastIncorrectAuthenticationDateTime.year +"    "+ currentSelfMTD.lastIncorrectAuthenticationDateTime.hour +":"+ currentSelfMTD.lastIncorrectAuthenticationDateTime.minute);
        setLastIncorrectAuthenticationLogicalAddress(currentSelfMTD.lastIncorrectAuthenticationLogicalAddress);
        setNumberOfAuthenticationFailures(currentSelfMTD.numberOfAuthenticationFailures)
        setVersion(currentSelfMTD.version);

    }, [])


    return (
        <>
            <Grid item style={{display: open ? "none" : "block"}} className={styles.item}>
                <RoundedButton color="blue"
                               onClick={onOpen}
                >{t("password change btn")}</RoundedButton>
            </Grid>
            <Grid item style={{display: open ? "block" : "none"}} className={styles['change-item']}>
                <h3>{t("password change")}</h3>
                <div>
                    <DarkedTextField
                        type="password"
                        label={t("old password")}
                        placeholder={t("old password")}
                        value={oldPassword}
                        onChange={event => {
                            setOldPassword(event.target.value)
                        }}/>

                    <DarkedTextField
                        type="password"
                        label={t("new password")}
                        placeholder={t("new password")}
                        value={newPassword}
                        onChange={event => {
                            setNewPassword(event.target.value)
                        }}/>

                    <DarkedTextField
                        type="password"
                        label={t("new password confirm")}
                        placeholder={t("new password confirm")}
                        value={confirmNewPassword}
                        onChange={event => {
                            setConfirmNewPassword(event.target.value)
                        }}/>
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
                    onConfirm={changePassword}
                    onCancel={() => {
                        setButtonPopupAcceptAction(false)
                    }}
                />
                <ConfirmCancelButtonGroup
                    onConfirm={() => setButtonPopupAcceptAction(true)}
                    onCancel={handleCancel}/>
                <tr>
                    <td><h4>{t("alterType")}</h4></td> <td><h4>{t("alteredBy")}</h4></td> <td><h4>{t("createdBy")}</h4></td>
                    <td><h4>{t("creationDateTime")}</h4></td> <td><h4>{t("lastAlterDateTime")}</h4></td> <td><h4>{t("lastCorrectAuthenticationDateTime")}</h4></td>
                    <td><h4>{t("lastCorrectAuthenticationLogicalAddress")}</h4></td> <td><h4>{t("lastIncorrectAuthenticationDateTime")}</h4></td> <td><h4>{t("lastIncorrectAuthenticationLogicalAddress")}</h4></td>
                    <td><h4>{t("numberOfAuthenticationFailures")}</h4></td> <td><h4>{t("version")}</h4></td>
                </tr>
                <tr>
                    <td><h4>{t(alterType)}</h4></td><td><h4>{alteredBy}</h4></td><td><h4>{createdBy}</h4></td>
                    <td><h4>{creationDateTime}</h4></td><td><h4>{lastAlterDateTime}</h4></td><td><h4>{lastCorrectAuthenticationDateTime}</h4></td>
                    <td><h4>{lastCorrectAuthenticationLogicalAddress}</h4></td><td><h4>{lastIncorrectAuthenticationDateTime}</h4></td><td><h4>{lastIncorrectAuthenticationLogicalAddress}</h4></td>
                    <td><h4>{numberOfAuthenticationFailures}</h4></td><td><h4>{version}</h4></td>
                </tr>
            </Grid>
        </>
    )
}