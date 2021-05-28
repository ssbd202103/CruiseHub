import {useEffect, useReducer, useState} from 'react'

import Grid from '@material-ui/core/Grid'

import {Link} from 'react-router-dom'

import {useTranslation} from 'react-i18next';

import styles from '../../../styles/ManageAccount.module.css'
import RoundedButton from '../../../components/RoundedButton';
import DarkedTextField from '../../../components/DarkedTextField';

import axios from "../../../Services/URL";
import {useSnackbarQueue} from "../../snackbar";
import store from "../../../redux/store";
import {getUser, refreshToken} from "../../../Services/userService";
import useHandleError from "../../../errorHandler";

import PopupAcceptAction from "../../../PopupAcceptAction";

export default function ChangeAccountData() {
    const {t} = useTranslation()
    const handleError = useHandleError()

    const showSuccess = useSnackbarQueue('success')

    const currentAccount = JSON.parse(sessionStorage.getItem("changeAccountData") as string)
    const clientAccount = JSON.parse(sessionStorage.getItem("changeAccountData") as string)
    const businessAccount = JSON.parse(sessionStorage.getItem("changeAccountData") as string)
    const acLevel = currentAccount.accessLevels.map((accessLevel: any) => accessLevel.accessLevelType)
    const clientAddr = clientAccount.accessLevels.find((data: any) => (data.accessLevelType.includes("CLIENT")))
    const businnesPhone = businessAccount.accessLevels.find((data: any) => (data.accessLevelType.includes("BUSINESS_WORKER")))
    const [, forceUpdate] = useReducer(x => x + 1, 0); // used to force component refresh on forceUpdate call
    const [ChangePerData, setPerData] = useState(false)
    const [ChangAddress, setChangChangAddress] = useState(false)
    const [ChangePhone, setChangePhone] = useState(false)
    const [ChangeMail, setMail] = useState(false)
    const [firstName, setFirstName] = useState('')
    const [secondName, setSecondName] = useState('')
    const [email, setEmail] = useState('')

    const [houseNumber, setHouseNumber] = useState('')
    const [street, setStreet] = useState('')
    const [postalCode, setPostalCode] = useState('')
    const [city, setCity] = useState('')
    const [country, setCountry] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')

    const [businessPhoneNumber, setBusinessPhoneNumber] = useState('')
    const {token} = store.getState()
    const [buttonPopupAcceptChangeNumber, setButtonPopupAcceptChangeNumber] = useState(false);
    const [buttonPopupAcceptChangeData, setButtonPopupAcceptChangeData] = useState(false);
    const [buttonPopupAcceptChangeAddress, setButtonPopupAcceptChangeAddress] = useState(false);
    const [buttonPopupAcceptChangeMail, setButtonPopupAcceptChangeMail] = useState(false);

    useEffect(() => {
        setFirstName(currentAccount.firstName);
        setSecondName(currentAccount.secondName);
        if (clientAddr) {
            setStreet(clientAddr.address.street);
            setPostalCode(clientAddr.address.postalCode);
            setHouseNumber(clientAddr.address.houseNumber);
            setCountry(clientAddr.address.country);
            setCity(clientAddr.address.city);
            setPhoneNumber(clientAddr.phoneNumber);
        }
        if (businnesPhone) setBusinessPhoneNumber(businnesPhone.phoneNumber);
    }, [])
    //Functions for personal data change
    const handleChangePerData = () => {
        setPerData(state => !state)
        setChangChangAddress(false)
        setChangePhone(false)
        setMail(false)
    }
    const handleChangeMail = () => {
        setMail(state => !state)
        setChangChangAddress(false)
        setChangePhone(false)
        setPerData(false)
    }
    //Functions for address data change
    const handleChangAddress = () => {
        setChangChangAddress(state => !state)
        setPerData(false)
        setChangePhone(false)
    }
    const handleChangePhone = () => {
        setChangChangAddress(false)
        setPerData(false)
        setChangePhone(state => !state)
        setMail(false)
    }

    const changeMail = async () => {
        const {token} = store.getState()
        await axios.post('account/request-other-email-change', {
            newEmail: email,
            login: currentAccount.login,
            version: currentAccount.version
        }, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }).then(res =>{
            setButtonPopupAcceptChangeMail(false)
            refreshToken()
            showSuccess(t('successful action'))
        },error =>{
            setButtonPopupAcceptChangeMail(false)
            const message = error.response.data
            handleError(message)
        });
    }
    const changePersonalData = async () => {
        const {token} = store.getState()
        const json = JSON.stringify({
            login: currentAccount.login,
            newFirstName: firstName,
            newSecondName: secondName,
            newEmail: email,
            version: currentAccount.version

        })

        await axios.put("/account/change-account-data", json, {
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag,
                'Authorization': `Bearer ${token}`
            }
        }).then(res =>{
            setButtonPopupAcceptChangeData(false)
            refreshToken()
            showSuccess(t('successful action'))
        },error =>{
            setButtonPopupAcceptChangeData(false)
            const message = error.response.data
            handleError(message)
        });

        await axios.get(`/account/details/${currentAccount.login}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(res => {
            sessionStorage.setItem("changeAccountData", JSON.stringify(res.data));
            forceUpdate()
            handleChangePerData()
        },error =>{
            const message = error.response.data
            handleError(message)
        });

    }


    const changeAddress = async () => {
        const {token} = store.getState()
        const json = JSON.stringify({
            login: currentAccount.login,
            version: currentAccount.version,

            newPhoneNumber: phoneNumber,
            newAddress: {
                newHouseNumber: houseNumber,
                newStreet: street,
                newPostalCode: postalCode,
                newCity: city,
                newCountry: country,
            },
            accVersion: clientAddr.accVersion
        })


        await axios.put("account/change-client-data", json, {
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag,
                "Authorization": `Bearer ${token}`
            }
        }).then(res =>{
            setButtonPopupAcceptChangeAddress(false)
            showSuccess(t('successful action'))
        }).catch(error => {
            setButtonPopupAcceptChangeAddress(false)
            const message = error.response.data
            handleError(t(message))
        });


        await axios.get(`/account/details/${currentAccount.login}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(res => {
            sessionStorage.setItem("changeAccountData", JSON.stringify(res.data));
            forceUpdate()
            handleChangAddress()
        },error =>{
            const message = error.response.data
            handleError(message)
        });
    }
    const changeBusinessPhone = async () => {
        const json = JSON.stringify({
            login: currentAccount.login,
            version: currentAccount.version,
            newPhoneNumber: businessPhoneNumber,
            accVersion: businnesPhone.accVersion

        })
        await axios.put('account/change-business-worker-data', json, {
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag,
                "Authorization": `Bearer ${token}`,
            }
        }).then(res =>{
            setButtonPopupAcceptChangeNumber(false)
            showSuccess(t('successful action'))
        },error =>{
            const message = error.response.data
            setButtonPopupAcceptChangeNumber(false)
            handleError(message)
        });

        await axios.get(`/account/details/${currentAccount.login}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(res => {
            sessionStorage.setItem("changeAccountData", JSON.stringify(res.data));
            forceUpdate()
            handleChangePhone()
        },error =>{
            const message = error.response.data
            handleError(message)
        });

    }
    return (
        <>
        <Grid container className={styles.wrapper}>
            <Grid item style={{display: ChangePerData ? "none" : "block"}} className={styles.item}>
                <h3>{t("personal data")}</h3>
                <div>
                    <div>
                        <h4>{t("name")}</h4>
                        <p>{currentAccount.firstName}</p>
                    </div>
                    <div>
                        <h4>{t("surname")}</h4>
                        <p>{currentAccount.secondName}</p>
                    </div>
                    <div>
                        <h4>{t("Login")}</h4>
                        <p>{currentAccount.login}</p>
                    </div>
                    <RoundedButton
                        color="blue"
                        onClick={handleChangePerData}
                    >{t("personal data change btn")}</RoundedButton>
                </div>
            </Grid>

            <Grid item style={{display: ChangePerData ? "block" : "none"}} className={styles['change-item']}>
                <h3>{t("personal data change")}</h3>
                <div>
                    <DarkedTextField
                        type="text"
                        label={t("name")}
                        placeholder={currentAccount.firstName}
                        value={firstName}
                        onChange={event => {
                            setFirstName(event.target.value)
                        }}/>
                    <DarkedTextField
                        type="text"
                        label={t("surname")}
                        placeholder={currentAccount.secondName}
                        value={secondName}
                        onChange={event => {
                            setSecondName(event.target.value)
                        }}/>
                </div>
                <RoundedButton color="blue"
                               onClick={()=>setButtonPopupAcceptChangeData(true)}
                >{t("confirm")}</RoundedButton>
                <RoundedButton color="pink"
                               onClick={handleChangePerData}
                >{t("cancel")}</RoundedButton>
            </Grid>
            <Grid>
                <Grid item style={{display: ChangeMail ? "none" : "block"}} className={styles.item}>
                    <h3>{t("email")}</h3>
                    <div>
                        <p>{currentAccount.email}</p>
                        <RoundedButton
                            color="blue"
                            onClick={handleChangeMail}
                        >{t("email change btn")}</RoundedButton>
                    </div>
                </Grid>
                <Grid item style={{display: ChangeMail ? "block" : "none"}} className={styles['change-item']}>
                    <h3>{t("email change")}</h3>
                    <div>
                        <DarkedTextField
                            type="text"
                            label={t("new email")}
                            placeholder={t(currentAccount.email)}
                            value={email}
                            onChange={event => {
                                setEmail(event.target.value)
                            }}/>
                    </div>
                <div>
                    <RoundedButton color="blue"
                                   onClick={()=>setButtonPopupAcceptChangeData(true)}
                    >{t("confirm")}</RoundedButton>
                    <RoundedButton color="pink"
                                   onClick={handleChangeMail}
                    >{t("cancel")}</RoundedButton>
                </div>
            </Grid>
        </Grid>
    <Grid item style={{display: acLevel.includes('CLIENT') ? "block" : "none"}} className={styles.item}>
        <Grid item style={{display: ChangAddress ? "none" : "block"}} className={styles.item}>
            <h3>{t("address")}</h3>

            <div>
                <div>
                    <h4>{t("street")}</h4>
                    <p>{clientAddr ? clientAddr.address.street : ""}</p>
                </div>
                <div>
                    <h4>{t("house number")}</h4>
                    <p>{clientAddr ? clientAddr.address.houseNumber : ""}</p>
                </div>
                <div>
                    <h4>{t("postal code")}</h4>
                    <p>{clientAddr ? clientAddr.address.postalCode : ""}</p>
                </div>
                <div>
                    <h4>{t("city")}</h4>
                    <p>{clientAddr ? clientAddr.address.city : ""}</p>
                </div>
                <div>
                    <h4>{t("country")}</h4>
                    <p>{clientAddr ? clientAddr.address.country : ""}</p>
                </div>
                <div>
                    <h4>{t("phone number")}</h4>
                    <p>{clientAddr ? clientAddr.phoneNumber : ""}</p>
                </div>
                <RoundedButton color="blue"
                               onClick={handleChangAddress}
                >{t("address change btn")}</RoundedButton>
            </div>

        </Grid>
        <Grid item style={{display: ChangAddress ? "block" : "none"}} className={styles['change-item']}>
            <h3>{t("address change")}</h3>
            <div>
                <DarkedTextField
                    type="text"
                    label={t("street")}
                    placeholder={clientAddr ? clientAddr.address.street : ""}
                    value={street}
                    onChange={event => {
                        setStreet(event.target.value)
                    }}/>
                <DarkedTextField
                    type="text"
                    label={t("house number")}
                    placeholder={clientAddr ? clientAddr.address.houseNumber : ""}
                    value={houseNumber}
                    onChange={event => {
                        setHouseNumber(event.target.value)
                    }}/>
                <DarkedTextField
                    type="text"
                    label={t("postal code")}
                    placeholder={clientAddr ? clientAddr.address.postalCode : ""}
                    value={postalCode}
                    onChange={event => {
                        setPostalCode(event.target.value)
                    }}/>
                <DarkedTextField
                    type="text"
                    label={t("city")}
                    placeholder={clientAddr ? clientAddr.address.city : ""}
                    value={city}
                    onChange={event => {
                        setCity(event.target.value)
                    }}/>
                <DarkedTextField
                    type="text"
                    label={t("country")}
                    placeholder={clientAddr ? clientAddr.address.country : ""}
                    value={country}
                    onChange={event => {
                        setCountry(event.target.value)
                    }}/>
                <DarkedTextField
                    type="text"
                    label={t("phone number")}
                    placeholder={clientAddr ? clientAddr.phoneNumber : ""}
                    value={phoneNumber}
                    onChange={event => {
                        setPhoneNumber(event.target.value)
                    }}/>
            </div>
            <RoundedButton
                color="blue"
                onClick={()=>setButtonPopupAcceptChangeAddress(true)}
            >{t("confirm")}</RoundedButton><RoundedButton
            color="pink"
            onClick={handleChangAddress}
        >{t("cancel")}</RoundedButton>

        </Grid>
    </Grid>
    <Grid item style={{display: acLevel.includes('BUSINESS_WORKER') ? "block" : "none"}}
          className={styles.item}>
        <Grid item style={{display: ChangePhone ? "none" : "block"}} className={styles.item}>
            <div>
                <div>
                    <h4>{t("phone number")}</h4>
                    <p>{businnesPhone ? businnesPhone.phoneNumber : ""}</p>
                </div>
                <RoundedButton color="blue"
                               onClick={handleChangePhone}
                >{t("phone change btn")}</RoundedButton>
            </div>

        </Grid>
        <Grid item style={{display: ChangePhone ? "block" : "none"}} className={styles['change-item']}>
            <h4>{t("phone number")}</h4>
            <div>
                <DarkedTextField
                    type="text"
                    label={t("phone number")}
                    placeholder={businnesPhone ? businnesPhone.phoneNumber : ""}
                    value={businessPhoneNumber}
                    onChange={event => {
                        setBusinessPhoneNumber(event.target.value)
                    }}/>
            </div>
            <RoundedButton
                color="blue"
                onClick={()=>setButtonPopupAcceptChangeNumber(true)}
            >{t("confirm")}</RoundedButton>
            <RoundedButton
                color="pink"
                onClick={handleChangePhone}
            >{t("cancel")}</RoundedButton>
        </Grid>

    </Grid>
    <Grid item>
        <Link to="/accounts">
            <RoundedButton color="pink" >
                {t("go back")}
            </RoundedButton>
        </Link>
    </Grid>
</Grid>
    <PopupAcceptAction
        open={buttonPopupAcceptChangeAddress}
        onConfirm={changeAddress}
        onCancel={() => {setButtonPopupAcceptChangeAddress(false)
        }}
    />
    <PopupAcceptAction
        open={buttonPopupAcceptChangeNumber}
        onConfirm={changeBusinessPhone}
        onCancel={() => {setButtonPopupAcceptChangeNumber(false)
        }}
    />
    <PopupAcceptAction
        open={buttonPopupAcceptChangeData}
        onConfirm={changePersonalData}
        onCancel={() => {setButtonPopupAcceptChangeData(false)
        }}
    />
    <PopupAcceptAction
        open={buttonPopupAcceptChangeMail}
        onConfirm={changeMail}
        onCancel={() => {setButtonPopupAcceptChangeMail(false)
        }}
    />
</>
)
}