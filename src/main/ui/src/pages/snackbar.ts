import {useSnackbar} from "notistack";
import Slide from "@material-ui/core/Slide";
import {ComponentType} from "react";
import {TransitionProps} from "@material-ui/core/transitions";

export function useErrorSnackbar() {
    const {enqueueSnackbar} = useSnackbar();

    return (message: string) => {
        enqueueSnackbar(message, {
            variant: 'error',
            anchorOrigin: {
                vertical: 'bottom',
                horizontal: 'left',
            },
            TransitionComponent: Slide as ComponentType<TransitionProps>,
        })
    }
}