import React, {useEffect, useState} from 'react';
import {makeStyles} from "@material-ui/core/styles";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import useHandleError from "../../../errorHandler";
import {useSelector} from "react-redux";
import {selectDarkMode} from "../../../redux/slices/userSlice";
import {refreshToken} from "../../../Services/userService";
import {useTranslation} from "react-i18next";
import Autocomplete from "@material-ui/lab/Autocomplete";
import {Button, TextField} from "@material-ui/core";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import {
    getSelfReservations,
} from "../../../Services/reservationService";

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

function createData(
    uuid: string,
    cruiseName: string,
    startDate: string,
    endDate: string,
    numberOfSeats: number,
    phoneNumber: string,
    price: number,
    attractions: [],
) {
    return {
        uuid: uuid,
        cruiseName: cruiseName,
        startDate: startDate,
        endDate: endDate,
        numberOfSeats: numberOfSeats,
        phoneNumber: phoneNumber,
        price: price,
        attractions: attractions,
    };
}

export interface RowProps {
    row: ReturnType<typeof createData>,
    style: React.CSSProperties
    onReload: () => void
}

function Row({row, style}: RowProps) {
    const {t} = useTranslation();
    const classes = useRowStyles();
    const buttonClass = useButtonStyles();

    const cancelReservation = () => {
        // dla Szymona xd
    }

    return (
        <TableRow className={classes.root}>
            <TableCell style={style}>{row.cruiseName}</TableCell>
            <TableCell style={style}>{row.attractions.map(item => t(item)).join(', ')}</TableCell>
            <TableCell style={style}>{row.startDate.replace('T', ' ').split('.').shift()}</TableCell>
            <TableCell style={style}>{row.endDate.replace('T', ' ').split('.').shift()}</TableCell>
            <TableCell style={style}>{row.phoneNumber}</TableCell>
            <TableCell style={style}>{row.numberOfSeats}</TableCell>
            <TableCell style={style}>{row.price}</TableCell>
            <TableCell style={style}>
                <Button onClick={cancelReservation}
                        className={buttonClass.root}>{t("cancel reservation btn")}</Button>
            </TableCell>
        </TableRow>
    );
}

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

const ListSelfReservations = () => {
    const [reservations, setReservations] = useState([]);
    const [searchInput, setSearchInput] = useState("");
    const handleError = useHandleError()

    const darkMode = useSelector(selectDarkMode)

    useEffect(() => {
        onReload()
    }, []);

    function onReload() {
        getSelfReservations().then(res => {
            setReservations(res.data)
            console.log(res.data)
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        }).then(res => {
            refreshToken()
        });
    }

    function search(rows: any[]) {
        if (Array.isArray(rows) && rows.length) {

            const filteredReservations = rows.filter(
                row => row.props.row.cruiseName.toLowerCase().indexOf(searchInput.toLowerCase()) > -1
            );

            filteredReservations.forEach(reservation => (listNames.includes(reservation.props.row.cruiseName) ?
                "" : listNames.push(reservation.props.row.cruiseName)));
            return filteredReservations

        } else {
            return rows;
        }
    }

    const listNames: String[] = [];

    const {t} = useTranslation()


    return (
        <div>
            <Autocomplete
                options={listNames}
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
            <TableContainer component={Paper}>
                <Table aria-label="Reservations">
                    <TableHead>
                        <TableRow>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("CruiseName")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("Attractions name")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("startDate")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("endDate")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("company phone number")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("numberOfSeatsReserved")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("price") + " pln"}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("cancel reservation btn")}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {search(reservations.map((reservation, index) => (
                            <Row key={index} row={reservation} style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`,
                            }} onReload={onReload}/>
                        )))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
};

export default ListSelfReservations;