import {useTranslation} from "react-i18next";
import {changeLanguage} from '../Services/changeDataService'
import {useSnackbarQueue} from "../pages/snackbar";
import useHandleError from "../errorHandler";

export default function LanguageSetter() {
    const {t} = useTranslation();
    const showSuccess = useSnackbarQueue('success')
    const handleError = useHandleError()

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
            width: '100%',
            display: 'flex',
            justifyContent: 'space-between',
            fontFamily: 'Montserrat, sans-serif',
            fontSize: '1.4rem',
            alignItems: 'center'
        }}
             onClick={handleClick}
        >
            <p>{t("changeLanguage")}</p>
        </div>
    )
}