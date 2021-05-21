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

export default function ChangeModeratorData({open, onOpen, onConfirm, onCancel}: ChangeDataComponentProps) {
    const {t} = useTranslation()

    const firstName = useSelector(selectFirstName)
    const secondName = useSelector(selectSecondName)

    const [firstNameValue, setFirstNameValue] = useState('')
    const [secondNameValue, setSecondNameValue] = useState('')

    const [buttonPopup, setButtonPopup] = useState(false);

    function verifyCallback(){
        //TODO
        setButtonPopup(false)
        if (!firstNameValue || !secondNameValue) {
            return alert("Values are missing")
        }

        changeModeratorData(firstNameValue, secondNameValue).then(res => {
            onConfirm()
        }).catch(error => {
            alert("ERROR: go to console")
            console.log(error)
        })

    }


    useEffect(() => {
        setFirstNameValue(firstName)
        setSecondNameValue(secondName)
    }, [firstName, secondName])

    const changeData = () => {

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
                <ConfirmCancelButtonGroup
                    onConfirm={changeData}
                    onCancel={onCancel} />
            </Grid>
        </>
)
}