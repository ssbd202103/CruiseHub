import {useTranslation} from 'react-i18next'
import React, {useState} from "react";
import ListClient from "./admin/ListClient";

import {
    Grid,
    Button, 
    List, 
    ListItem, 
    ListItemIcon, 
    ListItemText
} from "@material-ui/core";

import SettingsIcon from '@material-ui/icons/SettingsRounded'
import AccountsListIcon from '@material-ui/icons/PeopleAltRounded'
import GoBackIcon from '@material-ui/icons/ArrowBackRounded'

import {
    Link,
    Route,
    Redirect
} from 'react-router-dom'

import ManageAccount from "./common/ManageAccount"
import PanelMenu from '../../components/PanelMenu'
import RoundedButton from '../../components/RoundedButton'

import ChangeAccountData from "./admin/ChangeAccountData"
import ChangeAccountPassword from "./admin/ChangeAccountPassword"
import GrantAccessLevel from "./admin/GrantAccessLevel"

import styles from '../../styles/moderatorPanel.module.css'


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
        <Grid container className={styles.wrapper}>
            <Redirect to="/panels/adminPanel/accounts" />
            <Grid item xs={2} md={3} xl={2}>
                <PanelMenu color="dark">
                    <List className={styles.menu} component="nav" aria-label="panel menu">
                        <Link to="/panels/adminPanel/accounts">
                            <ListItem button>
                                <ListItemIcon>
                                    <AccountsListIcon style={{ fill: 'var(--white)' }} />
                                </ListItemIcon>
                                <ListItemText> {t("list accounts")} </ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/panels/adminPanel/settings">
                            <ListItem button>
                                <ListItemIcon>
                                    <SettingsIcon style={{ fill: 'var(--white)' }} />
                                </ListItemIcon>
                                <ListItemText>{t("settings")}</ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/">
                            <ListItem button>
                                <ListItemIcon>
                                    <GoBackIcon style={{fill: 'var(--white)'}} />
                                </ListItemIcon>
                                <ListItemText>{t("go back")}</ListItemText>
                            </ListItem>
                        </Link>
                    </List>
                </PanelMenu>
            </Grid>

            <Grid item className={styles.content} xs={10} md={9} xl={10}>
                <Route exact path="/panels/adminPanel/accounts">
                    <h3> {t("list accounts")} </h3>
                    <ListClient />
                </Route>
                <Route exact path="/panels/adminPanel/settings">
                    <ManageAccount />
                    <RoundedButton
                        color="pink">
                    {t("logout")}
                    </RoundedButton>
                </Route>
                <Route path="/panels/adminPanel/ChangeAccountData">
                    <ChangeAccountData/>
                </Route>
                <Route path="/panels/adminPanel/ChangeAccountPassword">
                    <ChangeAccountPassword/>
                </Route>
                <Route path="/panels/adminPanel/GrantAccessLevel">
                    <GrantAccessLevel/>
                </Route>
            </Grid>
        </Grid>
        // <div>
        //     <header >
        //         <h1>{t('admin panel')}</h1>
        //     </header>
        //     <div >
        //         <div>
        //             <ListItem button
        //                       onClick={handleListClient}>
        //                 <ListItemText primary={t("list accounts")} />
        //             </ListItem>

        //             <ListItem button  onClick={handleManageAccount}>
        //                 <ListItemText primary={t("manage account")} />
        //             </ListItem>
        //         </div>
        //     </div>

        //     <div style={{display: listClient ? "none" : "block"}}>
        //          <ListClient />
        //     </div>

        //     <div style={{display: manageAccount ? "none" : "block"}}>
        //         <ManageAccount/>
        //     </div>


        //     <div>
        //         <Button variant="contained">
        //             {t("logout")}
        //         </Button>
        //     </div>
        // </div>


    )
}