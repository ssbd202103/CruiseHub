import {useTranslation} from 'react-i18next'
import {useState} from 'react'
import Button from "@material-ui/core/Button";
import ManageAccount from "./common/ManageAccount"
import {ListItem, ListItemText} from "@material-ui/core";

export default function WorkerPanel() {
    const {t} = useTranslation()

    const [manageAccount, setManage] = useState(true)
    const handleManageAccount = () => {
        setManage(state => !state)
    }
    return (
        <div>
            <header>
                <h1>{t('worker panel')}</h1>
            </header>
            <div>
                <ListItem button  onClick={handleManageAccount}>
                    <ListItemText primary={t("manage account")} />
                </ListItem>
            </div>

            <div style={{display: manageAccount ? "none" : "block"}}>
                <ManageAccount/>
            </div>

            <div>
                <Button variant="contained">
                    {t("logout")}
                </Button>
            </div>
        </div>
    )
}
