import useMediaQuery from '@material-ui/core/useMediaQuery'

import StarIcon from '@material-ui/icons/StarRounded'
import EyeIcon from '@material-ui/icons/VisibilityRounded'

import styles from '../styles/CruiseCard.module.css'
import {useTranslation} from "react-i18next";
import {createCruiseGroup} from "./ListCruiseGroup";
import ship3 from "../images/ship3.jpg"

export interface  CruiseData{
   group: ReturnType<typeof createCruiseGroup>
}

export default function CruiseGroupCard(props: CruiseData) {
    const {t} = useTranslation()
    const xlMatches = useMediaQuery('(min-width: 1920px)')
    const lgMatches = useMediaQuery('(min-width: 1280px)')
    const mdMatches = useMediaQuery('(min-width: 960px)')
    const smMatches = useMediaQuery('(min-width: 600px)')
    const {group} = props
    let pictureUrl

    if(group.cruisePictures.length>0){
        pictureUrl ='data:image/png;base64,'+ group.cruisePictures[0].dataURL
    }
    else {
        pictureUrl= ship3
    }

    return (
        <div className={styles.wrapper} style={{
            height: xlMatches ? 400 : lgMatches ? 300 : 200,
            backgroundImage:`url(${pictureUrl})`
        }}>
            <div className={styles.cost}>{group.price}</div>
            <div >{group.numberOfSeats}</div>
            <div >{group.start_time}</div>
            <div >{group.end_time}</div>
            <div className={styles.description}>
                <span className={styles.title}>{group.name}</span>
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