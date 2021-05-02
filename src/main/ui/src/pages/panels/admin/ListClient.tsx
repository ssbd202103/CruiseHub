import React, {useEffect, useState} from 'react';
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


interface BlockAccountParams {
    admin: string;
    login: string;
    etag: string;
}

const unblockAccount = async ({admin, login, etag}: BlockAccountParams) => {
    const response = await axios.put('http://localhost:8080/api/account/unblock/' + login, admin, {
        headers:{
            'Content-Type': 'application/json',
            'If-Match': etag
        }

    });

    console.log(response.data)
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
) {
    return {
        login: login,
        email: email,
        active: active,
        accessLevels: accessLevels,
        etag: etag,
    };
}



export interface RowProps {
    row : ReturnType<typeof createData>
}

function Row(props: RowProps) {
    const {row} = props;
    const {t} = useTranslation()
    const [open, setOpen] = React.useState(false);
    const [buttonText, setButtonText] = useState("true");

    const classes = useRowStyles();
    const buttonClass = useButtonStyles();

    return (

        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell>
                    <IconButton aria-label="expand row" size="small" onClick={() => setOpen(!open)}>
                        {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>

                <TableCell component="th" scope="row">
                    {row.login}
                </TableCell>
                <TableCell>{row.email}</TableCell>
                <TableCell>{row.active.toString()}</TableCell>
                <TableCell>{row.accessLevels.toString()}</TableCell>

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
                                                    <Button className={buttonClass.root}>{t("edit")}</Button>
                                                </Link>

                                                <Link to="/panels/adminPanel/ChangeAccountPassword">
                                                    <Button className={buttonClass.root}>{t("change password")}</Button>
                                                </Link>

                                                <Button className={buttonClass.root}>{t("reset password")}</Button>


                                            <Button className={buttonClass.root} onClick={() => {unblockAccount({admin: String("rbranson"), etag: row.etag,
                                                login: row.login})}}>{row.active ? t("block") : t("unblock")}</Button> //to do change admin on logged user


                                                <Link to="/panels/adminPanel/GrantAccessLevel">
                                                    <Button className={buttonClass.root}>{t("grand access level")}</Button>
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
    useEffect(() => {
        loadUsers();
    },[]);


    const loadUsers = async () => {
        const result = await axios.get('http://localhost:8080/api/account/accounts');
        setUsers(result.data)
    }


    const {t} = useTranslation()
    return (
        <TableContainer component={Paper}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell/>
                        <TableCell>{t("login")}</TableCell>
                        <TableCell>{t("email")}</TableCell>
                        <TableCell>{t("active")}</TableCell>
                        <TableCell>{t("access level")}</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {users.map((user, index) => (
                        <Row key={index} row={user}/>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
