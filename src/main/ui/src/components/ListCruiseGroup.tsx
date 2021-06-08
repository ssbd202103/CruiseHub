import styles from "../styles/Home.module.css";
import CruiseGroupCardCard from "./CruiseCard";
import CruiseGroupCard from "./CruiseGroupCard";
import {useEffect, useState} from "react";
import useHandleError from "../errorHandler";
import {useSelector} from "react-redux";
import {selectDarkMode} from "../redux/slices/userSlice";
import {getAllCompanies} from "../Services/companiesService";
import {refreshToken} from "../Services/userService";
import {getAllCruiseGroup} from "../Services/cruiseGroupService";
import {ImageListType} from "react-images-uploading";

export function createCruiseGroup(
    price: any,
    start_time: any,
    end_time: any,
    numberOfSeats: any,
    name: any,
    cruisePictures:ImageListType
){
    return {
        price:price,
        start_time: start_time,
        end_time: end_time,
        numberOfSeats: numberOfSeats,
        name: name,
        cruisePictures:cruisePictures
    };
}
export default function ListCruiseGroup(){
    const [cruiseGroup, setCruiseGroup] = useState([]);
    const [searchInput, setSearchInput] = useState("");

    const handleError = useHandleError()

    const darkMode = useSelector(selectDarkMode)

    useEffect(() => {
        getAllCruiseGroup().then(res => {
            setCruiseGroup(res.data)
        }).catch(error => {
            const message = error.response.data
            handleError(message, error.response.status)
        }).then(res => {
            refreshToken()
        });
    }, []);

    return(
        <div className={styles['cruises-grid']}>
            {cruiseGroup.map((cruise, item) =>
                <CruiseGroupCard key={item} group={cruise}/>)}
        </div>
    )
}