import store from "../redux/store";
import axios from "../Services/URL"
import {update} from "../redux/slices/tokenSlice";
import {setUser, emptyUser} from "../redux/slices/userSlice";
import jwt_decode from "jwt-decode";

export function getUser(token: string) {
    return axios.get('self/account-details', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).then(res => {
        store.dispatch(update(token))
        store.dispatch(setUser(res.data))
    })
}

export function logOut() {
    store.dispatch(emptyUser())
}

export function updateToken() {
    const decodedToken: any = jwt_decode(store.getState().token)
    let expireTime = new Date(decodedToken.exp * 1000)
    let currentTime = new Date()
    let differenceInSeconds = (expireTime.getTime() - currentTime.getTime()) / 1000

    console.log(differenceInSeconds < 60 * 19 && differenceInSeconds > 0)
    if (differenceInSeconds < 60 * 19 && differenceInSeconds > 0) {
        return axios.post('/api/auth/refresh-token/', {}, {
            headers: {
                'Authorization': `Bearer ${store.getState().token}`
            }
        }).then(res => {
            store.dispatch(update(res.data))
        }).catch(error => {
            logOut()
        })
    }
}

// export function ref