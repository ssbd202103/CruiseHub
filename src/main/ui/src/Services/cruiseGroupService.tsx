import store from "../redux/store";
import axios from "./URL";

export function getAllCruiseGroup() {
    const {token} = store.getState()

    return axios.get('cruiseGroup/cruise-groups', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
}