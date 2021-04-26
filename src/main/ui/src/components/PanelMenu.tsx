import {
    Box
} from '@material-ui/core'

import styles from '../styles/PanelMenu.module.css'

export interface PanelMenuProps {
    color: "pink" | "pink-dart" | "pink-light" | "blue" | "blue-dark" | "blue-light" | "green" | "green-dark" | "green-light" | "yellow" | "yellow-dark" | "yellow-light" | "dark" 
    children?: any
}

export default function PanelMenu(props: PanelMenuProps) {
    return (
        <Box className={styles.wrapper} style={{backgroundColor: `var(--${props.color})`}}>
            <Box className={styles.profile}>
                <div className={styles.name}>Jan Kowalski</div>
                <div className={styles.email}>jan.kowalski@gmail.com</div>
            </Box>
            {props.children}
        </Box>
    )
}