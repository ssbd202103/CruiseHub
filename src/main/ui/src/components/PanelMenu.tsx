import {
    Box
} from '@material-ui/core'

import styles from '../styles/PanelMenu.module.css'

export interface PanelMenuProps {
    children?: any
}

export default function PanelMenu(props: PanelMenuProps) {
    return (
        <Box className={styles.wrapper}>
            <Box className={styles.profile}>
                <div className={styles.name}>Jan Kowalski</div>
                <div className={styles.email}>jan.kowalski@gmail.com</div>
            </Box>
            {props.children}
        </Box>
    )
}