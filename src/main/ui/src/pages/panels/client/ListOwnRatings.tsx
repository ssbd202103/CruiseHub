import React, {Component, useEffect, useState} from 'react';
import {getOwnRatings} from "../../../Services/ratingsService";
import {makeStyles} from "@material-ui/core/styles";
import {useTranslation} from "react-i18next";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import {Link} from "react-router-dom";
import {Button, TextField} from "@material-ui/core";
import RoundedButton from "../../../components/RoundedButton";
import styles from "../../../styles/Header.module.css";
import useHandleError from "../../../errorHandler";
import {useSelector} from "react-redux";
import {selectDarkMode} from "../../../redux/slices/userSlice";
import {getAllCompanies} from "../../../Services/companiesService";
import {refreshToken} from "../../../Services/userService";
import Autocomplete from "../../../components/Autocomplete";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import {removeRating} from "../../../Services/ratingService";
import {useSnackbarQueue} from "../../snackbar";

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

function createData(
    cruiseGroupUUID: string,
    name: string,
    login: string,
    rating: number,
) {
    return {
        cruiseGroupUUID: cruiseGroupUUID,
        name: name,
        login: login,
        rating: rating,
    };
}


export interface RowProps {
    row: ReturnType<typeof createData>,
    style: React.CSSProperties,
    onLoad: () => void,
}

function Row({row, style, onLoad}: RowProps) {
    const {t} = useTranslation();
    const classes = useRowStyles();

    const showSuccess = useSnackbarQueue('success');
    const handleError = useHandleError();

    return (
        <TableRow className={classes.root}>
            <TableCell component="th" scope="row" style={style}>{row.name}</TableCell>
            <TableCell style={style}>{row.login}</TableCell>
            <TableCell style={style}>{row.rating}</TableCell>
            <TableCell style={style}>{row.cruiseGroupUUID}</TableCell>
            <TableCell style={style}>
                <RoundedButton
                    color="pink"
                    onClick={() => {
                        removeRating(row.cruiseGroupUUID).then(res => {
                            onLoad();
                            showSuccess(t('successful action'));
                        }).catch(error => {
                            const message = error.response.data
                            const status =  error.response.status
                            handleError(message,status)
                        })
                    }}
                >
                    {t('delete')}
                </RoundedButton>
            </TableCell>
        </TableRow>
    );
}

const ListOwnRatings = () => {
    const [ratings, setRatings] = useState([]);
    const [searchInput, setSearchInput] = useState("");

    const handleError = useHandleError()

    const darkMode = useSelector(selectDarkMode)

    const getRatings = () => {
        getOwnRatings().then(res => {
            setRatings(res.data)
        }).catch(error => {
            const message = error.response.data
            const status =  error.response.status
            handleError(message,status)
        }).then(res => {
            refreshToken()
        });
    }

    useEffect(() => {
        getRatings()
    }, []);

    function search(rows: any[]) {
        if (Array.isArray(rows) && rows.length) {

            const filteredRatings = rows.filter(
                row => row.props.row.name.toLowerCase().indexOf(searchInput.toLowerCase()) > -1
            );

            filteredRatings.forEach(rating => (searchRatingList.includes(rating.props.row.name) ?
                "" : searchRatingList.push(rating.props.row.name)));
            return filteredRatings

        } else {
            return rows;
        }
    }

    const searchRatingList: String[] = [];

    const {t} = useTranslation()


    return (
        <>
            <div>
                <Autocomplete
                    options={searchRatingList}
                    inputValue={searchInput}
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
                    <Table aria-label="Companies">
                        <TableHead>
                            <TableRow>
                                <TableCell style={{
                                    backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                    color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                }}>{t("cruiseGroupName")}</TableCell>
                                <TableCell style={{
                                    backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                    color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                }}>{t("login")}</TableCell>
                                <TableCell style={{
                                    backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                    color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                }}>{t("rating")}</TableCell>
                                <TableCell style={{
                                    backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                    color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                }}>{"UUID"}</TableCell>
                                <TableCell style={{
                                    backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                    color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                }}>{t("remove button")}</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {search(ratings.map((rating, index) => (
                                <Row key={index} row={rating} style={{
                                    backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                    color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                }} onLoad={getRatings}/>
                            )))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </>
    );
};

export default ListOwnRatings;