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
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell component="th" scope="row">
                    {row.login}
                </TableCell>
                <TableCell align="center">{row.email}</TableCell>
                <TableCell align="center">{row.active}</TableCell>
                <TableCell align="center">{row.accessLevel}</TableCell>
            </TableRow>
        </React.Fragment>
    );
}

const rows = [
    createData('rbranson', 'rbranson@gmail.com','true', 'client'),
    createData('emusk', 'emusk@gmail.com','true', 'buisnessWorker'),
    createData('jbezos', 'jbezos@gmail.com','true', 'moderator'),
    createData('mzuckerberg', 'mzuckerberg@gmail.com','true', 'admin'),
];

export default function CollapsibleTable() {
    return (
        <TableContainer component={Paper}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>

                        <TableCell align="center">t{("login")}</TableCell>
                        <TableCell align="center">t{("email")}</TableCell>
                        <TableCell align="center">t{("active")}</TableCell>
                        <TableCell align="center">t{("access level")}</TableCell>
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
