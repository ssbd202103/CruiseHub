import React, {useEffect, useReducer, useState} from 'react';
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
import {Button} from "@material-ui/core";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import ButtonGroup from '@material-ui/core/ButtonGroup'
import axios from "axios";
import { useLocation } from 'react-router-dom';
import {useSelector} from "react-redux";
import {selectColor} from "../../../redux/slices/colorSlice";
import {getAccountDetailsAbout, getAllAccounts} from "../../../Services/accountsService";
import {selectToken} from "../../../redux/slices/tokenSlice";

interface UnblockAccountParams {
    login: string;
    etag: string;
    version: bigint,
    token: string
}


const unblockAccount = async ({login, etag, version, token}: UnblockAccountParams) => {
    const json = JSON.stringify({
            login: login,
            version: version,
        }
    );
    await axios.put('http://localhost:8080/api/account/unblock', json, {
        headers:{
            'Content-Type': 'application/json',
            'If-Match': etag,
            'Authorization': `Bearer ${token}`
        }

    }).then(response => {
        return response.status == 200;
    });

};
function refresh() {
    window.location.reload();
}

const blockAccount = async ({login, etag, version, token}: UnblockAccountParams) => {
    const json = JSON.stringify({
            login: login,
            version: version,
        }
    );
    await axios.put('http://localhost:8080/api/account/block', json, {
        headers:{
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
    email: string,
    active: boolean,
    accessLevels: string[],
    etag: string,
    version: bigint,
) {
    return {
        login: login,
        email: email,
        active: active,
        accessLevels: accessLevels,
        etag: etag,
        version: version
    };
}



export interface RowProps {
    row : ReturnType<typeof createData>,
    style: React.CSSProperties
}

function Row(props: RowProps) {
    const {row} = props;
    const {style} = props;
    const {t} = useTranslation();
    const [open, setOpen] = React.useState(false);
    const [buttonText, setButtonText] = useState("true");
    const token = useSelector(selectToken)

    const classes = useRowStyles();
    const buttonClass = useButtonStyles();

    const handleSetOpen = async () => {
        const result = await getAccountDetailsAbout(row.login);
        sessionStorage.setItem("changeAccountData", JSON.stringify(result.data));
        setOpen(state => !state);
    }

    const setCurrentGrantAccessLevelAccount = () => {
        sessionStorage.setItem('grantAccessLevelAccount', JSON.stringify(row));
    }


    const setCurrentResetPasswordAccount = () => {
        sessionStorage.setItem('resetPasswordAccount', JSON.stringify(row));
    }

    const setCurrentChangeAccessLevelStateAccount = async () => {
        const result = await getAccountDetailsAbout(row.login);
        sessionStorage.setItem("changeAccessLevelStateAccount", JSON.stringify(result.data));
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
                                                <Link to="/panels/adminPanel/ChangeAccountData">
                                                    <Button className={buttonClass.root} >{t("edit")}</Button>
                                                </Link>

{/*                                                <Link to="/panels/adminPanel/ChangeAccountPassword">
                                                    <Button className={buttonClass.root}>{t("change password")}</Button>
                                                </Link>*/}

                                            <Link to="/reset/resetSomebodyPassword">
                                                <Button onClick={setCurrentResetPasswordAccount} className={buttonClass.root}>{t("reset password")}</Button>
                                            </Link>

                                            <Button className={buttonClass.root} onClick={() => {
                                                if(row.active) {
                                                    if (blockAccount({etag: row.etag,
                                                        login: row.login, version: row.version, token: token})) {
                                                       refresh()
                                                    }
                                                } else {
                                                    if (unblockAccount({etag: row.etag,
                                                        login: row.login, version: row.version, token: token})) {
                                                        refresh()

                                                    }
                                                }
                                            }}>{row.active ? t("block") : t("unblock")}</Button>

                                            <Link to="/panels/adminPanel/GrantAccessLevel/">
                                                <Button onClick={setCurrentGrantAccessLevelAccount}
                                                        className={buttonClass.root}>{t("grant access level")}</Button>
                                            </Link>

                                            <Link to="/panels/adminPanel/ChangeAccessLevelState/">
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

    const color = useSelector(selectColor)

    useEffect(() => {
        getAllAccounts().then(res => {
            setUsers(res.data)
        })
    },[]);

    const {t} = useTranslation()
    return (
        <TableContainer component={Paper} style={{
            backgroundColor: `var(--${color ? 'white' : 'dark-light'}`
        }}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell/>
                        <TableCell style={{color: `var(--${color ? 'dark' : 'white'})`}}>{t("login")}</TableCell>
                        <TableCell style={{color: `var(--${color ? 'dark' : 'white'})`}}>{t("email")}</TableCell>
                        <TableCell style={{color: `var(--${color ? 'dark' : 'white'})`}}>{t("active")}</TableCell>
                        <TableCell style={{color: `var(--${color ? 'dark' : 'white'})`}}>{t("access level")}</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {users.map((user, index) => (
                        <Row key={index} row={user} style={{color: `var(--${color ? 'dark' : 'white'})`}} />
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
