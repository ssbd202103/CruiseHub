import {useSnackbarQueue} from "./pages/snackbar";
import {useTranslation} from "react-i18next";


function useHandleError() {
    const showError = useSnackbarQueue('error')
    const {t} = useTranslation()

    return (error: string | any) => {
        if (typeof error === 'string') {
            showError(t(error))
            return
        }
        for (let message of Object.values(error.errors)) {
            showError(t(message as string))
        }

    }
}

export default useHandleError;