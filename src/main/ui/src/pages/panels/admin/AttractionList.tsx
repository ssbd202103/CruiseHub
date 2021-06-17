import React, {useEffect, useState} from "react";
import {Attraction} from '../../../interfaces/Attraction';
import {getAttractionsByCruiseUUID} from "../../../Services/attractionService";
import {useSnackbarQueue} from "../../snackbar";
import {useTranslation} from "react-i18next";
import { useParams } from 'react-router-dom';
import { DataGrid, GridColDef, GridCellParams, GridRowSelectedParams } from '@material-ui/data-grid';
import DeleteIcon from '@material-ui/icons/DeleteRounded';
import EditIcon from '@material-ui/icons/EditRounded';
import styles from '../../../styles/AttractionList.module.css';
import Dialog from '../../../components/Dialog';
import DarkedTextField from "../../../components/DarkedTextField";
import {TextareaAutosize} from "@material-ui/core";
import RoundedButton from "../../../components/RoundedButton";

export default function AttractionList() {

    const { uuid } = useParams<{ uuid: string }>();

    const {t} = useTranslation();

    const showSuccess = useSnackbarQueue('success');
    const showError = useSnackbarQueue('error');

    const [attractions, setAttractions] = useState<Attraction[]>([]);

    useEffect(() => {
        getAttractionsByCruiseUUID(uuid).then(res => {
            setAttractions(res.data)
            showSuccess('successful action')
        }).catch(error => {
            showError(t(error))
        })
    }, [])


    // Data grid events
    const [selectedRow, setSelectedRow] = useState('')

    const handleSelectedRow = (row: GridRowSelectedParams) => {
        setSelectedRow(row.data.id as string)
    }

    const cols: GridColDef[] = [
        { field: 'name', headerName: t('attractionName'), flex: 1 },
        { field: 'description', headerName: t('description'), flex: 1 },
        { field: 'price', headerName: t('price'), flex: 1 },
        { field: 'numberOfSeats', headerName: t('numberOfSeats'), flex: 1 },
        {
            field: '',
            renderHeader: params => <RoundedButton color="blue" onClick={() => {setCreateAttractionDialogOpen(true)}}>{t('create')}</RoundedButton>,
            headerClassName: styles['create-wrap'],
            sortable: false,
            disableColumnMenu: true,
            flex: 0.5,
            renderCell: (params: GridCellParams) =>
                (
                    <>
                        {selectedRow === params.row.id && (
                            <div className={styles['icon-wrap']}>
                                <EditIcon
                                    className={styles.edit}
                                    onClick={() => {
                                        const attraction = attractions.find(attraction => attraction.uuid === params.row.id)

                                        if (attraction) {
                                            setUuidEdit(params.row.id)
                                            setNameEdit(attraction.name)
                                            setDescriptionEdit(attraction.description)
                                            setPriceEdit(attraction.price)
                                            setNumberOfSeatsEdit(attraction.numberOfSeats)
                                            setEditAttractionDialogOpen(true)
                                        }
                                    }}
                                />
                                <DeleteIcon
                                    className={styles.delete}
                                    onClick={handleDeleteAttraction}
                                />
                            </div>
                        )}
                    </>
                )
        }
    ];

    // Requests handler

    const handleDeleteAttraction = () => {
        // TODO removing implementation
        alert("ATTRACTION MUST BE DELETED!")
    }

    const handleEditAttraction = () => {
        // TODO editing implementation
        alert("ATTRACTION MUST BE EDITED!")
    }
    
    const handleCreateAttraction = () => {
        // TODO creating implementation
        alert("ATTRACTION MUST BE CREATED!")
    }

    // Dialogs data
    const [editAttractionDialogOpen, setEditAttractionDialogOpen] = useState(false);
    const [uuidEdit, setUuidEdit] = useState('');
    const [nameEdit, setNameEdit] = useState('');
    const [descriptionEdit, setDescriptionEdit] = useState('');
    const [priceEdit, setPriceEdit] = useState(0);
    const [numberOfSeatsEdit, setNumberOfSeatsEdit] = useState(0);

    const [createAttractionDialogOpen, setCreateAttractionDialogOpen] = useState(false);
    const [nameCreate, setNameCreate] = useState('');
    const [descriptionCreate, setDescriptionCreate] = useState('');
    const [priceCreate, setPriceCreate] = useState(0);
    const [numberOfSeatsCreate, setNumberOfSeatsCreate] = useState(0);
    
    return (
        <>
            <DataGrid
                columns={cols}
                rows={attractions.map((attraction) => ({id: attraction.uuid, ...attraction}))}
                onRowSelected={handleSelectedRow}
            />
            <Dialog 
                open={editAttractionDialogOpen} 
                title={t('edit')} 
                onConfirm={handleEditAttraction} 
                onCancel={() => {
                    setEditAttractionDialogOpen(false)
                    setUuidEdit('')
                    setNameEdit('')
                    setDescriptionEdit('')
                    setPriceEdit(0)
                    setNumberOfSeatsEdit(0)
                }}
            >
                <div style={{ display: 'flex', flexDirection: 'column' }}>
                    <DarkedTextField
                        style={{marginBottom: 16}}
                        type="text"
                        label={t("attractionName")}
                        value={nameEdit}
                        onChange={event => {setNameEdit(event.target.value)}}
                    />

                    <TextareaAutosize
                        style={{
                            fontFamily: "'Montserrat Alternates', sans-serif",
                            fontSize: '1.2rem',
                            marginBottom: 16,
                        }}
                        placeholder={t('description')}
                        value={descriptionEdit}
                        onChange={event => {setDescriptionEdit(event.target.value)}}
                        rowsMin={7}
                    />

                    <DarkedTextField
                        style={{marginBottom: 16}}
                        type="number"
                        label={t("price")}
                        value={priceEdit}
                        onChange={event => {setPriceEdit(event.target.value as unknown as number)}}
                    />

                    <DarkedTextField
                        style={{marginBottom: 16}}
                        type="number"
                        label={t("numberOfSeats")}
                        value={numberOfSeatsEdit}
                        onChange={event => {setNumberOfSeatsEdit(event.target.value as unknown as number)}}
                    />
                </div>
            </Dialog>
            <Dialog 
                open={createAttractionDialogOpen} 
                title={t('create')} 
                onConfirm={handleCreateAttraction} 
                onCancel={() => {
                    setCreateAttractionDialogOpen(false)
                    setNameCreate('')
                    setDescriptionCreate('')
                    setPriceCreate(0)
                    setNumberOfSeatsCreate(0)
                }}
            >
                <div style={{ display: 'flex', flexDirection: 'column' }}>
                    <DarkedTextField
                        style={{marginBottom: 16}}
                        type="text"
                        label={t("attractionName")}
                        value={nameCreate}
                        onChange={event => {setNameCreate(event.target.value)}}
                    />

                    <TextareaAutosize
                        style={{
                            fontFamily: "'Montserrat Alternates', sans-serif",
                            fontSize: '1.2rem',
                            marginBottom: 16,
                        }}
                        placeholder={t('description')}
                        value={descriptionCreate}
                        onChange={event => {setDescriptionCreate(event.target.value)}}
                        rowsMin={7}
                    />

                    <DarkedTextField
                        style={{marginBottom: 16}}
                        type="number"
                        label={t("price")}
                        value={priceCreate}
                        onChange={event => {setPriceCreate(event.target.value as unknown as number)}}
                    />

                    <DarkedTextField
                        style={{marginBottom: 16}}
                        type="number"
                        label={t("numberOfSeats")}
                        value={numberOfSeatsCreate}
                        onChange={event => {setNumberOfSeatsCreate(event.target.value as unknown as number)}}
                    />
                </div>
            </Dialog>
        </>
    )
}