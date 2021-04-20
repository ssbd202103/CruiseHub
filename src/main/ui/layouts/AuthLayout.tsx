import Link from 'next/link'

import Grid from '@material-ui/core/Grid'

import LayoutProps from './LayoutProps'
import styles from '../styles/auth.global.module.css'


export default function AuthLayout({children}: LayoutProps) {
    return (
        <Grid container className={styles.wrapper}>
            <Grid item className={styles.form} xs md={10} xl={6}>
                {children}
            </Grid>
            <Link href="../">
                <a style={{
                    position: 'absolute',
                    top: 20,
                    left: 20,
                    fontFamily: '"Montserrat", sans-serif',
                    fontSize: '1.2rem'
                }}>Wróć</a>
            </Link>
        </Grid>
    )
}