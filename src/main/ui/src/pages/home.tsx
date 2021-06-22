import ArrowIcon from '@material-ui/icons/ExpandMoreRounded'

import CruiseCard from '../components/CruiseCard'

import HeaderFooterLayout from '../layouts/HeaderFooterLayout'

import styles from '../styles/Home.module.css'

import {useTranslation} from 'react-i18next'
import {useSelector} from "react-redux";
import {selectDarkMode, selectLanguage} from "../redux/slices/userSlice";
import React, {useEffect, useState} from "react";
import {getPublishedCruises} from "../Services/cruisesService";
import store from "../redux/store";
import {refreshToken} from "../Services/userService";
import useHandleError from "../errorHandler";
import {getAllAccounts} from "../Services/accountsService";
import DateFnsUtils from "@date-io/date-fns";
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker
} from '@material-ui/pickers';
import pl from "date-fns/locale/pl";
import eng from "date-fns/locale/en-GB";
import {makeStyles} from "@material-ui/styles";
import RoundedButton from "../components/RoundedButton";
import axios from "../Services/URL";
import {TextField} from "@material-ui/core";
import Autocomplete from "../components/Autocomplete";


const useStyles = makeStyles(theme => ({
    light: {
        '& .MuiFormLabel-root, & .MuiInputBase-input': {
            color: 'var(--dark)',
        },
        '& .MuiSvgIcon-root': {
            fill: 'var(--dark)',
        },
        '& .MuiInput-underline::before, & .MuiInput-underline::after, & .MuiInput-underline:hover:not(.Mui-disabled):before' : {
            borderColor: 'var(--dark)',
        },
    },
    dark: {
        '& .MuiFormLabel-root, & .MuiInputBase-input': {
            color: 'var(--white)',
        },
        '& .MuiSvgIcon-root': {
            fill: 'var(--white)',
        },
        '& .MuiInput-underline::before, & .MuiInput-underline::after, & .MuiInput-underline:hover:not(.Mui-disabled):before' : {
            borderColor: 'var(--white)',
        },
    },
}));





export interface Cruise {
    price: number,
    name: string,
    img:string,
}


function createData(
    price: number,
    name: string,
    img: string,

) {
    return {
        price: price,
        name: name,
        img: img,
    };
}


export interface RowProps {
    row: ReturnType<typeof createData>,
}

function Row(props: RowProps) {
    const {

    } = props;


    const {row} = props;

    return (
        <React.Fragment>
            <CruiseCard
                price = {row.price}
                title = {row.name}
                img = {row.img}
            />
        </React.Fragment>
    );
}

export default function Home() {
    const [cruises, setCruises] = useState<Cruise[]>([]);
    const handleError = useHandleError()
    const {token} = store.getState()

    const {t} = useTranslation()


    function search() {

    }

    const HandleAddCruise = () => {



    }

    const HandleFilterByPriceCruise = () => {
        setCruises(prev=>cruises.sort((a, b) => (a.price - b.price)))
    }

    const darkMode = useSelector(selectDarkMode)
    const headersArray = t("mainPage.headers", {returnObjects: true})

    const [startDate, setStartDate] = React.useState<Date | null>(
        new Date(),
    );

    const handleStartDateChange = (startDate: Date | null) => {
        setStartDate(startDate);
    };



    const [endDate, setEndDate] = React.useState<Date | null>(
        new Date(),
    );

    const handleEndDateChange = (startDate: Date | null) => {
        setStartDate(startDate);
    };


    const languageType = useSelector(selectLanguage);

    const language = languageType === 'PL' ? pl : eng;


    const [searchInput, setSearchInput] = useState("");

    useEffect(() => {
        getPublishedCruises().then(res => {
            setCruises(res.data)
            refreshToken()
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        })
    }, []);


    const classes = useStyles()
    const cruisesNameList: String[] = [];

    return (
        <HeaderFooterLayout>
            <header className={styles.header}>
                <h1>{headersArray[Math.floor(Math.random() * headersArray.length)]}</h1>
                <ArrowIcon className={styles.arrow}/>
            </header>

            <section className={styles.ad}>
                <div>
                    <div>
                        <MuiPickersUtilsProvider locale={language} utils={DateFnsUtils} >
                            <KeyboardDatePicker
                                style={{marginRight: 30}}
                                className={darkMode ? classes.dark: classes.light}
                                disableToolbar
                                autoOk={true}
                                variant="inline"
                                maxDateMessage={t("max.date.message")}
                                minDateMessage={t("min.date.message")}
                                invalidDateMessage={t("invalid.date.message")}
                                invalidLabel={t("invalid.label")}
                                cancelLabel={t("cancel.label")}
                                clearLabel={t("clear.label")}
                                okLabel={t("ok.label")}
                                todayLabel={t("today.label")}
                                format="MM/dd/yyyy"
                                margin="normal"
                                label={t("startDate")}
                                value={startDate}
                                onChange={handleStartDateChange}
                                KeyboardButtonProps={{
                                    'aria-label': 'change date',
                                }}
                            />
                            <KeyboardDatePicker
                                style={{marginRight: 30}}
                                className={darkMode ? classes.dark: classes.light}
                                disableToolbar
                                autoOk={true}
                                variant="inline"
                                maxDateMessage={t("max.date.message")}
                                minDateMessage={t("min.date.message")}
                                invalidDateMessage={t("invalid.date.message")}
                                invalidLabel={t("invalid.label")}
                                cancelLabel={t("cancel.label")}
                                clearLabel={t("clear.label")}
                                okLabel={t("ok.label")}
                                todayLabel={t("today.label")}
                                format="MM/dd/yyyy"
                                margin="normal"
                                label={t("endDate")}
                                value={endDate}
                                onChange={handleEndDateChange}
                                KeyboardButtonProps={{
                                    'aria-label': 'change date',
                                }}
                            />
                        </MuiPickersUtilsProvider>
                        <RoundedButton
                            onClick={HandleAddCruise}
                            style={{width: '50%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                            color="pink"
                        >{t("filterCruises")} </RoundedButton>

                    </div>
                    <div>
                        <Autocomplete
                            options={cruisesNameList}
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
                    </div>
                    <div>
                        <RoundedButton
                            onClick={HandleFilterByPriceCruise}
                            style={{width: '50%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                            color="pink"
                        >{t("filterByPriceCruises")} </RoundedButton>
                    </div>
                </div>
            </section>


            <section className={styles['top-cruises']}>
                <h2 className={styles.h2}
                    style={{color: `var(--${darkMode ? 'dark' : 'white'}`}}>{t("the best cruises")}</h2>

                <div className={styles['cruises-grid']}>
                    {(cruises.map((cruise, index) =>(<CruiseCard
                        key = {index}
                        price = {cruise.price}
                        title = {cruise.name}
                        img = {cruise.img}
                    />)))}
                </div>
            </section>
        </HeaderFooterLayout>
    )
}
