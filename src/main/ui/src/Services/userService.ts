import store from "../redux/store";
import axios from "../Services/URL"
import {clearToken, setToken} from "../redux/slices/tokenSlice";
import {emptyUser, IUserSliceState, setUser} from "../redux/slices/userSlice";
import jwt_decode from "jwt-decode";

export function getUser(token: string) {
    return axios.get('self/account-details', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).then(res => {
        store.dispatch(setToken(token))
        store.dispatch(setUser(res.data))

        saveUser(res.data as IUserSliceState, token)
    })
}

export function getSavedUser() {
    const savedToken = sessionStorage.getItem('cruisehub_token') as string;
    const savedUser = sessionStorage.getItem('cruisehub_user');

    if (savedToken && savedUser) {
        store.dispatch(setToken(savedToken))
        store.dispatch(setUser(JSON.parse(savedUser) as IUserSliceState))
    }
}

export function logOut() {
    // store.dispatch(emptyUser())
    // store.dispatch(clearToken())
    sessionStorage.clear()
    document.location.replace('/')
}

export function saveUser(user: IUserSliceState, token: string | null = null) {
    sessionStorage.setItem('cruisehub_token', token || store.getState().token)
    sessionStorage.setItem('cruisehub_user', JSON.stringify(user))
}

export function refreshToken() {
    const decodedToken: any = jwt_decode(store.getState().token)
    let expireTime = new Date(decodedToken.exp * 1000)
    let currentTime = new Date()
    let differenceInSeconds = (expireTime.getTime() - currentTime.getTime()) / 1000

    if (differenceInSeconds < 60 && differenceInSeconds > 0) {
        return axios.post('auth/refresh-token/', {}, {
            headers: {
                'Authorization': `Bearer ${store.getState().token}`
            }
        }).then(res => {
            store.dispatch(setToken(res.data))
        }).catch(error => {
            logOut()
        })
    }
}

// export function ref