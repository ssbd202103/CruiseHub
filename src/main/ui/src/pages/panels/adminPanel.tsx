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
import Header from "../../components/Header";
import RequestSomeonePasswordReset from "../reset/requestSomeonesPasswordReset";

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
        <>
            <Header />
            <Grid container className={styles.wrapper}>
                <Redirect to="/accounts" />
                <Grid item xs={2} md={3} xl={2}>
                    <PanelMenu color={!darkMode ? 'yellow-dark' : 'white-light'}>
                        <List className={styles.menu + ' ' + styles['menu-' + (!darkMode ? 'light' : 'dark')]} component="nav" aria-label="panel menu">
                            <Link to="/accounts">
                                <ListItem button>
                                    <ListItemIcon>
                                        <AccountsListIcon style={{ fill: `var(--${!darkMode ? 'white' : 'dark'})` }} />
                                    </ListItemIcon>
                                    <ListItemText> {t("list accounts")} </ListItemText>
                                </ListItem>
                            </Link>
                            <Link to="/settings">
                                <ListItem button>
                                    <ListItemIcon>
                                        <SettingsIcon style={{ fill: `var(--${!darkMode ? 'white' : 'dark'})` }} />
                                    </ListItemIcon>
                                    <ListItemText>{t("settings")}</ListItemText>
                                </ListItem>
                            </Link>
                        </List>
                    </PanelMenu>
                </Grid>

                <Grid item className={styles.content + ' ' + styles[`content-${!darkMode ? 'light' : 'dark'}`]} xs={10} md={9} xl={10}>
                    <Route path="/accounts">
                        <h3> {t("list accounts")} </h3>
                        <ListClient />
                    </Route>
                    <Route path="/settings">
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
                    <Route path="/ChangeAccountData">
                        <ChangeAccountData/>
                    </Route>
                    <Route path="/ChangeAccountPassword">
                        <ChangeAccountPassword/>
                    </Route>
                    <Route path="/GrantAccessLevel">
                        <GrantAccessLevel/>
                    </Route>
                    <Route path="/ChangeAccessLevelState">
                        <ChangeAccessLevelState/>
                    </Route>
                    <Route path="/reset/resetSomebodyPassword">
                        <RequestSomeonePasswordReset/>
                    </Route>
                </Grid>
            </Grid>
        </>
    )
}