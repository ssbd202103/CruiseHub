import React, {useEffect, useState} from 'react';
import {getAllCompanies} from "../../../Services/companiesService";
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
import {Link} from "react-router-dom";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import {getReservationsForCruise} from "../../../Services/reservationService";

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

function createData(
    clientName: string,
    numberOfSeats: number,
    price: number,
    cruiseName: string,
) {
    return {
        clientName: clientName,
        numberOfSeats: numberOfSeats,
        price: price,
        cruiseName: cruiseName,
    };
}

export interface RowProps {
    row: ReturnType<typeof createData>,
    style: React.CSSProperties
}

function Row(props: RowProps) {
    const {row} = props;
    const {style} = props;
    const {t} = useTranslation();
    const classes = useRowStyles();

    return (
        <TableRow className={classes.root}>
            <TableCell component="th" scope="row" style={style}>{row.clientName}</TableCell>
            <TableCell style={style}>{row.numberOfSeats}</TableCell>
            <TableCell style={style}>{row.price}</TableCell>
            <TableCell style={style}>{row.cruiseName}</TableCell>
        </TableRow>
    );
}

const ListReservations = () => {
    const [reservations, setReservations] = useState([]);
    const [searchInput, setSearchInput] = useState("");

    const handleError = useHandleError()

    const darkMode = useSelector(selectDarkMode)
    const uuid =sessionStorage.getItem("cruiseUUID")
    useEffect(() => {

        getReservationsForCruise(uuid).then(res => {
            setReservations(res.data)
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        }).then(res => {
            refreshToken()
        });
    }, []);


    function search(rows: any[]) {
        if (Array.isArray(rows) && rows.length) {

            const filteredReservations = rows.filter(
                row => row.props.row.clientName.toLowerCase().indexOf(searchInput.toLowerCase()) > -1
            );

            filteredReservations.forEach(reservation => (reservationss.includes(reservation.props.row.clientName) ?
                "" : reservationss.push(reservation.props.row.clientName)));
            return filteredReservations

        } else {
            return rows;
        }
    }

    const reservationss: String[] = [];

    const {t} = useTranslation()

    return (
        <div>
            <Autocomplete
                options={reservationss}
                inputValue={searchInput}
                style={{width: 300}}
                noOptionsText={t('no options')}
                onChange={(event, value) => {
                    setSearchInput(value as string ?? '')
                }}
                renderInput={(params) => (
                    <TextField {...params} label={t('search reservation')} variant="outlined"
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
                            }}>{t("Client name")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("numberOfSeatsReserved")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("price")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("CruiseName")}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {search(reservations.map((reservation, index) => (
                            <Row key={index} row={reservation} style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}/>
                        )))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
};

export default ListReservations;