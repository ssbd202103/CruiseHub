import {useTranslation} from 'react-i18next'
import {useState} from 'react'

import ManageAccount from "./common/ManageAccount"
import {
    Grid,
    List,
    ListItem, 
    ListItemIcon,
    ListItemText
} from "@material-ui/core";

import {
    Route,
    Link,
    Redirect
} from 'react-router-dom'

import SettingsIcon from '@material-ui/icons/SettingsRounded'
import CruiseIcon from '@material-ui/icons/CardTravelRounded'
import GoBackIcon from '@material-ui/icons/ArrowBackRounded'

import PanelMenu from '../../components/PanelMenu'
import RoundedButton from '../../components/RoundedButton'

import styles from '../../styles/workerPanel.module.css'

export default function WorkerPanel() {
    const {t} = useTranslation()

    return (
        <Grid container className={styles.wrapper}>
            <Redirect to="/panels/workerPanel/cruises" />
            <Grid item xs={2} md={3}>
                <PanelMenu color="green-dark">
                    <List className={styles.menu} component="nav" aria-label="panel menu">
                        <Link to="/panels/workerPanel/cruises">
                            <ListItem button>
                                <ListItemIcon>
                                    <CruiseIcon style={{ fill: 'var(--white)' }} />
                                </ListItemIcon>
                                <ListItemText> {t("cruises")} </ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/panels/workerPanel/settings">
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

            <Grid item className={styles.content} xs={10} md={9}>
                <Route exact path="/panels/workerPanel/cruises">
                    <div> {t("my cruises")} </div>
                </Route>
                <Route exact path="/panels/workerPanel/settings">
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
