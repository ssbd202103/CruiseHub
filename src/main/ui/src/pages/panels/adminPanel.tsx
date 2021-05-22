import {useTranslation} from 'react-i18next'
import React, {useState} from "react";
import ListClient from "./admin/ListClient";


import {Grid, List, ListItem, ListItemIcon, ListItemText} from "@material-ui/core";

import SettingsIcon from '@material-ui/icons/SettingsRounded'
import AccountsListIcon from '@material-ui/icons/PeopleAltRounded'
import GoBackIcon from '@material-ui/icons/ArrowBackRounded'

import {Link, Redirect, Route} from 'react-router-dom'

import PanelMenu from '../../components/PanelMenu'

import ChangeAccountData from "./admin/ChangeAccountData"
import ChangeAccountPassword from "./admin/ChangeAccountPassword"
import GrantAccessLevel from "./admin/GrantAccessLevel"
import ChangeAccessLevelState from "./admin/ChangeAccessLevelState"

import styles from '../../styles/moderatorPanel.module.css'
import AppColorSetter from "../../components/AppColorSetter";

import {selectDarkMode} from "../../redux/slices/userSlice";
import {useSelector} from "react-redux";
import LogOutRoundedButton from "../../components/LogOutRoundedButton";
import ChangeEmail from "../../components/changeData/ChangeEmail";
import ChangePassword from "../../components/changeData/ChangePassword";
import ChangeAdministratorData from "../../components/changeData/ChangeAdministratorData";

export default function AdminPanel() {
    const {t} = useTranslation()
    const [listClient, setListClient] = useState(true)
    const handleListClient = () => {
        setListClient(state => !state)
        setManage(true)
    }

    const darkMode = useSelector(selectDarkMode)

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
            <Redirect to="/panels/adminPanel/accounts" />
            <Grid item xs={2} md={3} xl={2}>
                <PanelMenu color={!darkMode ? 'yellow-dark' : 'white-light'}>
                    <List className={styles.menu + ' ' + styles['menu-' + (!darkMode ? 'light' : 'dark')]} component="nav" aria-label="panel menu">
                        <Link to="/panels/adminPanel/accounts">
                            <ListItem button>
                                <ListItemIcon>
                                    <AccountsListIcon style={{ fill: `var(--${!darkMode ? 'white' : 'dark'})` }} />
                                </ListItemIcon>
                                <ListItemText> {t("list accounts")} </ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/panels/adminPanel/settings">
                            <ListItem button>
                                <ListItemIcon>
                                    <SettingsIcon style={{ fill: `var(--${!darkMode ? 'white' : 'dark'})` }} />
                                </ListItemIcon>
                                <ListItemText>{t("settings")}</ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/">
                            <ListItem button>
                                <ListItemIcon>
                                    <GoBackIcon style={{fill: `var(--${!darkMode ? 'white' : 'dark'})` }} />
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

            <Grid item className={styles.content + ' ' + styles[`content-${!darkMode ? 'light' : 'dark'}`]} xs={10} md={9} xl={10}>
                <Route exact path="/panels/adminPanel/accounts">
                    <h3> {t("list accounts")} </h3>
                    <ListClient />
                </Route>
                <Route exact path="/panels/adminPanel/settings">
                    <ChangeAdministratorData
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
                <Route path="/panels/adminPanel/ChangeAccountData">
                    <ChangeAccountData/>
                </Route>
                <Route path="/panels/adminPanel/ChangeAccountPassword">
                    <ChangeAccountPassword/>
                </Route>
                <Route path="/panels/adminPanel/GrantAccessLevel">
                    <GrantAccessLevel/>
                </Route>
                <Route path="/panels/adminPanel/ChangeAccessLevelState">
                    <ChangeAccessLevelState/>
                </Route>
            </Grid>
        </Grid>
    )
}