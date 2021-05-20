import {
    Box
} from '@material-ui/core'

import styles from '../styles/PanelMenu.module.css'
import {useSelector} from "react-redux";
import {selectDarkMode} from "../redux/slices/userSlice";


export interface PanelMenuProps {
    color: "pink" | "pink-dark" | "pink-light" |
        "blue" | "blue-dark" | "blue-light" |
        "green" | "green-dark" | "green-light" |
        "yellow" | "yellow-dark" | "yellow-light" |
        "dark" | "dark-dark" | "dark-light" |
        "white" | "white-dark" | "white-light"
    children?: any
}


export default function PanelMenu(props: PanelMenuProps) {

    const darkMode = useSelector(selectDarkMode)

    return (
        <Box className={styles.wrapper} style={{backgroundColor: `var(--${props.color})`}}>
            <Box className={styles.profile + ' ' + styles[`profile-${!darkMode ? 'light' : 'dark'}`]}>
                <div className={styles.name}>Jan Kowalski</div>
                <div className={styles.email}>jan.kowalski@gmail.com</div>
            </Box>
            {props.children}
        </Box>
    )
}