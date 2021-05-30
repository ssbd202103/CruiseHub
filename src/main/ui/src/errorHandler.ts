import {useSnackbarQueue} from "./pages/snackbar";
import {useTranslation} from "react-i18next";
import {logOut} from "./Services/userService";


function useHandleError() {
    const showError = useSnackbarQueue('error')
    const {t} = useTranslation()
    const showWarning = useSnackbarQueue('warning')

    return (error: string | any, status: number = 0) => {
        if (status == 401 && error != "auth.incorrect.password" && error != "error.facade.noSuchElement") {
            showWarning(t("error.unauthorized"))
            setTimeout(logOut, 3000)
            return
        }

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