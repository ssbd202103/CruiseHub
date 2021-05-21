import React from "react";
import {useSelector} from "react-redux";
import {Link, useHistory} from "react-router-dom";
import styles from "../styles/Header.module.css";
import RoundedButton from "./RoundedButton";
import Box from "@material-ui/core/Box";
import {useTranslation} from "react-i18next";
import {
    Button,
    Popper,
    MenuList,
    MenuItem,
    Paper,
    Fade,
    ClickAwayListener
} from '@material-ui/core'
import {getAccessLevelLabels, isUserEmpty, selectFirstName, selectSecondName} from "../redux/slices/userSlice";
import {logOut} from "../Services/userService";

const panels = {
    "CLIENT": 'clientPanel',
    "BUSINESS_WORKER": 'workerPanel',
    "MODERATOR": 'moderatorPanel',
    "ADMINISTRATOR": 'adminPanel'
}


export default function Authentication() {
    const {t} = useTranslation()
    const history = useHistory()

    const isEmpty = useSelector(isUserEmpty)

    const firstName = useSelector(selectFirstName)
    const secondName = useSelector(selectSecondName)
    const accessLevelLabels = useSelector(getAccessLevelLabels)

    console.log(accessLevelLabels)

    const [anchorEl, setAnchorEl] = React.useState<HTMLButtonElement | null>(null);
    const [open, setOpen] = React.useState(false);

    const handleCredClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
        setOpen(prev => !prev);
    };

    const handleClose = () => {
        setOpen(false)
        setAnchorEl(null)
    }

    const handleMenuItemClick = (panel: string) => () => {
        history.push(`/panels/${panel}`)
    }

    const handleLogOut = () => {
        logOut()
    }

    return (
        <Box>
            {
                isEmpty ? (
                    <>
                        <Link to="signin">
                            <a style={{marginRight: 20}} className={styles.link}>{t("signin")}</a>
                        </Link>

                        <Link to="signup/client">
                            <RoundedButton
                                color="pink"
                                style={{
                                    fontSize: '1rem',
                                    padding: '10px 20px',
                                    textTransform: 'none'
                                }}
                            >{t("signup")}</RoundedButton>
                        </Link>
                    </>
                ) : (
                    <>
                        <Button
                            onClick={handleCredClick}
                            className={styles.link}
                            style={{
                                marginRight: 20,
                                color: 'var(--white)',
                                fontFamily: 'Montserrat, sans-serif'
                            }}>{firstName + ' ' + secondName}</Button>

                        <Popper open={open} anchorEl={anchorEl} placement="bottom-end" transition style={{zIndex:5001}}>
                            {({ TransitionProps }) => (
                                <Fade {...TransitionProps} timeout={350}>
                                    <Paper style={{padding: 10}}>
                                        <ClickAwayListener onClickAway={handleClose}>
                                            {
                                                accessLevelLabels.length ? (
                                                    <MenuList>
                                                        {
                                                            accessLevelLabels.map(label => (
                                                                    <MenuItem
                                                                        style={{
                                                                            fontFamily: 'Montserrat, sans-serif',
                                                                            fontSize: '1.2rem'
                                                                        }}
                                                                        onClick={handleMenuItemClick(panels[label])}
                                                                    >{t(panels[label])}</MenuItem>
                                                                )
                                                            )
                                                        }
                                                    </MenuList>
                                                ) : <p style={{
                                                    fontFamily: 'Montserrat, sans-serif',
                                                    fontSize: '1.2rem'
                                                }}>{t('error.emptypanellist')}</p>
                                            }
                                        </ClickAwayListener>
                                    </Paper>
                                </Fade>
                            )}
                        </Popper>

                        <RoundedButton
                            color="pink"
                            style={{
                                fontSize: '1rem',
                                padding: '10px 20px',
                                textTransform: 'none'
                            }}
                            onClick={handleLogOut}
                        >{t("logout")}</RoundedButton>
                    </>
                )
            }


            {/*<Link to="panels/adminPanel">*/}
            {/*    <a style={{marginRight: 20}} className={styles.link}>{t("adminPanel")}</a>*/}
            {/*</Link>*/}
            {/*<Link to="panels/moderatorPanel">*/}
            {/*    <a style={{marginRight: 20}} className={styles.link}>{t("moderatorPanel")}</a>*/}
            {/*</Link>*/}
            {/*<Link to="panels/clientPanel">*/}
            {/*    <a style={{marginRight: 20}} className={styles.link}>{t("clientPanel")}</a>*/}
            {/*</Link>*/}
            {/*<Link to="panels/workerPanel">*/}
            {/*    <a style={{marginRight: 20}} className={styles.link}>{t("workerPanel")}</a>*/}
            {/*</Link>*/}
        </Box>
    )
}