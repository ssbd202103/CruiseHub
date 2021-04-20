import Link from 'next/link'

import Box from '@material-ui/core/Box'
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
                        width: '70%',
                        margin: '20px 0'
                    }}
                    icon={(<EmailIcon />)}
                    placeholder="example@email.com"
                />

                <DarkedTextField 
                    type="password"
                    label="Hasło"
                    style={{
                        width: '70%',
                        margin: '20px 0'
                    }} 
                    icon={(<PasswordIcon />)}
                    placeholder="1234567890"
                />

                <Box style={{
                    width: '70%',
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "space-between"
                }}>
                    <RoundedButton
                        style={{
                            width: '50%',
                            fontSize: '1.2rem',
                            padding: '10px 0'
                        }}
                        color="pink"
                    >Zaloguj</RoundedButton>
                    <Link href="signup/client">
                        <a className={styles.link}>Nie mam konta</a>
                    </Link>
                </Box>

                
        </AuthLayout>
    )
}