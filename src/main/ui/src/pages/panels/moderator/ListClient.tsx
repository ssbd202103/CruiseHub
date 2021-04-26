import React from 'react';
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
    active: string,
    accessLevel: string,
) {
    return {
        login: login,
        email: email,
        active: active,
        accessLevel: accessLevel,
    };
}

function Row(props: { row: ReturnType<typeof createData> }) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const classes = useRowStyles();

    return (
        <TableRow className={classes.root}>
            <TableCell component="th" scope="row">
            {row.login}
            </TableCell>
            <TableCell>{row.email}</TableCell>
            <TableCell>{row.active}</TableCell>
            <TableCell>{row.accessLevel}</TableCell>
        </TableRow>
    );
}

const rows = [
    createData('rbranson', 'rbranson@gmail.com','true', 'client'),
    createData('emusk', 'emusk@gmail.com','true', 'buisnessWorker'),
    createData('jbezos', 'jbezos@gmail.com','true', 'moderator'),
    createData('mzuckerberg', 'mzuckerberg@gmail.com','true', 'admin'),
];

export default function ModListClient() {
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
                    {rows.map((row) => (
                        <Row key={row.login} row={row} />
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
