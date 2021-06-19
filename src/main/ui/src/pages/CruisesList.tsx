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
import {Button, TextField} from "@material-ui/core";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";
import axios from "../Services/URL";
import {useSelector} from "react-redux";
import {selectDarkMode} from "../redux/slices/userSlice";
import {selectToken} from "../redux/slices/tokenSlice";
import {useSnackbarQueue} from "./snackbar";
import store from "../redux/store";
import {setChangeAccessLevelStateAccount} from "../redux/slices/changeAccessLevelStateSlice";
import Autocomplete from '@material-ui/lab/Autocomplete';
import {refreshToken} from "../Services/userService";
import useHandleError from "../errorHandler";
import {getAllAccounts} from "../Services/accountsService";
import {getPublishedCruises} from "../Services/cruisesService";

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
    name: string,
    price: number
) {
    return {
        name: name,
        price: price
    };
}


export interface RowProps {
    row: ReturnType<typeof createData>,
    style: React.CSSProperties,
    onChange: () => Promise<any>,
}

function Row(props: RowProps) {
    const {
        onChange
    } = props;


    const {row} = props;
    const {style} = props;
    const {t} = useTranslation();
    const [open, setOpen] = React.useState(false);
    const token = useSelector(selectToken)

    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')

    const classes = useRowStyles();
    const buttonClass = useButtonStyles();

    const handleSetOpen = async () => { }

    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell>
                    <IconButton aria-label="expand row" size="small" onClick={handleSetOpen}>
                        {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>

                <TableCell component="th" scope="row" style={style}>
                    {row.name}
                </TableCell>
                <TableCell style={style}>{row.price}</TableCell>

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
                                        {/*<TableCell align="center">*/}
                                        {/*    <Link to="/accounts/change_account_data">*/}
                                        {/*        <Button className={buttonClass.root}>{t("edit")}</Button>*/}
                                        {/*    </Link>*/}
                                        {/*    <Link to="/accounts/resetSomebodyPassword">*/}
                                        {/*        <Button onClick={setCurrentResetPasswordAccount}*/}
                                        {/*                className={buttonClass.root}>{t("reset password")}</Button>*/}
                                        {/*    </Link>*/}

                                        {/*    <Button className={buttonClass.root} onClick={() => {*/}
                                        {/*        if (row.active) {*/}
                                        {/*            blockAccount({*/}
                                        {/*                etag: row.etag,*/}
                                        {/*                login: row.login, version: row.version, token: token*/}
                                        {/*            }).then(res => {*/}
                                        {/*                onChange().then(() => {*/}
                                        {/*                    showSuccess(t('data.load.success'))*/}
                                        {/*                })*/}

                                        {/*            }).catch(error => {*/}
                                        {/*                const message = error.response.data*/}
                                        {/*                handleError(t(message),error.response.status)*/}
                                        {/*            })*/}
                                        {/*        } else {*/}
                                        {/*            unblockAccount({*/}
                                        {/*                etag: row.etag,*/}
                                        {/*                login: row.login, version: row.version, token: token*/}
                                        {/*            })*/}
                                        {/*                .then(res => {*/}
                                        {/*                    onChange().then(() => {*/}
                                        {/*                        showSuccess(t('data.load.success'))*/}
                                        {/*                    })*/}
                                        {/*                }).catch(error => {*/}
                                        {/*                const message = error.response.data*/}
                                        {/*                handleError(t(message),error.response.data)*/}
                                        {/*            })*/}
                                        {/*        }*/}
                                        {/*    }}>{row.active ? t("block") : t("unblock")}</Button>*/}

                                        {/*    <Link to="/accounts/grant_access_level">*/}
                                        {/*        <Button onClick={setCurrentGrantAccessLevelAccount}*/}
                                        {/*                className={buttonClass.root}>{t("grant access level")}</Button>*/}
                                        {/*    </Link>*/}

                                        {/*    <Link to="/accounts/change_access_level_state">*/}
                                        {/*        <Button onClick={setCurrentChangeAccessLevelStateAccount}*/}
                                        {/*                className={buttonClass.root}>{t("change access level state")}</Button>*/}
                                        {/*    </Link>*/}
                                        {/*</TableCell>*/}
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

export default function CruisesList() {
    const [cruises, setCruises] = useState([]);
    const [searchInput, setSearchInput] = useState("");

    const handleError = useHandleError()

    const darkMode = useSelector(selectDarkMode)

    const getCruises = () => {
        return getPublishedCruises().then(res => {
            setCruises(res.data)
            refreshToken()
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        })
    }

    useEffect(() => {
        getCruises()
    }, []);

    function search(rows: any[]) {
        if (Array.isArray(rows) && rows.length) {
            const filteredAccount = rows.filter(
                row => row.props.row.name.toLowerCase().indexOf(searchInput.toLowerCase()) > -1
            );

            // @ts-ignore
            filteredAccount.forEach(cruise => (cruises.includes(cruise.props.row.name) ? "" : cruises.push(cruise.props.row.name)));
            return filteredAccount
        } else {
            return rows;
        }
    }

    const {t} = useTranslation()

    const filteredCruises: String[] = [];


    return (
        <div>
            <div>
                <Autocomplete
                    options={filteredCruises}
                    inputValue={searchInput}
                    style={{width: 300}}
                    noOptionsText={t('no options')}
                    onChange={(event, value) => {
                        setSearchInput(value as string ?? '')
                    }}
                    renderInput={(params) => (
                        <TextField {...params} label={t('search cruise')} variant="outlined"
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
                                style={{color: `var(--${!darkMode ? 'dark' : 'white'})`}}>{t("CruiseName")}</TableCell>
                            <TableCell
                                style={{color: `var(--${!darkMode ? 'dark' : 'white'})`}}>{t("price")}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {/*{search(users.map((user, index) => (*/}
                        {/*    <Row key={index} row={user} style={{color: `var(--${!darkMode ? 'dark' : 'white'})`}} onChange={getAccounts}/>*/}
                        {/*)))}*/}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}
