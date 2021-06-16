import store from "../redux/store";
import axios from './URL';

export function createRating(rating: number, cruiseGroupUUID: string) {
    const {
        user: {
            login,
            etag
        },
        token
    } = store.getState()

    return axios.post('/ratings/create', {
        login: login,
        cruiseGroupUUID: cruiseGroupUUID,
        rating: rating
    }, {
        headers: {
            "If-Match": etag,
            "Authorization": `Bearer ${token}`
        }
    })
}