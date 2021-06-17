import {Box} from "@material-ui/core";
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
import {Link, useHistory} from "react-router-dom";
export default function (){
    const [, forceUpdate] = useReducer(x => x + 1, 0);
    const {t} = useTranslation();
    const [cruiseName, setCruiseName] = useState('')
    const [description, setDescription] = useState('')
    const [numberOfSeats, setNumberOfSeats] = useState('')
    const [price, setPrice] = useState('')
    const [streetNumber, setStreetNumber] = useState('')
    const [street, setStreet] = useState('')
    const [harborName, setHarborName] = useState('')
    const [city, setCity] = useState('')
    const [country, setCountry] = useState('')
    const handleError = useHandleError()
    const worker_Company = useSelector(selectCompany);
    const showSuccess = useSnackbarQueue('success')
    const history = useHistory();
    const [isPicture,setIsPicture] = React.useState(false)
      const maxNumber = 1;
      const [images, setImages] = React.useState([]);
      const onChange = (
          imageList: ImageListType,

      ) => {
          setIsPicture(state => !state)
          setImages(imageList as []);

      };

    const HandleAddCruise = async () =>{
        if( !numberOfSeats ||!price || !streetNumber || price.includes(','))
        {
            handleError('error.fields')
            return
        }
    const {token} = store.getState();
    const json = JSON.stringify({
        companyName: worker_Company,
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
        cruisePictures: images,
        description: description,
    })
    await axios.post('cruiseGroup/add-cuise-group',json,{
         headers: {
             "Content-Type": "application/json",
             "Accept": "application/json",
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

    return (
        <div>

            <Box style={{
                width: '100%',
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between"
            }}>
                <DarkedTextField
                    type="text"
                    label={t("cruiseName") + ' *'}
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
                                color="blue"

                                onClick={onImageUpload}
                            >
                                {t("addPicture")}
                            </RoundedButton>
                            &nbsp;
                            {imageList.map((image, index) => (
                                <div key={index} className="image-item">
                                    <img src={image.dataURL} alt="" width="60%" />
                                    <div className="image-item__btn-wrapper">
                                        <RoundedButton color="blue"
                                                       onClick={() => onImageRemove(index)}>{t("removePicture")}</RoundedButton>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </ImageUploading>
            </Box>
            <RoundedButton
                onClick={HandleAddCruise}
                style={{width: '50%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                color="pink"
            >{t("addCruise")} </RoundedButton>
        </div>
    )
}