import store from "../redux/store";
import axios from "./URL";

export function getOwnRatings() {
    const {token} = store.getState()

    return axios.get('self/ratings', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
}