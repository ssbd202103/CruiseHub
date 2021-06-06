import {useTranslation} from "react-i18next";
import {useSelector} from "react-redux";
import {selectFirstName, selectPhoneNumber, selectSecondName} from "../../redux/slices/userSlice";
import React, {useEffect, useState} from "react";
import Grid from "@material-ui/core/Grid";
import styles from "../../styles/ManageAccount.module.css";
import RoundedButton from "../RoundedButton";
import DarkedTextField from "../DarkedTextField";
import {ConfirmCancelButtonGroup} from "../ConfirmCancelButtonGroup";
import {changeBusinessWorkerData} from "../../Services/changeDataService";
import Recaptcha from "react-recaptcha";
import Popup from "../../PopupRecaptcha";
import {useSnackbarQueue} from "../../pages/snackbar";
import useHandleError from "../../errorHandler";
import PopupAcceptAction from "../../PopupAcceptAction";

export interface ChangeBusinessWorkerProps {
    open: boolean,
    onOpen(): void,
    onConfirm(): void,
    onCancel(): void
}

export default function ChangeBusinessWorkerData({open, onOpen, onConfirm, onCancel}: ChangeBusinessWorkerProps) {
    const {t} = useTranslation()

    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')
    const currentSelfMTD = JSON.parse(sessionStorage.getItem("changeSelfAccountDataMta") as string)

    const firstName = useSelector(selectFirstName)
    const secondName = useSelector(selectSecondName)
    const phoneNumber = useSelector(selectPhoneNumber("BUSINESS_WORKER"))

    const [firstNameValue, setFirstNameValue] = useState(firstName)
    const [secondNameValue, setSecondNameValue] = useState(secondName)
    const [phoneNumberValue, setPhoneNumberValue] = useState(phoneNumber)

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

    const [buttonPopup, setButtonPopup] = useState(false);

    const handleErase = () => {
        setFirstNameValue(firstName)
        setSecondNameValue(secondName)
        setPhoneNumberValue(phoneNumber)
    }

    const handleCancel = () => {
        handleErase()
        onCancel()
    }

    function verifyCallback(){
        setButtonPopup(false)
        if (!firstNameValue || !secondNameValue || !phoneNumberValue) {
            handleError('error.fields')
            return
        }

        changeBusinessWorkerData(firstNameValue, secondNameValue, phoneNumberValue).then(res => {
            onConfirm()
            showSuccess(t('successful action'))
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
            onCancel()
        });
    }

    useEffect(() => {
        setFirstNameValue(firstName)
        setSecondNameValue(secondName)
        setPhoneNumberValue(phoneNumber)

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

    }, [firstName, secondName, phoneNumber])

    const changeData = () => {
        //TODO
        setButtonPopup(true)
        setButtonPopupAcceptAction(false)
    }

    return (
        <>
            <Grid item style={{display: open ? "none" : "block"}} className={styles.item}>
                <h3>{t("personal data")}</h3>
                <div>
                    <div>
                        <h4>{t("name")}</h4>
                        <p>{firstName}</p>
                    </div>
                    <div>
                        <h4>{t("surname")}</h4>
                        <p>{secondName}</p>
                    </div>
                    <div>
                        <h4>{t("phone number")}</h4>
                        <p>{phoneNumber}</p>
                    </div>

                    <RoundedButton
                        color="blue"
                        onClick={onOpen}
                    >{t("personal data change btn")}</RoundedButton>
                </div>
            </Grid>

            <Grid item style={{display: open ? "block" : "none"}} className={styles['change-item']}>
                <h3>{t("personal data change")}</h3>
                <div>
                    <DarkedTextField
                        type="text"
                        label={t("name")}
                        placeholder={t("nameExample")}
                        value={firstNameValue}
                        onChange={event => {setFirstNameValue(event.target.value)}}/>
                    <DarkedTextField
                        type="text"
                        label={t("surname")}
                        placeholder={t("surnameExample")}
                        value={secondNameValue}
                        onChange={event => {setSecondNameValue(event.target.value)}}/>
                    <DarkedTextField
                        type="text"
                        label={t("phone")}
                        placeholder={t("phoneNumberExample")}
                        value={phoneNumberValue}
                        onChange={event => {setPhoneNumberValue(event.target.value)}}/>
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
                    onConfirm={changeData}
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
                    <td><h4>{t(alterType)}</h4></td><td><h4>{alteredBy}</h4></td><td><h4>{createdBy}</h4></td>
                    <td><h4>{creationDateTime}</h4></td><td><h4>{lastAlterDateTime}</h4></td><td><h4>{lastCorrectAuthenticationDateTime}</h4></td>
                    <td><h4>{lastCorrectAuthenticationLogicalAddress}</h4></td><td><h4>{lastIncorrectAuthenticationDateTime}</h4></td><td><h4>{lastIncorrectAuthenticationLogicalAddress}</h4></td>
                    <td><h4>{numberOfAuthenticationFailures}</h4></td><td><h4>{version}</h4></td>
                </tr>
            </Grid>
        </>
    )
}