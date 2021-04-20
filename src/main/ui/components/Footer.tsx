import Box from '@material-ui/core/Box'

import Brand from './Brand'

import styles from '../styles/Footer.module.css'

function Footer() {
    return (
        <Box component="footer" className={styles.wrapper}>
            <Brand />
            <div style={{
                fontFamily: '"Montserrat", sans-serrif',
                fontSize: '1.5rem', 
                color: 'var(--white)'}}>©{new Date(Date.now()).getFullYear()}</div>
        </Box>
    )
}

export default Footer