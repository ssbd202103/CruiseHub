import store from "../redux/store";
import axios from "axios";
import {update} from "../redux/slices/tokenSlice";
import {setUser, emptyUser} from "../redux/slices/userSlice";
import {IUserSliceState} from "../redux/slices/userSlice";

export function getUser(token: string) {
    return axios.get('/api/self/account-details', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).then(res => {
        store.dispatch(update(token))
        store.dispatch(setUser(res.data))

        saveUser(res.data as IUserSliceState, token)
    })
}

export function getSavedUser() {
    const savedToken = sessionStorage.getItem('cruisehub_token') as string;
    const savedUser = sessionStorage.getItem('cruisehub_user');

    if (savedToken && savedUser) {
        store.dispatch(update(savedToken))
        store.dispatch(setUser(JSON.parse(savedUser) as IUserSliceState))
    }
}

export function logOut() {
    store.dispatch(emptyUser())
    sessionStorage.clear()
}

export function saveUser(user: IUserSliceState, token: string | null = null) {
    sessionStorage.setItem('cruisehub_token', token || store.getState().token)
    sessionStorage.setItem('cruisehub_user', JSON.stringify(user))
}