import React, {useEffect, useState} from 'react';
import {getAllCompanies} from "../../../Services/companiesService";
import {makeStyles} from "@material-ui/core/styles";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import useHandleError from "../../../errorHandler";
import {useSelector} from "react-redux";
import {selectCompany, selectDarkMode} from "../../../redux/slices/userSlice";
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
import {ImageListType} from "react-images-uploading";
import {getAllCruiseGroup, getCruiseGroupForBusinessWorker} from "../../../Services/cruiseGroupService";

const useRowStyles = makeStyles({
    root: {
        '& > *': {
            borderBottom: 'unset',
        },
    },
});

export function createCruiseGroup(
    price: any,
    start_time: any,
    end_time: any,
    numberOfSeats: any,
    name: any,
    cruisePictures:ImageListType,
    etag: string,
    version: bigint,
    uuid: any,
    description: any,
    active: boolean,
    company: any,
){
    return {
        price:price,
        start_time: start_time,
        end_time: end_time,
        numberOfSeats: numberOfSeats,
        name: name,
        cruisePictures:cruisePictures,
        uuid: uuid,
        etag: etag,
        version: version,
        description: description,
        active: active,
        company: company,
    };
}

export interface  CruiseData{
    group: ReturnType<typeof createCruiseGroup>,
    style: React.CSSProperties
}

function Row(props: CruiseData) {
    const {group} = props;
    const {style} = props;
    const {t} = useTranslation();
    const classes = useRowStyles();

    return (
        <TableRow className={classes.root}>
            <TableCell component="th" scope="row" style={style}>{group.name}</TableCell>
            <TableCell style={style}>{group.company.name}</TableCell>
            <TableCell style={style}>{group.numberOfSeats}</TableCell>
            <TableCell style={style}>{group.price +" pln"}</TableCell>
            <TableCell style={style}>{group.description}</TableCell>
            <TableCell style={style}>{group.active.toString()}</TableCell>
        </TableRow>
    );
}

const ListCruiseGroups = () => {
    const [cruiseGroupL, setCruiseGroupL] = useState([]);
    const [searchInput, setSearchInput] = useState("");

    const handleError = useHandleError()

    const darkMode = useSelector(selectDarkMode)

    const worker_Company = useSelector(selectCompany);
    useEffect(() => {

        getCruiseGroupForBusinessWorker(worker_Company).then(res => {
            setCruiseGroupL(res.data)
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        }).then(res => {
            refreshToken()
        });
    }, []);


    function search(rows: any[]) {
        if (Array.isArray(rows) && rows.length) {

            const filteredCruiseGroups = rows.filter(
                row => row.props.group.name.toLowerCase().indexOf(searchInput.toLowerCase()) > -1
            );

            filteredCruiseGroups.forEach(company => (CruiseGroups.includes(company.props.group.name) ?
                "" : CruiseGroups.push(company.props.group.name)));
            return filteredCruiseGroups

        } else {
            return rows;
        }
    }


    const CruiseGroups: String[] = [];

    const {t} = useTranslation()

    return (
        <div>
            <Autocomplete
                options={CruiseGroups}
                inputValue={searchInput}
                style={{width: 300}}
                noOptionsText={t('no options')}
                onChange={(event, value) => {
                    setSearchInput(value as string ?? '')
                }}
                renderInput={(params) => (
                    <TextField {...params} label={t('search cruiseGroup')} variant="outlined"
                               onChange={(e) => setSearchInput(e.target.value)}/>
                )}
            />
            <TableContainer component={Paper}>
                <Table aria-label="CruiseGroups">
                    <TableHead>
                        <TableRow>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("cruiseName")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("company name")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("numberOfSeats")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("price")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("description")}</TableCell>
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("active")}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {search(cruiseGroupL.map((cruiseGroups, index) => (
                            <Row key={index} group={cruiseGroups} style={{
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

export default ListCruiseGroups;