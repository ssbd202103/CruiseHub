import React, {useEffect, useState} from 'react';
import {useHistory, useParams} from "react-router-dom";
import {Box, Card, CardActions, CardContent, Grid, Menu, MenuItem} from "@material-ui/core";
import {getCruiseByUUID, getRelatedCruises} from "../Services/cruisesService";
import styles from '../styles/Cruise.module.css';
import HeaderFooterLayout from "../layouts/HeaderFooterLayout";
import {useTranslation} from "react-i18next";
import PersonIcon from '@material-ui/icons/Person';
import AttachMoneyIcon from '@material-ui/icons/AttachMoney';
import DirectionsBoatIcon from '@material-ui/icons/DirectionsBoat';
import RoundedButton from "../components/RoundedButton";
import {getAttractionsByCruiseUUID} from "../Services/attractionService";
import useBreakpoints from "./breakpoints";

export default function Cruise() {
    const { id } = useParams<{id: string}>();

    const {t} = useTranslation();

    const history = useHistory();

    const [cruise, setCruise] = useState<any>()
    const [relatedCruises, setRelatedCruises] = useState<any[]>([])
    const [attractions, setAttractions] = useState<any[]>([])

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

            getRelatedCruises(res.data.cruiseGroupDto.uuid).then(related => {
                setRelatedCruises(related.data.filter((item: any) => item.uuid !== res.data.uuid))
            })

            getAttractionsByCruiseUUID(id).then(r => {
                setAttractions(r.data)
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
                <Grid item xs className={styles.infowrap}>
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

                <Grid item xs={1} />

                <Grid item xs className={styles.attractions}>
                    <h3 style={{ marginRight: 24 }}>{t('attractions')}</h3>
                    <div style={{ padding: '0 24px', width: '100%', overflow: 'auto', height: '100%' }}>
                        {
                            attractions.length ?
                               attractions.map(({name, description, price}, index) => (
                                    <Card key={index} style={{ marginBottom: 16 }}>
                                        <CardContent>
                                            <h4>{name}</h4>
                                            <p>{description}</p>
                                            <div style={{display: 'flex', alignItems: 'center'}}>
                                                <AttachMoneyIcon style={{fill: 'var(--dark-dark)', marginRight: 8}} />
                                                {price}
                                            </div>
                                        </CardContent>
                                        <CardActions>
                                            <RoundedButton color="yellow">{t('take')}</RoundedButton>
                                        </CardActions>
                                    </Card>
                                ))
                                : <h4>{t('no attractions')}</h4>
                        }
                    </div>
                </Grid>

                <Grid item xs={1} />

                <Grid item xs className={styles.opinions}>
                    <h3 style={{ marginRight: 24 }}>{t('opinions')}</h3>
                </Grid>
            </Grid>

        </HeaderFooterLayout>
    )
}