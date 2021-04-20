import Link from 'next/link'

import Box from '@material-ui/core/Box'
import EmailIcon from '@material-ui/icons/AlternateEmail'
import PasswordIcon from '@material-ui/icons/VpnKeyRounded'

import AuthLayout from '../../layouts/AuthLayout'
import DarkedTextField from '../../components/DarkedTextField'
import DarkedSelect from '../../components/DarkedSelect'
import RoundedButton from '../../components/RoundedButton'
import styles from '../../styles/auth.global.module.css'


export default function ClientSignUp() {
    return (
        <AuthLayout>
            <h1 className={styles.h1}>Witamy</h1>
            <h2 className={styles.h2}>W kilka klików uzyskasz dostęp do jednej z największych sieci klientów</h2>

            <Box style={{
                display: "flex",
                width: '100%',
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

            <Box style={{
                display: "flex",
                width: '100%',
                padding: 0
            }}>
                <DarkedSelect 
                    label="Firma"
                    options={["Firma 1", "Firma 2", "Firma 3", "Firma 4", "Firma 5"]}
                    className={styles.input}
                    style={{
                        marginRight: 20
                    }}
                />

                <DarkedTextField
                    type="email"
                    label="Email"
                    placeholder="example@email.com"
                    className={styles.input}
                    icon={(<EmailIcon />)}
                />
            </Box>

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
                <RoundedButton style={{width: '50%', fontSize: '1.2rem', padding: '10px 0'}} color="pink">Zarejestruj mnie</RoundedButton>
                <Link href="client">
                    <a className={styles.link}>Jestem klientem</a>
                </Link>
            </Box>
        </AuthLayout>
    )
}