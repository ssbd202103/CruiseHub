import Grid from '@material-ui/core/Grid'

import LayoutProps from './LayoutProps'
import styles from '../styles/auth.global.module.css'


export default function AuthLayout({children}: LayoutProps) {
    return (
        <Grid container className={styles.wrapper}>
            <Grid item className={styles.form} xs md={10} xl={5}>
                {children}
            </Grid>
        </Grid>
    )
}