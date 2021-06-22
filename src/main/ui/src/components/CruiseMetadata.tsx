import {Box, List, ListItem, ListItemIcon, ListItemText} from '@material-ui/core'
import {Link, useParams} from 'react-router-dom';
import styles from '../styles/PanelMenu.module.css'
import {useSelector} from "react-redux";
import {selectDarkMode, selectEmail, selectFirstName, selectSecondName} from "../redux/slices/userSlice";
import {Color} from "./interfaces";
import React, {useEffect} from "react";
import AppColorSetter from "./AppColorSetter";
import GoBackIcon from '@material-ui/icons/ArrowBackRounded';
import {useTranslation} from "react-i18next";
import LanguageSetter from "./LanguageSetter";
import {getCruiseMetadata} from "../Services/cruisesService";
import {refreshToken} from "../Services/userService";
import useHandleError from "../errorHandler";



export default function AdminHomePage() {
    const darkMode = useSelector(selectDarkMode)
    const {t} = useTranslation()
    const {uuid} = useParams<{ uuid: string }>();
    const handleError = useHandleError()

    useEffect( () =>{
        getCruiseMetadata(uuid).then(res => {
            sessionStorage.setItem("Test", JSON.stringify(res.data));
            refreshToken();
        }, error => {
            const message = error.response.data
            handleError(message, error.response.status)
        });
    },[])

    return (
        <div className={styles.panel_page}>
            <h1>{t("business_workerHomePage")}</h1>
        </div>
    )
}