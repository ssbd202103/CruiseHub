import {useTranslation} from "react-i18next";
import useHandleError from "../../../errorHandler";
import {useSnackbarQueue} from "../../snackbar";
import {Box} from "@material-ui/core";
import DarkedTextField from "../../../components/DarkedTextField";
import ImageUploading, {ImageListType} from "react-images-uploading";
import RoundedButton from "../../../components/RoundedButton";
import React, {useEffect, useReducer, useState} from "react";
import store from "../../../redux/store";
import axios from "../../../Services/URL";
import {getCruiseGroupForBusinessWorker} from "../../../Services/cruiseGroupService";
import {refreshToken} from "../../../Services/userService";
import {useSelector} from "react-redux";
import {selectCompany} from "../../../redux/slices/userSlice";
import ship3 from "../../../images/ship3.jpg";
import {Link, useHistory} from "react-router-dom";

export default function ChangeCruiseGroupData(){
    const {t} = useTranslation()
    const [, forceUpdate] = useReducer(x => x + 1, 0);
    const handleError = useHandleError()
    const showSuccess = useSnackbarQueue('success')
    const [cruiseName, setCruiseName] = useState('')
    const [description, setDescription] = useState('')
    const [numberOfSeats, setNumberOfSeats] = useState('')
    const [price, setPrice] = useState('')
    const [streetNumber, setStreetNumber] = useState('')
    const [street, setStreet] = useState('')
    const [harborName, setHarborName] = useState('')
    const [city, setCity] = useState('')
    const [country, setCountry] = useState('')
    const maxNumber = 1;
    const [images, setImages] = React.useState([]);
    const crusieGropData = JSON.parse(sessionStorage.getItem('ChangeCruiseGroupData') as string)
    const [visiblePicture,setVisiblePictrure] = useState(true)
    const history = useHistory();
    const onChange = (
        imageList: ImageListType,
    ) => {
        setImages(imageList as never[]);
        setVisiblePictrure(state => !state)
    };
useEffect(() =>{
    setCruiseName(crusieGropData.name);
    setDescription(crusieGropData.description);
    setNumberOfSeats(crusieGropData.numberOfSeats);
    setPrice(crusieGropData.price);
    setStreetNumber(crusieGropData.cruiseAddress.streetNumber);
    setStreet(crusieGropData.cruiseAddress.street);
    setHarborName(crusieGropData.cruiseAddress.harborName);
    setCity(crusieGropData.cruiseAddress.cityName);
    setCountry(crusieGropData.cruiseAddress.countryName);


}, [])

    const HandleChangeCruiseGroup =  async() =>{
        if( !numberOfSeats ||!price || !streetNumber)
        {
            handleError('error.fields')
            return
        }
        const {token} = store.getState();
        const json = JSON.stringify({
            uuid: crusieGropData.uuid,
            name: cruiseName,
            numberOfSeats: numberOfSeats,
            price: price,
            cruiseAddress:{
                street: street,
                streetNumber: streetNumber,
                harborName: harborName,
                cityName: city,
                countryName: country
            },
            picture: images[0],
            description: description,
            version: crusieGropData.version
        })

       await axios.put('cruiseGroup/change-cruise-group',json,{
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": crusieGropData.etag,
                "Authorization": `Bearer ${token}`
            }}).then(res => {
            showSuccess(t('successful action'))
            forceUpdate()
             history.push('/listCruiseGroup')
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        })

    }
    let pictureUrl: string | undefined
    if(crusieGropData.cruisePictures.length>0){
        pictureUrl =crusieGropData.cruisePictures[0].dataURL
    }
    else {
        pictureUrl= ship3
    }
    return(
        <div>
            <h3>{t("/changeCruiseGroupData")}</h3>
            <Box style={{
                width: '100%',
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between"
            }}>
                <DarkedTextField
                    type="text"
                    label={t("cruiseName")}
                    style={{
                        width: '100%',
                        margin: '20px 0'
                    }}
                    placeholder={t("cruisneNamePlaceHolder")}
                    value={cruiseName}
                    onChange={event => {
                        setCruiseName(event.target.value)
                    }}
                />
                <DarkedTextField
                    type="text"
                    label={t("description") + ' *'}
                    style={{
                        width: '100%',
                        margin: '20px 0'
                    }}
                    placeholder={t("descriptionPlaceHolder")}
                    value={description}
                    onChange={event => {
                        setDescription(event.target.value)
                    }}
                />
                <DarkedTextField
                    type="number"
                    label={t("numberOfSeats") + ' *'}
                    style={{
                        width: '100%',
                        margin: '20px 0'
                    }}
                    placeholder={t("seatsPlaceHolder")}
                    value={numberOfSeats}
                    onChange={event => {
                        setNumberOfSeats(event.target.value)
                    }}
                />
                <DarkedTextField
                    type="number"
                    label={t("price") + ' *'}
                    style={{
                        width: '100%',
                        margin: '20px 0'
                    }}
                    placeholder={t("pricePlaceHolder")}
                    value={price}
                    onChange={event => {
                        setPrice(event.target.value)
                    }}
                />
            </Box>
            <Box style={{
                width: '100%',
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between"
            }}>
                <DarkedTextField
                    type="text"
                    label={t("street") + ' *'}
                    style={{
                        width: '100%',
                        margin: '20px 0'
                    }}
                    placeholder={t("streetPlaceHolder")}
                    value={street}
                    onChange={event => {
                        setStreet(event.target.value)
                    }}
                />
                <DarkedTextField
                    type="number"
                    label={t("streetNumber") + ' *'}
                    style={{
                        width: '100%',
                        margin: '20px 0'
                    }}
                    placeholder={t("numberPlaceHolder")}
                    value={streetNumber}
                    onChange={event => {
                        setStreetNumber(event.target.value)
                    }}
                />
                <DarkedTextField
                    type="text"
                    label={t("harborName") + ' *'}
                    style={{
                        width: '100%',
                        margin: '20px 0'
                    }}
                    placeholder={t("harborPlaceHolder")}
                    value={harborName}
                    onChange={event => {
                        setHarborName(event.target.value)
                    }}
                />
                <DarkedTextField
                    type="text"
                    label={t("city") + ' *'}
                    style={{
                        width: '100%',
                        margin: '20px 0'
                    }}
                    placeholder={t("cityPlaceHolder")}
                    value={city}
                    onChange={event => {
                        setCity(event.target.value)
                    }}
                />
                <DarkedTextField
                    type="text"
                    label={t("country") + ' *'}
                    style={{
                        width: '100%',
                        margin: '20px 0'
                    }}
                    placeholder={t("countryPlaceHolder")}
                    value={country}
                    onChange={event => {
                        setCountry(event.target.value)
                    }}
                />
            </Box>
            <Box style ={{
                width: '100%',
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between"}}>
                <ImageUploading
                    multiple
                    value={images}
                    onChange={onChange}
                    maxNumber={maxNumber}
                >
                    {({
                          imageList,
                          onImageUpload,
                          onImageRemoveAll,
                          onImageUpdate,
                          onImageRemove,
                          isDragging,
                          dragProps
                      }) => (
                        <div className="upload__image-wrapper" >
                            <RoundedButton
                                color="blue-dark"

                                onClick={onImageUpload}
                            >
                                {t("addPicture")}
                            </RoundedButton>
                            <img  style={{display: visiblePicture? "block":"none"}} src = {pictureUrl} width="30%"/>
                            &nbsp;
                            {imageList.map((image, index) => (
                                <div key={index} className="image-item">

                                    <img src={image.dataURL} alt="" width="60%" />
                                    <div className="image-item__btn-wrapper">
                                        <RoundedButton color="blue-dark"
                                                       onClick={() => onImageRemove(index)}>{t("removePicture")}</RoundedButton>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </ImageUploading>

            </Box>
            <RoundedButton
                onClick={HandleChangeCruiseGroup}
                style={{width: '50%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                color="pink"
            >{t("changeData")} </RoundedButton>
        </div>
    )
}