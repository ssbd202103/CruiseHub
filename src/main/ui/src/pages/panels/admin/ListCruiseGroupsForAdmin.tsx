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
import {ImageListType} from "react-images-uploading";
import {
    getAllCruiseGroup,
    getCruiseGroupForBusinessWorker,
    getCruisesForCruiseGroup
} from "../../../Services/cruiseGroupService";
import RoundedButton from "../../../components/RoundedButton";
import {useSnackbarQueue} from "../../snackbar";
import {selectToken} from "../../../redux/slices/tokenSlice";
import axios from "../../../Services/URL";
import IconButton from "@material-ui/core/IconButton";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import Collapse from "@material-ui/core/Collapse";
import Box from "@material-ui/core/Box";


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

interface DeactivateCruiseData {
    uuid: string;
    etag: string;
    version: bigint,
    token: string
}

const deactivateCruiseGroup = async ({uuid, etag, version, token}: DeactivateCruiseData) => {

    const json = JSON.stringify({
            uuid: uuid,
            version: version,
        }
    )
    return await axios.put('cruiseGroup/deactivate-cruise-group', json, {
        headers: {
            'Content-Type': 'application/json',
            'If-Match': etag,
            'Authorization': `Bearer ${token}`
        }
    })
}

export interface  CruiseData{
    group: ReturnType<typeof createCruiseGroup>,
    style: React.CSSProperties,
    onChange: () => Promise<any>,
}

function Row(props: CruiseData) {
    const {group} = props;
    const {style} = props;
    const {onChange} = props;
    const {t} = useTranslation();
    const classes = useRowStyles();
    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')
    const token = useSelector(selectToken)
    const [open, setOpen] = useState(false);
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
            <TableCell style={style}>{group.price +" pln"}</TableCell>
            <TableCell style={style}>{group.description}</TableCell>
            <TableCell style={style}>{group.active.toString()}</TableCell>
            <TableCell style={style}>
                <RoundedButton
                    color="pink" onClick={() => {
                    deactivateCruiseGroup({
                        uuid: group.uuid,
                        etag: group.etag,
                        version: group.version,
                        token: token
                    }).then(res => {
                        onChange().then(() => {
                            showSuccess(t('data.load.success'))
                        })
                    }).catch(error => {
                        const message = error.response.data
                        handleError(t(message), error.response.status)
                    })
                }}
                    disabled={!group.active}
                >
                    {t("deactivate")}
                </RoundedButton>
            </TableCell>
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

const ListCruiseGroupsForAdmin = () => {
    const [cruiseGroupL, setCruiseGroupL] = useState([]);
    const [searchInput, setSearchInput] = useState("");

    const handleError = useHandleError()

    const darkMode = useSelector(selectDarkMode)

    const getCruiseGroupForAdmin = () => {
        return  getAllCruiseGroup().then(res => {
            setCruiseGroupL(res.data)
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        }).then(res => {
            refreshToken()
        })
    }

    useEffect(() => {
        getCruiseGroupForAdmin()
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
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t("deactivate")}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {search(cruiseGroupL.map((cruiseGroups, index) => (
                            <Row key={index} group={cruiseGroups} style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }} onChange={getCruiseGroupForAdmin}/>
                        )))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
};

export default ListCruiseGroupsForAdmin;