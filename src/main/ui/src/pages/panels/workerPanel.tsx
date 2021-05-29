import {useTranslation} from 'react-i18next'

import {Grid, List, ListItem, ListItemIcon, ListItemText} from "@material-ui/core";

import {Link, Redirect, Route} from 'react-router-dom'

import SettingsIcon from '@material-ui/icons/SettingsRounded'
import CruiseIcon from '@material-ui/icons/CardTravelRounded'
import GoBackIcon from '@material-ui/icons/ArrowBackRounded'

import PanelMenu from '../../components/PanelMenu'

import styles from '../../styles/workerPanel.module.css'
import {useSelector} from "react-redux";
import {selectDarkMode} from "../../redux/slices/userSlice";
import AppColorSetter from "../../components/AppColorSetter";
import LogOutRoundedButton from "../../components/LogOutRoundedButton";
import ChangeBusinessWorkerData from "../../components/changeData/ChangeBusinessWorkerData";
import React, {useState} from "react";
import ChangeEmail from "../../components/changeData/ChangeEmail";
import ChangePassword from "../../components/changeData/ChangePassword";
import Header from "../../components/Header";
import Breadcrumb from "../../components/Breadcrumb";

export default function WorkerPanel() {
    const {t} = useTranslation()

    const darkMode = useSelector(selectDarkMode)

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
            <Breadcrumb />
            <Grid container className={styles.wrapper}>
                <Redirect to="/cruises" />
                <Grid item xs={2} md={3} xl={2}>
                    <PanelMenu color={!darkMode ? 'green-dark' : 'white'}>
                        <List className={styles.menu + ' ' + styles['menu-' + (!darkMode ? 'light' : 'dark')]} component="nav" aria-label="panel menu">
                            <Link to="/cruises">
                                <ListItem button>
                                    <ListItemIcon>
                                        <CruiseIcon style={{ fill: `var(--${!darkMode ? 'white' : 'dark'})` }} />
                                    </ListItemIcon>
                                    <ListItemText> {t("cruises")} </ListItemText>
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
                    <Route exact path="/cruises">
                        <div> {t("my cruises")} </div>
                    </Route>
                    <Route exact path="/settings">
                        <ChangeBusinessWorkerData
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
                </Grid>
            </Grid>
        </>
    )
}
