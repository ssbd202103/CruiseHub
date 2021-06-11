import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {Box} from "@material-ui/core";
import {getCruiseByUUID} from "../Services/cruisesService";


export default function Cruise() {
    const { id } = useParams<{id: string}>();

    const [cruise, setCruise] = useState<any>(null)

    useEffect(() => {
        getCruiseByUUID(id).then(res => {
            setCruise(res.data)
            console.log(res.data)
        })
    }, [])

    console.log(id)

    return (
        <Box>
            ID: {id}
        </Box>
    )
}