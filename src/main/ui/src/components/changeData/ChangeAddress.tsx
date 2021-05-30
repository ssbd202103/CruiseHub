import {ChangeDataComponentProps} from '../interfaces'
import Grid from "@material-ui/core/Grid";
import styles from "../../styles/ManageAccount.module.css";
import RoundedButton from "../RoundedButton";
import DarkedTextField from "../DarkedTextField";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {useSelector} from "react-redux";
import {selectAddress} from "../../redux/slices/userSlice";
import {ConfirmCancelButtonGroup} from "../ConfirmCancelButtonGroup";
import {changeClientAddress} from "../../Services/changeDataService";
import Recaptcha from "react-recaptcha";
import Popup from "../../PopupRecaptcha";
import {useSnackbarQueue} from "../../pages/snackbar";
import useHandleError from "../../errorHandler";
import PopupAcceptAction from "../../PopupAcceptAction";

export default function ChangeAddress({open, onOpen, onConfirm, onCancel}: ChangeDataComponentProps) {
    const {t} = useTranslation()

    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')

    const address = useSelector(selectAddress)

    const [houseNumber, setHouseNumber] = useState(address.houseNumber)
    const [street, setStreet] = useState(address.street)
    const [postalCode, setPostalCode] = useState(address.street)
    const [city, setCity] = useState(address.city)
    const [country, setCountry] = useState(address.country)

    const [buttonPopupAcceptAction, setButtonPopupAcceptAction] = useState(false);

    const [buttonPopup, setButtonPopup] = useState(false);

    const handleErase = () => {
        setHouseNumber(address.houseNumber)
        setStreet(address.street)
        setPostalCode(address.postalCode)
        setCity(address.city)
        setCountry(address.country)

    }

    const handleCancel = () => {
        handleErase()
        onCancel()
    }

    async function verifyCallback() {
        setButtonPopup(false)
        if (!houseNumber || !street || !postalCode || !city || !country) {
            handleError('error.fields')
            return
        }


        changeClientAddress({
            houseNumber,
            street,
            postalCode,
            city,
            country
        }).then(res => {
            setButtonPopupAcceptAction(false)
            setHouseNumber('')
            setStreet('')
            setPostalCode('')
            setCity('')
            setCountry('')
            showSuccess(t('successful action'))
            onConfirm()
        }).catch(error => {
            handleErase()
            setButtonPopupAcceptAction(false)
            const message = error.response.data
            handleError(message, error.response.status)
            onCancel()
        });
    }


    const changeAddress = () => {
        setButtonPopup(true)
    }

    useEffect(() => {
        console.log(address)
        setHouseNumber(address.houseNumber)
        setPostalCode(address.postalCode)
        setStreet(address.street)
        setCity(address.city)
        setCountry(address.country)
    }, [address])

    return (
        <>
            <Grid item style={{display: open ? "none" : "block"}} className={styles.item}>
                <h3>{t("address")}</h3>
                <div>
                    <div>
                        <h4>{t("street")}</h4>
                        <p>{address.street}</p>
                    </div>
                    <div>
                        <h4>{t("house number")}</h4>
                        <p>{address.houseNumber}</p>
                    </div>
                    <div>
                        <h4>{t("postal code")}</h4>
                        <p>{address.postalCode}</p>
                    </div>
                    <div>
                        <h4>{t("city")}</h4>
                        <p>{address.city}</p>
                    </div>
                    <div>
                        <h4>{t("country")}</h4>
                        <p>{address.country}</p>
                    </div>
                    <RoundedButton color="blue"
                                   onClick={onOpen}
                    >{t("address change btn")}</RoundedButton>
                </div>
            </Grid>
            <Grid item style={{display: open ? "block" : "none"}} className={styles['change-item']}>
                <h3>{t("address change")}</h3>
                <div>
                    <DarkedTextField
                        type="text"
                        label={t("street")}
                        placeholder="11-Listopada 28"
                        value={street}
                        onChange={event => {
                            setStreet(event.target.value)
                        }}/>
                    <DarkedTextField
                        type="text"
                        label={t("house number")}
                        placeholder="23"
                        value={houseNumber}
                        onChange={event => {
                            setHouseNumber(event.target.value)
                        }}/>
                    <DarkedTextField
                        type="text"
                        label={t("postal code")}
                        placeholder="91-345"
                        value={postalCode}
                        onChange={event => {
                            setPostalCode(event.target.value)
                        }}/>
                    <DarkedTextField
                        type="text"
                        label={t("city")}
                        placeholder="Zakopianka"
                        value={city}
                        onChange={event => {
                            setCity(event.target.value)
                        }}/>
                    <DarkedTextField
                        type="text"
                        label={t("country")}
                        placeholder="Zanzibar"
                        value={country}
                        onChange={event => {
                            setCountry(event.target.value)
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
                    onConfirm={changeAddress}
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