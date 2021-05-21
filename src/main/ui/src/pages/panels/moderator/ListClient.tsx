import React, {useEffect, useState} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import {useTranslation} from "react-i18next";
import {useSelector} from "react-redux";
import {selectDarkMode} from "../../../redux/slices/userSlice";
import {getAllAccounts} from "../../../Services/accountsService";
import DarkedTextField from "../../../components/DarkedTextField";

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

function createData(
    login: string,
    firstName: string,
    secondName: string,
    email: string,
    active: boolean,
    accessLevels: string[],
) {
    return {
        login: login,
        firstName: firstName,
        secondName: secondName,
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
            <TableCell style={style}>{row.firstName}</TableCell>
            <TableCell style={style}>{row.secondName}</TableCell>
            <TableCell style={style}>{row.email}</TableCell>
            <TableCell style={style}>{row.active.toString()}</TableCell>
            <TableCell style={style}>{row.accessLevels.toString()}</TableCell>
        </TableRow>
    );
}



export default function ModListClient() {
    const [users, setUsers] = useState([]);
    const [searchInput, setSearchInput] = useState("");

    const darkMode = useSelector(selectDarkMode)

    useEffect(() => {
        getAllAccounts().then(res => {
            setUsers(res.data)
        })
    },[]);

    function search(rows: any[]) {
        if (Array.isArray(rows) && rows.length) {
            return rows.filter(
                row => row.props.row.firstName.toLowerCase().indexOf(searchInput.toLowerCase()) > -1 ||
                    row.props.row.secondName.toLowerCase().indexOf(searchInput.toLowerCase()) > -1
            );
        } else {
            return rows;
        }
    }

    const {t} = useTranslation()
    return (
        <div>
            <div>
                <DarkedTextField
                    label={t('search account')}
                    type="text"
                    value={searchInput}
                    onChange={(e) => setSearchInput(e.target.value)}
                    style={{marginBottom: '20px'}} />
            </div>
            <TableContainer component={Paper}>
                <Table aria-label="Clients">
                    <TableHead>
                        <TableRow>
                            <TableCell style={{backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`, color: `var(--${!darkMode ? 'dark' : 'white-light'}`}}>{t("login")}</TableCell>
                            <TableCell style={{backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`, color: `var(--${!darkMode ? 'dark' : 'white-light'}`}}>{t("first name")}</TableCell>
                            <TableCell style={{backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`, color: `var(--${!darkMode ? 'dark' : 'white-light'}`}}>{t("last name")}</TableCell>
                            <TableCell style={{backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`, color: `var(--${!darkMode ? 'dark' : 'white-light'}`}}>{t("email")}</TableCell>
                            <TableCell style={{backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`, color: `var(--${!darkMode ? 'dark' : 'white-light'}`}}>{t("active")}</TableCell>
                            <TableCell style={{backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`, color: `var(--${!darkMode ? 'dark' : 'white-light'}`}}>{t("access level")}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {search(users.map((user, index) => (
                            <Row key={index} row={user} style={{backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`, color: `var(--${!darkMode ? 'dark' : 'white-light'}`}} />
                        )))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}
