import React, {useEffect, useState} from 'react';
import {makeStyles} from "@material-ui/core/styles";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import useHandleError from "../../../errorHandler";
import {useSelector} from "react-redux";
import Box from '@material-ui/core/Box';
import {selectDarkMode} from "../../../redux/slices/userSlice";
import {refreshToken} from "../../../Services/userService";
import Collapse from '@material-ui/core/Collapse';
import {Button, TextField} from "@material-ui/core";
import {useTranslation} from "react-i18next";
import Autocomplete from "@material-ui/lab/Autocomplete";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableBody from "@material-ui/core/TableBody";
import {ImageListType} from "react-images-uploading";
import {getAllCruiseGroup, getCruisesForCruiseGroup} from "../../../Services/cruiseGroupService";
import IconButton from "@material-ui/core/IconButton";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import {useSnackbarQueue} from "../../snackbar";
import {Link} from "react-router-dom";


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

export function createCruiseGroup(
    price: any,
    start_time: any,
    end_time: any,
    numberOfSeats: any,
    name: any,
    cruisePictures: ImageListType,
    etag: string,
    version: bigint,
    uuid: any,
    description: any,
    active: boolean,
    company: any,
) {
    return {
        price: price,
        start_time: start_time,
        end_time: end_time,
        numberOfSeats: numberOfSeats,
        name: name,
        cruisePictures: cruisePictures,
        uuid: uuid,
        etag: etag,
        version: version,
        description: description,
        active: active,
        company: company,
    };
}

export interface CruiseData {
    group: ReturnType<typeof createCruiseGroup>,
    style: React.CSSProperties
}

function Row(props: CruiseData) {
    const {group} = props;
    const {style} = props;
    const {t} = useTranslation();
    const [open, setOpen] = useState(false);
    const classes = useRowStyles();
    const handleError = useHandleError();
    const [cruises, setCruises] = useState([]);
    const darkMode = useSelector(selectDarkMode)
    const buttonClass = useButtonStyles();

    const handleSetOpen = async () => {
        console.log("Someone clicked me")
        setOpen(state => !state);
        await getCruisesForCruiseGroup(group.name)
            .then(res => {
                setCruises(res.data)
                console.log(cruises)
                console.log(res.data)
                refreshToken();
            })
            .catch(error => {
                const message = error.response.data
                const status = error.response.status
                handleError(message, status)
            })
    }

    const getParsedDate = (date: any) => {
        return `${date.dayOfMonth}-${date.monthValue.toString().padStart(2, '0')}-${date.year}`
    }
    const handleReservations =(props: any) =>{
        const uuid = props
        sessionStorage.setItem("cruiseUUID",uuid)
    }

    return (
        <React.Fragment>
            <TableRow className={classes.root}>
                <TableCell>
                    <IconButton aria-label="expand row" size="small" onClick={handleSetOpen}>
                        {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>
                <TableCell component="th" scope="row" style={style}>{group.name}</TableCell>
                <TableCell style={style}>{group.company.name}</TableCell>
                <TableCell style={style}>{group.numberOfSeats}</TableCell>
                <TableCell style={style}>{group.price + " pln"}</TableCell>
                <TableCell style={style}>{group.description}</TableCell>
                <TableCell style={style}>{group.active.toString()}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box margin={1}>
                            <Table size="small" aria-label="clients">
                                <TableHead>
                                    <TableRow>
                                        <TableCell align="center" style={{
                                            backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                            color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                        }}>{t("start_date")}</TableCell>
                                        <TableCell align="center" style={{
                                            backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                            color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                        }}>{t("end_date")}</TableCell>
                                        <TableCell align="center" style={{
                                            backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                            color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                        }}>{t("active")}</TableCell>
                                        <TableCell align="center" style={{
                                            backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                            color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                        }}>{t("reservations")}</TableCell>
                                        <TableCell align="center" style={{
                                            backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                            color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                        }}>{t("attractions")}</TableCell>
                                        <TableCell align="center" style={{
                                            backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                            color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                                        }}>{t("publish")}</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {cruises.map((cruise: any, index) => (
                                        <TableRow>
                                            <TableCell align="center"
                                                       style={style}>{getParsedDate(cruise.startDate)}</TableCell>
                                            <TableCell align="center"
                                                       style={style}>{getParsedDate(cruise.endDate)}</TableCell>
                                            <TableCell align="center"
                                                       style={style}>{cruise.active.toString()}</TableCell>
                                            <TableCell align="center">
                                                <Link to="/reservations">
                                                    <Button  className={buttonClass.root} onClick={() => {handleReservations(cruise.uuid)}}
                                                    >{t("reservations")}</Button>
                                                </Link>
                                            </TableCell>
                                            <TableCell align="center">
                                                <Button className={buttonClass.root}>{t("attractions")}</Button>
                                            </TableCell>
                                            {cruise.published ? "" :
                                                <TableCell align="center">
                                                    <Button className={buttonClass.root}>{t("publish")}</Button>
                                                </TableCell>
                                            }
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}

const ListCruiseGroups = () => {
    const [cruiseGroupL, setCruiseGroupL] = useState([]);
    const [searchInput, setSearchInput] = useState("");

    const handleError = useHandleError()

    const darkMode = useSelector(selectDarkMode)

    useEffect(() => {
        getAllCruiseGroup().then(res => {
            console.log(res.data)
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
            <TableContainer component={Paper} style={{
                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`
            }}>
                <Table aria-label="CruiseGroups">
                    <TableHead>
                        <TableRow>
                            <TableCell>{t('expand cruises')}</TableCell>
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