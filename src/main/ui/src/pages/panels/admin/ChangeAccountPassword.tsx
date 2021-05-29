import {useState} from 'react'

import Grid from '@material-ui/core/Grid'

import {Link} from 'react-router-dom'

import {useTranslation} from 'react-i18next'

import styles from '../../../styles/ManageAccount.module.css'
import RoundedButton from '../../../components/RoundedButton';
import DarkedTextField from '../../../components/DarkedTextField';

export default function ChangeAccountPassword() {
    const {t} = useTranslation()

    const [ChangPasswd, setChangPasswd] = useState(false)

    //Functions for password
    const handleChangPasswd = () => {
        setChangPasswd(state => !state)

    }
    const changePassword = () => {
        //Place for transfer function (change password in database)
        handleChangPasswd()
    }

    return (
        <Grid container className={styles.wrapper}>
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
                <Grid item>
                    <Link to="/accounts">
                        <RoundedButton color="pink">
                            {t("go back")}
                        </RoundedButton>
                    </Link>
                </Grid>
        </Grid>
    )
}