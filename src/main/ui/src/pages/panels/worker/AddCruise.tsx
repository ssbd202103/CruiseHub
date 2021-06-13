import {Box, Grid, TextField} from "@material-ui/core";
import PasswordIcon from "@material-ui/icons/VpnKeyRounded";
import DarkedTextField from "../../../components/DarkedTextField";
import React, {useEffect, useReducer, useState} from "react";
import {useTranslation} from "react-i18next";
import RoundedButton from "../../../components/RoundedButton";
import ImageUploading, { ImageListType } from "react-images-uploading";
import store from "../../../redux/store";
import {useSelector} from "react-redux";
import {selectCompany} from "../../../redux/slices/userSlice";
import axios from "../../../Services/URL";
import useHandleError from "../../../errorHandler";
import {useSnackbarQueue} from "../../snackbar";
import moment from 'moment';
import pl from "date-fns/locale/pl"
import eng from "date-fns/locale/en-GB"
import styles from "../../../styles/auth.global.module.css";
import DarkedSelect from "../../../components/DarkedSelect";
import {getAllCruiseGroup} from "../../../Services/cruiseGroupService";
import {refreshToken} from "../../../Services/userService";
// import LocalizationProvider from '@material-ui/lab/LocalizationProvider';
// import AdapterDateFns from '@material-ui/lab/AdapterDateFns';
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker, KeyboardTimePicker
} from '@material-ui/pickers';
import DateFnsUtils from "@date-io/date-fns";




export default function AddCruise(){
    const [, forceUpdate] = useReducer(x => x + 1, 0);
    const {t} = useTranslation();
    console.log(t.name)
    const [cruiseName, setCruiseName] = useState('')
    const [startDate, setStartDate] = React.useState<Date | null>(
        new Date(),
    );
    const [endDate, setEndDate] = React.useState<Date | null>(
        new Date(),
    );
    const [endTime, setEndTime] = React.useState<Date | null>(
        new Date(),
    );
    const [startTime, setStartTime] = React.useState<Date | null>(
        new Date(),
    );
    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')
    const [cruiseGroupList, setCruiseGroupList] = useState([]);
    const [cruiseGroup, setCruiseGroup] = useState("")

    const handleStartDateChange = (startDate: Date | null) => {
        setStartDate(startDate);
    };
    const handleEndDateChange = (endDate: Date | null) => {
        setEndDate(endDate);
    };
    const handleStartTimeChange = (startTime: Date | null) => {
        setStartTime(startTime);
    };
    const handleEndTimeChange = (endTime: Date | null) => {
        setEndTime(endTime);
    };
    const maxNumber = 1;


    useEffect(() => {

        getAllCruiseGroup().then(res => {
            setCruiseGroupList(res.data.map((cruiseGroup: {uuid: string }) => cruiseGroup.uuid.toString()))
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        }).then(res => {
            refreshToken()
        });
    }, []);



    const HandleAddCruise =  () =>{


        if(!cruiseGroup ||!startDate || !endDate || !startTime || !endTime)
        {
            handleError('error.fields')
            return
        }

        startDate.setHours(0, 0, 0, 0)
        var newStartDateWithoutTime = new Date(startTime.getTime())
        newStartDateWithoutTime.setHours(0,0,0,0)
        var newStartDate = new Date(startDate.getTime() + startTime?.getTime() - newStartDateWithoutTime.getTime())


        endDate.setHours(0, 0, 0, 0)
        var newEndDateWithoutTime = new Date(endTime.getTime())
        newEndDateWithoutTime.setHours(0,0,0,0)
        var newEndDate = new Date(endDate.getTime() + endTime.getTime() - newEndDateWithoutTime.getTime())

        var date = new Date()

        if(newStartDate.getTime() > newEndDate.getTime())
        {
            handleError('error.field.end.date.before.start.date')
            return
        }
        if(newStartDate.getTime() <  date.getTime())
        {
            handleError('error.field.start.date')
            return;
        }

        newStartDate.setSeconds(0)
        newEndDate.setSeconds(0)

        const {token} = store.getState();
        const json = JSON.stringify({
            cruiseGroup: cruiseGroup,
            startDate: startDate,
            endDate: endDate
        })

        // axios.post('cruiseGroup/add-cuise-group',json,{
        //     headers: {
        //         "Content-Type": "application/json",
        //         "Accept": "application/json",
        //         "Authorization": `Bearer ${token}`
        //     }}).then(res => {
        //     showSuccess(t('successful action'))
        //     forceUpdate()
        // }).catch(error => {
        //     const message = error.response.data
        //     handleError(message, error.response.status)
        // })
    }

    return (
        <div>
            <Box>
                {/*//<MuiPickersUtilsProvider locale={eng} utils={DateFnsUtils}>*/}
                {/*<LocalizationProvider dateAdapter={AdapterDateFns} locale={localeMap[locale]}>*/}
                {/*    <KeyboardDatePicker*/}
                {/*        disableToolbar*/}
                {/*        variant="inline"*/}
                {/*        format="MM/dd/yyyy"*/}
                {/*        margin="normal"*/}
                {/*        id="date-picker-inline"*/}
                {/*        label={t("startDate") + ' *'}*/}
                {/*        value={startDate}*/}
                {/*        onChange={handleStartDateChange}*/}
                {/*        KeyboardButtonProps={{*/}
                {/*            'aria-label': 'change date',*/}
                {/*        }}*/}
                {/*    />*/}
                {/*    <KeyboardTimePicker*/}
                {/*        margin="normal"*/}
                {/*        id="time-picker"*/}
                {/*        label={t("startTime") + ' *'}*/}
                {/*        value={startTime}*/}
                {/*        onChange={handleStartTimeChange}*/}
                {/*        KeyboardButtonProps={{*/}
                {/*            'aria-label': 'change time',*/}
                {/*        }}*/}
                {/*    />*/}
                {/*</LocalizationProvider>*/}
                {/*</MuiPickersUtilsProvider>*/}
            </Box>
            <Box>
                <MuiPickersUtilsProvider locale={pl} utils={DateFnsUtils}>
                    <KeyboardTimePicker
                        margin="normal"
                        id="time-picker"
                        label={t("endTime") + ' *'}
                        value={endTime}
                        onChange={handleEndTimeChange}
                        KeyboardButtonProps={{
                            'aria-label': 'change time',
                        }}
                    />
                    <KeyboardDatePicker
                        disableToolbar
                        variant="inline"
                        format="MM/dd/yyyy"
                        margin="normal"
                        id="date-picker-inline"
                        label={t("endDate") + ' *'}
                        value={endDate}
                        onChange={handleEndDateChange}
                        KeyboardButtonProps={{
                            'aria-label': 'change date',
                        }}
                    />

                </MuiPickersUtilsProvider>
            </Box>
            <Box>
                <DarkedSelect
                    label={t("cruiseGroup") + ' *'}
                    options={cruiseGroupList}
                    className={styles.input}
                    style={{
                        marginRight: 20
                    }}
                    onSelectedChange={setCruiseGroup}
                    colorIgnored
                />
            </Box>

            <RoundedButton
                onClick={HandleAddCruise}
                style={{width: '50%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                color="pink"
            >{t("addCruise")} </RoundedButton>
        </div>
    )
}