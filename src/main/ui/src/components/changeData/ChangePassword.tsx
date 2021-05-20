import {ChangeDataComponentProps} from "../interfaces";
import Grid from "@material-ui/core/Grid";
import styles from "../../styles/ManageAccount.module.css";
import RoundedButton from "../RoundedButton";
import DarkedTextField from "../DarkedTextField";
import React, {useState} from "react";
import {changeOwnPassword as changeOwnPasswordService} from "../../Services/changePasswordService";
import {useTranslation} from "react-i18next";
import {ConfirmCancelButtonGroup} from "../ConfirmCancelButtonGroup";

export default function ChangePassword({open, onOpen, onConfirm, onCancel}: ChangeDataComponentProps) {
    const {t} = useTranslation()

    const [oldPassword, setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const [confirmNewPassword, setConfirmNewPassword] = useState('')

    const changePassword = async () => {
        if (!oldPassword || !newPassword || !confirmNewPassword) {
            //TODO
            return alert("FATAL: fields or values are missing")
        }

        if (newPassword != confirmNewPassword) {
            //TODO
            return alert("FATAL: new passwords are not equal")
        }

        await changeOwnPasswordService(oldPassword, newPassword)

        onConfirm()

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
                <ConfirmCancelButtonGroup
                    onConfirm={changePassword}
                    onCancel={onCancel} />
            </Grid>
        </>
    )
}