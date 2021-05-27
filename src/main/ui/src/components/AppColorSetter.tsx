import { useTranslation } from "react-i18next";
import LightThemeIcon from '@material-ui/icons/WbSunnyRounded';
import DarkThemeIcon from '@material-ui/icons/Brightness3Rounded';
import {IconButton} from "@material-ui/core";
import {useSelector} from "react-redux";

import {selectDarkMode} from '../redux/slices/userSlice'
import {changeDarkMode} from '../Services/changeDataService'
import {useSnackbarQueue} from "../pages/snackbar";
import PopupAcceptAction from "../PopupAcceptAction";
import {useState} from "react";

export default function AppColorSetter() {
    const {t} = useTranslation();
    const showSuccess = useSnackbarQueue('success')
    const showError = useSnackbarQueue('error')
    const [buttonPopupAcceptAction, setButtonPopupAcceptAction] = useState(false);
    const darkMode = useSelector(selectDarkMode)

    const handleClick = () => {
        //TODO
        changeDarkMode().then(res=>{
            setButtonPopupAcceptAction(false)
            showSuccess(t('successful action'))
        }).catch(error => {
            setButtonPopupAcceptAction(false)
            const message = error.response.data
            showError(t(message))
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
             onClick={()=>setButtonPopupAcceptAction(true)}
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
            <PopupAcceptAction
                open={buttonPopupAcceptAction}
                onConfirm={handleClick}
                onCancel={() => {setButtonPopupAcceptAction(false)
                }}
            />
        </div>
    )
}