import {useTranslation} from "react-i18next";
import {useSelector} from "react-redux";
import {selectFirstName, selectSecondName} from "../../redux/slices/userSlice";
import React, {useEffect, useState} from "react";
import Grid from "@material-ui/core/Grid";
import styles from "../../styles/ManageAccount.module.css";
import RoundedButton from "../RoundedButton";
import DarkedTextField from "../DarkedTextField";
import {ChangeDataComponentProps} from '../interfaces'
import {ConfirmCancelButtonGroup} from "../ConfirmCancelButtonGroup";
import {changeModeratorData} from "../../Services/changeDataService";
import Recaptcha from "react-recaptcha";
import Popup from "../../PopupRecaptcha";
import {useSnackbarQueue} from "../../pages/snackbar";
import useHandleError from "../../errorHandler";
import PopupAcceptAction from "../../PopupAcceptAction";

export default function ChangeModeratorData({open, onOpen, onConfirm, onCancel}: ChangeDataComponentProps) {
    const {t} = useTranslation()

    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')
    const firstName = useSelector(selectFirstName)
    const secondName = useSelector(selectSecondName)

    const [firstNameValue, setFirstNameValue] = useState(firstName)
    const [secondNameValue, setSecondNameValue] = useState(secondName)

    const [buttonPopupAcceptAction, setButtonPopupAcceptAction] = useState(false);

    const [buttonPopup, setButtonPopup] = useState(false);

    const handleErase = () => {
        setFirstNameValue(firstName)
        setSecondNameValue(secondName)
    }

    const handleCancel = () => {
        handleErase()
        onCancel()
    }

    function verifyCallback(){
        setButtonPopup(false)
        if (!firstNameValue || !secondNameValue) {
            handleError('error.fields')
            return;
        }

        changeModeratorData(firstNameValue, secondNameValue).then(res => {
            onConfirm()
            showSuccess(t('successful action'))
        }).catch(error => {
            handleErase()
            const message = error.response.data
            handleError(message, error.response.status)
            onCancel()
        });
    }


    useEffect(() => {
        setFirstNameValue(firstName)
        setSecondNameValue(secondName)
    }, [firstName, secondName])

    const changeData = () => {
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
                        onChange={event => {setFirstNameValue(event.target.value)}} />
                    <DarkedTextField
                        type="text"
                        label={t("surname")}
                        placeholder="Blazymur"
                        value={secondNameValue}
                        onChange={event => {setSecondNameValue(event.target.value)}} />
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
            </Grid>
        </>
)
}