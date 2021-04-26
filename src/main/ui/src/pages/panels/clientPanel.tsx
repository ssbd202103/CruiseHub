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
    Link
} from 'react-router-dom'

import SettingsIcon from '@material-ui/icons/SettingsRounded'
import CruiseIcon from '@material-ui/icons/CardTravelRounded'

import ManageAccount from "./common/ManageAccount"
import PanelMenu from '../../components/PanelMenu'

import { useTranslation } from 'react-i18next'

import styles from '../../styles/clientPanel.module.css'

export default function ClientPanel() {
    const { t } = useTranslation()

    return (
        <Grid container className={styles.wrapper}>
            <Grid item xs={2}>
                <PanelMenu>
                    <List className={styles.menu} component="nav" aria-label="panel menu">
                        <Link to="cruises">
                            <ListItem button>
                                <ListItemIcon>
                                    <CruiseIcon style={{ fill: 'var(--white)' }} />
                                </ListItemIcon>
                                <ListItemText> {t("cruises")} </ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="settings">
                            <ListItem button>
                                <ListItemIcon>
                                    <SettingsIcon style={{ fill: 'var(--white)' }} />
                                </ListItemIcon>
                                <ListItemText>{t("settings")}</ListItemText>
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
                </Route>
            </Grid>
            {/* <h2>{t("client panel")}</h2>
            <div>
                <ManageAccount/>
            </div>
            <div>
                <Button variant="contained">
                    {t("logout")}
                </Button>
            </div> */}
        </Grid>
    )
}
