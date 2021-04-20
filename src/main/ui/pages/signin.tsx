import EmailIcon from '@material-ui/icons/AlternateEmail'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'

import AuthLayout from '../layouts/AuthLayout'
import DarkedTextField from '../components/DarkedTextField'
import RoundedButton from '../components/RoundedButton'

import styles from '../styles/auth.global.module.css'

export default function SignIn() {
    return (
        <AuthLayout>
                <h1 className={styles.h1}>Witamy ponownie</h1>
                <h2 className={styles.h2}>Bardzo się cziesimy, że znów jesteś z nami</h2>

                

                <DarkedTextField 
                    type="email"
                    label="Email"
                    style={{
                        width: '50%',
                        margin: '20px 0'
                    }}
                    icon={(<EmailIcon />)}
                    placeholder="example@email.com"
                />

                <DarkedTextField 
                    type="password"
                    label="Hasło"
                    style={{
                        width: '50%',
                        margin: '20px 0'
                    }} 
                    icon={(<PasswordIcon />)}
                    placeholder="1234567890"
                />

                <RoundedButton
                    style={{
                        width: '50%',
                        fontSize: '1.2rem',
                    }}
                    color="pink"
                >Zaloguj</RoundedButton>
        </AuthLayout>
    )
}