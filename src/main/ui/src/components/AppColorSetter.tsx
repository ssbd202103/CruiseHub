import { useTranslation } from "react-i18next";
import LightThemeIcon from '@material-ui/icons/WbSunnyRounded';
import DarkThemeIcon from '@material-ui/icons/Brightness3Rounded';
import {IconButton} from "@material-ui/core";
import {useSelector} from "react-redux";

import {selectDarkMode} from '../redux/slices/userSlice'
import {changeDarkMode} from '../Services/changeDataService'

export default function AppColorSetter() {
    const {t} = useTranslation();

    const darkMode = useSelector(selectDarkMode)

    const handleClick = () => {
        //TODO
        changeDarkMode().catch(error => {
            alert('ERROR. Go to console')
            console.log(error)
        })
    }

    return (
        <div style={{
            width: '100%',
            display: 'flex',
            justifyContent: 'space-between',
            fontFamily: 'Montserrat, sans-serif',
            fontSize: '1.4rem',
            alignItems: 'center'
        }}
             onClick={handleClick}
        >
            <p>{t("color")}</p>
            <IconButton>
                {
                    darkMode ?
                        <DarkThemeIcon
                            fontSize="large"
                            style={{fill: 'var(--dark)'}} /> :
                        <LightThemeIcon
                            fontSize="large"
                            style={{fill: 'var(--white)'}} />
                }
            </IconButton>
        </div>
    )
}