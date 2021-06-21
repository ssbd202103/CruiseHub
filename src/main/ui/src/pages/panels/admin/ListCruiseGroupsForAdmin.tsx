import React, {useEffect, useReducer, useState} from 'react';
import {getAllCompanies} from "../../../Services/companiesService";
import {makeStyles} from "@material-ui/core/styles";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import useHandleError from "../../../errorHandler";
import {useSelector} from "react-redux";
import {selectDarkMode} from "../../../redux/slices/userSlice";
import {refreshToken} from "../../../Services/userService";
import {useTranslation} from "react-i18next";
import Autocomplete from "../../../components/Autocomplete";
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
import PopupAcceptAction from "../../../PopupAcceptAction";
import store from "../../../redux/store";
import {getSelfReservations} from "../../../Services/reservationService";
import ActiveIcon from "../../../components/ActiveIcon";


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

interface DeactivateCruise {
    uuid: string,
    etag: string,
    version:bigint
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
    const [deactivateCruise, setDeactivateCruise] = useState<DeactivateCruise>({
        uuid : "",
        etag : "",
        version: BigInt(0) });
    const [, forceUpdate] = useReducer(x => x + 1, 0);
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
    const [buttonPopupAcceptAction, setButtonPopupAcceptAction] = useState(false);



    const handleConfirm = async ({uuid, etag, version}: DeactivateCruise) => {
        const {token} = store.getState();
        const json = JSON.stringify({
            uuid: uuid,
            version: version
        })
        await axios.put("cruise/deactivate-cruise", json, {
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    'Authorization': `Bearer ${token}`,
                    "If-Match": etag
                }
            }
        ).then(() => {
            setButtonPopupAcceptAction(false)
            showSuccess(t('successful action'))
            forceUpdate()
            refreshToken()
            reload()
        }).catch(error => {
            setButtonPopupAcceptAction(false)
            const message = error.response.data
            handleError(message, error.response.status)
        });
    }

    const reload = () => {
        getCruisesForCruiseGroup(group.uuid)
            .then(res => {
                setCruises(res.data)
                refreshToken();
            })
            .catch(error => {
                const message = error.response.data
                const status = error.response.status
                handleError(message, status)
            })
    }

    const handleSetOpen = () => {
        setOpen(state => !state);
        getCruisesForCruiseGroup(group.uuid)
            .then(res => {
                setCruises(res.data)
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
            <TableCell style={style}>{group.uuid}</TableCell>
            <TableCell style={style}>{group.numberOfSeats}</TableCell>
            <TableCell style={style}>{group.price +" pln"}</TableCell>
            <TableCell style={style}>{group.description}</TableCell>
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
                            showSuccess(t('successful action'))
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
                                        }}>{t("isPublished")}</TableCell>
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
                                        }}>{t("deactivate")}</TableCell>
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
                                                       style={style}><ActiveIcon active={cruise.active} /></TableCell>
                                            <TableCell align="center"
                                                       style={style}><ActiveIcon active={cruise.published} /></TableCell>
                                            <TableCell align="center">
                                                <Link to="/reservations">
                                                    <Button  className={buttonClass.root} onClick={() => {handleReservations(cruise.uuid)}}
                                                    >{t("reservations")}</Button>
                                                </Link>
                                            </TableCell>
                                            <TableCell align="center">
                                                <Link to={`attractions/${cruise.uuid}`}><Button className={buttonClass.root}>{t("attractions")}</Button></Link>
                                            </TableCell>
                                                <React.Fragment>
                                                    <TableCell align="center">
                                                    <RoundedButton
                                                        color="pink"
                                                        disabled={!(cruise.published && cruise.active)}
                                                        className={buttonClass.root}
                                                        onClick={() => {
                                                            setDeactivateCruise({
                                                                uuid : cruise.uuid,
                                                                etag : cruise.etag,
                                                                version : cruise.version
                                                            })
                                                            handleConfirm(deactivateCruise)
                                                            }
                                                        }
                                                    >
                                                        {t("deactivate")}
                                                    </RoundedButton>
                                                </TableCell>
                                              </React.Fragment>
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

const useAutocompleteStyles = (darkMode: boolean) => makeStyles(theme => ({
    root: {
        '& .MuiFormLabel-root, & input': {
            color: `var(--${darkMode ? 'white' : 'dark'})`,
        },
        '& svg': {
            fill: `var(--${darkMode ? 'white' : 'dark'})`,
        },
        '& .MuiOutlinedInput-notchedOutline, & .MuiOutlinedInput-root:hover .MuiOutlinedInput-notchedOutline, & .MuiOutlinedInput-root.Mui-focused .MuiOutlinedInput-notchedOutline': {
            borderColor: `var(--${darkMode ? 'white' : 'dark'})`,
        },
        '': {

        }
    }
}))()

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

    const autocomplete = useAutocompleteStyles(darkMode)

    return (
        <div>
            <Autocomplete
                className={autocomplete.root}
                options={CruiseGroups}
                inputValue={searchInput}
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
                            <TableCell style={{
                                backgroundColor: `var(--${!darkMode ? 'white' : 'dark-light'}`,
                                color: `var(--${!darkMode ? 'dark' : 'white-light'}`
                            }}>{t('expand cruises')}</TableCell>
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
                            }}>{"UUID"}</TableCell>
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
                            }}></TableCell>
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