import Link from 'next/link'

import Box from '@material-ui/core/Box'
import EmailIcon from '@material-ui/icons/AlternateEmail'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'

import AuthLayout from '../../layouts/AuthLayout'
import DarkedTextField from '../../components/DarkedTextField'
import RoundedButton from '../../components/RoundedButton'
import styles from '../../styles/auth.global.module.css'


export default function ClientSignUp() {
    return (
        <AuthLayout>
            <h1 className={styles.h1}>Witamy</h1>
            <h2 className={styles.h2}>Zostań członkiem największej przygody w Twoim życiu</h2>

            <Box style={{
                display: "flex",
                width: '100%',
                padding: 0
            }}>
                <DarkedTextField
                    label="Imię"
                    placeholder="John"
                    className={styles.input}
                    style={{
                        marginRight: 20
                    }}
                />


                <DarkedTextField
                    label="Nazwisko"
                    placeholder="Doe"
                    className={styles.input}
                />
            </Box>            

            <DarkedTextField
                type="email"
                label="Email"
                placeholder="example@email.com"
                className={styles.input}
                icon={(<EmailIcon />)}
            />

            <Box style={{
                display: "flex",
                width: '100%',
                padding: 0
            }}>
                <DarkedTextField
                    type="password"
                    label="Hasło"
                    placeholder="1234567890"
                    className={styles.input}
                    style={{marginRight: 20}}
                    icon={(<PasswordIcon />)}
                />

                <DarkedTextField
                    type="password"
                    label="Potwierdzenie hasła"
                    placeholder="1234567890"
                    className={styles.input}
                    icon={(<PasswordIcon />)}
                />
            </Box>


            
            <Box style={{
                width: '100%',
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between"
            }}>
                <RoundedButton style={{width: '50%', fontSize: '1.2rem'}} color="pink">Zarejestruj mnie</RoundedButton>
                <Link href="worker">
                    <a className={styles.link}>Jestem pracownikiem firmy</a>
                </Link>
            </Box>
        </AuthLayout>
    )
}