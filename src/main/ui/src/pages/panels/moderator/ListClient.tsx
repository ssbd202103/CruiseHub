import React, {useEffect, useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import {useTranslation} from "react-i18next";
import {useSelector} from "react-redux";
import {selectColor} from "../../../redux/slices/colorSlice";
import {getAllAccounts} from "../../../Services/accountsService";

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
        getAllAccounts().then(res => {
            setUsers(res.data)
        })
    },[]);



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
