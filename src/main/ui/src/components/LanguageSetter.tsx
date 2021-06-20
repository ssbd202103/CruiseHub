import {useTranslation} from "react-i18next";
import {changeLanguage} from '../Services/changeDataService'
import {useSnackbarQueue} from "../pages/snackbar";
import useHandleError from "../errorHandler";
import {useSelector} from "react-redux";
import {selectDarkMode, selectLanguage} from "../redux/slices/userSlice";

export default function LanguageSetter() {
    const {t} = useTranslation();
    const showSuccess = useSnackbarQueue('success')
    const handleError = useHandleError()
    const darkMode = useSelector(selectDarkMode)
    const language = useSelector(selectLanguage)

    const handleClick = () => {
        changeLanguage().then(res => {
            showSuccess(t('successful action'))
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        });
    }

    return (
        <div style={{
                margin: '0 16px',
                cursor: 'pointer',
                color: 'var(--white)',
                display: 'flex',
                justifyContent: 'space-between',
                fontFamily: 'Montserrat, sans-serif',
                fontSize: '1.4rem',
                alignItems: 'center',
            }}
             onClick={handleClick}
        >
            <p>{language}</p>
        </div>
    )
}