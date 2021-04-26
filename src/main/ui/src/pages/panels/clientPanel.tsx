import {
    Box,
    Grid,
    Button,
    List,
    ListItem,
    ListItemIcon,
    ListItemText
} from '@material-ui/core'


import {
    Route,
    Link,
    Redirect
} from 'react-router-dom'

import SettingsIcon from '@material-ui/icons/SettingsRounded'
import CruiseIcon from '@material-ui/icons/CardTravelRounded'
import GoBackIcon from '@material-ui/icons/ArrowBackRounded'

import ManageAccount from "./common/ManageAccount"
import PanelMenu from '../../components/PanelMenu'
import RoundedButton from '../../components/RoundedButton'

import { useTranslation } from 'react-i18next'

import styles from '../../styles/clientPanel.module.css'

export default function ClientPanel() {
    const { t } = useTranslation()

    return (
        <Grid container className={styles.wrapper}>
            <Redirect to="/panels/clientPanel/cruises" />
            <Grid item xs={2}>
                <PanelMenu color="blue">
                    <List className={styles.menu} component="nav" aria-label="panel menu">
                        <Link to="/panels/clientPanel/cruises">
                            <ListItem button>
                                <ListItemIcon>
                                    <CruiseIcon style={{ fill: 'var(--white)' }} />
                                </ListItemIcon>
                                <ListItemText> {t("cruises")} </ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/panels/clientPanel/settings">
                            <ListItem button>
                                <ListItemIcon>
                                    <SettingsIcon style={{ fill: 'var(--white)' }} />
                                </ListItemIcon>
                                <ListItemText>{t("settings")}</ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/">
                            <ListItem button>
                                <ListItemIcon style={{fill: 'var(--white)'}}>
                                    <GoBackIcon />
                                </ListItemIcon>
                                <ListItemText>{t("go back")}</ListItemText>
                            </ListItem>
                        </Link>
                    </List>
                </PanelMenu>
            </Grid>

            <Grid item className={styles.content} xs={10}>
                <Route exact path="/panels/clientPanel/cruises">
                    <div> {t("my cruises")} </div>
                </Route>
                <Route exact path="/panels/clientPanel/settings">
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
