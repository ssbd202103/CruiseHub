import {useRouter} from 'next/router'

import {
    Grid,
    Button
} from '@material-ui/core'
import EmailIcon from '@material-ui/icons/AlternateEmail'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'
import { withStyles } from '@material-ui/styles'

import DarkedTextField from '../components/DarkedTextField'
import RoundedButton from '../components/RoundedButton'

import styles from '../styles/auth.global.module.css'



export default function SignIn() {
    return (
        <Grid container className={styles.wrapper}>
            <Grid item className={styles.form} xs sm={9} md={9} lg={7} xl={5}>
                <h1 className={styles.h1}>Witamy ponownie</h1>
                <h2 className={styles.h2}>Bardzo się cziesimy, że znów jesteś z nami</h2>

                <DarkedTextField 
                    label="Email"
                    className={styles.input} 
                    icon={(<EmailIcon />)}
                    placeholder="example@email.com"
                />

                <DarkedTextField 
                    label="Hasło"
                    className={styles.input} 
                    icon={(<PasswordIcon />)}
                    placeholder="1234567890"
                />

                <RoundedButton
                    className={styles.button}
                    color="pink"
                >Zaloguj</RoundedButton>

            </Grid>
        </Grid>
    )
}