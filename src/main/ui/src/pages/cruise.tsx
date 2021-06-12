import React, {useEffect, useState} from 'react';
import {useHistory, useParams} from "react-router-dom";
import {Box, Grid, Menu, MenuItem} from "@material-ui/core";
import {getCruiseByUUID, getRelatedCruises} from "../Services/cruisesService";
import styles from '../styles/Cruise.module.css';
import HeaderFooterLayout from "../layouts/HeaderFooterLayout";
import {useTranslation} from "react-i18next";
import PersonIcon from '@material-ui/icons/Person';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';
import DirectionsBoatIcon from '@material-ui/icons/DirectionsBoat';
import RoundedButton from "../components/RoundedButton";

export default function Cruise() {
    const { id } = useParams<{id: string}>();

    const {t} = useTranslation();

    const history = useHistory();

    const [cruise, setCruise] = useState<any>()
    const [relatedCruises, setRelatedCruises] = useState<any[]>([])

    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

    const handleMenuOpen = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleCruiseOpen = (uuid: string) => () => {
        history.push(`/cruise/${uuid}`)
        document.location.reload()
    }

    useEffect(() => {
        getCruiseByUUID(id).then(res => {
            setCruise(res.data)
            console.log(res.data)

            getRelatedCruises(res.data.cruiseGroupDto.uuid).then(related => {
                setRelatedCruises(related.data.filter((item: any) => item.uuid !== res.data.uuid))
                console.log(related.data.filter((item: any) => item.uuid !== res.data.uuid))
            })
        })
    }, [])

    const getDate = (date: any) => {
        if (!date) {
            return
        }

        const {
            dayOfMonth,
            monthValue,
            year
        } = date;

        return dayOfMonth + '.' + monthValue + '.' + year;
    }

    return (
        <HeaderFooterLayout>

            <Grid container className={styles.wrapper}>
                <Grid item xs={12} md={7} lg={6} className={styles.infowrap}>
                    <div>
                        <h3>{cruise?.cruiseGroupDto?.company.name}</h3>
                        <h1>{cruise?.cruiseGroupDto?.name}</h1>
                        <h4>{getDate(cruise?.startDate)} - {getDate(cruise?.endDate)}</h4>
                        <p className={styles.description}>
                            {cruise?.cruiseGroupDto?.description || t('no description')}
                        </p>
                        <div style={{display: 'flex', alignItems: 'center'}}>
                            <PersonIcon fontSize="large" style={{fill: 'var(--dark-dark)', marginRight: 8}} />
                            {cruise?.cruiseGroupDto?.numberOfSeats}
                        </div>
                        <div style={{display: 'flex', alignItems: 'center'}}>
                            <AttachMoneyIcon fontSize="large" style={{fill: 'var(--dark-dark)', marginRight: 8}} />
                            {cruise?.cruiseGroupDto?.price}
                        </div>
                        <div style={{display: 'flex', alignItems: 'center'}}>
                            <DirectionsBoatIcon fontSize="large" style={{fill: 'var(--dark-dark)', marginRight: 8}} />
                            <div>
                                <div>{`${cruise?.cruiseGroupDto?.cruiseAddress.street}, ${cruise?.cruiseGroupDto?.cruiseAddress.streetNumber} (${cruise?.cruiseGroupDto?.cruiseAddress.harborName})`}</div>
                                <div>{`${cruise?.cruiseGroupDto?.cruiseAddress.cityName}, ${cruise?.cruiseGroupDto?.cruiseAddress.countryName}`}</div>
                            </div>
                        </div>
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                        <RoundedButton color="blue" style={{fontSize: 16}}>
                            {t('reserve')}
                        </RoundedButton>

                        <div>
                            <RoundedButton disabled={!relatedCruises.length} color="dark" onClick={relatedCruises.length ? handleMenuOpen : undefined}>
                                {t(relatedCruises.length ? 'choose another date' : 'no dates')}
                            </RoundedButton>
                            <Menu
                                id="simple-menu"
                                anchorEl={anchorEl}
                                keepMounted
                                open={Boolean(anchorEl)}
                                onClose={handleMenuClose}
                                style={{height: 300}}
                            >
                                {
                                    relatedCruises.map(({uuid, startDate, endDate}: any, index: number) => (
                                        <MenuItem key={index} onClick={handleCruiseOpen(uuid)}>
                                            {`${getDate(startDate)} - ${getDate(endDate)}`}
                                        </MenuItem>
                                    ))
                                }
                            </Menu>
                        </div>
                    </div>
                </Grid>
            </Grid>

        </HeaderFooterLayout>
    )
}