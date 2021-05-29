import React from "react";
import {useDispatch, useSelector} from "react-redux";
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
import {
    selectOtherAccessLevel,
    isUserEmpty,
    selectActiveAccessLevel,
    selectFirstName,
    selectSecondName, AccessLevelType, setActiveAccessLevel
} from "../redux/slices/userSlice";
import {logOut} from "../Services/userService";


export default function Authentication() {
    const {t} = useTranslation()
    const history = useHistory()
    const dispatch = useDispatch()

    const isEmpty = useSelector(isUserEmpty)

    const firstName = useSelector(selectFirstName)
    const secondName = useSelector(selectSecondName)
    const activeAccessLevel = useSelector(selectActiveAccessLevel)
    const accessLevelLabels = useSelector(selectOtherAccessLevel)

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

    const handleMenuItemClick = (accessLevel: AccessLevelType) => () => {
            dispatch(setActiveAccessLevel(accessLevel))
            history.push('/')
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
                            }}>{t(activeAccessLevel)}</Button>

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
                                                                        onClick={handleMenuItemClick(label)}
                                                                    >{t(label)}</MenuItem>
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
                        <Button
                            className={styles.link}
                            style={{
                                marginRight: 20,
                                color: 'var(--white)',
                                fontFamily: 'Montserrat, sans-serif'
                            }}
                            onClick={activeAccessLevel ? () => {history.push('/profile')} : undefined}
                        >
                            {firstName + ' ' + secondName}
                        </Button>
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
        </Box>
    )
}