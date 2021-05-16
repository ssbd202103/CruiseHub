import React, {createRef, useState} from 'react'

import Grid from '@material-ui/core/Grid'

import {useTranslation} from 'react-i18next';

import styles from '../../../styles/ManageAccount.module.css'
import RoundedButton from '../../../components/RoundedButton';
import DarkedTextField from '../../../components/DarkedTextField';
import {useDispatch, useSelector} from "react-redux";
import {selectColor} from "../../../redux/slices/colorSlice";
import { changeOwnPassword as changeOwnPasswordService } from "../../../Services/changePasswordService";
import ChangeEmail from "../../../components/changeData/ChangeEmail";

export default function ManageAccount() {
    const {t} = useTranslation()

    const dispatch = useDispatch()



    const color = useSelector(selectColor)

    const [ChangAddress, setChangChangAddress] = useState(false)
    const [ChangPasswd, setChangPasswd] = useState(false)




    //Functions for address data change
    const handleChangAddress = () => {
        setChangChangAddress(state => !state)
        setChangPasswd( false)
    }
    const changeAddress = () => {
        //Place for transfer function (change address in database)
        handleChangAddress()
    }

    //Functions for password
    const handleChangPasswd = () => {
        setChangPasswd(state => !state)
        setChangChangAddress(false)
    }


    return (
        <Grid container className={styles.wrapper + ' ' + styles[`text-${color ? 'white' : 'dark'}`]} >

        </Grid>
    )
}