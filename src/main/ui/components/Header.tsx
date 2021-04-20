import Link from 'next/link'

import Box from '@material-ui/core/Box'

import Brand from './Brand'

import styles from '../styles/Header.module.css'
import RoundedButton from './RoundedButton'

function Header() {
    return (
        <Box className={styles.wrapper}>
            <Brand />

           

            <Box>
                <Link href="signin">
                    <a style={{marginRight: 20}} className={styles.link}>Zaloguj się</a>
                </Link>

                <RoundedButton
                            style={{
                                fontSize: '1rem',
                                padding: '10px 20px',
                                textTransform: 'none'
                            }}
                            color="pink"
                >
                    <Link href="signin">
                        <a>Zarejestruj</a>
                    </Link>
                </RoundedButton>
            </Box>
            

        </Box>
    )
}

export default Header