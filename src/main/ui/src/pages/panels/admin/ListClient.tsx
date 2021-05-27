import React, {ChangeEvent, useEffect, useState} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import {Button, TextField} from "@material-ui/core";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import DarkedTextField from "../../../components/DarkedTextField";
import axios from "axios";
import {useSelector} from "react-redux";
import {selectDarkMode} from "../../../redux/slices/userSlice";
import {getAccountDetailsAbout, getAllAccounts} from "../../../Services/accountsService";
import {selectToken} from "../../../redux/slices/tokenSlice";
import {useSnackbarQueue} from "../../snackbar";
import Autocomplete, {createFilterOptions} from '@material-ui/lab/Autocomplete';


interface UnblockAccountParams {
    login: string;
    etag: string;
    version: bigint,
    token: string
}


const unblockAccount = ({login, etag, version, token}: UnblockAccountParams) => {
    const json = JSON.stringify({
            login: login,
            version: version,
        }
    );
    return axios.put('/api/account/unblock', json, {
        headers: {
            'Content-Type': 'application/json',
            'If-Match': etag,
            'Authorization': `Bearer ${token}`
        }

    }).then(response => {
        return response.status == 200;
    })
};

function refresh() {
    window.location.reload();
}

const blockAccount = ({login, etag, version, token}: UnblockAccountParams) => {
    const json = JSON.stringify({
            login: login,
            version: version,
        }
    );
    return axios.put('/api/account/block', json, {
        headers: {
            'Content-Type': 'application/json',
            'If-Match': etag,
            'Authorization': `Bearer ${token}`
        }

    }).then(response => {
        return response.status == 200;
    });
};

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

const useButtonStyles = makeStyles({
    root: {
        fontFamily: '"Montserrat", sans-serif',
        color: 'var(--white)',
        backgroundColor: "var(--blue)",
        padding: '8px 16px',
        margin: '0 16px',
        '&:hover': {
            backgroundColor: "var(--blue-dark)",
        }
    }
})

function createData(
    login: string,
    firstName: string,
    secondName: string,
    email: string,
    active: boolean,
    accessLevels: string[],
    etag: string,
    version: bigint,
) {
    return {
        login: login,
        firstName: firstName,
        secondName: secondName,
        email: email,
        active: active,
        accessLevels: accessLevels,
        etag: etag,
        version: version
    };
}


export interface RowProps {
    row: ReturnType<typeof createData>,
    style: React.CSSProperties
}

function Row(props: RowProps) {
    const {row} = props;
    const {style} = props;
    const {t} = useTranslation();
    const [open, setOpen] = React.useState(false);
    const [buttonText, setButtonText] = useState("true");
    const token = useSelector(selectToken)

    const showError = useSnackbarQueue('error')

    const classes = useRowStyles();
    const buttonClass = useButtonStyles();

    const handleSetOpen = async () => {
        getAccountDetailsAbout(row.login).then(res => {
            sessionStorage.setItem("changeAccountData", JSON.stringify(res.data));
            setOpen(state => !state);
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        });

    }

    const setCurrentGrantAccessLevelAccount = () => {
        sessionStorage.setItem('grantAccessLevelAccount', JSON.stringify(row));
    }


    const setCurrentResetPasswordAccount = () => {
        sessionStorage.setItem('resetPasswordAccount', JSON.stringify(row));
    }

    const setCurrentChangeAccessLevelStateAccount = async () => {
        getAccountDetailsAbout(row.login).then(res => {
            sessionStorage.setItem("changeAccessLevelStateAccount", JSON.stringify(res.data));
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        });
    }

    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell>
                    <IconButton aria-label="expand row" size="small" onClick={handleSetOpen}>
                        {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>

                <TableCell component="th" scope="row" style={style}>
                    {row.login}
                </TableCell>
                <TableCell style={style}>{row.firstName}</TableCell>
                <TableCell style={style}>{row.secondName}</TableCell>
                <TableCell style={style}>{row.email}</TableCell>
                <TableCell style={style}>{row.active.toString()}</TableCell>
                <TableCell style={style}>{row.accessLevels.toString()}</TableCell>

            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box margin={1}>
                            <Table size="small" aria-label="clients">
                                <TableHead>

                                </TableHead>
                                <TableBody>
                                    <TableRow>
                                        <TableCell align="center">
                                            <Link to="/ChangeAccountData">
                                                <Button className={buttonClass.root}>{t("edit")}</Button>
                                            </Link>
                                            <Link to="/reset/resetSomebodyPassword">
                                                <Button onClick={setCurrentResetPasswordAccount}
                                                        className={buttonClass.root}>{t("reset password")}</Button>
                                            </Link>

                                            <Button className={buttonClass.root} onClick={() => {
                                                if (row.active) {
                                                    blockAccount({
                                                        etag: row.etag,
                                                        login: row.login, version: row.version, token: token
                                                    }).then(res => {
                                                        refresh()
                                                    }).catch(error => {
                                                        const message = error.response.data
                                                        showError(t(message))
                                                    });
                                                } else {
                                                    unblockAccount({
                                                        etag: row.etag,
                                                        login: row.login, version: row.version, token: token
                                                    })
                                                        .then(res => {
                                                            refresh()
                                                        }).catch(error => {
                                                        const message = error.response.data
                                                        showError(t(message))
                                                    });

                                                }
                                            }}>{row.active ? t("block") : t("unblock")}</Button>

                                            <Link to="/GrantAccessLevel/">
                                                <Button onClick={setCurrentGrantAccessLevelAccount}
                                                        className={buttonClass.root}>{t("grant access level")}</Button>
                                            </Link>

                                            <Link to="/ChangeAccessLevelState/">
                                                <Button onClick={setCurrentChangeAccessLevelStateAccount}
                                                        className={buttonClass.root}>{t("change access level state")}</Button>
                                            </Link>
                                        </TableCell>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}


export default function AdminListClient() {
    const [users, setUsers] = useState([]);
    const [searchInput, setSearchInput] = useState("");

    const showError = useSnackbarQueue('error')

    const darkMode = useSelector(selectDarkMode)

    useEffect(() => {
        getAllAccounts().then(res => {
            setUsers(res.data)
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        })
    }, []);

    function search(rows: any[]) {
        if (Array.isArray(rows) && rows.length) {
            const filteredAccount = rows.filter(
                row => row.props.row.firstName.concat(" ", row.props.row.secondName).toLowerCase().indexOf(searchInput.toLowerCase()) > -1
            );

            filteredAccount.forEach(account => (accounts.includes(account.props.row.firstName + " " + account.props.row.secondName) ?
                "" : accounts.push(account.props.row.firstName + " " + account.props.row.secondName)));
            return filteredAccount
        } else {
            return rows;
        }
    }

    const {t} = useTranslation()

    const accounts: String[] = [];


    return (
        <div>
            <div>
                <Autocomplete
                    options={accounts}
                    inputValue={searchInput}
                    style={{width: 300}}
                    noOptionsText={t('no options')}
                    onChange={(event, value) => {
                        setSearchInput(value as string ?? '')
                    }}
                    renderInput={(params) => (
                        <TextField {...params} label={t('search account')} variant="outlined"
                                   onChange={(e) => setSearchInput(e.target.value)}/>
                    )}
                />

            </div>
            <TableContainer component={Paper} style={{
                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`
            }}>
                <Table aria-label="collapsible table">
                    <TableHead>
                        <TableRow>
                            <TableCell/>
                            <TableCell
                                style={{color: `var(--${!darkMode ? 'dark' : 'white'})`}}>{t("login")}</TableCell>
                            <TableCell
                                style={{color: `var(--${!darkMode ? 'dark' : 'white'})`}}>{t("first name")}</TableCell>
                            <TableCell
                                style={{color: `var(--${!darkMode ? 'dark' : 'white'})`}}>{t("last name")}</TableCell>
                            <TableCell
                                style={{color: `var(--${!darkMode ? 'dark' : 'white'})`}}>{t("email")}</TableCell>
                            <TableCell
                                style={{color: `var(--${!darkMode ? 'dark' : 'white'})`}}>{t("active")}</TableCell>
                            <TableCell
                                style={{color: `var(--${!darkMode ? 'dark' : 'white'})`}}>{t("access level")}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {search(users.map((user, index) => (
                            <Row key={index} row={user} style={{color: `var(--${!darkMode ? 'dark' : 'white'})`}}/>
                        )))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}
