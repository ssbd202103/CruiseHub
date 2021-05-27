import {Link, useHistory} from 'react-router-dom'

import Box from '@material-ui/core/Box'

import Brand from './Brand'

import styles from '../styles/Header.module.css'
import RoundedButton from './RoundedButton'

import {useTranslation} from 'react-i18next'
import {useSelector} from "react-redux";
import {selectDarkMode} from "../redux/slices/userSlice";
import Authentication from "./Authentication";
import Breadcrumb from "./Breadcrumb";

function Header({fixed = false}: {fixed?: boolean}) {
    const {t} = useTranslation()

    const darkMode = useSelector(selectDarkMode)
    const history = useHistory()

    return (
        <Box className={styles.wrapper + ' ' + styles[`wrapper-${!darkMode ? 'light' : 'dark'}`]}
             style={{
                 position: fixed ? 'fixed' : 'static'
             }}
        >
            <Brand/>
            <Authentication />
        </Box>
    )
}

export default Header