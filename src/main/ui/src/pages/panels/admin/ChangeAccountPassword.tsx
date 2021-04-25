import {useState} from 'react'

import Box from '@material-ui/core/Box'
import Button from "@material-ui/core/Button";

import {useTranslation} from 'react-i18next'

export default function ChangeAccountPassword() {
    const {t} = useTranslation()

    const [ChangPasswd, setChangPasswd] = useState(false)


    //Functions for password
    const handleChangPasswd = () => {
        setChangPasswd(state => !state)
    }
    const changePassword = () => {
        //Place for transfer function (change password in database)
        handleChangPasswd()
    }

    return (
        <Box>
            <div style={{display: ChangPasswd ? "none" : "block"}}>
                <h3>{t("password")}</h3>
                <Button variant="contained"
                        onClick={handleChangPasswd}
                >{t("passwordChange")}</Button>
            </div>
            <div style={{display: ChangPasswd ? "block" : "none"}}>
                <h3>{t("passwordChange")}</h3>
                <input type="password" placeholder={t("oldPassword")}/>
                <input type="password" placeholder={t("newPassword")}/>
                <input type="password" placeholder={t("newPasswordConfirm")}/>
                <Button variant="contained"
                        onClick={changePassword}
                >{t("confirm")}</Button>
            </div>

        </Box>
    )
}