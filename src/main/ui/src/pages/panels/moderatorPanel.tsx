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
import {useSelector} from "react-redux";
import {selectColor} from "../../redux/slices/colorSlice";
import AppColorSetter from "../../components/AppColorSetter";


export default function ModeratorPanel() {
    const {t} = useTranslation()
    const [listClient, setListClient] = useState(true)
    const handleListClient = () => {
        setListClient(state => !state)
        setManage(true)
    }

    const color = useSelector(selectColor)

    const [manageAccount, setManage] = useState(true)
    const handleManageAccount = () => {
        setManage(state => !state)
        setListClient(true)
    }
    return (
        <Grid container className={styles.wrapper}>
            <Redirect to="/panels/moderatorPanel/accounts" />
            <Grid item xs={2} md={3} xl={2}>
                <PanelMenu color={color ? 'pink-dark' : 'white'}>
                    <List className={styles.menu + ' ' + styles['menu-' + (color ? 'light' : 'dark')]} component="nav" aria-label="panel menu">
                        <Link to="/panels/moderatorPanel/accounts">
                            <ListItem button>
                                <ListItemIcon>
                                    <AccountsListIcon style={{ fill: `var(--${color ? 'white' : 'dark'})` }} />
                                </ListItemIcon>
                                <ListItemText> {t("list accounts")} </ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/panels/moderatorPanel/settings">
                            <ListItem button>
                                <ListItemIcon>
                                    <SettingsIcon style={{ fill: `var(--${color ? 'white' : 'dark'})` }} />
                                </ListItemIcon>
                                <ListItemText>{t("settings")}</ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/">
                            <ListItem button>
                                <ListItemIcon>
                                    <GoBackIcon style={{fill: `var(--${color ? 'white' : 'dark'})` }} />
                                </ListItemIcon>
                                <ListItemText>{t("go back")}</ListItemText>
                            </ListItem>
                        </Link>
                        <ListItem button>
                            <AppColorSetter />
                        </ListItem>
                    </List>
                </PanelMenu>
            </Grid>

            <Grid item className={styles.content + ' ' + styles[`content-${color ? 'light' : 'dark'}`]} xs={10} md={9} xl={10}>
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