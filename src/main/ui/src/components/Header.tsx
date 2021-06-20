import Box from '@material-ui/core/Box'

import Brand from './Brand'

import styles from '../styles/Header.module.css'
import RoundedButton from './RoundedButton'

import {useTranslation} from 'react-i18next'
import {useSelector} from "react-redux";
import {selectDarkMode} from "../redux/slices/userSlice";
import Authentication from "./Authentication";
import LanguageSetter from "./LanguageSetter";


function Header({fixed = false}: {fixed?: boolean}) {
    const {t} = useTranslation()

    const darkMode = useSelector(selectDarkMode)

    return (
        <Box className={styles.wrapper + ' ' + styles[`wrapper-${!darkMode ? 'light' : 'dark'}`]}
             style={{
                 position: fixed ? 'fixed' : 'static'
             }}
        >
            <Brand/>
            <Box style={{
                display: 'flex',
                height: '100%',
                alignItems: 'center'
            }}>
                <RoundedButton style={{marginRight: 50}} color={"dark"} onClick={() => {window.location.reload()}}>{t('update')}</RoundedButton>
                <Authentication />
                <LanguageSetter />
            </Box>
        </Box>
    )
}

export default Header