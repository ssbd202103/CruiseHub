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
import Autocomplete from "@material-ui/lab/Autocomplete";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

function createData(
    cruiseGroupName: string,
    login: number,
    rating: string,
    uuid: string
) {
    return {
        cruiseGroupName: cruiseGroupName,
        login: login,
        rating: rating,
        uuid: uuid
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
            <TableCell component="th" scope="row" style={style}>{row.cruiseGroupName}</TableCell>
            <TableCell style={style}>{row.login}</TableCell>
            <TableCell style={style}>{row.rating}</TableCell>
            <TableCell style={style}>{row.uuid}</TableCell>
            <TableCell style={style}>
                <Button
                    className={styles.link}
                    style={{
                        marginRight: 20,
                        color: 'var(--dark)',
                        fontFamily: 'Montserrat, sans-serif'

                    }}>TEMP REMOVE BUTTON</Button>
            </TableCell>
        </TableRow>
    );
}

const ListOwnRatings = () => {
    const [ratings, setRatings] = useState([]);
    const [searchInput, setSearchInput] = useState("");

    const handleError = useHandleError()

    const darkMode = useSelector(selectDarkMode)

    useEffect(() => {
        getOwnRatings().then(res => {
            setRatings(res.data)
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        }).then(res => {
            refreshToken()
        });
    }, []);

    function search(rows: any[]) {
        if (Array.isArray(rows) && rows.length) {

            const filteredCompanies = rows.filter(
                row => row.props.row.cruiseGroupName.toLowerCase().indexOf(searchInput.toLowerCase()) > -1
            );

            filteredCompanies.forEach(rating => (searchRatingList.includes(rating.props.row.cruiseGroupName) ?
                "" : searchRatingList.push(rating.props.row.cruiseGroupName)));
            return filteredCompanies

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
                                }}/>
                            )))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </>
    );
};

export default ListOwnRatings;