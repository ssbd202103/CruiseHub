import {useTranslation} from 'react-i18next'
import { useState } from "react";
import ListClient from "./moderator/ListClient";

import SettingsIcon from '@material-ui/icons/SettingsRounded'
import AccountsListIcon from '@material-ui/icons/PeopleAltRounded'
import GoBackIcon from '@material-ui/icons/ArrowBackRounded'

import {
    Grid,
    Button, 
    List,
    ListItem, 
    ListItemIcon,
    ListItemText
} from "@material-ui/core";

import {
    Link,
    Redirect,
    Route
} from 'react-router-dom'

import ManageAccount from "./common/ManageAccount"
import PanelMenu from '../../components/PanelMenu'
import RoundedButton from '../../components/RoundedButton'

import styles from '../../styles/moderatorPanel.module.css'


export default function ModeratorPanel() {
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
            <Redirect to="/panels/moderatorPanel/accounts" />
            <Grid item xs={2} md={3} xl={2}>
                <PanelMenu color="pink-dark">
                    <List className={styles.menu} component="nav" aria-label="panel menu">
                        <Link to="/panels/moderatorPanel/accounts">
                            <ListItem button>
                                <ListItemIcon>
                                    <AccountsListIcon style={{ fill: 'var(--white)' }} />
                                </ListItemIcon>
                                <ListItemText> {t("list accounts")} </ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/panels/moderatorPanel/settings">
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
                <Route exact path="/panels/moderatorPanel/accounts">
                    <h3> {t("list accounts")} </h3>
                    <ListClient />
                </Route>
                <Route exact path="/panels/moderatorPanel/settings">
                    <ManageAccount />
                    <RoundedButton
                        color="pink">
                    {t("logout")}
                    </RoundedButton>
                </Route>
            </Grid>
        </Grid>
    )
}