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
    const currentSelfMTD = JSON.parse(sessionStorage.getItem("changeSelfAccountDataMta") as string)
    const currentSelfAddressMTD = JSON.parse(sessionStorage.getItem("changeSelfAddressDataMta") as string)

    const address = useSelector(selectAddress)

    const [houseNumber, setHouseNumber] = useState(address.houseNumber)
    const [street, setStreet] = useState(address.street)
    const [postalCode, setPostalCode] = useState(address.street)
    const [city, setCity] = useState(address.city)
    const [country, setCountry] = useState(address.country)

    const [alterTypeAdr, setAlterTypeAdr] = useState('')
    const [alteredByAdr, setAlteredByAdr] = useState('')
    const [createdByAdr, setCreatedByAdr] = useState('')
    const [creationDateTimeAdr, setCreationDateTimeAdr] = useState('')
    const [lastAlterDateTimeAdr, setLastAlterDateTimeAdr] = useState('')
    const [versionAdr, setVersionAdr] = useState('')

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
            setHouseNumber('')
            setStreet('')
            setPostalCode('')
            setCity('')
            setCountry('')
            showSuccess(t('successful action'))
            onConfirm()
        }).catch(error => {
            handleErase()
            const message = error.response.data
            handleError(message, error.response.status)
            onCancel()
        });
    }


    const changeAddress = () => {
        setButtonPopup(true)
        setButtonPopupAcceptAction(false)
    }

    useEffect(() => {
        setHouseNumber(address.houseNumber)
        setPostalCode(address.postalCode)
        setStreet(address.street)
        setCity(address.city)
        setCountry(address.country)
        setAlterTypeAdr(currentSelfAddressMTD.alterType);
        setAlteredByAdr(currentSelfAddressMTD.alteredBy);
        setCreatedByAdr(currentSelfAddressMTD.createdBy);
        if(currentSelfAddressMTD.creationDateTime !=null)
            setCreationDateTimeAdr(currentSelfAddressMTD.creationDateTime.dayOfMonth +"/"+ t(currentSelfAddressMTD.creationDateTime.month) +" / "+ currentSelfAddressMTD.creationDateTime.year +"    "+ currentSelfAddressMTD.creationDateTime.hour +":"+ currentSelfAddressMTD.creationDateTime.minute )
        if(currentSelfAddressMTD.lastAlterDateTime !=null)
            setLastAlterDateTimeAdr(currentSelfAddressMTD.lastAlterDateTime.dayOfMonth +"/"+ t(currentSelfAddressMTD.lastAlterDateTime.month) +" / "+ currentSelfAddressMTD.lastAlterDateTime.year +"    "+ currentSelfAddressMTD.lastAlterDateTime.hour +":"+ currentSelfAddressMTD.lastAlterDateTime.minute);
        setVersionAdr(currentSelfAddressMTD.version);
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
                        placeholder={t("streetExample")}
                        value={street}
                        onChange={event => {
                            setStreet(event.target.value)
                        }}/>
                    <DarkedTextField
                        type="text"
                        label={t("house number")}
                        placeholder={t("houseNumberExample")}
                        value={houseNumber}
                        onChange={event => {
                            setHouseNumber(event.target.value)
                        }}/>
                    <DarkedTextField
                        type="text"
                        label={t("postal code")}
                        placeholder={t("postalCodeExample")}
                        value={postalCode}
                        onChange={event => {
                            setPostalCode(event.target.value)
                        }}/>
                    <DarkedTextField
                        type="text"
                        label={t("city")}
                        placeholder={t("cityExample")}
                        value={city}
                        onChange={event => {
                            setCity(event.target.value)
                        }}/>
                    <DarkedTextField
                        type="text"
                        label={t("country")}
                        placeholder={t("countryExample")}
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
                <tr>
                    <td><h4>{t("alterType")}</h4></td> <td><h4>{t("alteredBy")}</h4></td> <td><h4>{t("createdBy")}</h4></td>
                    <td><h4>{t("creationDateTime")}</h4></td> <td><h4>{t("lastAlterDateTime")}</h4></td><td><h4>{t("version")}</h4></td>
                </tr>
                <tr>
                    <td><h4>{t(alterTypeAdr)}</h4></td><td><h4>{alteredByAdr}</h4></td><td><h4>{createdByAdr}</h4></td>
                    <td><h4>{creationDateTimeAdr}</h4></td><td><h4>{lastAlterDateTimeAdr}</h4></td><td><h4>{versionAdr}</h4></td>
                </tr>
            </Grid>
        </>
    )
}