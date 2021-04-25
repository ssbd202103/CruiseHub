import Box from '@material-ui/core/Box'
import Button from "@material-ui/core/Button";
import ManageAccount from "./common/ManageAccount"

import {useTranslation} from 'react-i18next'

export default function ClientPanel() {
    const {t} = useTranslation()

    return (
        <Box>
            <h2>{t("client panel")}</h2>
            <div>
                <ManageAccount/>
            </div>
            <div>
                <Button variant="contained">
                    {t("logout")}
                </Button>
            </div>
        </Box>
    )
}
