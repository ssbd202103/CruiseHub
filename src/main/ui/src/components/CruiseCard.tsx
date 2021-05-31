import useMediaQuery from '@material-ui/core/useMediaQuery'

import StarIcon from '@material-ui/icons/StarRounded'
import EyeIcon from '@material-ui/icons/VisibilityRounded'

import styles from '../styles/CruiseCard.module.css'
import {useTranslation} from "react-i18next";


export default function CruiseCard() {
    const {t} = useTranslation()
    const xlMatches = useMediaQuery('(min-width: 1920px)')
    const lgMatches = useMediaQuery('(min-width: 1280px)')
    const mdMatches = useMediaQuery('(min-width: 960px)')
    const smMatches = useMediaQuery('(min-width: 600px)')

    return (
        <div className={styles.wrapper} style={{
            height: xlMatches ? 400 : lgMatches ? 300 : 200
        }}>
            <div className={styles.cost}>1,000 zł</div>
            <div className={styles.description}>
                <span className={styles.title}>{t("cruiseExample")}</span>
                <div>
                    {[0,1,2,3,4].map(id => 
                        <StarIcon key={id} style={{
                            fill: 'var(--yellow)'
                        }} />)}
                </div>
            </div>
            <div className={styles.eye}>
                <div>
                    <EyeIcon style={{fill: '#FFFFFF', transform: 'scale(2)'}} />
                </div>
            </div>
        </div>
    )
}