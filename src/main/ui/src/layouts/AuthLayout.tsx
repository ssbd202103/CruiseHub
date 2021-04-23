import {Link} from 'react-router-dom'

import Grid from '@material-ui/core/Grid'

import LayoutProps from './LayoutProps'
import styles from '../styles/auth.global.module.css'

import { useTranslation } from 'react-i18next'

export default function AuthLayout({children}: LayoutProps) {

    const {t} = useTranslation()

    return (
            <Grid container className={styles.wrapper}>
                <Grid item className={styles.form} xs md={9} xl={6}>
                    {children}
                </Grid>
                <Link to="/">
                    <a style={{
                        position: 'absolute',
                        top: 20,
                        left: 20,
                        fontFamily: '"Montserrat", sans-serif',
                        fontSize: '1.2rem'
                    }}>{t('go back')}</a>
                </Link>
            </Grid>
    )
}