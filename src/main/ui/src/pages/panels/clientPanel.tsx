import {
    Grid,
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

import PanelMenu from '../../components/PanelMenu'
import RoundedButton from '../../components/RoundedButton'

import { useTranslation } from 'react-i18next'

import styles from '../../styles/clientPanel.module.css'
import manageStyles from '../../styles/ManageAccount.module.css'
import AppColorSetter from "../../components/AppColorSetter";
import {useSelector} from "react-redux";
import {selectColor} from "../../redux/slices/colorSlice";
import ChangeEmail from "../../components/changeData/ChangeEmail";
import {useState} from "react";
import ChangeClientData from "../../components/changeData/ChangeClientData";
import ChangePassword from "../../components/changeData/ChangePassword";
import ChangeAddress from "../../components/changeData/ChangeAddress";
import LogOutRoundedButton from "../../components/LogOutRoundedButton";

export default function ClientPanel() {
    const { t } = useTranslation()

    const color = useSelector(selectColor)

    const [isEmailEdit, setIsEmailEdit] = useState(false)
    const [isDataEdit, setIsDataEdit] = useState(false)
    const [isAddressEdit, setIsAddressEdit] = useState(false)
    const [isPasswordEdit, setIsPasswordEdit] = useState(false)

    const handleIsEmailEdit = () => {
        setIsEmailEdit(true)
        setIsDataEdit(false)
        setIsAddressEdit(false)
        setIsPasswordEdit(false)
    }

    const handeIsDataEdit = () => {
        setIsDataEdit(true)
        setIsEmailEdit(false)
        setIsAddressEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsAddressEdit = () => {
        setIsAddressEdit(true)
        setIsDataEdit(false)
        setIsEmailEdit(false)
        setIsPasswordEdit(false)
    }

    const handleIsPasswordEdit = () => {
        setIsPasswordEdit(true)
        setIsDataEdit(false)
        setIsEmailEdit(false)
        setIsAddressEdit(false)
    }

    return (
        <Grid container className={styles.wrapper}>
            <Redirect to="/panels/clientPanel/cruises" />
            <Grid item xs={2} md={3} xl={2}>
                <PanelMenu color={color ? 'blue' : 'white-dark'}>
                    <List className={styles.menu + ' ' + styles['menu-' + (color ? 'light' : 'dark')]} component="nav" aria-label="panel menu">
                        <Link to="/panels/clientPanel/cruises">
                            <ListItem button>
                                <ListItemIcon>
                                    <CruiseIcon style={{ fill: `var(--${color ? 'white' : 'dark'})` }} />
                                </ListItemIcon>
                                <ListItemText> {t("cruises")} </ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/panels/clientPanel/settings">
                            <ListItem button>
                                <ListItemIcon>
                                    <SettingsIcon style={{ fill: `var(--${color ? 'white' : 'dark'})` }} />
                                </ListItemIcon>
                                <ListItemText>{t("settings")}</ListItemText>
                            </ListItem>
                        </Link>
                        <Link to="/">
                            <ListItem button>
                                <ListItemIcon style={{fill: `var(--${color ? 'white' : 'dark'})` }}>
                                    <GoBackIcon />
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
                <Route exact path="/panels/clientPanel/cruises">
                    <div> {t("my cruises")} </div>
                </Route>
                <Route exact path="/panels/clientPanel/settings">
                    <Grid container className={manageStyles.wrapper + ' ' + manageStyles[`text-${color ? 'white' : 'dark'}`]} >
                        <ChangeClientData
                            open={isDataEdit}
                            onOpen={handeIsDataEdit}
                            onConfirm={() => {setIsDataEdit(false)}}
                            onCancel={() => {setIsDataEdit(false)}} />
                        <ChangeAddress
                            open={isAddressEdit}
                            onOpen={handleIsAddressEdit}
                            onConfirm={() => {setIsAddressEdit(false)}}
                            onCancel={() => {setIsAddressEdit(false)}} />
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
                    </Grid>
                    <LogOutRoundedButton />
                </Route>
            </Grid>
        </Grid>
    )
}
