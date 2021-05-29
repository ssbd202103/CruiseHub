import Grid from "@material-ui/core/Grid";
import styles from "../../styles/ManageAccount.module.css";
import RoundedButton from "../RoundedButton";
import DarkedTextField from "../DarkedTextField";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {useSelector} from "react-redux";
import {selectFirstName, selectPhoneNumber, selectSecondName,} from '../../redux/slices/userSlice'
import {ChangeDataComponentProps} from "../interfaces";
import {ConfirmCancelButtonGroup} from "../ConfirmCancelButtonGroup";
import {changeClientData} from "../../Services/changeDataService";
import Recaptcha from "react-recaptcha";
import Popup from "../../PopupRecaptcha";
import {useSnackbarQueue} from "../../pages/snackbar";
import useHandleError from "../../errorHandler";
import PopupAcceptAction from "../../PopupAcceptAction";

export default function ChangeClientData({open, onOpen, onConfirm, onCancel}: ChangeDataComponentProps) {
    const {t} = useTranslation()

    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')

    const firstName = useSelector(selectFirstName)
    const secondName = useSelector(selectSecondName)
    const phoneNumber = useSelector(selectPhoneNumber("CLIENT"))

    const [firstNameValue, setFirstNameValue] = useState('')
    const [secondNameValue, setSecondNameValue] = useState('')
    const [phoneNumberValue, setPhoneNumberValue] = useState('')

    const [buttonPopupAcceptAction, setButtonPopupAcceptAction] = useState(false);

    const [buttonPopup, setButtonPopup] = useState(false);

    const handleCancel = () => {
        setFirstNameValue('')
        setSecondNameValue('')
        setPhoneNumberValue('')
        onCancel()
    }

    function verifyCallback(){
        setButtonPopup(false)
        if (!firstNameValue || !secondNameValue || !phoneNumberValue) {
            handleError('error.fields')
            return;
        }

        changeClientData(firstNameValue, secondNameValue, phoneNumberValue).then(res => {
            setFirstNameValue('')
            setSecondNameValue('')
            setPhoneNumberValue('')
            onConfirm()
            setButtonPopupAcceptAction(false)
            showSuccess(t('successful action'))
        }).catch(error => {
            setButtonPopupAcceptAction(false)
            const message = error.response.data
            handleError(message, error.response.status)
        });

    }

    useEffect(() => {
        setFirstNameValue(firstName)
        setSecondNameValue(secondName)
        setPhoneNumberValue(phoneNumber)
    }, [firstName, secondName, phoneNumber])

    const changeData = () => {
        //TODO
        setButtonPopup(true)
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
                        placeholder="Michał"
                        value={firstNameValue}
                        onChange={event => {setFirstNameValue(event.target.value)}}/>
                    <DarkedTextField
                        type="text"
                        label={t("surname")}
                        placeholder="Blazymur"
                        value={secondNameValue}
                        onChange={event => {setSecondNameValue(event.target.value)}}/>
                    <DarkedTextField
                        type="text"
                        label={t("phone")}
                        placeholder="+48-767-765-456"
                        value={phoneNumberValue}
                        onChange={event => {setPhoneNumberValue(event.target.value)}}/>
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
                </div>

                <ConfirmCancelButtonGroup
                    onConfirm={()=>setButtonPopupAcceptAction(true)}
                    onCancel={handleCancel} />
            </Grid>
        </>
    )
}