import axios from "./URL";
import store from "../redux/store";

export function createReservation(cruiseVersion: number, uuid: string, numberOfSeats: number) {

    const {token} = store.getState()
    const json = {
        cruiseVersion: cruiseVersion,
        cruiseUuid: uuid,
        numberOfSeats: numberOfSeats,
    }
    return axios.post('cruise/reserve', json, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
}