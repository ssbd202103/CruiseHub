import {ChangeDataComponentProps} from "../interfaces";
import Grid from "@material-ui/core/Grid";
import styles from "../../styles/ManageAccount.module.css";
import RoundedButton from "../RoundedButton";
import DarkedTextField from "../DarkedTextField";
import React, {useState} from "react";
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

    const [oldPassword, setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const [confirmNewPassword, setConfirmNewPassword] = useState('')

    const [buttonPopup, setButtonPopup] = useState(false);

    const [buttonPopupAcceptAction, setButtonPopupAcceptAction] = useState(false);


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
            setOldPassword('')
            setNewPassword('')
            setConfirmNewPassword('')
            onConfirm()
            setButtonPopupAcceptAction(false)
            showSuccess(t('successful action'))
        }).catch(error => {
            setButtonPopupAcceptAction(false)
            const message = error.response.data
            handleError(t(message))
        });

    }


    const changePassword = async () => {
        setButtonPopup(true)
    }

    return (
        <>
            <Grid item  style={{display: open ? "none" : "block"}} className={styles.item}>
                <h3>{t("password")}</h3>
                <RoundedButton color="blue"
                               onClick={onOpen}
                >{t("password change btn")}</RoundedButton>
            </Grid>
            <Grid item  style={{display: open ? "block" : "none"}} className={styles['change-item']}>
                <h3>{t("password change")}</h3>
                <div>
                    <DarkedTextField
                        type="password"
                        label={t("old password")}
                        placeholder={t("old password")}
                        value={oldPassword}
                        onChange={event => {setOldPassword(event.target.value)}}/>

                    <DarkedTextField
                        type="password"
                        label={t("new password")}
                        placeholder={t("new password")}
                        value={newPassword}
                        onChange={event => {setNewPassword(event.target.value)}}/>

                    <DarkedTextField
                        type="password"
                        label={t("new password confirm")}
                        placeholder={t("new password confirm")}
                        value={confirmNewPassword}
                        onChange={event => {setConfirmNewPassword(event.target.value)}}/>
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