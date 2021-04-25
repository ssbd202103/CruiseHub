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
                <h1>{t('adminPanel')}</h1>
            </header>
            <div >
                <div>
                    <ListItem button
                              onClick={handleListClient}>
                        <ListItemText primary={t("list accounts")} />
                    </ListItem>

                    <ListItem button  onClick={handleManageAccount}>
                        <ListItemText primary={t("manage account")} />
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
                    {t("logout")}
                </Button>
            </div>
        </div>


    )
}