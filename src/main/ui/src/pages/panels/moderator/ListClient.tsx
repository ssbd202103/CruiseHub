import React, {useEffect, useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Box from '@material-ui/core/Box';
import Collapse from '@material-ui/core/Collapse';
import IconButton from '@material-ui/core/IconButton';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import KeyboardArrowDownIcon from '@material-ui/icons/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@material-ui/icons/KeyboardArrowUp';
import {Button} from "@material-ui/core";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import axios from "axios";

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

function createData(
    login: string,
    email: string,
    active: boolean,
    accessLevels: string[],
) {
    return {
        login: login,
        email: email,
        active: active,
        accessLevels: accessLevels,
    };
}

export interface RowProps {
    row : ReturnType<typeof createData>
}

function Row(props: RowProps) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();

    return (
        <TableRow className={classes.root}>
            <TableCell component="th" scope="row">
            {row.login}
            </TableCell>
            <TableCell>{row.email}</TableCell>
            <TableCell>{row.active.toString()}</TableCell>
            <TableCell>{row.accessLevels.toString()}</TableCell>
        </TableRow>
    );
}



export default function ModListClient() {
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
            <Table aria-label="Clients">
                <TableHead>
                    <TableRow>
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
