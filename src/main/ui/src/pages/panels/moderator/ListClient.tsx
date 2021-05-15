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
import {useSelector} from "react-redux";
import {selectColor} from "../../../redux/slices/colorSlice";

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
    row: ReturnType<typeof createData>,
    style: React.CSSProperties
}

function Row(props: RowProps) {
    const { row } = props;
    const { style } = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();

    return (
        <TableRow className={classes.root}>
            <TableCell component="th" scope="row" style={style}>
            {row.login}
            </TableCell>
            <TableCell style={style}>{row.email}</TableCell>
            <TableCell style={style}>{row.active.toString()}</TableCell>
            <TableCell style={style}>{row.accessLevels.toString()}</TableCell>
        </TableRow>
    );
}



export default function ModListClient() {
    const [users, setUsers] = useState([]);

    const color = useSelector(selectColor)

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
                        <TableCell style={{backgroundColor: `var(--${color ? 'white' : 'dark-light'}`, color: `var(--${color ? 'dark' : 'white-light'}`}}>{t("login")}</TableCell>
                        <TableCell style={{backgroundColor: `var(--${color ? 'white' : 'dark-light'}`, color: `var(--${color ? 'dark' : 'white-light'}`}}>{t("email")}</TableCell>
                        <TableCell style={{backgroundColor: `var(--${color ? 'white' : 'dark-light'}`, color: `var(--${color ? 'dark' : 'white-light'}`}}>{t("active")}</TableCell>
                        <TableCell style={{backgroundColor: `var(--${color ? 'white' : 'dark-light'}`, color: `var(--${color ? 'dark' : 'white-light'}`}}>{t("access level")}</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {users.map((user, index) => (
                        <Row key={index} row={user} style={{backgroundColor: `var(--${color ? 'white' : 'dark-light'}`, color: `var(--${color ? 'dark' : 'white-light'}`}} />
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
