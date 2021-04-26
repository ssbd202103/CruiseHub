import {useState} from 'react'

import Grid from '@material-ui/core/Grid'
import Button from "@material-ui/core/Button";

import {useTranslation} from 'react-i18next';

import styles from '../../../styles/ManageAccount.module.css'
import RoundedButton from '../../../components/RoundedButton';
import DarkedTextField from '../../../components/DarkedTextField';

export default function ManageAccount() {
    const {t} = useTranslation()

    const [ChangePerData, setPerData] = useState(false)
    const [ChangAddress, setChangChangAddress] = useState(false)
    const [ChangEmail, setChangEmail] = useState(false)
    const [ChangPasswd, setChangPasswd] = useState(false)

    //Functions for personal data change
    const handleChangePerData = () => {
        setPerData(state => !state)
        setChangChangAddress( false)
        setChangEmail( false)
        setChangPasswd(false)
    }
    const changePersonalData = () => {
        //Place for transfer function (change personal data in database)
        handleChangePerData()
    }

    //Functions for address data change
    const handleChangAddress = () => {
        setChangChangAddress(state => !state)
        setPerData(false)
        setChangEmail(false)
        setChangPasswd( false)
    }
    const changeAddress = () => {
        //Place for transfer function (change address in database)
        handleChangAddress()
    }

    //Functions for email data change
    const handleChangEmail = () => {
        setChangEmail(state => !state)
        setPerData( false)
        setChangChangAddress( false)
        setChangPasswd(false)
    }
    const changeEmail = () => {
        //Place for transfer function (change email in database)
        handleChangEmail()
    }

    //Functions for password
    const handleChangPasswd = () => {
        setChangPasswd(state => !state)
        setPerData( false)
        setChangChangAddress(false)
        setChangEmail( false)
    }
    const changePassword = () => {
        //Place for transfer function (change password in database)
        handleChangPasswd()
    }

    return (
        <Grid container className={styles.wrapper}>
                <Grid item style={{display: ChangePerData ? "none" : "block"}} className={styles.item}>
                    <h3>{t("personal data")}</h3>
                    <div>
                        <div>
                            <h4>{t("name")}</h4>
                            <p>Michał</p>
                        </div>
                        <div>                        
                            <h4>{t("surname")}</h4>
                            <p>Blazymur</p>
                        </div>
                        <div>                        
                            <h4>{t("Login")}</h4>
                            <p>BigMike</p>
                        </div>
                        <div>                                            
                            <h4>{t("phone number")}</h4>
                            <p>+48-767-765-456</p>
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
                            placeholder="Michał" />
                        <DarkedTextField 
                            type="text" 
                            label={t("surname")}
                            placeholder="Blazymur"/>
                        <DarkedTextField 
                            type="text" 
                            label="Login"
                            placeholder="BigMike"/>
                        <DarkedTextField 
                            type="text" 
                            label={t("phone")}
                            placeholder="+48-767-765-456"/>
                    </div>
                    <RoundedButton color="blue"
                                onClick={changePersonalData}
                        >{t("confirm")}</RoundedButton>
                </Grid>

                <Grid item  style={{display: ChangAddress ? "none" : "block"}} className={styles.item}>
                    <h3>{t("address")}</h3>
                    <div>
                        <div>
                            <h4>{t("street")}</h4>
                            <p>11-Listopada 28</p>
                        </div>
                        <div>
                            <h4>{t("house number")}</h4>
                            <p>23</p>
                        </div>
                        <div>
                            <h4>{t("postal code")}</h4>
                            <p>91-345</p>
                        </div>
                        <div>
                            <h4>{t("city")}</h4>
                            <p>Zakopianka</p>
                        </div>
                        <div>
                            <h4>{t("country")}</h4>
                            <p>Zanzibar</p>
                        </div>
                    <RoundedButton color="blue"
                            onClick={handleChangAddress}
                    >{t("address change btn")}</RoundedButton>
                    </div>
                </Grid>
                <Grid item  style={{display:ChangAddress  ? "block" : "none"}} className={styles['change-item']}>
                    <h3>{t("address change")}</h3>
                    <div>
                    <DarkedTextField 
                        type="text" 
                        label={t("street")}
                        placeholder="11-Listopada 28" />
                    <DarkedTextField 
                        type="text" 
                        label={t("house number")}
                        placeholder="23" />
                    <DarkedTextField 
                        type="text" 
                        label={t("postal code")}
                        placeholder="91-345" />
                    <DarkedTextField 
                        type="text" 
                        label={t("city")}
                        placeholder="Zakopianka" />
                    <DarkedTextField 
                        type="text"
                        label={t("country")} 
                        placeholder="Zanzibar" />
                    </div>

                    <RoundedButton 
                        color="blue"
                        onClick={changeAddress}
                    >{t("confirm")}</RoundedButton>
                </Grid>

                <Grid item  style={{display: ChangEmail ? "none" : "block"}} className={styles.item}>
                    <h3>{t("email")}</h3>
                    <div>
                        <p>BigMIke@gmail.com</p>
                        <RoundedButton 
                            color="blue"
                            onClick={handleChangEmail}
                        >{t("email change btn")}</RoundedButton>
                    </div>
                </Grid>
                <Grid item  style={{display: ChangEmail ? "block" : "none"}} className={styles['change-item']}>
                    <h3>{t("email change")}</h3>
                    <div>
                        <DarkedTextField 
                            type="text"
                            label={t("new email")} 
                            placeholder={t("new email")} />
                        <DarkedTextField 
                            type="text" 
                            label={t("new email confirm")}
                            placeholder={t("new email confirm")} />
                    </div>
                    <RoundedButton 
                        color="blue"
                        onClick={changeEmail}
                    >{t("confirm")}</RoundedButton>
                </Grid>

                <Grid item  style={{display: ChangPasswd ? "none" : "block"}} className={styles.item}>
                    <h3>{t("password")}</h3>
                    <RoundedButton color="blue"
                            onClick={handleChangPasswd}
                    >{t("password change btn")}</RoundedButton>
                </Grid>
                <Grid item  style={{display: ChangPasswd ? "block" : "none"}} className={styles['change-item']}>
                    <h3>{t("password change")}</h3>
                    <div>
                        <DarkedTextField 
                            type="password"
                            label={t("old password")}
                            placeholder={t("old password")} />

                        <DarkedTextField
                            type="password" 
                            label={t("new password")}
                            placeholder={t("new password")} />

                        <DarkedTextField 
                            type="password" 
                            label={t("new password confirm")}
                            placeholder={t("new password confirm")} />
                    </div>
                    <RoundedButton 
                        color="blue"
                        onClick={changePassword}
                    >{t("confirm")}</RoundedButton>
                    
                </Grid>
        </Grid>
    )
}