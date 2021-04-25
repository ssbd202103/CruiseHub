import {useTranslation} from 'react-i18next'
import React, {useState} from "react";
import ListClient from "./admin/ListClient";

import ManageAccount from "./admin/ManageAccount"
import {Button, ListItem, ListItemText} from "@material-ui/core";


export default function AdminPanel() {
    const {t} = useTranslation()
    const [listClient, setListClient] = useState(true)
    const handleListClient = () => {
        setListClient(state => !state)
        setManage(true)
    }

    const [manageAccount, setManage] = useState(true)
    const handleManageAccount = () => {
        setManage(state => !state)
        setListClient(true)
    }
    return (

        <div>
            <header >
                <h1>{t('AdminPanel')}</h1>
            </header>
            <div >
                <div>
                    <ListItem button
                              onClick={handleListClient}>
                        <ListItemText primary={t("List accounts")} />
                    </ListItem>

                    <ListItem button  onClick={handleManageAccount}>
                        <ListItemText primary={t("Manage account")} />
                    </ListItem>
                </div>
            </div>

            <div style={{display: listClient ? "none" : "block"}}>
                 <ListClient />
            </div>

            <div style={{display: manageAccount ? "none" : "block"}}>
                <ManageAccount/>
            </div>


            <div>
                <Button variant="contained">
                    {t("Logout")}
                </Button>
            </div>
        </div>


    )
}