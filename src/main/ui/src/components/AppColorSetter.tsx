import {useTranslation} from "react-i18next";
import LightThemeIcon from '@material-ui/icons/WbSunnyRounded';
import DarkThemeIcon from '@material-ui/icons/Brightness3Rounded';
import {IconButton} from "@material-ui/core";
import {useSelector} from "react-redux";

import {selectDarkMode} from '../redux/slices/userSlice'
import {changeDarkMode} from '../Services/changeDataService'
import {useSnackbarQueue} from "../pages/snackbar";
import useHandleError from "../errorHandler";

export default function AppColorSetter() {
    const {t} = useTranslation();
    const showSuccess = useSnackbarQueue('success')
    const handleError = useHandleError()

    const darkMode = useSelector(selectDarkMode)

    const handleClick = () => {
        changeDarkMode().catch(error => {
            const message = error.response.data
            handleError(message)
        }).then(res => {
            showSuccess(t('successful action'))
        });
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
                            style={{fill: 'var(--dark)'}}/> :
                        <LightThemeIcon
                            fontSize="large"
                            style={{fill: 'var(--white)'}}/>
                }
            </IconButton>
        </div>
    )
}