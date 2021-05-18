import { useTranslation } from "react-i18next";
import LightThemeIcon from '@material-ui/icons/WbSunnyRounded';
import DarkThemeIcon from '@material-ui/icons/Brightness3Rounded';
import {IconButton} from "@material-ui/core";
import {useState} from "react";
import {useDispatch, useSelector} from "react-redux";

import {selectColor} from '../redux/slices/colorSlice'
import {change} from '../redux/slices/colorSlice'

export default function AppColorSetter() {
    const {t} = useTranslation();

    const color = useSelector(selectColor)

    const dispatch = useDispatch()

    const handleClick = () => {
        dispatch(change())
    }

    return (
        <div style={{
            width: '100%',
            display: 'flex',
            justifyContent: 'space-between',
            fontFamily: 'Montserrat, sans-serif',
            fontSize: '1.4rem',
            alignItems: 'center'
        }}
             onClick={handleClick}
        >
            <p>{t("color")}</p>
            <IconButton>
                {
                    color ?
                        <LightThemeIcon
                            fontSize="large"
                            style={{fill: 'var(--white)'}} /> :
                        <DarkThemeIcon
                            fontSize="large"
                            style={{fill: 'var(--dark)'}} />
                }
            </IconButton>
        </div>
    )
}