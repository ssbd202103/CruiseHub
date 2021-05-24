import {useTranslation} from 'react-i18next'
import React, {useState} from "react";
import ListClient from "./moderator/ListClient";

import SettingsIcon from '@material-ui/icons/SettingsRounded'
import AccountsListIcon from '@material-ui/icons/PeopleAltRounded'
import GoBackIcon from '@material-ui/icons/ArrowBackRounded'

import {Grid, List, ListItem, ListItemIcon, ListItemText} from "@material-ui/core";

import {Link, Redirect, Route} from 'react-router-dom'

import PanelMenu from '../../components/PanelMenu'
import styles from '../../styles/moderatorPanel.module.css'
import {useSelector} from "react-redux";
import {selectDarkMode} from "../../redux/slices/userSlice";
import AppColorSetter from "../../components/AppColorSetter";
import LogOutRoundedButton from "../../components/LogOutRoundedButton";
import ChangeEmail from "../../components/changeData/ChangeEmail";
import ChangePassword from "../../components/changeData/ChangePassword";
import ChangeModeratorData from "../../components/changeData/ChangeModeratorData";
import ManageWorkers from "./moderator/ManageBusinessWorkers";
import ChangeAccountData from "./admin/ChangeAccountData";


export default function ModeratorPanel() {
    const {t} = useTranslation()
    const [listClient, setListClient] = useState(true)
    const handleListClient = () => {
        setListClient(state => !state)
        setManage(true)
    }

    const darkModel = useSelector(selectDarkMode)

    const [manageAccount, setManage] = useState(true)
    const handleManageAccount = () => {
        setManage(state => !state)
        setListClient(true)
    }

    const [isEmailEdit, setIsEmailEdit] = useState(false)
    const [isDataEdit, setIsDataEdit] = useState(false)
    const [isPasswordEdit, setIsPasswordEdit] = useState(false)

    const handleIsEmailEdit = () => {
        setIsEmailEdit(true)
        setIsDataEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsDataEdit = () => {
        setIsDataEdit(true)
        setIsEmailEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsPasswordEdit = () => {
        setIsPasswordEdit(true)
        setIsDataEdit(false)
        setIsEmailEdit(false)
    }

    return (
        <Grid container className={styles.wrapper}>
            <Redirect to="/panels/moderatorPanel/accounts" />
            <Grid item xs={2} md={3} xl={2}>
                <PanelMenu color={!darkModel ? 'pink-dark' : 'white'}>
                    <List className={styles.menu + ' ' + styles['menu-' + (!darkModel ? 'light' : 'dark')]} component="nav" aria-label="panel menu">
                        <Link to="/panels/moderatorPanel/accounts">
                            <ListItem button>
                                <ListItemIcon>
                                    <AccountsListIcon style={{ fill: `var(--${!darkModel ? 'white' : 'dark'})` }} />
                                </ListItemIcon>
                                <ListItemText> {t("list accounts")} </ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/panels/moderatorPanel/ManageWorkers">
                            <ListItem button>
                                <ListItemIcon>
                                    <AccountsListIcon style={{ fill: `var(--${!darkModel ? 'white' : 'dark'})` }} />
                                </ListItemIcon>
                                <ListItemText> {t("Manage business workers")} </ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/panels/moderatorPanel/settings">
                            <ListItem button>
                                <ListItemIcon>
                                    <SettingsIcon style={{ fill: `var(--${!darkModel ? 'white' : 'dark'})` }} />
                                </ListItemIcon>
                                <ListItemText>{t("settings")}</ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/">
                            <ListItem button>
                                <ListItemIcon>
                                    <GoBackIcon style={{fill: `var(--${!darkModel ? 'white' : 'dark'})` }} />
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

            <Grid item className={styles.content + ' ' + styles[`content-${!darkModel ? 'light' : 'dark'}`]} xs={10} md={9} xl={10}>
                <Route exact path="/panels/moderatorPanel/accounts">
                    <h3> {t("list accounts")} </h3>
                    <ListClient />
                </Route>
                <Route exact path="/panels/moderatorPanel/settings">
                    <ChangeModeratorData
                        open={isDataEdit}
                        onOpen={handleIsDataEdit}
                        onConfirm={() => {setIsDataEdit(false)}}
                        onCancel={() => {setIsDataEdit(false)}} />
                    <ChangeEmail
                        open={isEmailEdit}
                        onOpen={handleIsEmailEdit}
                        onConfirm={() => {setIsEmailEdit(false)}}
                        onCancel={() => {setIsEmailEdit(false)}} />
                    <ChangePassword
                        open={isPasswordEdit}
                        onOpen={handleIsPasswordEdit}
                        onConfirm={() => {setIsPasswordEdit(false)}}
                        onCancel={() => {setIsPasswordEdit(false)}} />
                    <LogOutRoundedButton />
                </Route>
                <Route exact path="/panels/moderatorPanel/ManageWorkers">
                    <ManageWorkers/>
                </Route>
            </Grid>
        </Grid>
    )
}