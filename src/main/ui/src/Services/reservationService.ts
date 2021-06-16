import axios from "./URL";
import store from "../redux/store";

export function createReservation() {
    console.log("12345")
    const {token} = store.getState()
    const json = {
        cruiseVersion: 0,
        cruiseUuid: "581d626f-d421-47dd-89ef-b41bc30aa36c",
        numberOfSeats: 1,
    }
    return axios.put('cruise/reserve', json, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
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