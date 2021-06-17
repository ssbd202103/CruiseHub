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
export function getReservationsForCruise(uuid: any){
    const {token} = store.getState()
    const cruiseUUID = uuid
    return axios.get(`reservation/reservations-for-cruise/${cruiseUUID}`, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    })
}

export function getReservationsForWorkerCruise(uuid: any){
    const {token} = store.getState()
    const cruiseUUID = uuid
    return axios.get(`reservation/reservations-for-worker-cruise/${cruiseUUID}`, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    })
}